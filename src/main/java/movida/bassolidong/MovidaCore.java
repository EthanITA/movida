package movida.bassolidong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import movida.bassolidong.HashIndirizzamentoAperto.HashTable;
import movida.bassolidong.custom_classes.ArrayOutOfSizeException;
import movida.bassolidong.custom_classes.LambdaExpressions;
import movida.commons.Collaboration;
import movida.commons.IMovidaCollaborations;
import movida.commons.IMovidaConfig;
import movida.commons.IMovidaDB;
import movida.commons.IMovidaSearch;
import movida.commons.MapImplementation;
import movida.commons.MovidaFileException;
import movida.commons.Movie;
import movida.commons.Person;
import movida.commons.SortingAlgorithm;

/**
 * Hello world!
 */
public final class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {

    // Le due variabili rappresentano il tipo di algoritmo da utilizzare

    // BubbleSort e MergeSort
    private SortingAlgorithm algorithm;
    // AVL e HashIndirizzamentoAperto
    private MapImplementation dataStructure = MapImplementation.HashIndirizzamentoAperto;

    private AVLTree avl;
    // hash ha come chiavi Title
    private HashIndirizzamentoAperto hash;

    /**
     * Inizializza la hash table e AVL
     * 
     * @param size dimensione dell'array di hash table
     */
    public MovidaCore(int size) {
        avl = new AVLTree();
        hash = new HashIndirizzamentoAperto(size);
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        MovidaCore mc = new MovidaCore(10);
        mc.loadFromFile(
                new File("/home/marco/Documents/uni/alg/MOVIDA/src/main/java/movida/commons/esempio-formato-dati.txt"));
        System.out.println(mc.getAllPeople().length);
        for (Integer i : mc.getActorsOccurence(mc.getAllPeople())) {

            System.out.print(i + " ");
        }
        System.out.println(mc.getAllPeople());
        mc.clear();
        System.out.println(mc.getAllMovies().length);

    }

    /* funzioni ausiliarie */

    /**
     * Controlla se l'algoritmo di sorting è assegnato al nostro gruppo
     * 
     * @param alg l'algoritmo da controllare
     * @return true/false se è assegnato/non assegnato
     */
    public boolean isSort(SortingAlgorithm alg) {
        return alg == SortingAlgorithm.BubbleSort || alg == SortingAlgorithm.MergeSort;
    }

    /**
     * Controlla se la struttura dati è assegnato al nostro gruppo
     * 
     * @param alg struttura dati da controllare
     * @return true/false se è assegnato/non assegnato
     */
    public boolean isMap(MapImplementation data) {
        return data == MapImplementation.AVL || data == MapImplementation.HashIndirizzamentoAperto;
    }

