import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.TreeException;
import implementation.BSTree;
import implementation.BSTree.InorderIterator;
import implementation.BSTree.PostorderIterator;
import implementation.BSTree.PreorderIterator;

public class BSTreeTests {
	private BSTree<String> tree;
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

	@Before
	public void setUp() throws Exception {
		tree = new BSTree<>();
	}

	@After
	public void tearDown() throws Exception {
		tree = null;
	}

	@Test
	public void testGetRoot() throws TreeException {
		assertThrows(TreeException.class, () -> tree.getRoot());
		tree.add("banana");
		assertEquals("banana", tree.getRoot().getElement());
		BSTree<String> tree2 = new BSTree<>("apple");
		assertEquals("apple", tree2.getRoot().getElement());
		tree2 = null;
	}

	
	@Test
	public void TEST123123123() throws TreeException {
		tree.add("a");
		tree.add("a");
		System.out.println(tree.getRoot().getLeft().getElement());

	}
	
	@Test
	public void testGetHeight() {
		assertEquals(0, tree.getHeight());
		tree.add("b");
		assertEquals(1, tree.getHeight());
		tree.add("a");
		tree.add("c");
		assertEquals(2, tree.getHeight());
		tree.add("d");
		tree.add("e");
		tree.add("f");
		assertEquals(5, tree.getHeight());

	}

	@Test
	public void testSize() {
		assertEquals(0, tree.size());
		tree.add("b");
		assertEquals(1, tree.size());
		tree.add("a");
		tree.add("c");
		assertEquals(3, tree.size());
		tree.add("d");
		tree.add("e");
		tree.add("f");
		assertEquals(6, tree.size());
	}

	@Test
	public void testClear() {
		assertTrue(tree.isEmpty());
		tree.add("a");
		tree.add("b");
		tree.add("d");
		assertFalse(tree.isEmpty());
		tree.clear();
		assertTrue(tree.isEmpty());
	}

	@Test
	public void testContains() throws TreeException {
		tree.add("g");
		tree.add("b");
		tree.add("a");
		tree.add("k");
		tree.add("l");
		tree.add("c");
		tree.add("q");
		assertTrue(tree.contains("g"));
		assertTrue(tree.contains("q"));
		assertFalse(tree.contains("z"));
		assertFalse(tree.contains("x"));

	}

	@Test
	public void testSearch() throws TreeException {
		assertThrows(TreeException.class, () -> tree.search("a"));
		tree.add("b");
		tree.add("a");
		tree.add("c");
		assertEquals("a", tree.search("a").getElement());
		assertEquals("b", tree.search("b").getElement());
		assertEquals("c", tree.search("c").getElement());
		assertEquals(null, tree.search("d"));
	}

	@Test
	public void testAdd() throws TreeException {
		assertTrue(tree.isEmpty());
		tree.add("b");
		assertFalse(tree.isEmpty());
		assertEquals("b", tree.getRoot().getElement());
		tree.add("a");
		assertEquals("a", tree.getRoot().getLeft().getElement());
		tree.add("c");
		assertEquals("c", tree.getRoot().getRight().getElement());
		tree.add("d");
		assertEquals("d", tree.getRoot().getRight().getRight().getElement());
	}

	@Test
	public void testInorderIterator() {
		tree.add("g");
		tree.add("b");
		tree.add("a");
		tree.add("k");
		tree.add("l");
		tree.add("c");
		tree.add("q");
		InorderIterator i = (InorderIterator) tree.inorderIterator();
		String[] expected = { "a", "b", "c", "g", "k", "l", "q" };

		assertTrue(i.hasNext());
		assertEquals("a", i.next());
		assertEquals("b", i.next());
		assertEquals("c", i.next());
		assertEquals("g", i.next());
		assertEquals("k", i.next());
		assertEquals("l", i.next());
		assertEquals("q", i.next());
		assertFalse(i.hasNext());
		assertThrows(EmptyStackException.class, () -> i.next());

	}

	@Test
	public void testPreorderIterator() {
		tree.add("g");
		tree.add("b");
		tree.add("a");
		tree.add("k");
		tree.add("l");
		tree.add("c");
		tree.add("q");
		PreorderIterator i = (PreorderIterator) tree.preorderIterator();
		String[] expected = { "g", "b", "a", "c", "k", "l", "q" };

		assertTrue(i.hasNext());
		assertEquals("g", i.next());
		assertEquals("b", i.next());
		assertEquals("a", i.next());
		assertEquals("c", i.next());
		assertEquals("k", i.next());
		assertEquals("l", i.next());
		assertEquals("q", i.next());
		assertFalse(i.hasNext());
		assertThrows(NoSuchElementException.class, () -> i.next());
	}

	@Test
	public void testPostorderIterator() {
		tree.add("g");
		tree.add("b");
		tree.add("a");
		tree.add("k");
		tree.add("l");
		tree.add("c");
		tree.add("q");
		PostorderIterator i = (PostorderIterator) tree.postorderIterator();
		String[] expected = { "a", "c", "b", "q", "l", "k", "g" };

		assertTrue(i.hasNext());
		assertEquals("a", i.next());
		assertEquals("c", i.next());
		assertEquals("b", i.next());
		assertEquals("q", i.next());
		assertEquals("l", i.next());
		assertEquals("k", i.next());
		assertEquals("g", i.next());
		assertFalse(i.hasNext());
		assertThrows(EmptyStackException.class, () -> i.next());
	}

}
