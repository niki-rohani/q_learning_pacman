package learning.statistic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import learning.perceptron.Perceptron;



/**
 * Classe permettant de garde la trace des statistiques d'un perceptron
 * @author Niki Rohani 2700881
 *
 */
public class StatisticController {

	
	private ArrayList <Stat> s;
	
	public StatisticController () {
		s = new ArrayList <Stat> ();
	}
	
	
	/**
	 * Ajoute un nouveau set de stat
	 * @param sensor Taille du sensor
	 * @param perceptron Type de perceptron @see Stat.PERCEPTRON0_FOUR
	 * 										@see Stat.PERCEPTRONt_FOUR
	 * @param epsilon Epsilon utilisé pour l'apprentissage
	 * @param tour Nombre de tour
	 * @param exemple Nombre d'exemple
	 * @param err Taux d'erreur
	 * @param map
	 * @param reward
	 */
	public void add (int sensor, int perceptron, double epsilon, int tour, int exemple, double err, String map, String reward, double score) {
		Stat stat = new Stat (perceptron, epsilon, map, reward, sensor, tour, exemple, score);
		stat.setErr(err);
		s.add (stat);
	}
	
	public void displayErrorByExemple() {
		for (Stat stat:s) 
			System.out.println (stat.getVectorErrorByExemple());
	}
	
	public void displayErrorByTime() {
		for (Stat stat:s) 
			System.out.println (stat.getVectorErrorByTime());
	}
	
	public String save() {
		File repertoire = new File(".");
		int children = repertoire.list().length; 
		String save = "";
		String fileName = (s.get(0).getPerceptronType() == Stat.PERCEPTRON0_FOUR)? ( "Perceptron_0_" + children ) : ("Perceptron_t_" + children) ;
		for (Stat stat:s) 
			save = save + stat.getVectorError() + System.getProperty("line.separator");
		try {
			FileWriter file = new FileWriter (new File (fileName));
			file.write (save);
			file.flush();
			file.close();
			return fileName;
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
			return "Erreur durant la sauvegarde";
		}
	}
	
	public String getErrorByExemple() {
		String error = "";
		for (Stat stat:s) 
			error = error + (stat.getVectorErrorByExemple()) + System.getProperty("line.separator");
		return error;
	}


	public String savePerf() {
		File repertoire = new File(".");
		int children = repertoire.list().length; 
		String save = "";
		String fileName = (s.get(0).getPerceptronType() == Stat.PERCEPTRON0_FOUR)? ( "Perceptron_0_" + children + "_Perf" ) : ("Perceptron_t_" + children + "_Perf") ;
		for (Stat stat:s) 
			save = save + stat.getScore() + " " + stat.getExemple() + System.getProperty("line.separator");
		try {
			FileWriter file = new FileWriter (new File (fileName));
			file.write (save);
			file.flush();
			file.close();
			return fileName;
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
			return "Erreur durant la sauvegarde";
		}
	
	}
	
	
	
	
}
