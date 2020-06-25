package movida.bassolidong;

import movida.commons.Movie;

//classe avl node
public class AVLNode {
	
	Movie m;
	int height;
	
	AVLNode left, right;
	
	
	
//costruttori
	AVLNode()
	{
		left=null;
		right=null;
		
		m=null;
		
		height=0;
		
	}
	
	AVLNode(Movie m){
		
		left=null;
		right=null;
		
		this.m=m;
		
		height=1;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
