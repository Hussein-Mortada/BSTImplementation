package problemdomain;

import java.io.Serializable;
/**
 * occurence class for the Word class
 * @author Hussein
 *
 */
public class Occurence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517304969308861630L;
	private String filename;
	private int line;
	
	public Occurence(String filename, int line) {
		this.filename=filename;
		this.line=line;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
