package movida.bassolidong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import movida.bassolidong.custom_exceptions.ArrayOutOfSizeException;
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
    private MapImplementation dataStructure;

    private AVLTree avl;
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
        System.out.println(mc.getMovieByTitle("Cape Fear"));
    }

    /* funzioni ausiliarie */
    private void print(Object x) {
        System.out.println(x);
    }

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
    private boolean validMovidaFile(String title, String year, String director, String cast, String votes) {
        // I SPAZI DIETRO I NUMERI, I DOPPI SPAZI E I TAB SONO UNA COSA TROPPO
        // PERVERSA!!!!!
        class movidaRegex {
            String title = "Title:\\s+([A-Z]+[a-z]*(\\s)*)+(\\s)*", year = "Year:\\s+[0-9]+(\\s)*",
                    director = "Director:\\s+([A-Z]+[a-z]*(\\s)*)+(\\s)*",
                    cast = "Cast:\\s+(([A-Z]+[a-z]*(\\s)*)+(,\\s)*)+(\\s)*", votes = "Votes:\\s+[0-9]+(\\s)*";
        }
        movidaRegex reg = new movidaRegex();
        /*
         * print(regtest(reg.title, title) + "\t" + title); print(regtest(reg.year,
         * year) + "\t" + year); print(regtest(reg.director, director) + "\t" +
         * director); print(regtest(reg.cast, cast) + "\t" + cast);
         * print(regtest(reg.votes, votes) + "\t" + votes);
         */
        return regtest(reg.title, title) && regtest(reg.year, year) && regtest(reg.director, director)
                && regtest(reg.cast, cast) && regtest(reg.votes, votes);
    }

    private List<Person> stringToListPersons(String s) {
        List<Person> result = new ArrayList<>();
        boolean skip_space = false;
        String person_name = "";
        for (char c : s.toCharArray()) {
            if (c == ',') {
                skip_space = true;
                result.add(new Person(person_name));
                person_name = "";
            } else {
                if (skip_space) {
                    skip_space = false;
                } else {
                    person_name += c;
                }
            }
        }
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

    /////////////////////////////////////////////////////

    /* INIZIO IMovidaDB */

    @Override
    public void loadFromFile(File f) {
        // TODO Auto-generated method stub

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
                if (!validMovidaFile(movie[0], movie[1], movie[2], movie[3], movie[4])) {
                    throw new MovidaFileException();
                } else {
                    Movie m = stringToMovie(movie);
                    hash.insert(m.getTitle(), m);
                    // avl.insert(m);
                }
                line = br.readLine();
                // System.out.println(movie);
            }
            fr.close();

        } catch (IOException | ArrayOutOfSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(File f) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        avl.makeEmpty();
        //marco, da implementare
        
    }

    @Override
    public int countMovies() {
        /*if(isAVL())*/ { return avl.size();}
    	//else
    }

    @Override
    public int countPeople() {
        if(isAVL()) {
        	return avl.countPeople();
        }
        return 0;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        avl.delete_by_title(title); //funzione void che elimina il nodo con il titolo in input
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
        // TODO Auto-generated method stub
        return null;
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
    @Override
    public Movie[] searchMoviesByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        // TODO Auto-generated method stub
        return null;
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
