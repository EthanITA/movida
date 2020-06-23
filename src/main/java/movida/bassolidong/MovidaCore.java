package movida;

import java.io.File;

import movida.commons.Collaboration;
import movida.commons.IMovidaCollaborations;
import movida.commons.IMovidaConfig;
import movida.commons.IMovidaDB;
import movida.commons.IMovidaSearch;
import movida.commons.MapImplementation;
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

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        MovidaCore mc = new MovidaCore();
        mc.print(mc.setSort(SortingAlgorithm.InsertionSort));
        mc.print(mc.setSort(SortingAlgorithm.BubbleSort));
        mc.print(mc.setSort(SortingAlgorithm.BubbleSort));
        mc.print(mc.setSort(SortingAlgorithm.MergeSort));
        mc.print(mc.setSort(SortingAlgorithm.SelectionSort));
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

    /////////////////////////////////////////////////////

    /* INIZIO IMovidaDB */

    @Override
    public void loadFromFile(File f) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveToFile(File f) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public int countMovies() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int countPeople() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
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
