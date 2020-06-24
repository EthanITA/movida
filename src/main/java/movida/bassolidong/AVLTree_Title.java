package movida.bassolidong;


public class AVLTree_Title extends AVLNode{

	public AVLNode root;
	
	//costruttore
	public AVLTree_Title() {
		root=null;
	}
	
	//controllo se l'albero è vuoto oppure no
	public boolean isEmpty() {
		return root==null;
	}
	
	//traforma l'albero in un albero vuoto
	public void makeEmpty() {
		root=null;
	}
	
	//ritorna l'altezza del nodo
	public int height(AVLNode t) {
		return t==null ? -1: t.height;
	}
	
	public void insert(String Title,String Year, String Director, int numcast, String []cast, String votes)
    {
        root = insert(Title,Year,Director,numcast,cast,votes, root);
    }
	
	public AVLNode insert (String Title,String Year, String Director, int numcast, String []cast, String votes, AVLNode t) 
	{
		if(t == null) 
		{
			t=new AVLNode(Title,Year,Director,numcast ,cast,votes);
		}
		
		else if(Title.compareTo(t.Title) < 0 ) 
		{
			t.left=insert(Title,Year,Director,numcast,cast,votes,t.left);
				
			if(height(t.left) - height (t.right) ==2) 
			{
			if(Title.compareTo(t.left.Title) < 0 ) 
			{	t=rotateWhitLeftChild(t);	}
			else 
			{	t=doubleWithLeftChild(t);	}
			}
			
		}
		
		else if(Title.compareTo(t.Title) > 0) 
		{
			
			t.right= insert(Title,Year,Director,numcast,cast,votes,t.right);
			if(height(t.right) - height (t.left) ==2) 
			{
			if(Title.compareTo(t.right.Title) > 0 ) 
			{	t=rotateWhitRightChild(t);	}
			else 
			{	t=doubleWithRightChild(t);	}
			}
		}
		
		else {/*duplicato...non inserisco niente*/}
			
		t.height=Math.max(height(t.left), height(t.right))+1;
		return t;
		
		
		
	}
	
	
	public AVLNode rotateWhitLeftChild(AVLNode alb1) {
		AVLNode alb2=alb1.left;
		alb1.left=alb2.right;
		alb2.right=alb1;
		
		alb1.height=Math.max(height(alb1.left), height(alb1.right))+1;
		alb2.height=Math.max(height(alb2.left), height(alb2.right))+1;
		
		return alb2;
	}
	
	
	public AVLNode rotateWhitRightChild(AVLNode alb1) {
		
		AVLNode alb2=alb1.right;
		alb1.right=alb2.left;
		alb2.left=alb1;
		
		alb1.height=Math.max(height(alb1.left), height(alb1.right))+1;
		alb2.height=Math.max(height(alb2.left), height(alb2.right))+1;
		
		return alb2;
	}
	
	public AVLNode doubleWithLeftChild(AVLNode alb3)
    {
		alb3.left = rotateWhitRightChild( alb3.left );
        return rotateWhitLeftChild( alb3 );
    }
	
	public AVLNode doubleWithRightChild(AVLNode alb3)
    {
		alb3.right = rotateWhitLeftChild( alb3.right );
        return rotateWhitRightChild( alb3 );
    }    
	
	
	public int countNodes() {
		return countNodes(root);
	}
	//conta i nodi
	public int countNodes(AVLNode r) 
	{
		
		if(r == null) 
		{
			return 0;
		}
		else 
		{
			int l=1;
			l = l + countNodes(r.left);
			l = l + countNodes(r.right);
			
			return l;
		}
	}
	
	
	public boolean search(String Title)
    {
        return search(root, Title);
    }
	//cerca un titolo di un film
	public boolean search(AVLNode r, String Title) {
		boolean found=false;
		
		while((r != null) && !found) 
		{
			if(Title.compareTo(r.Title) < 0) 
			{
				r = r.left;
			}
			else if(Title.compareTo(r.Title) > 0) 
			{
				r= r.right;
			}
			else 
			{
				found=true;
				break;
			}
			
			found=search(r,Title);
		}
		
		
		
		return found;
	}
	
	
	
}
