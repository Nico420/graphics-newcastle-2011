package tools;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reading and writing in files.
 * 
 * @author Nicolas
 * 
 */
public final class Fichier {

	/** Max score. */
	public static final int MAX_SCORE = 10;

	/**
	 * Write a string into a file.
	 * 
	 * @param nomFic
	 *            File to write in
	 * @param i
	 *            String to write
	 * @param pName
	 *            Fichier name
	 */
	public static void ecrire(String nomFic, int i, String pName) {

		ArrayList<String> highScore = getScore(nomFic);
		int position = getInsertionPosition(highScore, i);
		if (position != -1) {
			highScore.add(position, i + " " + pName);

			try {
				FileOutputStream fichier = new FileOutputStream(nomFic);
				ObjectOutputStream oos = new ObjectOutputStream(fichier);
				for (int j = 0; j < highScore.size() && j < MAX_SCORE; j++) {
					oos.writeObject(highScore.get(j));
				}
				oos.flush();
				oos.close();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}

	};

	/**
	 * Get the insert position.
	 * 
	 * @param highScore
	 *            High score list
	 * @param a
	 *            number to insert.
	 * @return The position where to insert.
	 */
	private static int getInsertionPosition(ArrayList<String> highScore, int a) {
		if (highScore.size() > 0) {
			for (int i = 0; i < highScore.size(); i++) {
				if (a > Integer.parseInt(highScore.get(i).split("\\s")[0])) {
					return i;
				}
			}
			return -1;
		}
		return 0;
	}

	/**
	 * Generate the score list from the high score file.
	 * 
	 * @param pString
	 *            File to read
	 * @return high score list
	 */
	public static ArrayList<String> getScore(final String pString) {
		ArrayList<String> res = new ArrayList<String>();
		try {

			try {
				FileInputStream fichier = new FileInputStream(pString);
				ObjectInputStream ois = new ObjectInputStream(fichier);
				String line;
				while ((line = (String) ois.readObject()) != null) {
					res.add(line);
				}
			} catch (EOFException e) {
				// Do nothing
				e.getCause();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
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
