package problemdomain;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Word class that contains a list of occurences and a string value of the word
 * @author Hussein
 *
 */
public class Word implements Comparable<Word>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002804636598320355L;
	private ArrayList<Occurence> occurences;
	private String word;
	/**
	 * Constructor to set the word and create the arraylist of occurence objects
	 * @param word word to use
	 * @param filename filename of the occurence
	 * @param line line number of the occurence
	 */
	public Word(String word, String filename, int line) {
		this.word = word;
		occurences = new ArrayList<>();
		Occurence o = new Occurence(filename, line);
		occurences.add(o);

	}
	/**
	 * adds an occurence for a word
	 * @param filename filename of occurence
	 * @param line line number of occurence
	 */
	public void addOccurence(String filename, int line) {
		Occurence o = new Occurence(filename, line);
		occurences.add(o);
	}
	/**
	 * returns the arraylist of all occurences for a given word
	 * @return arraylist of all occurences
	 */
	public ArrayList<Occurence> getOccurences() {
		return occurences;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * comparison for words 
	 */
	@Override
	public int compareTo(Word that) {
	    return this.word.compareTo(that.word);
	}

}
