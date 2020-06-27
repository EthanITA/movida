package movida.bassolidong;

public class ArrayUtils {

    /**
     * Dato n e un array, trova n in array e restituisce l'indice
     * 
     * @param n
     * @param array
     * @return l'indice di n in array, altrimenti -1
     */
    public Integer findIndex(Integer n, Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            if (n.equals(array[i]))
                return i;
        }
        return -1;
    }

    /**
     * preso n e un array, trova n in array e lo sostituisce con -1
     * 
     * n = 1999
     * 
     * array = [..., 13232, 1999, 2002]
     * 
     * result = [..., 13232, -1, 2002]
     * 
     * @param n
     * @param array
     * @return result
     */
    public Integer[] remove(Integer n, Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            if (n.equals(array[i])) {
                array[i] = -1;
                return array;
            }
        }
        return array;
    }

    /**
     * Dato un array1 e un array2 permutato da array1, restituisce un array di
     * indici associato ad array2
     * 
     * array1 = [6, 4, 3, 9] array2 = [3, 4, 6, 9]
     * 
     * indici1 = [0, 1, 2, 3] indici2 = [2, 1, 0, 3]
     * 
     * @param sortedArray array permutato
     * @param toSortArray array originale
     * @return indici2
     */
    public Integer[] sortIndex(Integer[] sortedArray, Integer[] toSortArray) {
        Integer[] result = new Integer[sortedArray.length];
        Integer[] tempToSortArray = toSortArray;
        for (int i = 0; i < sortedArray.length; i++) {
            result[i] = findIndex(sortedArray[i], tempToSortArray);
            tempToSortArray = remove(sortedArray[i], tempToSortArray);
        }
        return result;
    }
}