    /**
     * Fa la stessa cosa di Pattern.matches()
     * 
     * @param reg   regex
     * @param input test
     * @return se reg matcha input
     */
    private boolean regtest(String reg, String input) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(input);
        return m.find();
    }

    /**
     * Controlla se ogni entry del movidafile è corretto, per esempio l'ordine tra
     * titolo e l'anno
     * 
     * @param title
     * @param year
     * @param director
     * @param cast
     * @param votes
     * @return per ogni param controlla se il suo rispettivo regex matcha
     */
    private boolean validMovidaMovie(String title, String year, String director, String cast, String votes) {
        // I SPAZI DIETRO I NUMERI, I DOPPI SPAZI E I TAB SONO UNA COSA TROPPO
        // PERVERSA!!!!!
        class MovidaRegex {
            String title = "Title:\\s+([A-Z0-9]+[a-z0-9]*(\\s)*)+(\\s)*";
            String year = "Year:\\s+[0-9]+(\\s)*";
            String director = "Director:\\s+([A-Z]+[a-z]*(\\s)*)+(\\s)*";
            String cast = "Cast:\\s+(([A-Z]+[a-z]*(\\s)*)+(,\\s)*)+(\\s)*";
            String votes = "Votes:\\s+[0-9]+(\\s)*";
        }
        MovidaRegex reg = new MovidaRegex();
        return regtest(reg.title, title) && regtest(reg.year, year) && regtest(reg.director, director)
                && regtest(reg.cast, cast) && regtest(reg.votes, votes);
    }

    /**
     * Dato la stringa raw di cast: nome1, nome2, ...; converte i nomi in lista di
     * Person
     * 
     * @param s
     * @return List<Person>
     */
    private List<Person> stringToListPersons(String s) {
        List<Person> result = new ArrayList<>();
        boolean skipSpace = false;
        StringBuilder personName = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == ',') {
                skipSpace = true;
                result.add(new Person(personName.toString()));
                personName = new StringBuilder();
            } else {
                if (skipSpace) {
                    skipSpace = false;
                } else {
                    personName.append(c);
                }
            }
        }
        result.add(new Person(personName.toString()));
        return result;
    }

    /**
     * Dato la stringa raw di cast: nome1, nome2, ..; converte i nomi in array di
     * Person
     * 
     * @param s
     * @return Person[]
     */
    private Person[] stringToPersons(String s) {
        List<Person> persons = stringToListPersons(s);
        Person[] result = new Person[persons.size()];
        return persons.toArray(result);

    }

    /**
     * Converte la stringa raw di movie: Title: ... \nYear: ... \n...; lo converte
     * in tipo Movie
     * 
     * @param m stringa Movie da convertire
     * @return Movie convertito
     */
    private Movie stringToMovie(String[] m) {
        String[] replace = { "Title: ", "Year: ", "Director: ", "Cast: ", "Votes: " };
        for (int i = 0; i < replace.length; i++) {
            m[i] = m[i].replaceAll("\t", " ");
            m[i] = m[i].replace(replace[i], "");
            m[i] = m[i].trim();
        }

        return new Movie(m[0], Integer.parseInt(m[1]), Integer.parseInt(m[4]), stringToPersons(m[3]), new Person(m[2]));
    }

    /**
     * Controlla se c'è da utilizzare AVL o HashIndirizzamentoAperto
     * 
     * @return
     */
    private boolean isAVL() {
        return dataStructure == MapImplementation.AVL;
    }

    /**
     * Controlla se c'è da utilizzare BubbleSort o MergeSort
     * 
     * @return
     */
    private boolean isBubbleSort() {
        return algorithm == SortingAlgorithm.BubbleSort;
    }

    /**
     * Dato un array di interi, ordina in modo decrescente ritornando gli indici
     * invece del contenuto
     * 
     * @param array
     * @return
     */
    private Integer[] sort(Integer[] array) {
        // TODO restituire gli indici invece degli elementi!!

        Integer[] result = new Integer[array.length];
        if (isBubbleSort()) {
        } else {

        }
        return result;
    }

    /**
     * Tiene i primi n elementi dell'array Movie, gli altri vengono scartati
     * 
     * @param n     elementi da tenere
     * @param array da filtrare
     * @return array filtrato
     */
    private Movie[] firstMovies(Integer n, Movie[] array) {
        Movie[] result = new Movie[n];
        for (int i = 0; i < n; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Dato un array di Person restituisce in formato movida cast: nome1, nome2, ...
     * 
     * @param p
     * @return
     */
    public String personsToMovidaCast(Person[] p) {
        StringBuilder result = new StringBuilder(p[0].getName());
        for (int i = 1; i < p.length; i++) {
            result.append(", " + p[i].getName());
        }
        return result.toString();
    }

    /**
     * Dato un Movie scrive sul file in formato Movida
     * 
     * @param bw = BufferedWriter(OutputStreamWriter(File))
     * @param m
     * @throws IOException
     */
    private void writeMovieToFile(BufferedWriter bw, Movie m) throws IOException {
        bw.write("Title: " + m.getTitle());
        bw.newLine();
        bw.write("Year: " + m.getYear());
        bw.newLine();
        bw.write("Director: " + m.getDirector().getName());
        bw.newLine();
        bw.write("Cast: " + personsToMovidaCast(m.getCast()));
        bw.newLine();
        bw.write("Votes: " + m.getVotes());
    }

    /**
     * Converte List<Movie> in Movie[]
     * 
     * @param l
     * @return
     */
    private Movie[] listOfMoviesToArray(List<Movie> l) {
        Movie[] result = new Movie[l.size()];
        return l.toArray(result);

    }

    /**
     * Converte List<Integer> in Integer[]
     * 
     * @param l
     * @return
     */
    private Integer[] listOfIntegerToArray(List<Integer> l) {
        Integer[] result = new Integer[l.size()];
        return l.toArray(result);
    }

    /**
     * Controlla se un elemento si trova nell'array
     * 
     * @param elem
     * @param array
     * @return
     */
    private boolean elemInArray(Object elem, Object[] array) {
        for (Object object : array) {
            if (object.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dato gli indici, restituisce Movie[] in ordine <code>indexes</code>
     * 
     * @param indexes
     * @return
     */
    private Movie[] sortMoviesWithIndexes(Integer[] indexes) {
        Movie[] allM = getAllMovies();
        Movie[] result = new Movie[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            result[i] = allM[indexes[i]];
        }
        return result;
    }

    /**
     * Conta le occorrenze tra tutti i Movie dato il nome di un attore
     * 
     * @param name
     * @return
     */
    private Integer findActorOccurence(String name) {
        Integer count = 0;
        for (Movie m : getAllMovies()) {
            for (Person p : m.getCast()) {
                if (p.getName().equals(name)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * Conta le occorrenze per ogni attore
     * 
     * @param persons
     * @return array di interi associato all'attore
     */
    private Integer[] getActorsOccurence(Person[] persons) {
        Integer[] occurence = new Integer[persons.length];
        for (int i = 0; i < occurence.length; i++) {
            occurence[i] = findActorOccurence(persons[i].getName());
        }
        return occurence;
    }

    /**
     * Ordina Person[] secondo l'ordine di <code>indexes</code>
     * 
     * @param persons
     * @param indexes
     * @return
     */
    private Person[] sortPersonsWithIndexes(Person[] persons, Integer[] indexes) {
        Person[] result = new Person[persons.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = persons[indexes[i]];
        }
        return result;
    }

    /**
     * Ritorna i primi <code>n</code> elementi di Person[]
     * 
     * @param n
     * @param p
     * @return
     */
    private Person[] firstPersons(Integer n, Person[] p) {
        Person[] result = new Person[n];
        for (int i = 0; i < n; i++) {
            result[i] = p[i];
        }
        return result;
    }

    /////////////////////////////////////////////////////

    /* INIZIO IMovidaDB */

    @Override
    public void loadFromFile(File f) {

        try {
            FileReader fr = new FileReader(f); // read file
            BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream

            String line = "";
            while (line != null) {
                String[] movie = new String[5];
                for (int i = 0; i < 5; i++) {
                    line = br.readLine();
                    movie[i] = line;
                }
                if (!validMovidaMovie(movie[0], movie[1], movie[2], movie[3], movie[4])) {
                    throw new MovidaFileException();
                } else {
                    Movie m = stringToMovie(movie);
                    hash.insert(m.getTitle(), m);
                    // avl.insert(m);
                }
                line = br.readLine();
            }

        } catch (IOException | ArrayOutOfSizeException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void saveToFile(File f) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            Movie[] m = getAllMovies();
            if (m.length > 0) {
                writeMovieToFile(bw, m[0]);
                for (int i = 1; i < m.length; i++) {
                    bw.newLine();
                    bw.newLine();
                    writeMovieToFile(bw, m[i]);
                }

            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clear() {
        avl.makeEmpty();
        hash.makeEmpty();

    }

    @Override
    public int countMovies() {
        if (isAVL()) {
            return avl.size();
        } else {
            return hash.countMovies();
        }
    }

    @Override
    public int countPeople() {
        if (isAVL()) {
            return avl.countPeople();
        } else {
            return hash.getAllPeoplesName().size();
        }
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        avl.delete_by_title(title); // funzione void che elimina il nodo con il titolo in input
        return hash.delete(title);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        if (isAVL()) {
            return avl.search_by_title(title).m;
        } else {
            return hash.get(title).isEmpty() ? null : hash.get(title).get(0);
        }

    }

    @Override
    public Person getPersonByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] getAllMovies() {
        Movie[] m;
        if (isAVL()) {
            // TODO lorenzo
            m = new Movie[0];
        } else {
            HashTable[] h = hash.getHashTable();
            List<Movie> mtemp = new ArrayList<>();

            for (int i = 0; i < h.length; i++) {
                if (h[i].key != null) {
                    mtemp.add(h[i].data.get(0));
                }
            }
            m = listOfMoviesToArray(mtemp);

        }
        return m;
    }

    @Override
    public Person[] getAllPeople() {
        Person[] p;
        if (isAVL()) {
            // TODO lorenzo
            return new Person[0];
        } else {
            if (!hash.getAllPeoplesName().isEmpty()) {
                List<String> peoplesName = new ArrayList<>(hash.getAllPeoplesName());
                p = new Person[peoplesName.size()];
                for (int i = 0; i < peoplesName.size(); i++) {
                    p[i] = new Person(peoplesName.get(i));
                }
                return p;

            } else {
                return new Person[0];
            }
        }
    }

    /* FINE IMovidaDB */

    //////////////////////////////////////////////////////

    /* INIZIO IMovidaCollaborations */
    @Override
    public Person[] getDirectCollaboratorsOf(Person actor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person[] getTeamOf(Person actor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
        // TODO Auto-generated method stub
        return null;
    }
    /* FINE IMovidaCollaborations */

    ///////////////////////////////////////////////////////////////////

    /* INIZIO IMovidaSearch */

    /**
     * Ritorna tutti i Movie che rispettano <code>condition</code>
     * 
     * @param condition funzione anonima (Movie -> boolean)
     * @return
     */
    public Movie[] searchMovies(LambdaExpressions.MovieSearchElem condition) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            if (condition.searchIn(m)) {
                result.add(m);
            }
        }
        return listOfMoviesToArray(result);
    }

    @Override
    public Movie[] searchMoviesByTitle(String title) {

        return searchMovies(m -> m.getTitle().contains(title));
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        return searchMovies(m -> m.getYear().equals(year));
    }

    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        return searchMovies(m -> m.getDirector().getName().equals(name));
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        return searchMovies(m -> elemInArray(name, m.getCast()));
    }

    /**
     * ritorna certi campi di Movie secondo <code>condition</code>
     * 
     * @param condition
     * @return
     */
    public Integer[] getFieldsAsArray(LambdaExpressions.MovieGetIntegerField condition) {
        List<Integer> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            result.add(condition.getField(m));
        }
        return listOfIntegerToArray(result);
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        return firstMovies(N, sortMoviesWithIndexes(sort(getFieldsAsArray(m -> m.getVotes()))));
    }

    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        return firstMovies(N, sortMoviesWithIndexes(sort(getFieldsAsArray(m -> m.getYear()))));
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        Person[] persons = getAllPeople();
        Integer[] indexes = getActorsOccurence(persons);
        return firstPersons(N, sortPersonsWithIndexes(persons, indexes));
    }

    /* FINE IMovidaSearch */

    //////////////////////////////////////////////////////

    /* INIZIO IMovidaConfig */

    @Override
    public boolean setSort(SortingAlgorithm a) {
        SortingAlgorithm old = this.algorithm;
        this.algorithm = isSort(a) ? a : this.algorithm;
        return this.algorithm != old;
    }

    @Override
    public boolean setMap(MapImplementation m) {
        MapImplementation old = this.dataStructure;
        this.dataStructure = isMap(m) ? m : this.dataStructure;
        return this.dataStructure != old;
    }

    /* FINE IMovidaConfig */
}
