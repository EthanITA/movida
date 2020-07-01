package movida.bassolidong;

import java.util.HashSet;
import java.util.Set;

import movida.commons.Movie;
import movida.commons.Person;

public class AVLTree {
	public class AVLNode {

		int bf;
		Movie m;
		int height;
		AVLNode left, right;

		public AVLNode(Movie m) {
			this.m = m;
		}

		public AVLNode getLeft() {
			return left;
		}

		public AVLNode getRight() {
			return right;
		}

		public String getTitle() {
			return this.m.getTitle();
		}
	}

	public AVLNode root;
	public int nodecount = 0;

	// get height
	public int height_tree() {
		return height_tree(root);
	}

	public int height_tree(AVLNode N) {
		if (root == null) {
			return 0;
		} else {
			return root.height;
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return nodecount;
	}

	// make empty
	public void makeEmpty() {
		root.right = null;
		root.left = null;
		root = null;
		nodecount = 0;
	}

	public boolean contains(Movie m) {
		return contains(root, m);
	}

	public boolean contains(AVLNode node, Movie m) {
		if (node == null) {
			return false;
		}

		int compare = m.getTitle().compareTo(node.getTitle());

		if (compare < 0) {
			return contains(node.left, m);
		}
		if (compare > 0) {
			return contains(node.right, m);
		}

		return true;
	}

	public AVLNode balance(AVLNode node) {

		// left heavy subtree
		if (node.bf == -2) {

			if (node.left.bf <= 0) {
				return leftLeftCase(node);
			} else {
				return leftRightCase(node);
			}

		}

		else if (node.bf == 2) {

			if (node.right.bf >= 0) {
				return rightRightCase(node);
			} else {
				return rightLeftCase(node);
			}

		}

		return node;
	}

	public AVLNode leftLeftCase(AVLNode node) {
		return rightRotation(node);
	}

	public AVLNode leftRightCase(AVLNode node) {
		node.left = leftRotation(node.left);
		return leftLeftCase(node);
	}

	public AVLNode rightRightCase(AVLNode node) {
		return leftRotation(node);
	}

	public AVLNode rightLeftCase(AVLNode node) {
		node.right = rightRotation(node.right);
		return rightRightCase(node);
	}

	public AVLNode leftRotation(AVLNode node) {
		AVLNode newparent = node.right;
		node.right = newparent.left;
		newparent.left = node;

		update(node);
		update(newparent);
		return newparent;

	}

	public AVLNode rightRotation(AVLNode node) {
		AVLNode newparent = node.left;
		node.left = newparent.right;
		newparent.right = node;

		update(node);
		update(newparent);
		return newparent;

	}

	public void update(AVLNode node) {
		int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
		int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

		node.height = Math.max(leftNodeHeight, rightNodeHeight) + 1;
		node.bf = rightNodeHeight - leftNodeHeight;
	}

	public boolean insert(Movie m) {
		if (m == null || m.getTitle() == null) {
			return false;
		}

		if (!contains(root, m)) {
			root = insert(root, m);
			nodecount++;
			return true;
		}
		return false;
	}

	AVLNode insert(AVLNode node, Movie m) {
		if (node == null) {
			node = new AVLNode(m);
			return node;
		}

		int compare = m.getTitle().compareTo(node.getTitle());

		if (compare < 0) {
			node.left = insert(node.left, m);
		} else {
			node.right = insert(node.right, m);
		}

		update(node);
		return balance(node);
	}

	public boolean remove(Movie m) {
		if (m == null || m.getTitle() == null) {
			return false;
		}
		if (contains(root, m)) {
			root = remove(root, m);
			nodecount--;
			return true;
		}
		return false;
	}

	public AVLNode remove(AVLNode node, Movie m) {
		if (node == null || node.getTitle() == null) {
			return null;
		}

		int compare = m.getTitle().compareTo(node.getTitle());

		if (compare < 0) {
			node.left = remove(node.left, m);
		} else if (compare > 0) {
			node.right = remove(node.right, m);
		}

		else {
			// only right subtree o no subtree
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			}

			else {
				// remove from left subtree
				if (node.left.height > node.right.height) {
					Movie successorMovie = findMax(node.left);
					node.m = successorMovie;

					node.left = remove(node.left, successorMovie);

				} else {
					Movie successorMovie = findMin(node.right);
					node.m = successorMovie;

					node.right = remove(node.right, successorMovie);
				}
			}

		}
		update(node);
		return balance(node);

	}

	public Movie findMin(AVLNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node.m;
	}

	public Movie findMax(AVLNode node) {
		while (node.right != null) {
			node = node.right;
		}
		return node.m;
	}

	/////

	Set<Person> actor;

	// conta i nodi
	public int countPeople() {
		actor = new HashSet<>();
		countPeople(root);
		return actor.size();
	}

	public void countPeople(AVLNode r) {
		if (r != null) {
			EsaminaNodoActor(r);
			countPeople(r.left);
			countPeople(r.right);

		}

	}

	public Set<Person> getPersonSet() {
		actor = new HashSet<>();
		getPersonSet(root);
		return actor;
	}

	public void getPersonSet(AVLNode r) {
		if (r != null) {
			EsaminaNodoActor(r);
			getPersonSet(r.left);
			getPersonSet(r.right);
		}
	}

	public void stampaPersone() {
		Set<Person> personSet = getPersonSet();
		for (Person p : personSet) {
			System.out.println(p.getName());
		}
	}

	public void EsaminaNodoActor(AVLNode n) {
		if (n != null) {
			actor.add(n.m.getDirector());
			for (int i = 0; i < n.m.getCast().length; i++) {
				actor.add(n.m.getCast()[i]);

			}

		}

	}

	Set<Movie> Titles;

	public Set<Movie> getMovieSet() {
		Titles = new HashSet<>();
		getMovieSet(root);
		return Titles;
	}

	public void getMovieSet(AVLNode r) {
		if (r != null) {
			EsaminaNodoTitles(r);
			getMovieSet(r.left);
			getMovieSet(r.right);
		}
	}

	public void EsaminaNodoTitles(AVLNode n) {
		if (n != null) {
			Titles.add(n.m);
		}

	}

	public Movie getMovieByTitle(String Title) {
		return getMovieByTitle(root, Title);
	}

	public Movie getMovieByTitle(AVLNode node, String Title) {
		if (node == null) {
			return null;
		}

		int compare = Title.compareTo(node.m.getTitle());

		if (compare < 0) {
			return getMovieByTitle(node.left, Title);
		}
		if (compare > 0) {
			return getMovieByTitle(node.right, Title);
		}
		if (compare == 0) {
			return node.m;
		}

		return null;
	}

	public Person getPersonByName(String name) {
		for (Person p : getPersonSet()) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public void deleteMovieByTitle(String Title) {
		if (getMovieByTitle(Title) != null) {
			remove(getMovieByTitle(Title));
		}

	}

}
