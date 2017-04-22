package learning;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import learning.tp.MainTool;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;
import pacman.prof.Game;
import pacman.prof.GameStateWritable;
import pacman.prof.graphics.GamePanel;
import pacman.sma.IntelligentGhost_Agent2;

public class RewardTools {

	public static double getReward(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int size_max_trajectory)
	{
		GameState s=initial_state.copy();
		double rs=0;
		int time=0;
		boolean flag=true;		
		while(flag)
		{
			GameState iv=s.copy();
			AgentAction action=pacman_agent.getAction(s.getPacmanState(0),s);
			s=s.nextStatePacman(action);
			
			if ((!s.isLose()) && (!s.isWin()))
			{
				ArrayList<AgentAction> gactions=new ArrayList<AgentAction>();
				for(int i=0;i<ghosts_agents.size();i++)
				{
					Agent a=ghosts_agents.get(i);
					AgentState ss=s.getGhostState(i);
					AgentAction aa=a.getAction(ss, s);
					gactions.add(a.getAction(ss,s));				
				}			
				s=s.nextStateGhosts(gactions);
				double r=reward.getReward(iv, s);
				rs+=r;
			}
			else
			{
				double r=reward.getReward(iv, s);
				rs+=r;
				flag=false;
			}
			
			if (s.isLose() || s.isWin())
				flag=false;
			
			if (time++>=(size_max_trajectory-1))
				flag=false;			
		}
		
		return(rs);
	}	
	
	
	// Retourne le reward global
	public static double getAverageReward(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int sizemax_trajectory,int nb_trajectories)
	{
		double corbeau = 0.0;
		
		
		
		for (int i = 0; i < nb_trajectories; i ++ ) 
			corbeau = corbeau + getReward (initial_state, pacman_agent, ghosts_agents, reward, sizemax_trajectory);
		
		corbeau /= nb_trajectories;
		
		return corbeau;
		
	}
	
	/**
	 * Permet de visualiser un agent
	 * @param initial_state
	 * @param pacman_agent
	 * @param ghosts_agents
	 * @param reward
	 * @param timestep
	 */
	public static void vizualize(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int size_max_trajectory,int timestep)
	{
		Game game=new Game(initial_state);
		game.addPacmanAgent(pacman_agent);
				
		for(Agent a:ghosts_agents)
			game.addGhostAgent(a);	
		
		GamePanel panel=new GamePanel(initial_state);
		game.addObserver(panel);			
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640,480));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		GameState fstate=game.runUntilEnd(timestep,size_max_trajectory);				
	}	
	
	
	
	public static Game vizualize(GameState initial_state,Agent pacman_agent,ArrayList<Agent> ghosts_agents,Reward reward,int size_max_trajectory,int timestep, JPanel frame)
	{
		Game game=new Game(initial_state);
		game.addPacmanAgent(pacman_agent);
		
		for(Agent a:ghosts_agents)
			game.addGhostAgent(a);	
		
		if (frame == null)
			return game;
		GamePanel panel=new GamePanel(initial_state);
		game.addObserver(panel);			
		
		frame.removeAll();
		frame.add(panel);
		panel.setPreferredSize (new Dimension (600, 500));
		frame.repaint();
		frame.setVisible(false);
		frame.setVisible(true);
		
		
		return game;
	}


	public static double getAverageRewardStat(PerceptronAgent perceptronAgent) {
		// TODO Auto-generated method stub
		
		String [] arg = {"500", "layouts/mediumClassic.lay" } ;
		
		Agent agent_ghost;
			agent_ghost = new IntelligentGhost_Agent2(0.1);
		
		GameStateWritable state;
		 state = MainTool.initMaze (arg[1]);
		
		
		ArrayList <Agent> ghosts = new ArrayList <Agent> ();
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts.add (agent_ghost);
	
		Reward reward = new SimpleReward();
		
		
		
		return getAverageReward (state, perceptronAgent, ghosts, reward, 100, 100);
		
	}	
	
}
