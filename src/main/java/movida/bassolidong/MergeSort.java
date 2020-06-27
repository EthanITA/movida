package movida.bassolidong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import movida.bassolidong.ArrayUtils;

public class MergeSort {
    private Integer[] toSortArray;
    private Integer[] sortedArray;
    private Integer[] toSortIndexes;
    private Integer[] sortedIndexes;
    private ArrayUtils arrayUtils;

    private Integer[] listOfIntegerToArray(List<Integer> l) {
        Integer[] result = new Integer[l.size()];
        return l.toArray(result);
    }

    public MergeSort(Integer[] array) {
        toSortArray = array;
        toSortIndexes = new Integer[toSortArray.length];

        sortedArray = new Integer[toSortArray.length];
        sortedIndexes = new Integer[toSortArray.length];

        for (int i = 0; i < toSortArray.length; i++) {
            toSortIndexes[i] = i;
        }
        sort();
        arrayUtils = new ArrayUtils();
        sortedIndexes = arrayUtils.sortIndex(sortedArray, toSortArray);
    }

    private void sort() {
        sortedArray = mergeSort(toSortArray);

    }

    private Integer[] mergeSort(Integer[] Array) {
        if (Array.length <= 1) {
            return Array;
        } else {
            int m = Array.length / 2;
            Integer[] left = Arrays.copyOfRange(Array, 0, m);
            Integer[] right = Arrays.copyOfRange(Array, m, Array.length);
            return merge(mergeSort(left), mergeSort(right));
        }
    }

    private Integer[] merge(Integer[] left, Integer[] right) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        boolean isMerged = false;
        while (!isMerged) {

            // casi basi
            if (i >= left.length && j >= right.length) {
                isMerged = true;
            } else if (left[i] <= right[j]) {
                merged.add(left[i]);
                i += 1;

            } else {
                merged.add(right[j]);
                j += 1;
            }

            // caso quando uno dei due indici arriva alla fine
            if (i >= left.length) {
                for (; j < right.length; j++) {
                    merged.add(right[j]);
                }
            } else if (j >= right.length) {
                for (; i < left.length; i++) {
                    merged.add(left[i]);
                }
            }

        }
        return listOfIntegerToArray(merged);
    }

    public Integer[] getSortedArray() {
        return sortedArray;
    }

    public Integer[] getReverseSortedArray() {
        return arrayUtils.reverse(sortedArray);
    }

    public Integer[] getSortedIndexes() {
        return sortedIndexes;
    }

    public Integer[] getReverseSortedIndexes() {
        return arrayUtils.reverse(sortedIndexes);
    }

    public static void main(String[] args) {
        MergeSort s = new MergeSort(new Integer[] { 5, 2, 4, 6, 9, 2 });
        s.sort();
        for (Integer i : s.getReverseSortedIndexes()) {
            System.out.println(i);
        }
    }
}