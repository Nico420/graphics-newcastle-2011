package tools;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Fichier {
	
	public static final int MAX_SCORE=10;
	static List<String> highScore = new ArrayList<String>();
	
	public static void ecrire(String nomFic, String texte)
	{
		//on va chercher le chemin et le nom du fichier et on me tout ca dans un String
		String adressedufichier = System.getProperty("user.dir") + "/"+ nomFic;
	
		//on met try si jamais il y a une exception
		try
		{
			
			Scanner scanner = new Scanner(new FileReader(nomFic));
			try {
				// first use a Scanner to get each line
				
				while (scanner.hasNextLine()) {
					highScore.add(scanner.next());
				}
				if(highScore.size()>MAX_SCORE){
					highScore=highScore.subList(0, highScore.size()-2);
				}
			} finally {
				scanner.close();
			}
			/**
			 * BufferedWriter a besoin d un FileWriter, 
			 * les 2 vont ensemble, on donne comme argument le nom du fichier
			 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 
			 
			 */
			FileWriter fw = new FileWriter(adressedufichier, true);
			
			// le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
			BufferedWriter output = new BufferedWriter(fw);
			//highScore = insert(highScore,texte);
			//on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
			Iterator<String> ite = highScore.iterator();
			while(ite.hasNext()){
				output.write(ite.next());
			}
			//on peut utiliser plusieurs fois methode write
			
			output.flush();
			//ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter
			
			output.close();
			//et on le ferme
			System.out.println("fichier créé");
		}
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
			}

	}

	/*private static List<String> insert(List<String> highScore2, String texte) {
		List<String> res = new ArrayList<String>();
		Iterator<String> ite = highScore2.iterator();
		int value = Integer.parseInt(texte);
		boolean insertion=false;
		while(ite.hasNext()){
			
			Scanner scanner = new Scanner(ite.next());
			scanner.useDelimiter(" - ");
			if (scanner.hasNext()) {
				String name = scanner.next();
				String value2 = scanner.next();
				
				if(Integer.parseInt(value2)<value && !insertion){
					res.add(name+" - "+texte);
					insertion=true;
				}
				res.add(name+" - "+value2);
				
			}
		}
		return res;
		
		
	}*/
}
