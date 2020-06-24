package movida.bassolidong;
import java.io.*;

import movida.commons.Movie;
import movida.commons.Person;
public class AvlTest {

	public static void main(String[] args) throws Exception {
		
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
        /* Creating object of AVLTree */
        AVLTree avlt = new AVLTree();
        Person [] cast= {new Person("Lorenzo")};
        
        Movie m=new Movie("Inception",2010,20,cast,new Person("Lorenzo"));
        
        char ch;
        
        do {
        System.out.println(" 1 Inserisci Nodo ");
        System.out.println(" 2 Cerca Nodo");
        System.out.println(" 3 Conta Nodi");
        
        int choice=Integer.parseInt(tastiera.readLine());
        
        switch (choice)
        {
        case 1 : 
        	
            avlt.insert(m);
            break;  
            
        case 2 : 
        	String Title;
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
