package learning.tp;

import learning.gui.ExperimentalQLearning;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import learning.CapsuleStateSensor;
import learning.FSA;
import learning.FSAplus;
import learning.PerceptronAgent;
import learning.Sensor;
import learning.StateSensor;
import learning.perceptron.LabeledSet;
import learning.perceptron.Perceptron;
import learning.perceptron.SparseVector;
import learning.statistic.StatisticController;

import pacman.eleves.GameState;
import pacman.eleves.MazeException;
import pacman.prof.GameStateWritable;
import pacman.prof.MazeWritable;

/**
 * MainTool Outils principaux
 * 
 * @author 2700881
 * 
 */

public class MainTool {

	/**
	 * Permet de g√©n√©rer un Maze
	 * 
	 * @param layout_file
	 *            : fichier de description
	 * @return
	 */
	public static GameStateWritable initMaze(String layout_file) {
		MazeWritable maze = null;
		try {
			maze = new MazeWritable(layout_file);
		} catch (MazeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameStateWritable state = new GameStateWritable(maze);

		int sx = maze.getPacmanStartX(0);
		int sy = maze.getPacmanStartY(0);
		int dx = 1;
		int dy = 1;

		if ((maze.isWall(sx, sy) || (maze.isWall(dx, dy)))) {
			System.out.println("Probleme mur...");
			// System.exit(1);
		}

		return state;

	}

	/**
	 * Retourne un Perceptron hasard
	 * 
	 * @param v
	 *            taille vectorielle
	 * @return
	 */
	public static Perceptron randomPerc(int v, double e) {

		int POSITION = 2;
		Perceptron c = new Perceptron(100, e, v);

		for (int i = 0; i < c.getW().size(); i++) {
			c.getW().setValue(i, (int) (Math.random() * 2));
		}
		return c;

	}

	public static PerceptronAgent randomPerc(int v, double e,
			ExperimentalQLearning eql, int tour, Sensor s, String type) {

		ArrayList<Perceptron> p = new ArrayList<Perceptron>();

		for (int i = 0; i < 4; i++) {
			p.add(new Perceptron(tour, e, v, eql, type, i));
			for (int pi = 0; pi < p.get(i).getW().size(); pi++) {
				p.get(i).getW().setValue(pi, (Math.random()));
			}
		}

		PerceptronAgent perceptron = new PerceptronAgent(p, s, type);

		return perceptron;

	}

	/**
	 * Permet de crÈer et entrainer un nouveau perceptron Agent
	 * 
	 * @param fsa
	 *            La classe permettant de gÈrer les examples
	 * @param p
	 *            Si null alors on crÈe un nouveau perceptronAgent, sinon on
	 *            prends celui existant afin de faire un nouvel entrainement
	 * @param e
	 *            Pas d'apprentissage
	 * @param train
	 *            Nombre de tour d'apprentissage
	 * @param f
	 *            Fenetre
	 * @param direc
	 * @return
	 */
	public static PerceptronAgent perceptron(FSA fsa, PerceptronAgent p,
			double e, int train, ExperimentalQLearning f, boolean direc,
			String map, String reward, String type) {

		int v = fsa.getVector().get(0).size();
		// Le label permet de gÔøΩnerer nos exemple d'apprentissage
		ArrayList<LabeledSet> s = new ArrayList<LabeledSet>();
		ArrayList<LabeledSet> testing = new ArrayList<LabeledSet>();

		for (int i = 0; i < 4; i++) {
			s.add(new LabeledSet(fsa.getVector().get(0).size()));
			testing.add(new LabeledSet(fsa.getVector().get(0).size()));
		}

		System.out.println("Generation des exemples ... ");

		System.out.println("Generation des exemple training ");

		int training = 0;
		// On gÔøΩnere les exemple
		for (int i = 0; i < fsa.getVector().size() / 2; i++) {
			// On rÔøΩcupere le vecteur et la rÔøΩcompense
			s.get(fsa.getFSA().get(i).getA()).addExample(
					fsa.getFSA().get(i).vector(), fsa.getReward(i));
			training++;
		}

		System.out.println(" 		... Fait       Taille " + training + " ");

		System.out.println("Generation des exemple testing ");

		for (int i = fsa.getVector().size() / 2; i < fsa.getVector().size(); i++) {
			// On rÔøΩcupere le vecteur et la rÔøΩcompense
			testing.get(fsa.getFSA().get(i).getA()).addExample(
					fsa.getFSA().get(i).vector(), fsa.getReward(i));

		}

		System.out.println("		 ... Fait     Taille " + fsa.getVector().size()
				/ 2);

		PerceptronAgent fitted = null;
		if (p == null) {
			ArrayList<Perceptron> perceptron = new ArrayList<Perceptron>();
			for (int i = 0; i < 4; i++)
				perceptron.add(new Perceptron(train, e, v, f, type, i));
			p = new PerceptronAgent(perceptron, null, type);

		} else {

			p.save("perc");
			if (direc) {

			} else
				fitted = new PerceptronAgent(null, null, type);
			p.load("perc");

		}

		System.out.print("GENERATION PERCEPTRON ... ");
		if (direc)
			p.train(s, testing, map, reward);
		else
			fitted.train(s, testing, map, reward);
		System.out.println("GENERATION FAIT en " + train + " ÔøΩtapes ");

		if (direc)
			return p;
		return fitted;

	}

	public static StatisticController getStatisticSet(ExperimentalQLearning eql) {
		StatisticController stats = new StatisticController();

		// Nous allons crÈer 4 classes de statistiques

		// Classe 1 : Sensor 1, nbExemples = 50k, nbtour = 10000,
		// Classe 2 : Sensor 3, nbExemples = 50k, nbtour = 10000,
		// Classe 3 : Sensor 3, nbExemples = 50k, nbtour = 10000, epsilon = 0.4,
		// puis 0.01
		// Classe 4 : Sensor 3, nbExemples = 200k, nbtour = 10000, epsilon =
		// 0.01
		// Classe 5 : Sensor 3, nbExemples = 200k, nbtour = 10000, epsilon =
		// 0.01, Reward : +1 mangÈ, -1 perdu

		// Classe 6 Perceptron t : Sensor 1, nbExemples = 50k, nbtour = 10000,
		// Classe 7 : Sensor 3, nbExemples = 50k, nbtour = 10000,
		// Classe 8 : Sensor 3, nbExemples = 50k, nbtour = 10000, epsilon = 0.4,
		// puis 0.01
		// Classe 9 : Sensor 3, nbExemples = 200k, nbtour = 10000, epsilon =
		// 0.01
		// Classe 10 : Sensor 3, nbExemples = 200k, nbtour = 10000, epsilon =
		// 0.01, Reward : +1 mangÈ, -1 perdu

		// Preparation des outils :
		// ArrayList de fsa et fsaPlus
		String agent = "pacman.prof.tp1.RandomAgent";

		FSA fsa = new FSA(agent);
		FSAplus fsaplus;

		// ParamËtrage :
		int sensor1 = 1;
		int sensor3 = 3;

		int exemple50 = 50000;
		int exemple200 = 200000;

		String layout = "layouts/mediumClassic.lay";

		// ///////////////////////////////////// CrÈation d'exemples pour sensor
		// 1 P0 ////////////////////////////////////////////////
		int i = 0;
		double err = 0;
		while (i < exemple50) {
			// GENERATION des quadruplets, en mettant 10000 le step max de
			// chaque partie
			i = fsa.generate(layout, 2, 10000, sensor1, i, exemple50);
			System.out.println("Generation Exemple C1 " + fsa.getFSA().size()
					+ " exemples");
		}
		fsa.vector();
		System.out.println("Exemples C1 GÈnÈrÈs ......... "
				+ fsa.getFSA().size() + " exemples");
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("Generation perceptron 1 - " + i);
		// //////////////////////////////// CrÈation du perceptron C1 P0
		// //////////////////////////////////////////////
		StateSensor s = new CapsuleStateSensor(sensor1);
		Sensor environment = new Sensor(s);
		PerceptronAgent perceptron = MainTool.randomPerc(environment.size(),
				0.01, eql, 100, environment, "0");
		MainTool.perceptron(fsa, perceptron, 0.01, 100, eql, true, layout,
				"CapsuleStateReward", "0");

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// ///////////////////////////// RÈcupÈration des statistiques du
		// perceptron ////////////////////////////////
		err = perceptron.getSurvival(layout);

		stats.add(sensor1, 0, 0.01, 100, 1, err, layout, "", 0);
		System.out.println("GÈnÈration perceptron Classe 1 - TerminÈ");

		// ////////////////////////////////////////////////////////// CrÈation
		// du perceptron C6 Pt /////////////////////////////////////////
		fsaplus = new FSAplus(perceptron, agent);
		i = 0;
		err = 0;
		// GENERATION des quadruplets, en mettant 10000 le step max de chaque
		// partie
		while (i < exemple50) {
		i = fsaplus.generate(layout, 2, 10000, sensor1, i, exemple50);
		System.out.println("Generation Exemple C6 " + fsaplus.getFSA().size()
				+ " exemples");
		}

		fsaplus.vector();
		System.out.println("Exemples C6 GÈnÈrÈs ......... "
				+ fsaplus.getFSA().size() + " exemples");
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		s = new CapsuleStateSensor(sensor1);
		environment = new Sensor(s);
		PerceptronAgent perceptront = MainTool.randomPerc(environment.size(),
				0.01, eql, 10000, environment, "t");
		MainTool.perceptron(fsaplus, perceptront, 0.01, 100, eql, true,
				layout, "CapsuleStateReward", "0");

		err = perceptront.getSurvival(layout);

		stats.add(sensor1, 0, 0.01, 100, 1, err, layout, "", 0);

		System.out.println("Generation C6 Fait");
		return stats;
	}

}
