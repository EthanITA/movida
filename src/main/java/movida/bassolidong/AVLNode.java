package movida.bassolidong;

import movida.commons.Movie;

//classe avl node
public class AVLNode {
	
	AVLNode left, right;
	Movie m;
	int height, balancefactor;
	
//costruttori
	AVLNode()
	{
		left=null;
		right=null;
		
		m=null;
		
		height=0;
		balancefactor=0;
	}
	
	AVLNode(Movie m){
		
		left=null;
		right=null;
		
		this.m=m;
		
		height=0;
		balancefactor=0;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
