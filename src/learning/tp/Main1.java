package learning.tp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import pacman.eleves.Agent;
import pacman.eleves.GameState;
import pacman.eleves.MazeException;
import pacman.prof.Game;
import pacman.prof.GameStateWritable;

import pacman.sma.IntelligentGhost_Agent2;

import learning.PerceptronAgent;
import learning.Reward;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.perceptron.Perceptron;


public class Main1 {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) 
	{

		int timestep=50;
		String [] arg = {"500", "layouts/mediumClassic.lay" } ;
		
		
		// Random 
		Agent agent_pacman = null;
		
		
		
		
		Agent agent_ghost;
			agent_ghost = new IntelligentGhost_Agent2(0.1);
		
		
		GameStateWritable state;
		 state = MainTool.initMaze (arg[1]);
		
		
		ArrayList <Agent> ghosts = new ArrayList <Agent> ();
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts.add (agent_ghost);
	
		
		
		
		Reward reward = new SimpleReward();
		
		
		Random.print();
		
		Random.run(arg);
		System.out.println();
		Random.print();
		
		System.out.println ("init pacman");
		   agent_pacman = Random.elt.get(0).perceptron;
		
		
		
		System.out.println (RewardTools.getAverageReward(state, agent_pacman, ghosts, reward, 100, 10000));
		
		System.out.println("visualize");
		RewardTools.vizualize(state,        agent_pacman   , ghosts, reward, 30, timestep);
		
		
		
		
	
		
		
		
	}
}
