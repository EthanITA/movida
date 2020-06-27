package movida.bassolidong;

public class BubbleSort {
	
	
	
	//bubble sort
		public static void bubbleSort(int A[]) {
			
			for (int i = 1; i < A.length; i++) {
				
			boolean scambi = false;
			
			for (int j = 1; j <= A.length - i; j++) 
			{
				if (A[j - 1] > A[j] ) 
				{
					int temp = A[j - 1];
					A[j - 1] = A[j];
					A[j] = temp;
					scambi = true;
				}
			}
			
			if (!scambi) break;
		}
		
	}
	
}