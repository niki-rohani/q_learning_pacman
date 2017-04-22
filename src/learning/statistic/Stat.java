package learning.statistic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import learning.perceptron.Perceptron;
import learning.perceptron.SparseVector;


public class Stat {


public static int PERCEPTRON0_FOUR = 0;
static int PERCEPTRONt_FOUR = 1;
static int PERCEPTRON0 = 2;
static int PERCEPTRONt = 3;
	
	/**
	 * Nombre d'exemples
	 */
	private int exemple;
	
	/**
	 * Epsilon utilisé lors de l'apprentissage
	 */
	private double epsilon;
	
	/**
	 * Indice du tableau
	 */
	private String map;
	
	/**
	 * Type de reward utilisé
	 */
	private String reward;
	
	/**
	 * Taille de sensor
	 */
	private int s;
	
	/**
	 * Type de perceptron utilisé
	 */
	private int perceptronType;
	
	/**
	 * Nombre de tour effectués
	 */
	private int tour;
	
	/**
	 * Taux d'erreur
	 */
	private double err;
	
	private double score;
	
	public Stat (int type) {
		exemple = 0;
		epsilon = 0;
		map = "";
		reward = "";
		s = 0;
		perceptronType = type;
		tour = 0;
		err = 0;
	}
	
	public int getExemple() {
		return exemple;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public String getMap() {
		return map;
	}

	public String getReward() {
		return reward;
	}

	public int getS() {
		return s;
	}

	public int getPerceptronType() {
		return perceptronType;
	}

	public int getTour() {
		return tour;
	}

	public double getErr() {
		return err;
	}

	public void setExemple(int exemple) {
		this.exemple = exemple;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public void setS(int s) {
		this.s = s;
	}

	public void setPerceptronType(int perceptronType) {
		this.perceptronType = perceptronType;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public void setErr(double err) {
		this.err = err;
	}

	public Stat (int type, double e, String m, String r, int sensor, int t, int exem, double scr) {
		perceptronType = type;
		epsilon = e;
		map = m;
		reward = r;
		s = sensor;
		tour = t;
		exemple = exem;
		score = scr;
	}
	
	
	public SparseVector getVectorErrorByTime () {
		SparseVector v = new SparseVector (2);
		v.setValue(0, err);
		v.setValue(1, tour);
		return v;
	}
	
	public SparseVector getVectorErrorByExemple () {
		SparseVector vector = new SparseVector (2);
		vector.setValue (0, err);
		vector.setValue (1, exemple);
		return vector;
	}
	
	public String getVectorError () {
		return err + " " + exemple;
	}
	
	
	/**
	 * Save dans le fichier f les statistique des erreur par rapport aux Tours
	 * @param f String
	 */
	public void saveErrorByTime(String f) {
		try {
			FileWriter file = new FileWriter (new File (f));
			file.write (getVectorErrorByTime().toString());
			file.flush();
			file.close();
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
		}
	}
	
	
	/**
	 * Save dans le fichier f les statistique des erreur par rapport aux Exemples
	 * @param f String
	 */
	public void saveErrorByExemple (String f) {
		try {
			FileWriter file = new FileWriter (new File (f));
			file.write (getVectorErrorByExemple().toString());
			file.flush();
			file.close();
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
		}
	}
	
	/**
	 * Save dans le fichier f les statistique des erreur par rapport aux Exemples
	 * @param f String
	 */
	public void savePerformanceByExemple (String f) {
		try {
			FileWriter file = new FileWriter (new File (f));
			file.write (getVectorErrorByExemple().toString());
			file.flush();
			file.close();
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
		}
	}
	
	
	/** 
	 * Crée un exemple statistique à partir d'un exemple déjà existant
	 * @param stat Object de statistique déjà existant
	 * @param exemple Nombre d'exemple suplémentaires
	 * @param err Taux d'erreur
	 * @param t Nombre de tour suplémentaires
	 * @return
	 */
	public static Stat getStatFromStat (Stat stat, int exemple, double err, int t, double score) {
		Stat r = new Stat (stat.getPerceptronType(), stat.getEpsilon(), stat.getMap(), stat.getReward(), stat.getS(), stat.getTour() + t, stat.getExemple() + exemple, stat.getScore() + score) ;
		r.setErr (err);
		return r;
	}

	public double getScore() {
		return score;
	}
	
}
