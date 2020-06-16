package movida;

import java.io.File;

import movida.commons.Collaboration;
import movida.commons.IMovidaCollaborations;
import movida.commons.IMovidaConfig;
import movida.commons.IMovidaDB;
import movida.commons.MapImplementation;
import movida.commons.Movie;
import movida.commons.Person;
import movida.commons.SortingAlgorithm;

/**
 * Hello world!
 */
public final class MovidaCore implements IMovidaCollaborations, IMovidaConfig, IMovidaDB {
    private MovidaCore() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

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

    @Override
    public boolean setSort(SortingAlgorithm a) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setMap(MapImplementation m) {
        // TODO Auto-generated method stub
        return false;
    }

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
}
