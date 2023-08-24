package utilities;

import java.io.Serializable;
/**
 * BST node for the BST class.  
 * @author Hussein
 *
 * @param <E> type of node to create
 */
public class BSTreeNode<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private E element;
	private BSTreeNode<E> left, right;
	/**
	 * Constructor with only element
	 * @param element element 
	 */
	public BSTreeNode(E element) {
		this.element = element;
	}
	/**
	 * Constructor with data, left child and right child
	 * @param element data
	 * @param left left child
	 * @param right right child
	 */
	public BSTreeNode(E element, BSTreeNode<E> left, BSTreeNode<E> right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}
	/**
	 * gets the element
	 * @return element
	 */
	public E getElement() {
		return element;
	}
	/**
	 * sets the element
	 * @param element  element to set
	 */
	public void setElement(E element) {
		this.element = element;
	}
	/**
	 * gets the left child
	 * @return left child
	 */
	public BSTreeNode<E> getLeft() {
		return left;
	}
	/**
	 * sets the left child
	 * @param left node to set
	 */
	public void setLeft(BSTreeNode<E> left) {
		this.left = left;
	}
	/**
	 * gets the right child
	 * @return right child
	 */
	public BSTreeNode<E> getRight() {
		return right;
	}
	/**
	 * sets the right child
	 * @param right node to set
	 */
	public void setRight(BSTreeNode<E> right) {
		this.right = right;
	}
/**
 * if the node has a left child
 * @return true/false if has left child
 */
	public boolean hasLeftChild() {
		return this.left != null;
	}
	/**
	 * if the node has a right child
	 * @return true/false if has right child
	 */
	public boolean hasRightChild() {
		return this.right != null;
	}
	/**
	 * if the node has any children (is a leaf)
	 * @return true/false if has any children
	 */
	public boolean isLeaf() {
		return this.right == null && this.left == null;
	}
	/**
	 * gets the number of nodes up until the current node calling the method
	 * @return number of nodes 
	 */
	@SuppressWarnings("unused")
	public int getNumberNodes() {
		if (this == null)
			return 0;

		int leftCount = 0;
		if (this.getLeft() != null) {
			leftCount = this.getLeft().getNumberNodes();
		}
		int rightCount = 0;
		if (this.getRight() != null) {
			rightCount = this.getRight().getNumberNodes();
		}
		return leftCount + rightCount + 1;
	}
	/**
	 * gets the height of the node calling the method
	 * @return height number of node
	 */
	@SuppressWarnings("unused")
	public int getHeight() {
		if (this == null)
			return 0;

		int leftHeight = 0;
		if (this.getLeft() != null) {
			leftHeight = this.getLeft().getHeight();
		}

		int rightHeight = 0;
		if (this.getRight() != null) {
			rightHeight = this.getRight().getHeight();
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}
	/**
	 *  calculates the height of a binary search tree node
	 * @param <E> type of node
	 * @param bstNode node to calculate height of
	 * @return integer number for height of given node
	 */
	public static <E> int getHeight(BSTreeNode<E> bstNode) {
		// If the node is null, the height is 0.
		if (bstNode == null) {
			return 0;
		}

		// Calculate the height of the left and right subtrees.
		int leftHeight = getHeight(bstNode.getLeft());
		int rightHeight = getHeight(bstNode.getRight());

		// Return the maximum height of the left and right subtrees, plus 1 for the
		// current node.
		return Math.max(leftHeight, rightHeight) + 1;
	}

}
