package movida.bassolidong;

public class BubbleSort {

	// bubble sort
	public Integer[] bubbleSort(Integer[] B) {
		Integer[] A = new Integer[B.length];
		for (int i = 0; i < A.length; i++) {
			A[i] = B[i];
		}
		for (int i = 1; i < A.length; i++) {

			boolean scambi = false;

			for (int j = 1; j <= A.length - i; j++) {
				if (A[j - 1] > A[j]) {
					Integer temp = A[j - 1];
					A[j - 1] = A[j];
					A[j] = temp;
					scambi = true;
				}
			}

			if (!scambi)
				break;
		}
		return A;

	}

}