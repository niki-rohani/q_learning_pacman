package pacman.eleves;

import java.util.ArrayList;

import pacman.prof.AgentStateWritable;
import pacman.prof.MazeWritable;

/**
 * Cette classe décrit un état du jeu
 * @author denoyer
 *
 */
public abstract class GameState 
{
	public static int TIMEPACMANSCARRED=20;
	public static int TIMEGHOSTSCARRED=20;
	
	protected MazeWritable maze;
	protected ArrayList<AgentStateWritable> pacmans_states;
	protected ArrayList<AgentStateWritable> ghosts_states;
	protected int foodEaten;
	protected int capsulesEaten;
	protected int ghostsEaten;
	protected boolean win;
	protected boolean lose;
	protected int time;
	
	
	protected GameState(MazeWritable maze)
	{
		this.maze=maze;
		foodEaten=0;
		capsulesEaten=0;
		ghostsEaten=0;
		win=false;
		lose=false;
		time=0;
		
		pacmans_states=new ArrayList<AgentStateWritable>();
		ghosts_states=new ArrayList<AgentStateWritable>();
		
		for(int i=0;i<maze.getNumberOfPacmans();i++)
		{
			AgentStateWritable a=new AgentStateWritable(maze.getPacmanStartX(i),maze.getPacmanStartY(i));
			pacmans_states.add(a);
		}

		for(int i=0;i<maze.getNumberOfGhosts();i++)
		{
			AgentStateWritable a=new AgentStateWritable(maze.getGhostStartX(i),maze.getGhostStartY(i));
			ghosts_states.add(a);
		}
	}
	
	/**
	 * Renvoie le labyrinthe du jeu
	 * @return
	 */
	public Maze getMaze() {return(maze);}
	
	/**
	 * Renvoie le temps écoulé
	 * @return
	 */
	public int getTime() {return(time);}

	/**
	 * Renvoie le nombre de pacmans du jeu (y compris les morts)
	 * @return
	 */
	public int getNumberOfPacmans() {return(pacmans_states.size());}
	
	/**
	 * Renvoie le nombre de fantomes
	 * @return
	 */
	public int getNumberOfGhosts() {return(ghosts_states.size());}
	
	/**
	 * Renvoie l'etat du pacman numero i
	 * @param i le numero du pacman
	 * @return
	 */
	public AgentState getPacmanState(int i) {return(pacmans_states.get(i));}
	
	/**
	 * Renvoie l'etat du fantome i
	 * @param i le numero du fantome
	 * @return
	 */
	public AgentState getGhostState(int i) {return(ghosts_states.get(i));}
	
	/**
	 * Renvoie le nombre de nourriture mangée
	 * @return
	 */
	public int getFoodEaten() {	return foodEaten; }
	
	/**
	 * Renvoie le nombre de capsules mangées
	 * @return
	 */
	public int getCapsulesEaten() {	return capsulesEaten;}
	
	/**
	 * Renvoie le nombre de fantomes manges
	 * @return
	 */
	public int getGhostsEaten() {	return ghostsEaten;	}
	
	/**
	 * Renvoie si l'on a gagne la partie
	 * @return
	 */
	public boolean isWin() { return win;}
	
	/**
	 * Renvoie si l'on a perdu
	 * @return
	 */
	public boolean isLose() { return lose; }
	
	/**
	 * Renvoie true si l'action est valable (pas de mur)
	 * @param action
	 * @param state
	 * @return
	 */
	public boolean isLegalMove(AgentAction action,AgentState state)
	{
		int x=action.getX();
		int y=action.getY();
		if (maze.isWall(state.getX()+x,state.getY()+y)) return(false);
		return(true);
	}
	
	public boolean isGhost(int x,int y)
	{
		for(AgentState ss:ghosts_states)
		{
			if ((ss.getX()==x) && (ss.getY()==y))
					return(true);			
		}
		return(false);
	}
	
	/**
	 * Permet de calculer l'etat prochain du jeu a partir d'une action de pacman (on considère ici qu'il n'y a qu'un seul pacman)
	 * @param pacmanAction
	 * @return
	 */
	public abstract GameState nextStatePacman(AgentAction pacmanAction);
	
	/**
	 * Permet de calculer l'etat prochain du jeu en fonction des actions des différentes pacmans
	 * @param pacmansActions
	 * @return
	 */
	public abstract GameState nextStatePacmans(ArrayList<AgentAction> pacmansActions);
	
	/**
	 * Permet de calculer l'etat du jeu en fonction de l'action d'un unique fantome
	 * @param ghostsAction
	 * @return
	 */
	public abstract GameState nextStateGhost(AgentAction ghostsAction);
	
	/**
	 * Permet de calculer l'etat du jeu en fonction des actions des fantomes
	 * @param ghostsActions
	 * @return
	 */
	public abstract GameState nextStateGhosts(ArrayList<AgentAction> ghostsActions);
	
	/**
	 * Permet de copier l'etat du jeu
	 * @return
	 */
	public abstract GameState copy();
	
	
	/**
	 * Permet de transformer un etat en chaine de caractere
	 */
	public String toString()
	{
		StringBuffer sb=new StringBuffer();
		for(int y=0;y<maze.getSizeY();y++)
		{
			for(int x=0;x<maze.getSizeX();x++)
			{
				boolean isp=false;
				for(int i=0;i<pacmans_states.size();i++)
				{
					if ((pacmans_states.get(i).getX()==x) && (pacmans_states.get(i).getY()==y))
					{
						isp=true;
					}
				}boolean isg=false;
				for(int i=0;i<ghosts_states.size();i++)
				{
					if ((ghosts_states.get(i).getX()==x) && (ghosts_states.get(i).getY()==y))
					{
						isg=true;
					}
				}
				if (isp && isg) sb.append("@");
				else if (isp) sb.append("P");
				else if (isg) sb.append("G");
				else
				if (maze.isFood(x, y)) sb.append(".");
				else if (maze.isCapsule(x, y)) sb.append("o");
				else if (maze.isWall(x, y)) sb.append("%");
				else sb.append(" ");
			}
			sb.append("\n");
		}
		return(sb.toString());
	}

}
