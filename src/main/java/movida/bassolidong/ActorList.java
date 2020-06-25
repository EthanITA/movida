package movida.bassolidong;

class Nodo {
	String actor;
	Nodo next;
}

public class ActorList {
	Nodo Head;
	int size;

	public ActorList() {
		size = 0;
		Head = null;
	}

	public void add(String a) {
		Nodo newNodo = new Nodo();
		newNodo.actor = a;

		if (Head == null) 
		{
			Head = newNodo;
			Head.next = null;
		}

		else if (Head.next == null) 
		{
			Head.next = newNodo;
		}

		else {
			
			Nodo iter = Head.next;
			while( iter.next != null )
			{iter = iter.next;}
			
			iter.next = newNodo;
		}

		size++;
	}

	public boolean search(String a) {
		for (Nodo iter = Head; iter != null; iter = iter.next) {
			if (iter.actor == a)
				return true;
		}
		return false;
	}

	public void remove(String a) {
		Nodo iter=Head;
		Nodo iterprec=Head;
		
		if(Head == null) {
			return;
		}
		
		if(Head.next == null) {
			if(Head.actor.equals(a)) {
				Head = null;
			}
		}
		
		
		else {
			
			boolean found=false;
			while(iter.next != null) {
				iterprec=iter;
				iter=iter.next;
				if(iter.actor.equals(a)) { found=true; break;}
				
			}
			if(found) {
				iterprec.next=iter.next;
			}	
			
		}
	}

	
	
	public int size() {
		return size;
	}

}
