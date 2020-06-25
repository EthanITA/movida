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

    public MovidaCore(int avl_size, int hash_size) {
        avl = new AVLTree();
        hash = new HashIndirizzamentoAperto(hash_size);
    }

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
        System.out.println(mc.countMovies());

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

    private Person[] stringToPersons(String s) {
        List<Person> persons = stringToListPersons(s);
        Person[] result = new Person[persons.size()];
        return persons.toArray(result);

    }

    private Movie stringToMovie(String[] m) {
        String[] replace = { "Title: ", "Year: ", "Director: ", "Cast: ", "Votes: " };
        for (int i = 0; i < replace.length; i++) {
            m[i] = m[i].replaceAll("\t", " ");
            m[i] = m[i].replace(replace[i], "");
            m[i] = m[i].trim();
        }

        return new Movie(m[0], Integer.parseInt(m[1]), Integer.parseInt(m[4]), stringToPersons(m[3]), new Person(m[2]));
    }

    private boolean isAVL() {
        return dataStructure == MapImplementation.AVL;
    }

    private boolean isBubbleSort() {
        return algorithm == SortingAlgorithm.BubbleSort;
    }

    private Integer[] sort(Integer[] array) {
        // TODO restituire gli indici invece degli elementi!!

        Integer[] result = new Integer[array.length];
        if (isBubbleSort()) {
        } else {

        }
        return result;
    }

    private Movie[] first(Integer n, Movie[] array) {
        Movie[] result = new Movie[n];
        for (int i = 0; i < n; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public String personsToMovidaCast(Person[] p) {
        StringBuilder result = new StringBuilder(p[0].getName());
        for (int i = 1; i < p.length; i++) {
            result.append(", " + p[i].getName());
        }
        return result.toString();
    }

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

    private Movie[] listOfMoviesToArray(List<Movie> l) {
        Movie[] result = new Movie[l.size()];
        return l.toArray(result);

    }

    private Integer[] listOfIntegerToArray(List<Integer> l) {
        Integer[] result = new Integer[l.size()];
        return l.toArray(result);
    }

    private boolean elemInArray(Object elem, Object[] array) {
        for (Object object : array) {
            if (object.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    private Movie[] sortMoviesWithIndexes(Integer[] indexes) {
        Movie[] allM = getAllMovies();
        Movie[] result = new Movie[indexes.length];
        // se getAllMovies è null non è un problema perchè indexes avrà length 0
        for (int i = 0; i < indexes.length; i++) {
            result[i] = allM[indexes[i]];
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
                    avl.insert(m);
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
            if (m != null) {
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
        /* if(isAVL()) */ {
            return avl.size();
        }
        // else
    }

    @Override
    public int countPeople() {
        if (isAVL()) {
            return avl.countPeople();
        }
        return 0;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        avl.delete_by_title(title); // funzione void che elimina il nodo con il titolo in input
        return false;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return avl.search_by_title(title).m;

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
            m = null;
        } else {
            HashTable[] h = hash.getHashTable();
            m = new Movie[h.length];
            for (int i = 0; i < m.length; i++) {
                m[i] = h[i].data.get(0);
            }
        }
        return m;
    }

    @Override
    public Person[] getAllPeople() {
        // TODO Auto-generated method stub
        return null;
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

    public Integer[] getFieldsAsArray(LambdaExpressions.MovieGetIntegerField condition) {
        List<Integer> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            result.add(condition.getField(m));
        }
        return listOfIntegerToArray(result);
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        return first(N, sortMoviesWithIndexes(sort(getFieldsAsArray(m -> m.getVotes()))));
    }

    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        return first(N, sortMoviesWithIndexes(sort(getFieldsAsArray(m -> m.getYear()))));
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        // TODO Auto-generated method stub
        return null;
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
