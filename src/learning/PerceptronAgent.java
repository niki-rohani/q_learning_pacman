package learning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import learning.perceptron.LabeledSet;
import learning.perceptron.Perceptron;
import learning.perceptron.SparseVector;
import learning.statistic.Stat;
import learning.statistic.StatisticController;
import learning.tp.MainTool;
import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;
import pacman.prof.Game;
import pacman.prof.GameStateWritable;
import pacman.sma.IntelligentGhost_Agent2;

public class PerceptronAgent implements Agent {

	private ArrayList <Perceptron> t;
	private Sensor S;
	private double lastScore;
	private StatisticController stat;
	private int exemple;
	
	
	public StatisticController getStat() {
		return stat;
	}


	private String move[] = {"HAUT", "BAS", "DROITE", "GAUCHE"};
	
	
	
	public PerceptronAgent(ArrayList <Perceptron> te, Sensor s, String type) {
		if (te == null) {
			te = new ArrayList <Perceptron> ();
			for (int i = 0; i < 4; i++)
				MainTool.randomPerc(100, 0.1, null, 100, s, type);
		}
		else
		t = te;
		S = s;
		lastScore = 0;
		stat = new StatisticController ();
		exemple = 0;
	}
	
	

	public AgentAction getAction(AgentState as, GameState s) {
		int [] inv = {1,0,3,2};
		double score = -10000000;
		int a = 4;
		
	//	System.out.println ("Je suis un perceptron " );
	//	System.out.println ("Alors je vois le jeu");
	//	System.out.println (s);
	//	System.out.println ("La meilleur action est ");
		for (int i = 0; i < 4; i++)
			if (s.isLegalMove(new AgentAction(i), as) == true) {
				SparseVector v = S.getVector(s, null);
				if (t.get(i).getScore(v) >= score )  {
					score = t.get(i).getScore(v);
					a = i;
					lastScore = score;
				}
			}
	//	System.out.println ("  " + move [a] + " avec le score " + score);
		return new AgentAction(a);

	}
	
	
	
	
	public void bruit (double var) {
		for (Perceptron p:t)
			p.bruit (var);
	}
	
	
	public ArrayList <Perceptron> getPerceptron() {
		return t;
	}
	
	public void setEpsilon (double e) {
		for (Perceptron p:t)
			p.setE(e);
	}
	
	public void setTour (int tour) {
		for (Perceptron p:t)
			p.setNbIteration(tour);
	}
	
	/**
	 * Permet d'entrainer tout les perceptron
	 * @param s ArrayList de labeled set représentant les example pour chaque perceptron
	 */
	public void train (ArrayList <LabeledSet> s, ArrayList <LabeledSet> testing, String map, String reward) {
		float time = System.nanoTime();
		System.out.println ("Entrainement des " + t.size() + "Perceptron");
		for (int i = 0; i < t.size(); i++) {
			t.get(i).train (s.get(i));
			exemple = exemple + s.get(i).size();
		}
		
		double score = RewardTools.getAverageRewardStat(this);
				stat.add(S.size(), Stat.PERCEPTRON0_FOUR, t.get(0).getE(), t.get(0).getNbIteration(), exemple, getAccuracy (testing), map, reward, score);
		
		int testingSize = 0;
		int trainingSize = 0;
		
		for (LabeledSet train:s)
			trainingSize = trainingSize + train.size();
		
		
		
		System.out.println ("=====================================");
		System.out.println ("Statistiques d'apprentissage ");
		System.out.println ("Liste de statistique : ");
		System.out.println (stat.getErrorByExemple());
		System.out.println ("Taille training set " + trainingSize);
		
		
		time = System.nanoTime() - time;
		
		System.out.println ("Temps d'apprentissage " + ( time / 1000000000.0 ) + " s" );
		}
	
	
	/**
	 * Renvoie le taux d'erreur pour un labeledSet et une action
	 * @param s
	 * @param a
	 * @return
	 */
	private double getAccuracyAction (LabeledSet s, int a) {
		return t.get(a).getError(s);
	}
	
	/**
	 * Renvoie le taux d'erreur pour un labeledSet
	 * @param s LabeledSet
	 * @return
	 */
	public double getAccuracy (ArrayList <LabeledSet> s) {
		double acc = 0;
		for (int i = 0; i < 4; i++)
			acc = acc + getAccuracyAction(s.get(i), i);
		System.out.println ("Taux d'erreur pour un exemple de test de " + (s.get(0).size() + s.get(1).size() + s.get(2).size() + s.get(3).size()) + "    erreur : " + acc );
		return acc;
	}
	
	
	/**
	 * Permet de sauvegarder les données des perceptron dans un fichier
	 * @param f Nom du fichier où sauvegarder les données
	 */
	public void save(String f) {
		String save = "";
		for (Perceptron te:t) 
			save = save + te.save() + System.getProperty("line.separator");
		try {
			FileWriter file = new FileWriter (new File (f));
			file.write (save);
			file.flush();
			file.close();
		}
		catch (IOException e) {
			System.err.println("Erreur d'ouverture de fichier " + e);
		}
	}
	
	/**
	 * Permet de charger les données des perceptron depuis un fichier
	 * @param f Nom du fichier où charger les données
	 */
	public void load (String f) {
			BufferedReader file = null;
		try { 
			file = new BufferedReader (new FileReader (new File (f)));
		}
		catch (IOException e) {
			
		}
		if (file == null)
			System.exit(0);
		for (Perceptron te:t)
			try {
				te.load(file.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getScore() {
		return lastScore;
	}



	
	public double getScoreMax(AgentState pacmanState, GameStateWritable state) {
		double score = -10000000;
		int a = 4;
		
	/*	System.out.println ("Je suis un perceptron t" );
		System.out.println ("Alors je vois le jeu");
		System.out.println (state);
		System.out.println ("La meilleur action est ");
	*/	for (int i = 0; i < 4; i++)
			if (state.isLegalMove(new AgentAction(i), pacmanState) == true) {

				SparseVector v = S.getVector(state, null);
				if (t.get(i).getScore(v) >= score )  {
					score = t.get(i).getScore(v);
					a = i;
				}
			}
	/*	System.out.println ("  " + move [a] + " avec le score " + score);
	*/	return score;

	}
	
	
	public double getScoreMin(AgentState pacmanState, GameStateWritable state) {
		double score = +10000000;
		int a = 4;
		
		System.out.println ("Je suis un perceptron t" );
		System.out.println ("Alors je vois le jeu");
		System.out.println (state);
		System.out.println ("La meilleur action est ");
		for (int i = 0; i < 4; i++)
			if (state.isLegalMove(new AgentAction(i), pacmanState) == true) {

				SparseVector v = S.getVector(state, null);
				if (t.get(i).getScore(v) < score )  {
					score = t.get(i).getScore(v);
					a = i;
				}
			}
		System.out.println ("  " + move [a] + " avec le score " + score);
		return score;

	}



	public int getSurvival(String layout) {
		Agent agent_pacman = this;
		
		GameStateWritable state;
		 state = MainTool.initMaze (layout);
		
		ArrayList <Agent> ghosts = new ArrayList <Agent> ();
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts.add (new IntelligentGhost_Agent2(0.8));
	
		
		
		
		Reward reward = new SimpleReward();
		
		
		
		Game runing = RewardTools.vizualize(state,        agent_pacman   , ghosts, reward, 30, 0, null);
		GameState state0 = runing.runUntilEnd(1);
		
		
		return state0.getTime();
	}
}
