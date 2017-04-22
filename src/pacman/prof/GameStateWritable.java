package pacman.prof;

import java.util.ArrayList;

import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;
import pacman.eleves.Maze;

/**
 * Cette classe décrit un état du jeu
 * @author denoyer
 *
 */
public class GameStateWritable extends GameState
{

	
	public GameStateWritable(MazeWritable maze)
	{
		super(maze);
	}

	public GameState copy()
	{
		GameStateWritable g=new GameStateWritable(maze.copy());

		g.setFoodEaten(foodEaten);
		g.setCapsulesEaten(capsulesEaten);
		g.setGhostsEaten(ghostsEaten);
		g.setWin(win);
		g.setLose(lose);	
		g.setTime(time);
		g.initPacmansStates();
		for(int i=0;i<pacmans_states.size();i++)
			g.addPacmanState(pacmans_states.get(i).copy());

		g.initGhostsStates();
		for(int i=0;i<ghosts_states.size();i++)
			g.addGhostState(ghosts_states.get(i).copy());
		return g;	
	}
	
	public void setFoodEaten(int foodEaten) { 		this.foodEaten = foodEaten;	}
	public void setCapsulesEaten(int capsulesEaten) {		this.capsulesEaten = capsulesEaten;	}
	public void setGhostsEaten(int ghostsEaten) {		this.ghostsEaten = ghostsEaten;	}
	public void setWin(boolean win) {		this.win = win;	}
	public void setLose(boolean lose) {		this.lose = lose;	}
	public void addPacmanState(AgentStateWritable pacmans_state) {		this.pacmans_states.add( pacmans_state);	}
	public void addGhostState(AgentStateWritable ghosts_state) {		this.ghosts_states.add(ghosts_state);	}
	public void initPacmansStates() {pacmans_states=new ArrayList<AgentStateWritable>();}
	public void initGhostsStates() {ghosts_states=new ArrayList<AgentStateWritable>();}
	public void setTime(int t) {time=t;}
	
	protected void movePacman(int i,AgentAction action)
	{
		AgentStateWritable s=pacmans_states.get(i);
		int x=s.getX();
		int y=s.getY();
		if (maze.isWall(x+action.getX(), y+action.getY()))
		{			
		}
		else if (pacmans_states.get(i).isDead())
		{		
			s.setLastX(x);
			s.setLastY(y);
		}
		else
		{
			s.setX(x+action.getX());
			s.setY(y+action.getY());
			if (action.getDirection()!=Maze.STOP) s.setDirection(action.getDirection());
			s.setLastX(x);
			s.setLastY(y);
		}
	}
	
	protected void moveGhost(int i,AgentAction action)
	{
		AgentStateWritable s=ghosts_states.get(i);
		int x=s.getX();
		int y=s.getY();
		if (maze.isWall(x+action.getX(), y+action.getY()))
		{
			s.setLastX(x);
			s.setLastY(y);
		}
		else
		{
			s.setX(x+action.getX());
			s.setY(y+action.getY());
			if (action.getDirection()!=Maze.STOP) s.setDirection(action.getDirection());
			s.setLastX(x);
			s.setLastY(y);			
		}
	}	
	
	protected void updateMaze()
	{
		for(int i=0;i<getNumberOfPacmans();i++)
		{
			AgentStateWritable s=pacmans_states.get(i);
			int x=s.getX();
			int y=s.getY();
			if (maze.isFood(x, y))
			{
				maze.setFood(x, y, false);
				foodEaten++;
			}
			if (maze.isCapsule(x, y))
			{
				maze.setCapsule(x, y, false);
				capsulesEaten++;
				s.setScarredTimer(GameState.TIMEPACMANSCARRED);
			}
			
			for(int j=0;j<getNumberOfGhosts();j++)
			{
				AgentStateWritable gs=ghosts_states.get(j);
				int gx=gs.getX();
				int gy=gs.getY();
				if ((gx==x) && (gy==y))
				{
					if (!gs.isScarred())
					{
						if (s.isScarred())
						{
							gs.setScarredTimer(GameState.TIMEGHOSTSCARRED);
							ghostsEaten++;
						}
						else
						{
							s.setDead(true);
						}
					}					
				}
			}					
		}
	}
	
	public GameState nextStatePacman(AgentAction pacmanAction)
	{
		GameStateWritable gw=(GameStateWritable)(copy());
		gw.updatePacman(pacmanAction);
		return(gw);
	}
	
	public GameState nextStatePacmans(ArrayList<AgentAction> pacmansActions)
	{
		GameStateWritable gw=(GameStateWritable)(copy());
		gw.updatePacmans(pacmansActions);		
		return(gw);
	}
	
	public GameState nextStateGhost(AgentAction ghostsAction)
	{
		GameStateWritable gw=(GameStateWritable)(copy());
		gw.updateGhost(ghostsAction);
		return(gw);
	}
	public GameState nextStateGhosts(ArrayList<AgentAction> ghostsActions)
	{
		GameStateWritable gw=(GameStateWritable)(copy());
		gw.updateGhosts(ghostsActions);
		return(gw);
	}	

	
	protected void updatePacman(AgentAction pacmanAction)
	{
		ArrayList<AgentAction> p=new ArrayList<AgentAction>();
		p.add(pacmanAction);		
		updatePacmans(p);
	}
	
	protected void updatePacmans(ArrayList<AgentAction> pacmansActions)
	{
		assert(getNumberOfPacmans()==pacmansActions.size());
		for(int i=0;i<pacmansActions.size();i++)
		{
			movePacman(i,pacmansActions.get(i));
		}
		
		updateMaze();
		
		//Win ? 
				boolean b=true;
				for(int x=0;x<maze.getSizeX();x++)
					for(int y=0;y<maze.getSizeY();y++)
					{
						if ((maze.isFood(x, y)) || (maze.isCapsule(x, y)))
							b=false;
					}
				win=b;
				
				for(int i=0;i<pacmansActions.size();i++)
				{
					if (pacmans_states.get(i).isScarred())
					{
						pacmans_states.get(i).setScarredTimer(pacmans_states.get(i).getScarredTimer()-1);
					}
				}
				time++;	
				
				//Lose ? 
			b=true;
			for(int i=0;i<getNumberOfPacmans();i++)
				if (!pacmans_states.get(i).isDead()) b=false;
			lose=b;				
	}
	
	protected void updateGhost(AgentAction ga)
	{
		ArrayList<AgentAction> p=new ArrayList<AgentAction>();
		p.add(ga);		
		updateGhosts(p);
	}
	
	//C'est ici que sont implémentées les règles du jeu
	protected void updateGhosts(ArrayList<AgentAction> ghostsActions)
	{
		assert(getNumberOfGhosts()==ghostsActions.size());
		

		for(int i=0;i<ghostsActions.size();i++)
		{
			moveGhost(i,ghostsActions.get(i));
		}		
		updateMaze();
		
		
		//Win ? 
				boolean b=true;
				for(int x=0;x<maze.getSizeX();x++)
					for(int y=0;y<maze.getSizeY();y++)
					{
						if ((maze.isFood(x, y)) || (maze.isCapsule(x, y)))
							b=false;
					}
				win=b;
		
		for(int i=0;i<ghostsActions.size();i++)
		{
			if (ghosts_states.get(i).isScarred())
			{
				ghosts_states.get(i).setScarredTimer(ghosts_states.get(i).getScarredTimer()-1);
			}
		}
				
		
		//Lose ? 
		b=true;
		for(int i=0;i<getNumberOfPacmans();i++)
			if (!pacmans_states.get(i).isDead()) b=false;
		lose=b;
		
		time++;
	}
	
}
