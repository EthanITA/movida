package movida.bassolidong;
import java.io.*;
public class AvlTest {

	public static void main(String[] args) throws Exception {
		
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
        /* Creating object of AVLTree */
        AVLTree_Title avlt = new AVLTree_Title();
        char ch;
        
        do {
        System.out.println(" 1 Inserisci Nodo ");
        System.out.println(" 2 Cerca Nodo");
        System.out.println(" 3 Conta Nodi");
        
        int choice=Integer.parseInt(tastiera.readLine());
        
        switch (choice)
        {
        case 1 : 
        	String Title ,Year, Director, votes;
        	int numcast;
        	
            System.out.println("Inserisci titolo film ");
            Title=tastiera.readLine();
            
            System.out.println("Inserisci anno film");
            Year=tastiera.readLine();
            
            System.out.println("Inserisci chi ha diretto il film ");
            Director=tastiera.readLine();
            
            System.out.println("Inserisci numero di persone che compongono il cast");
            numcast= Integer.parseInt(tastiera.readLine()); 
            
            int dimcast=20;
            String [] cast= new String[dimcast];
            
            for(int i=0;i<numcast; i++) {
            System.out.println("Inserisci un attore del cast");
            cast[i]=tastiera.readLine();
            }  
            
            System.out.println("Inserisci votazioni del film ");
            votes=tastiera.readLine();
            
            avlt.insert(Title, Year, Director, numcast, cast, votes);
            break;  
            
        case 2 : 
        	
            System.out.println("Inserisci Film da cercare");
            Title=tastiera.readLine();
            if( avlt.search(Title)) {
            	System.out.println("Il titolo è presente");
            }
            else {
            	System.out.println("Non è stato trovato nessun film con questo titolo");
            }
            break; 
            
        case 3 : 
        	int nodi=avlt.countNodes();
            System.out.println("Nodi = "+nodi);
            break; 
        
        default : 
            System.out.println("Wrong Entry \n ");
            break;   
        }
        
        System.out.println("\nVuoi continuare? (digita s per continuare) \n");
        ch = tastiera.readLine().charAt(0);
        }while(ch == 'S' || ch == 's'); 
        
        
        
        

	}

}
