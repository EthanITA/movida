package movida.bassolidong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import movida.bassolidong.custom_classes.ArrayOutOfSizeException;
import movida.commons.Movie;
import movida.commons.Person;

public class HashIndirizzamentoAperto {

    private int m;
    private HashTable[] record;

    class HashTable {
        String key;
        List<Movie> data;

        HashTable() {
            data = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "key: " + key + "\t" + "movies: " + data;
        }
    }

    /**
     * Costruttore
     * 
     * @param size dimensione dell'array di chiavi
     */
    public HashIndirizzamentoAperto(int size) {
        this.m = size;
        record = new HashTable[size];
        for (int i = 0; i < size; i++) {
            record[i] = new HashTable();
        }

    }

    public int countMovies() {
        int count = 0;
        for (HashTable hashTable : record) {
            if (hashTable.key != null) {
                count += 1;
            }
        }
        return count;
    }

    public Set<String> getAllPeoplesName() {
        Set<String> setOfPeoplesName = new HashSet<>();
        for (HashTable h : record) {
            if (h.key != null) {
                for (Person person : h.data.get(0).getCast()) {
                    setOfPeoplesName.add(person.getName());
                }
                setOfPeoplesName.add(h.data.get(0).getDirector().getName());
            }
        }
        return setOfPeoplesName;
    }

    public HashTable[] getHashTable() {
        return record;
    }

    public void makeEmpty() {
        for (int i = 0; i < m; i++) {
            record[i] = new HashTable();
        }
    }

    /**
     * Funzione di hash utilizzando java.lang.String.hashCode()
     * 
     * @param k chiave
     * @return indice compreso tra 0 a m-1
     */
    protected int fHash(String k) {
        return (k.hashCode() & 0xfffffff) % this.m;
    }

    /**
     * Funzione che cerca un nuovo indice se avviene una collisione
     * 
     * @param k chiave
     * @param i passo i-esimo della mia iterazione
     * @return nuovo indice
     */
    protected int fScansione(String k, int i) {

        return (fHash(k) + i) % m;
    }

    private void add(String key, Movie data, int i) {
        record[i].key = key;
        record[i].data.add(data);
    }

    private boolean find(int index, String key) {
        return record[index].key == null || record[index].key.equals(key);
    }

    protected int indexOf(String key) {
        int index = fHash(key);
        if (find(index, key)) {
            return index;
        } else {
            int i = 0;
            index = fScansione(key, i);
            while (!find(index, key)) {
                i += 1;
                index = fScansione(key, i);
                if (i >= m) {
                    return -1;
                }
            }
            return index;
        }
    }

    public boolean delete(String key) {
        int index = indexOf(key);
        if (index != -1) {
            record[index] = new HashTable();
        }
        return index != -1;
    }

    /**
     * Cerca la lista di Movie
     * 
     * @param key chiave per cercare
     * @return lista di movie, se non è presente ritorna lista vuota
     */
    public List<Movie> get(String key) {
        int index = indexOf(key);
        return index != -1 ? record[index].data : new ArrayList<>();
    }

    /**
     * Inserisce un Movie nella mia struttura, se è presente la chiave/non è
     * presente aggiunge un nuovo campo, altrimenti cerca un nuovo posto.
     * 
     * @param key  chiave
     * @param data Movie
     * @throws ArrayOutOfSizeException lancia un'eccezione se lo spazio è esaurito
     */
    public void insert(String key, Movie data) throws ArrayOutOfSizeException {
        int index = indexOf(key);
        if (index == -1) {
            throw new ArrayOutOfSizeException("\n\t" + "Spazio esaurito: " + m + "!!");
        } else {
            add(key, data, index);
        }
    }

    public static void main(String[] args) throws ArrayOutOfSizeException {
        HashIndirizzamentoAperto h = new HashIndirizzamentoAperto(10);
        Person p1 = new Person("paolo");
        Person[] p = { new Person("giacomo") };
        h.insert("titolo", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo2", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo2", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo3", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo4", new Movie("titolo", 1999, 13232, p, p1));

        h.insert("titolo5", new Movie("titolo", 1999, 13232, p, p1));

        h.insert("titolo28", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo22", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo112", new Movie("titolo", 1999, 13232, p, p1));

        h.insert("titolo27", new Movie("titolo", 1999, 13232, p, p1));

        h.insert("titolo2", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo2", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo12", new Movie("titolo", 1999, 13232, p, p1));

        System.out.println(h.get("titolo2"));
        h.delete("titolo2");
        System.out.println(h.get("titolo2"));

    }

    @Override
    public String toString() {
        return "HashIndirizzamentoAperto [m=" + m + ", record=" + Arrays.toString(record) + "]";
    }

}