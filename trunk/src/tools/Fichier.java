package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Reading and writing in files.
 * 
 * @author Nicolas
 * 
 */
public final class Fichier {

	/** Highscore list. */
	private static LinkedList<Integer> highScore = new LinkedList<Integer>();

	/** Max score. */
	public static final int MAX_SCORE = 10;

	/**
	 * Write a string into a file.
	 * 
	 * @param nomFic
	 *            File to write in
	 * @param i
	 *            String to write
	 */
	public static void ecrire(String nomFic, int i) {

		highScore = getScore(nomFic);
		int position = getInsertionPosition(highScore, i);
		highScore.add(position, i);
		if (highScore.size() > MAX_SCORE) {
			highScore = new LinkedList<Integer>(highScore.subList(0, MAX_SCORE));
		}

		try {
			FileOutputStream fichier = new FileOutputStream(nomFic);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(highScore);
			oos.flush();
			oos.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

	};

	/**
	 * Get the insert position.
	 * 
	 * @param highScore2
	 *            High score list
	 * @param a
	 *            number to insert.
	 * @return The position where to insert.
	 */
	private static int getInsertionPosition(List<Integer> highScore2, int a) {
		int position = 0;
		Iterator<Integer> ite = highScore2.iterator();
		while (ite.hasNext()) {
			int b = ite.next().intValue();
			if (a > b) {
				return position;
			}
			position++;
		}
		return position;
	}

	/**
	 * Generate the score list from the high score file.
	 * 
	 * @param pString
	 *            File to read
	 * @return high score list
	 */
	@SuppressWarnings("unchecked")
	public static LinkedList<Integer> getScore(final String pString) {
		try {
			FileInputStream fichier = new FileInputStream(pString);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			highScore = (LinkedList<Integer>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return highScore;
	}

	/**
	 * Generate a hashmap from a text file.
	 * 
	 * @param pNomFich
	 *            file to read
	 * @return hashmap from the file.
	 */
	public static HashMap<String, String> lire(final String pNomFich) {
		HashMap<String, String> res = new HashMap<String, String>();
		InputStream ips;
		try {
			ips = new FileInputStream(pNomFich);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] tmp = ligne.split("\\s");
				res.put(tmp[0], tmp[1]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	/** Constructor. */
	private Fichier() {
	}
}
