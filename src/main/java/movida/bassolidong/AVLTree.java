package movida.bassolidong;

import movida.commons.Movie;

public class AVLTree extends AVLNode {

	public AVLNode root;

	// costruttore
	public AVLTree() {
		root = null;
	}

	// controllo se l'albero è vuoto oppure no
	public boolean isEmpty() {
		return size() == 0;
	}

	// traforma l'albero in un albero vuoto
	public void makeEmpty() {
		root = null;
	}

	// ritorna l'altezza del nodo
	public int height(AVLNode t) {
		return t == null ? -1 : t.height;
	}

	public int size() {
		return countNodes();
	}

	public boolean insert(Movie m) {
		if (m == null)
			return false;
		if (!search(root, m.getTitle())) {
			root = insert(root, m);
			return true;
		}
		return false;
	}

	public AVLNode insert(AVLNode n, Movie m) {
		// caso base
		if (n == null)
			return new AVLNode(m);

		int cmp = m.getTitle().compareTo(n.m.getTitle());

		if (cmp < 0) {
			n.left = insert(n.left, m);
		} else {
			n.right = insert(n.right, m);
		}

		update(n);

		return balance(n);

	}

	public int countNodes() {
		return countNodes(root);
	}

	// conta i nodi
	public int countNodes(AVLNode r) {

		if (r == null) {
			return 0;
		} else {
			int l = 1;
			l = l + countNodes(r.left);
			l = l + countNodes(r.right);

			return l;
		}
	}

	public boolean search(String Title) {
		return search(root, Title);
	}

	// cerca un titolo di un film
	public boolean search(AVLNode r, String Title) {
		boolean found = false;

		while ((r != null) && !found) {
			if (Title.compareTo(r.m.getTitle()) < 0) {
				r = r.left;
			} else if (Title.compareTo(r.m.getTitle()) > 0) {
				r = r.right;
			} else {
				found = true;
				break;
			}

			found = search(r, Title);
		}

		return found;
	}

	public void update(AVLNode n) {
		int leftNodeHeight = (n.left == null) ? -1 : n.left.height;
		int rightNodeHeight = (n.right == null) ? -1 : n.right.height;

		n.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
		n.balancefactor = rightNodeHeight - leftNodeHeight;
	}

	public AVLNode balance(AVLNode n) {
		if (n.balancefactor == -2) {
			if (n.left.balancefactor <= 0) {
				return leftLeftCase(n);
			} else {
				return leftRightCase(n);
			}

		} else if (n.right.balancefactor == 2) {
			if (n.right.balancefactor >= 0) {
				return rightRightCase(n);
			} else {
				return rightLeftCase(n);
			}
		}
		return n;
	}

	public AVLNode leftLeftCase(AVLNode n) {
		return rightRotation(n);
	}

	public AVLNode leftRightCase(AVLNode n) {
		n.left = leftRotation(n.left);
		return leftLeftCase(n);
	}

	public AVLNode rightRightCase(AVLNode n) {
		return leftRotation(n);
	}

	public AVLNode rightLeftCase(AVLNode n) {
		n.right = rightRotation(n.right);
		return rightRightCase(n);
	}

	public AVLNode leftRotation(AVLNode n) {
		AVLNode newparent = n.right;
		n.right = newparent.left;
		newparent.left = n;
		update(n);
		update(newparent);

		return newparent;
	}

	public AVLNode rightRotation(AVLNode n) {
		AVLNode newparent = n.left;
		n.left = newparent.right;
		newparent.right = n;
		update(n);
		update(newparent);

		return newparent;
	}

	public AVLNode findMax(AVLNode r) {
		while (r.right != null) {
			r = r.right;
		}
		return r;
	}

	public AVLNode findMin(AVLNode r) {
		while (r.left != null) {
			r = r.left;
		}
		return r;
	}

	public AVLNode search_by_title(String Title) {
		return search_by_title(root, Title);
	}

	public void delete_by_title(String Title) {
		delete_by_title(root, Title);
	}

	public AVLNode search_by_title(AVLNode r, String Title) {
		boolean found = false;

		while ((r != null) && !found) {
			if (Title.compareTo(r.m.getTitle()) < 0) {
				r = r.left;
			} else if (Title.compareTo(r.m.getTitle()) > 0) {
				r = r.right;
			} else {
				found = true;
				return r;
			}
		}
		return null;

	}

	public void delete_by_title(AVLNode r, String Title) {
		boolean found = false;

		while ((r != null) && !found) {
			if (Title.compareTo(r.m.getTitle()) < 0) {
				r = r.left;
			} else if (Title.compareTo(r.m.getTitle()) > 0) {
				r = r.right;
			} else {
				found = true;
				remove(r.m);
			}
		}

	}

	public boolean remove(Movie m) {
		if (m == null) {
			return false;
		}

		if (search(root, m.getTitle())) {
			root = remove(root, m);
			return true;
		}
		return false;
	}

	public AVLNode remove(AVLNode r, Movie m) {
		if (r == null) {
			return null;
		}

		if (m.getTitle().compareTo(r.m.getTitle()) < 0) {
			r.left = remove(r.left, m);
		} else if (m.getTitle().compareTo(r.m.getTitle()) > 0) {
			r.right = remove(r.right, m);
		} else {
			// caso con solo sottoalbero destro o nessun sottoalbero
			if (r.left == null) {
				return r.right;
			}

			// caso con solo sottoalbero sinistro o nessun sottalbero
			else if (r.right == null) {
				return r.left;
			} else {
				// rimuovi dal sottoalbero sx
				if (r.left.height > r.right.height) {
					AVLNode succ = findMax(r.left);
					r.m = succ.m;

					r.left = remove(r.left, succ.m);
				} else {
					AVLNode succ = findMin(r.right);
					r.m = succ.m;

					r.right = remove(r.right, succ.m);

				}

			}

		}

		update(r);
		return balance(r);
	}

	class Nodo {
		String actor;
		Nodo next;
	}

	public class List {
		Nodo Head;
		int size;

		public List() {
			size = 0;
			Head = null;
		}

		public void add(String a) {
			Nodo newNodo = new Nodo();
			newNodo.actor = a;

			if (Head == null) {
				Head = newNodo;
				Head.next = null;
			}

			else if (Head.next == null) {
				Head.next = newNodo;
			}

			else {
				Nodo iter = null;
				for (iter = Head.next; iter.next != null; iter = iter.next)
					;
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

		public void remove(String a, boolean all) {
			while (Head != null && Head.actor == a) {
				Head = Head.next;
				size--;
				if (!all || Head == null)
					return;
			}

			Nodo current = Head.next;
			Nodo previous = Head;
			for (; current != null; current = current.next) {
				if (current.actor == a) {
					previous.next = current.next;
					current = previous;
					size--;
					if (!all)
						return;
				} else {
					previous = previous.next;
				}
			}
		}

		public int size() {
			return size;
		}

	}

	List actor = null;

	// conta i nodi
	public int countPeople() {
		return countPeople(root);
	}

	public int countPeople(AVLNode r) {
		if (r != null) {
			AggiungiAllaLista(r);
			countPeople(r.left);
			countPeople(r.right);

		}

		return actor.size();
	}

	public void AggiungiAllaLista(AVLNode n) {
		if (n != null) {
			if (!actor.search(n.m.getDirector().getName())) {
				actor.add(n.m.getDirector().getName());
			}
			for (int i = n.m.getCast().length; i < 0; i--) {
				if (!actor.search(n.m.getCast()[i].getName())) {
					actor.add(n.m.getCast()[i].getName());
				}
			}

		}

	}

}
