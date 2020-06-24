package movida.bassolidong;

//classe avl node
public class AVLNode {
	
	AVLNode left, right;
	String Title,year,Director, votes;
	int dimcast=20;
	String []cast=new String[dimcast];
	int numcast;
	int height;
	
//costruttori
	AVLNode()
	{
		left=null;
		right=null;
		
		Title=null;
		year=null;
		Director=null;
		numcast=0;
		for(int i=0; i<dimcast; i++) {cast[i]=null;}
		
		
		height=0;	
	}
	
	AVLNode(String Title,String year, String Director, int numcast ,String []cast, String votes){
		
		left=null;
		right=null;
		
		this.Title=Title;
		this.year=year;
		this.Director=Director;
		this.numcast=numcast;
		for(int i=0;i<dimcast;i++) {this.cast[i]=cast[i];}
		
		height=0;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
