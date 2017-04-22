package pacman.prof;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;

import java.util.ArrayList;

public class Game 
{
	int time;
	protected GameState state;
	protected ArrayList<Agent> pacmansAgents;
	protected ArrayList<Agent> ghostsAgents;
	protected ArrayList<GameObserver> observers;
   
	
	public Game(GameState state)
	{
		this.state=state;	
		pacmansAgents=new ArrayList<Agent>();
		ghostsAgents=new ArrayList<Agent>();
		observers=new ArrayList<GameObserver>();
	}
	
	public void addPacmanAgent(Agent a)
	{
		pacmansAgents.add(a);
	}
	
	public void addGhostAgent(Agent a)
	{
		ghostsAgents.add(a);
	}
	
	public void addObserver(GameObserver o)
	{
		observers.add(o);
	}
	
	public void setTime (int time_delay) {
		time = time_delay;
	}
	
	public GameState runUntilEnd(long time_delay)
	{
		assert((pacmansAgents.size()==state.getNumberOfPacmans()));
		assert((ghostsAgents.size()==state.getNumberOfGhosts()));
		
		
		time = (int) time_delay;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}		
		
		boolean isPacmanTurn=true;
		while((state.isLose()==false) && (state.isWin()==false))
		{
			ArrayList<AgentAction> pactions=new ArrayList<AgentAction>();
			ArrayList<AgentAction> gactions=new ArrayList<AgentAction>();
			if (isPacmanTurn)
			{
				for(int i=0;i<state.getNumberOfPacmans();i++)
				{
					Agent a=pacmansAgents.get(i);
					AgentState ss=state.getPacmanState(i);
					pactions.add(a.getAction(ss,state));				
				}
				
				state=state.nextStatePacmans(pactions);
				if (state.getNumberOfGhosts()!=0) isPacmanTurn=false;
			}
			else
			{
				for(int i=0;i<state.getNumberOfGhosts();i++)
				{
					Agent a=ghostsAgents.get(i);
					AgentState ss=state.getGhostState(i);
					AgentAction aa=a.getAction(ss, state);
					gactions.add(a.getAction(ss,state));				
				}			
				
				state=state.nextStateGhosts(gactions);
				isPacmanTurn=true;
			}
			
			for(GameObserver o:observers)
				o.update(state);
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return(state);
	}

	public GameState runUntilEnd(int time_delay, int size_max_trajectory) {
		assert((pacmansAgents.size()==state.getNumberOfPacmans()));
		assert((ghostsAgents.size()==state.getNumberOfGhosts()));
		
		
		time = (int) time_delay  ;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}		
		
		boolean isPacmanTurn=true;
		int nb_turn=0;
		while((state.isLose()==false) && (state.isWin()==false) && (nb_turn*2<size_max_trajectory))
		{
			ArrayList<AgentAction> pactions=new ArrayList<AgentAction>();
			ArrayList<AgentAction> gactions=new ArrayList<AgentAction>();
			
			if (isPacmanTurn)
			{
				for(int i=0;i<state.getNumberOfPacmans();i++)
				{
					Agent a=pacmansAgents.get(i);
					AgentState ss=state.getPacmanState(i);
					pactions.add(a.getAction(ss,state));				
				}
				
				state=state.nextStatePacmans(pactions);
				if (state.getNumberOfGhosts()!=0) isPacmanTurn=false;
			}
			else
			{
				for(int i=0;i<state.getNumberOfGhosts();i++)
				{
					Agent a=ghostsAgents.get(i);
					AgentState ss=state.getGhostState(i);
					AgentAction aa=a.getAction(ss, state);
					gactions.add(a.getAction(ss,state));				
				}			
				
				state=state.nextStateGhosts(gactions);
				isPacmanTurn=true;
			}
			
			for(GameObserver o:observers)
				o.update(state);
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return(state);
	}
	
	
	

}
