package movida.bassolidong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import movida.commons.Movie;

public class AVLTree extends AVLNode {

	public AVLNode root;

	public AVLTree() {
		root = new AVLNode();
	}

	public boolean isEmpty() {
		return countNodes() == 0;
	}

	// get height
	int height_tree(AVLNode N) {

		if (N == null) {
			return 0;
		}
		return N.height;
	}

	// make empty
	public void makeEmpty() {
		root = null;
	}

	// right rotate subtree
	AVLNode rightRotate(AVLNode y) {
		AVLNode x = y.left;
		AVLNode T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = Math.max(height_tree(y.left), height_tree(y.right)) + 1;
		x.height = Math.max(height_tree(x.left), height_tree(x.right)) + 1;

		// Return new root
		return x;
	}

	// left rotate subtree

	AVLNode leftRotate(AVLNode x) {
		AVLNode y = x.right;
		AVLNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = Math.max(height_tree(x.left), height_tree(x.right)) + 1;
		y.height = Math.max(height_tree(y.left), height_tree(y.right)) + 1;

		// Return new root
		return y;
	}

	// Get Balance factor of AVLnode N
	int getBalance(AVLNode N) {
		if (N == null)
			return 0;

		return height_tree(N.left) - height_tree(N.right);
	}

	// insert
	void insert(Movie m) {
		// return insert(root, m);
		if (root.m == null) {
			root.m = m;
		} else if (m.getTitle().compareTo(root.m.getTitle()) < 0) {
			root.left = insert(root.left, m);
		}

		else if (m.getTitle().compareTo(root.m.getTitle()) > 0) {
			root.right = insert(root.right, m);
		}

	}

	AVLNode insert(AVLNode node, Movie m) {

		if (node == null) {
			return new AVLNode(m);
		}

		else if (m.getTitle().compareTo(node.m.getTitle()) < 0) {
			node.left = insert(node.left, m);
		} else if (m.getTitle().compareTo(node.m.getTitle()) > 0) {
			node.right = insert(node.right, m);
		} else // Duplicate keys not allowed
		{
			return node;
		}

		// Update height
		node.height = 1 + (node.m == null ? 0 : (Math.max(height_tree(node.left), height_tree(node.right))));

		// get the balance factor to check if It's unbalanced
		int balance = getBalance(node);

		if (balance > 1 && m.getTitle().compareTo(node.m.getTitle()) < 0)
			return rightRotate(node);

		// Right Right Case
		if (balance < -1 && m.getTitle().compareTo(node.m.getTitle()) > 0)
			return leftRotate(node);

		// Left Right Case
		if (balance > 1 && m.getTitle().compareTo(node.m.getTitle()) > 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		// Right Left Case
		if (balance < -1 && m.getTitle().compareTo(node.m.getTitle()) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		return node;
	}

	AVLNode minValueNode() {
		return minValueNode(root);
	}

	AVLNode minValueNode(AVLNode node) {
		AVLNode current = node;

		while (current.left != null) {
			current = current.left;
		}

		return current;
	}

	// delete node
	AVLNode deleteNode(AVLNode root, Movie m) {
		// STEP 1: PERFORM STANDARD BST DELETE
		if (root == null) {
			return root;
		}

		// If the key to be deleted is smaller than
		// the root's key, then it lies in left subtree
		if (m.getTitle().compareTo(root.m.getTitle()) < 0)
			root.left = deleteNode(root.left, m);

		// If the key to be deleted is greater than the
		// root's key, then it lies in right subtree
		else if (m.getTitle().compareTo(root.m.getTitle()) > 0)
			root.right = deleteNode(root.right, m);

		// if key is same as root's key, then this is the node
		// to be deleted
		else {

			// node with only one child or no child
			if ((root.left == null) || (root.right == null)) {
				AVLNode temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				// No child case
				if (temp == null) {
					temp = root;
					root = null;
				} else // One child case
					root = temp; // Copy the contents of
									// the non-empty child
			} else {

				// node with two children: Get the inorder
				// successor (smallest in the right subtree)
				AVLNode temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.m = temp.m;

				// Delete the inorder successor
				root.right = deleteNode(root.right, temp.m);
			}
		}

		// If the tree had only one node then return
		if (root == null) {
			return root;
		}

		// UPDATE HEIGHT OF THE CURRENT NODE
		root.height = Math.max(height_tree(root.left), height_tree(root.right)) + 1;

		// GET THE BALANCE FACTOR OF THIS NODE (to check)
		int balance = getBalance(root);

		// If this node becomes unbalanced...
		if (balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);

		// Left Right Case
		if (balance > 1 && getBalance(root.left) < 0) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}

		// Right Right Case
		if (balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);

		// Right Left Case
		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}

		return root;
	}

	public boolean searchNode(String Title) {
		return searchNode(root, Title);
	}

	// search title
	public boolean searchNode(AVLNode r, String Title) {
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

			found = searchNode(r, Title);
		}

		return found;
	}

	// count Number of nodes
	public int countNodes() {
		return countNodes(root);
	}

	public int countNodes(AVLNode r) {

		if (r == null) {
			return 0;
		}

		else {
			int nodes = 1;
			nodes = nodes + countNodes(r.left);
			nodes = nodes + countNodes(r.right);

			return nodes;
		}
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

	public void delete_by_title(AVLNode node, String Title) {
		boolean found = false;
		node = root;

		while ((node != null) && !found) {
			if (Title.compareTo(node.m.getTitle()) < 0) {
				node = node.left;
			}

			else if (Title.compareTo(node.m.getTitle()) > 0) {
				node = node.right;
			}

			else {
				found = true;
				break;
			}
		}
		if (found) {
			deleteNode(root, node.m);
		}
	}

	// ActorList actor = null;

	Set<String> actor;

	// conta i nodi
	public int countPeople() {
		actor = new HashSet<>();
		countPeople(root);
		return actor.size();
	}

	public void countPeople(AVLNode r) {
		System.out.println(r);
		if (r != null) {
			AggiungiAllaLista(r);
			countPeople(r.left);
			countPeople(r.right);

		}

	}

	public void AggiungiAllaLista(AVLNode n) {
		if (n != null) {
			actor.add(n.m.getDirector().getName());
			for (int i = 0; i < n.m.getCast().length; i++) {
				actor.add(n.m.getCast()[i].getName());

			}

		}

	}

}
