package tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeReader {

	
	public static void main(String[] aArgs) throws FileNotFoundException {
		MazeReader parser = new MazeReader("maze.txt");
		parser.processLineByLine();
		log("Done.");
	}

	public static ArrayList<Position> buildWallList(String s) throws FileNotFoundException{
		MazeReader parser = new MazeReader(s);
		return parser.processLineByLine();
	}
	/**
	 * Constructor.
	 * 
	 * @param aFileName
	 *            full name of an existing, readable file.
	 */
	public MazeReader(String aFileName) {
		fFile = new File(aFileName);
	}

	/** Template method that calls {@link #processLine(String)}. */
	public final ArrayList<Position> processLineByLine() throws FileNotFoundException {
		// Note that FileReader is used, not File, since File is not Closeable
		Scanner scanner = new Scanner(new FileReader(fFile));
		try {
			// first use a Scanner to get each line
			ArrayList<Position> wallsFromFile = new ArrayList<Position>();
			while (scanner.hasNextLine()) {
				Position e = processLine(scanner.nextLine());
				wallsFromFile.add(e);
			}
			
			return wallsFromFile;
		} finally {
			// ensure the underlying stream is always closed
			// this only has any effect if the item passed to the Scanner
			// constructor implements Closeable (which it does in this case).
			scanner.close();
		}
	}

	/**
	 * Overridable method for processing lines in different ways.
	 * 
	 * <P>
	 * This simple default implementation expects simple name-value pairs,
	 * separated by an '=' sign. Examples of valid input :
	 * <tt>height = 167cm</tt> <tt>mass =  65kg</tt>
	 * <tt>disposition =  "grumpy"</tt>
	 * <tt>this is the name = this is the value</tt>
	 * 
	 * @return
	 */
	protected Position processLine(String aLine) {
		// use a second Scanner to parse the content of each line
		Position p = new Position(0, 0);
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()) {
			String name = scanner.next();
			String value = scanner.next();
			p = new Position(Float.parseFloat(name.trim()), Float.parseFloat(value.trim()));
			log(p);
		} else {
			log("Empty or invalid line. Unable to process.");
		}
		return p;
		// no need to call scanner.close(), since the source is a String
	}

	// PRIVATE
	private final File fFile;

	private static void log(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}

	private String quote(String aText) {
		String QUOTE = "'";
		return QUOTE + aText + QUOTE;
	}
}