package tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeReader {

	private final File fFile;

	public static ArrayList<Position> buildWallList(String s) throws FileNotFoundException{
		MazeReader parser = new MazeReader(s);
		return parser.processLineByLine();
	}

	
	public MazeReader(String aFileName) {
		fFile = new File(aFileName);
	}

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
			scanner.close();
		}
	}

	/**
	 * Read a line, and return a position.
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
	}


	private static void log(Object aObject) {
		System.err.println(String.valueOf(aObject));
	}
	
	public static void main(String[] aArgs) throws FileNotFoundException {
		MazeReader parser = new MazeReader("maze.txt");
		parser.processLineByLine();
	}
}