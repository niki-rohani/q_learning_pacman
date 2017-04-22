package learning;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import learning.perceptron.SparseVector;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.GameState;
import pacman.eleves.MazeException;
import pacman.prof.Game;
import pacman.prof.GameObserver;
import pacman.prof.GameStateWritable;
import pacman.prof.MazeWritable;
import pacman.prof.graphics.GamePanel;


/**
 * Classe Permettant de calculer des quadruplet et de les stocker. 
 * Calcul un perceptron de t + 1
 * 
 * @author Dantidot
 * 
 */
public class FSAplus extends FSA {

	PerceptronAgent agent;
	/**
	 * 
	 * @param pacwoman
	 *            The agent to run the quadruplet
	 */
	public FSAplus(PerceptronAgent pacwoman, String pacman) {
		super (pacman);
		agent = pacwoman;
		
	}
	
	

	/**
	 * Genere des quadruplet pour un agent
	 * 
	 * @param layout
	 *            Le layout
	 * @param ghost
	 */
	public void generate(String layout, int ghost, int size, int sensor) {
		
		Agent agent = null;
		try {

			agent = (Agent) Class.forName(pacman).newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MazeWritable maze = null;
		try {
			maze = new MazeWritable(layout);
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
			System.exit(1);
		}

		Game game = new Game(state);

		game.addPacmanAgent(agent);

		ArrayList<Agent> ghostt = new ArrayList<Agent>();

		for (int i = 0; i < maze.getNumberOfGhosts(); i++) {

			ghostt.add(new pacman.sma.IntelligentGhost_Agent2(0.2));

			game.addGhostAgent(ghostt.get(ghostt.size() - 1));
		}

		boolean flag = true;

		int time = 0;

		while (flag) {
			GameState s = state.copy();
			int a = agent.getAction(state.getPacmanState(0), state)
					.getDirection();
			if (state.isLegalMove(
					agent.getAction(state.getPacmanState(0), state),
					state.getPacmanState(0))) {

			//	GameState sgame = state.copy();

				state = (GameStateWritable) state
						.nextStatePacman(new AgentAction(a));

				
				
				/* Old Version */
				
				
				
				
				   
				   //////////////////// Perceptron t + n /////////////////////////////////
							double r = new SimpleReward().getReward(s, state);
							GameStateWritable s0 = (GameStateWritable) state.copy();
							for (int i = 0; i < 4; i++) {
								r = r + this.agent.getScoreMin (s0.getPacmanState(0), s0);
								s0.nextStatePacman(this.agent.getAction(s0.getPacmanState(0), s0));
							}
							/////////////////////////////////////////////////////////////////
									System.out.println ("Reward is " + r + " For action " + a);
				fsa.add(new FSAState(s, state.copy(), a, r, sensor));

				ArrayList<AgentAction> ghost_action = new ArrayList<AgentAction>();
				for (int anonymous = 0; anonymous < state.getNumberOfGhosts(); anonymous++)
					ghost_action.add(ghostt.get(anonymous).getAction(
							state.getGhostState(anonymous), state));

				state = (GameStateWritable) state.nextStateGhosts(ghost_action);
				
				if (state.isWin() || state.isLose() || time > size)
					flag = false;

			}

			time++;

		}
		

			}

	/**
	 * Genere des quadruplet pour un agent
	 * 
	 * @param layout
	 *            Le layout
	 * @param ghost
	 */
	public int generate(String layout, int ghost, int size, int sensor, int t, int imax) {
		
		Agent agent = null;
		try {

			agent = (Agent) Class.forName(pacman).newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MazeWritable maze = null;
		try {
			maze = new MazeWritable(layout);
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
			System.exit(1);
		}

		Game game = new Game(state);

		game.addPacmanAgent(agent);

		ArrayList<Agent> ghostt = new ArrayList<Agent>();

		for (int i = 0; i < maze.getNumberOfGhosts(); i++) {

			ghostt.add(new pacman.sma.IntelligentGhost_Agent2(0.2));

			game.addGhostAgent(ghostt.get(ghostt.size() - 1));
		}

		boolean flag = true;

		int time = 0;

		while (flag) {
			GameState s = state.copy();
			int a = agent.getAction(state.getPacmanState(0), state)
					.getDirection();
			if (state.isLegalMove(
					agent.getAction(state.getPacmanState(0), state),
					state.getPacmanState(0))) {

			//	GameState sgame = state.copy();

				state = (GameStateWritable) state
						.nextStatePacman(new AgentAction(a));

				
				
				/* Old Version */
				
				
				
				
				   //////////////////// Perceptron t + n /////////////////////////////////
				double r = new SimpleReward().getReward(s, state);
				GameStateWritable s0 = (GameStateWritable) state.copy();
				for (int i = 0; i < 1; i++) {
					r = r + this.agent.getScoreMin (s0.getPacmanState(0), s0);
					s0.nextStatePacman(this.agent.getAction(s0.getPacmanState(0), s0));
				}
				/////////////////////////////////////////////////////////////////
					System.out.println ("Reward is " + r + " For action " + a);
				fsa.add(new FSAState(s, state.copy(), a, r, sensor));

				ArrayList<AgentAction> ghost_action = new ArrayList<AgentAction>();
				for (int anonymous = 0; anonymous < state.getNumberOfGhosts(); anonymous++)
					ghost_action.add(ghostt.get(anonymous).getAction(
							state.getGhostState(anonymous), state));

				state = (GameStateWritable) state.nextStateGhosts(ghost_action);
				
				if (state.isWin() || state.isLose() || time > size)
					flag = false;

			}

			time++;
			t++;
			if (t > imax)
				return t;
		}
		
		return t;
			}

	
	/**
	 * Vector : R�cupere les vecteur pour les quadruplet
	 */

	public void vector() {
		saV.clear();
		for (int i = 0; i < fsa.size(); i++) {
			saV.add(fsa.get(i).vector());
		}
	}

	public ArrayList<SparseVector> getVector() {
		return saV;
	}

	public double getReward(int i) {
		return fsa.get(i).getReward();
	}

	public ArrayList<FSAState> getFSA() {
		return fsa;
	}
	
	
	public void clear() {
		fsa.clear();
		saV.clear();
	}

}
