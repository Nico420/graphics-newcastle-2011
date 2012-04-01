package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Read positions from a file.
 * 
 * @author Nicolas
 * 
 */
public class MazeReader {

	/** File to read. */
	private final File fFile;

	/**
	 * Build the wall list.
	 * 
	 * @param s
	 *            The wall file name
	 * @return The wall list
	 */
	public static ArrayList<Position> buildWallList(String s) {
		MazeReader parser = new MazeReader(s);
		return parser.processLineByLine();
	}

	/**
	 * Constructor.
	 * 
	 * @param aFileName
	 *            the file name.
	 */
	public MazeReader(String aFileName) {
		fFile = new File(aFileName);
	}

	/**
	 * File reader.
	 * 
	 * @return Found positions list
	 */
	public final ArrayList<Position> processLineByLine() {
		// Note that FileReader is used, not File, since File is not Closeable

		try {
			Scanner scanner = new Scanner(new FileReader(fFile));
			// first use a Scanner to get each line
			ArrayList<Position> wallsFromFile = new ArrayList<Position>();
			while (scanner.hasNextLine()) {
				Position e = processLine(scanner.nextLine());
				wallsFromFile.add(e);
			}
			scanner.close();
			return wallsFromFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<Position>();
	}

	/**
	 * Read a line, and return a position.
	 * 
	 * @param aLine
	 *            Line to read
	 * @return Position find
	 */
	protected Position processLine(String aLine) {
		// use a second Scanner to parse the content of each line
		Position p = new Position(0, 0);
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()) {
			String name = scanner.next();
			String value = scanner.next();
			p = new Position(Float.parseFloat(name.trim()),
					Float.parseFloat(value.trim()));
		}
		return p;
	}

}