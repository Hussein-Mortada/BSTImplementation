package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import exceptions.TreeException;
import implementation.BSTree;
import implementation.BSTree.InorderIterator;
import problemdomain.Occurence;
import problemdomain.Word;
/**
 * Word tracking application.  This application tracks word counts for files and then serializes it after it 
 * is complete.  First it reads if the command line arguments are correct.  Then, it sees if it has already
 * created a serial file for the search tree, if yes it reconstructs the tree, if no, it creates a new tree.
 * Then it goes through the file, adding the words to the tree.  Finally, it prints out the tree according
 * to the user choice, and outputs the print if the user selected to a file of their choice.
 * @author Hussein
 *
 *
 *it also assumes that the user will always input the command line arguments in order!
 */
public class WordTracker {

	private BSTree<Word> bst;
	private String filename;
	private String printOrder;
	private String outfile=null;
	/**
	 * constructor with the arguments
	 * @param args command line arguments
	 * @throws IOException if file is invalid
	 */
	public WordTracker(String[] args) throws IOException {
		// invalid command line
		if(args==null || args.length==0) {
			System.out.println("Not enough arguments.  Try again");
			System.exit(0);
		}
		else {
			filename=args[0];
			printOrder=args[1];
			if(args.length==3) { //if they want to output the print to a file
				if(args[2].substring(0,2).equals("-f"))
				outfile=args[2].substring(2);
				else {
					System.out.println("Please enter the output file as '-f<filename>'");
					System.exit(0);
				}
			}
		}
		File file = new File(filename);
		if(!file.exists()) { //test the input file
			System.out.println("Input file not valid");
			System.exit(0);
		}
		if(!printOrder.equals("-pf")&&!printOrder.equals("-pl")&&!printOrder.equals("-po")) { //test invalid prints
			System.out.println("Invalid report option.  Please make it -pf or -pl or -po");
			System.exit(0);
		}
		startApplication();
	}
	/**
	 * starts the application if all command line arguments are valid
	 * @throws IOException if file is invalid
	 */
	private void startApplication() throws IOException {
		File file = new File("res/repository.ser");
		if (!file.exists()) { //constructs a new tree if there is no serialized file already
			bst=new BSTree<>();
		}
		else { //reconstructs the tree
			buildTree();
			//System.out.println(file.getAbsolutePath());

		}
		try { //reads in the file and builds the tree
			readFile();
		} catch (FileNotFoundException | TreeException e) {
			e.printStackTrace();
		}
		printReport(); //prints the report
		if(outfile!=null) { //outputs the file if the user chose to
			printReportFile();
		}
		try {
			writeTree(); //writes the tree to the serialized file
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * writes the entire tree into the serialized file in the res folder
	 * @throws IOException if invalid file
	 */
	private void writeTree() throws IOException {
	    FileOutputStream fileOut = new FileOutputStream("res/repository.ser");
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(bst);
	    fileOut.close();
	    out.close();
	}
	/**
	 * prints the report  to the file the user has chosen and using the same print order they chose
	 * @throws IOException if invalid file
	 */
	private void printReportFile() throws IOException {
		File file = new File(outfile);
	    file.createNewFile();
		PrintWriter out = new PrintWriter(file);
		if (printOrder.equals("-pf")) { //pf choice
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		Set<String> uniqueFileNames = new HashSet<>();
	    		out.write("\n");
	    		out.write("Word: "+word.getWord());
	    		for(Occurence s: occurences) {
	    			out.write("\n");
	    			out.write("File Name: "+s.getFilename());
	    			 if (!uniqueFileNames.contains(filename)) {
	                     uniqueFileNames.add(filename);
	                     out.write("\n");
	                     out.write("File Name: " + filename);
	                 }
	    		}
	    	}
	    	out.close();
	    } else if (printOrder.equals("-pl")) { //pl choice
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		out.write("\n");
	    		out.write("Word: "+word.getWord());
	    		for(Occurence s: occurences) {
	    			out.write("\n");
	    			out.write("File Name: "+s.getFilename()+ ".  Line Number: "+s.getLine());
	    		}
	    	}
	    	out.close();
	    } else if (printOrder.equals("-po")) { //po choice
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		out.write("\n");
	    		out.write("Word: "+word.getWord()+ " . Number of occurences: "+ occurences.size());
	    		for(Occurence s: occurences) {
	    			out.write("\n");
	    			out.write("File Name: "+s.getFilename()+ ".  Line Number: "+s.getLine());
	    		}
	    	}
	    	out.close();
	    }
	}
	/**
	 * prints the report to the screen
	 */
	private void printReport() {
	    if (printOrder.equals("-pf")) {
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		Set<String> uniqueFileNames = new HashSet<>();
	    		System.out.println("Word: "+word.getWord());
	    		for(Occurence s: occurences) {
	    			System.out.println("File Name: "+s.getFilename());
	    			 if (!uniqueFileNames.contains(filename)) {
	                     uniqueFileNames.add(filename);
	                     System.out.println("File Name: " + filename);
	                 }
	    		}
	    	}
	    } else if (printOrder.equals("-pl")) {
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		System.out.println("Word: "+word.getWord());
	    		for(Occurence s: occurences) {
	    			System.out.println("File Name: "+s.getFilename()+ ".  Line Number: "+s.getLine());
	    		}
	    	}
	    } else if (printOrder.equals("-po")) {
	    	BSTree<Word>.InorderIterator<Word> i = (BSTree<Word>.InorderIterator<Word>) bst.inorderIterator();
	    	while(i.hasNext()) {
	    		Word word= i.next();
	    		ArrayList<Occurence> occurences=word.getOccurences();
	    		System.out.println("Word: "+word.getWord()+ " . Number of occurences: "+ occurences.size());
	    		for(Occurence s: occurences) {
	    			System.out.println("File Name: "+s.getFilename()+ ".  Line Number: "+s.getLine());
	    		}
	    	}
	    } else {
	        System.out.println("Invalid report option. Please make it -pf or -pl or -po");
	    }
	}
	/**
	 * reads in the file and builds the tree using the words 
	 * @throws FileNotFoundException if file not found
	 * @throws TreeException if tree errors occur
	 */
	private void readFile() throws FileNotFoundException, TreeException {
		File file = new File(filename);
		Scanner in = new Scanner(file);
		int lineNum=1;
		while(in.hasNextLine()) { //loop through all lines
			String line = in.nextLine();
			String[] words=line.split(" ");
			for(int i=0;i<words.length;i++) { //loop through all words
				String word = words[i];
				word=word.toLowerCase();
				if(containsSymbol(word)) { //remove symbols
					word=removeSymbol(word);
				}
				if(bst.isEmpty()) { //if tree is empty, add 
					Word w = new Word(word, filename, lineNum);
					bst.add(w);
				}
				else{ //otherwise, search if the tree contains the word.  if it contains the word add occurence, else add node
				    Word w = new Word(word, filename, lineNum);
				    if (bst.contains(w)) { //add occurence
				        bst.search(w).getElement().addOccurence(filename, lineNum);
				    } else { //add node
				        bst.add(w);
				    }
				}
			}
			lineNum++;
		}
	}
	/**
	 * checks if there are symbols in words
	 * @param word word with/without symbols
	 * @return true/false if word has symbols
	 */
	private boolean containsSymbol(String word) {
	    return word.matches(".*[^\\p{Alnum}].*");
	}
	/**
	 * if word contains symbols, it will remove
	 * @param word word to remove symbols from
	 * @return word without symbols
	 */
	private String removeSymbol(String word) {
	    return word.replaceAll("[^\\p{Alnum}]", "");
	}
	/**
	 * rebuilds the tree from the serialized file
	 */
	private void buildTree() {
	    try {
	        FileInputStream fileIn = new FileInputStream("res/repository.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        bst = (BSTree<Word>) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch (IOException i) {
	        i.printStackTrace();
	        return;
	    } catch (ClassNotFoundException c) {
	        System.out.println("BSTree class not found");
	        c.printStackTrace();
	        return;
	    }
	}
	
	
}
