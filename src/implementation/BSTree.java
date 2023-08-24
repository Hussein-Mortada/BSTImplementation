package implementation;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Stack;

import exceptions.TreeException;
import utilities.BSTreeADT;
import utilities.BSTreeNode;
import utilities.Iterator;
/**
 * BST data structure following the BSTADT and Iterator ADT.  Please see the ADT in the utilities package
 * for full documentation
 * @author Hussein
 *
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public class BSTree<E extends Comparable<E>> implements BSTreeADT , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	private BSTreeNode<E> root;
	/**
	 * creates a tree
	 */
	public BSTree(){
		this.size=0;
		this.root=null;
	}

	public BSTree(E element){
		this.size=0;
		BSTreeNode<E> e = new BSTreeNode<>(element);
		this.root=e;
	}

	@Override
	public BSTreeNode<E> getRoot() throws TreeException {
		if(root==null) {
			throw new TreeException();
		}
		return root;
	}

	@Override
	public int getHeight() {
		if(root==null)
			return 0;
		return root.getHeight();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return root==null;
	}

	@Override
	public void clear() {
		root=null;
		size=0;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean contains(Comparable entry) throws TreeException {
		if(entry==null) {
			throw new NullPointerException();
		}
		if(root==null) {
			throw new TreeException();
		}
		return containsHelper(root,entry);
	}

	private boolean containsHelper(BSTreeNode<E> node, Comparable<E> entry) {
		if(node==null)
			return false;
		int comparison = entry.compareTo(node.getElement());
		if(comparison==0) //node element is equal to entry
			return true;
		else if(comparison<0) //entry is less than node
			return containsHelper(node.getLeft(),entry);
		else //entry is greater than node
			return containsHelper(node.getRight(),entry);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public BSTreeNode<E> search(Comparable entry) throws TreeException {
		if(entry==null) {
			throw new NullPointerException();
		}
		if(root==null) {
			throw new TreeException();
		}
		BSTreeNode<E> current = root;
	    while (current != null) {
	        int comparison = entry.compareTo(current.getElement());
	        if (comparison == 0) 
	            return current;
	         else if (comparison < 0) 
	            current = current.getLeft();
	         else 
	            current = current.getRight();
	        
	    }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Comparable newEntry) throws NullPointerException {
		if(newEntry==null) {
			throw new NullPointerException();
		}
		if(root==null) {
			root=new BSTreeNode<E>((E) newEntry);
			size++;
			return true;
		}
		BSTreeNode<E> current = root;
		while(current!=null) {
			int comparison = newEntry.compareTo(current.getElement());
//			if(comparison==0) {
//				//add to the arraylist of entries the word has...
//				return true;
//			}
			if(comparison<=0) {
				if(current.hasLeftChild()) {
					current=current.getLeft();
				}
				else {
					BSTreeNode<E> left = new BSTreeNode(newEntry);
					current.setLeft(left);
					size++;
					return true;
				}
			}
			else {
				if(current.hasRightChild()) {
					current=current.getRight();
				}
				else {
					BSTreeNode<E> right = new BSTreeNode(newEntry);
					current.setRight(right);
					size++;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterator<E> inorderIterator() {
		return new InorderIterator<E>();
	}

	@Override
	public Iterator preorderIterator() {
		return new PreorderIterator<E>();
	}

	@Override
	public Iterator postorderIterator() {
		return new PostorderIterator<E>();
	}
	
	@SuppressWarnings("hiding")
	public class PostorderIterator<E> implements Iterator<E>{
	    private BSTreeNode<E> current;
	    private Stack<BSTreeNode<E>> stack;
	    private BSTreeNode<E> lastVisited;
	    
	    @SuppressWarnings("unchecked")
	    public PostorderIterator() {
	        current=(BSTreeNode<E>) root;
	        stack = new Stack<BSTreeNode<E>>();
	        lastVisited = null;
	    }
	    
	    @Override
	    public boolean hasNext() {
	        return (!stack.isEmpty()||current!=null);
	    }
	    
	    @Override
	    public E next() throws NoSuchElementException {
	        while(current!=null) {
	            stack.push(current);
	            current=current.getLeft();
	        }
	        while(!stack.isEmpty() && (stack.peek().getRight()==null || stack.peek().getRight()==lastVisited)) {
	            lastVisited = stack.pop();
	            return lastVisited.getElement();
	        }
	        current=stack.peek().getRight();
	        return next();
	    }
	}
	
	@SuppressWarnings("hiding")
	public class PreorderIterator<E> implements Iterator<E>{
		private BSTreeNode<E> current;
		private Stack<BSTreeNode<E>> stack;
		
		@SuppressWarnings("unchecked")
		public PreorderIterator() {
			current=(BSTreeNode<E>) root;
			stack = new Stack<BSTreeNode<E>>();
			if(current!=null) {
				stack.push(current);
			}
		}
		
		@Override
		public boolean hasNext() {
			return (!stack.isEmpty());
		}
		
		@Override
		public E next() throws NoSuchElementException {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current=stack.pop();
			E element = current.getElement();
			if(current.hasRightChild()) {
				stack.push(current.getRight());
			}
			if(current.hasLeftChild()) {
				stack.push(current.getLeft());
			}
			return element;
		}
	}
	
	@SuppressWarnings("hiding")
	public class InorderIterator<E> implements Iterator<E>{
		private BSTreeNode<E> current;
		private Stack<BSTreeNode<E>> stack;
		
		@SuppressWarnings("unchecked")
		public InorderIterator() {
			current=(BSTreeNode<E>) root;
			stack = new Stack<BSTreeNode<E>>();
		}
		@Override
		public boolean hasNext() {
			return (!stack.isEmpty()||current!=null);
		}
		@Override
		public E next() throws NoSuchElementException {
			while(current!=null) {
				stack.push(current);
				current=current.getLeft();
			}
			current=stack.pop();
			E element = current.getElement();
			current=current.getRight();
			return element;

		}
	}

}
