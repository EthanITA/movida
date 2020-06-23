package movida.bassolidong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import movida.bassolidong.custom_exception.ArrayOutOfSizeException;
import movida.commons.Movie;
import movida.commons.Person;

public class HashIndirizzamentoAperto {

    int m;
    int count = 0;
    Hash[] record;

    class Hash {
        String key;
        List<Movie> data;

        Hash() {
            data = new ArrayList<>();
        }
    }

    /**
     * Costruttore
     * 
     * @param size dimensione dell'array di chiavi
     */
    public HashIndirizzamentoAperto(int size) {
        this.m = size;
        record = new Hash[size];
        for (int i = 0; i < size; i++) {
            record[i] = new Hash();
        }

    }

    /**
     * Funzione di hash utilizzando java.lang.String.hashCode()
     * 
     * @param k chiave
     * @return indice compreso tra 0 a m-1
     */
    private int hash(String k) {
        return (k.hashCode() & 0xfffffff) % this.m;
    }

    /**
     * Funzione che cerca un nuovo indice se avviene una collisione
     * 
     * @param k chiave
     * @param i passo i-esimo della mia iterazione
     * @return nuovo indice
     */
    private int scansione(String k, int i) {

        return (hash(k) + i) % m;
    }

    private void add(String key, Movie data, int i) {
        record[i].key = key;
        record[i].data.add(data);
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
        int index = hash(key);
        if (record[index].key == null || record[index].key.equals(key)) {
            add(key, data, index);
        } else {
            int i = 0;
            index = scansione(key, i);
            while (!(record[index].key == null || record[index].key.equals(key))) {
                i += 1;
                index = scansione(key, i);
                if (i >= m) {

                    throw new ArrayOutOfSizeException("\n\t" + "Spazio esaurito!");

                }
            }
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
        h.insert("titolo11112", new Movie("titolo", 1999, 13232, p, p1));
        h.insert("titolo12", new Movie("titolo", 1999, 13232, p, p1));

        for (int i = 0; i < 10; i++) {
            System.out.print(h.record[i].key + "\t");
            System.out.println(h.record[i].data);
        }
    }

}