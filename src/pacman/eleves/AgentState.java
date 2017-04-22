package pacman.eleves;

/**
 * La classe décrit l'état d'un agent
* @author denoyer
 *
 */
public class AgentState 
{
	protected int x;
	protected int y;
	private int start_x;
	private int start_y;	
	protected int scarredTimer;
	protected int direction;
	protected boolean isdead;
	protected int last_x;
	protected int last_y;
	
	
	public AgentState(int start_x,int start_y)
	{
		this.start_x=start_x;
		this.start_y=start_y;
		this.last_x=-1;
		this.last_y=-1;
		this.x=start_x;
		this.y=start_y;
		scarredTimer=0;
		direction=Maze.NORTH;
		isdead=false;
	}
	
	/**
	 * Renvoie la direction courante de l'agent
	 * @return
	 */
	public int getDirection() {return(direction);}
	
	/**
	 * Renvoie le X du point de départ de l'agent
	 * @return
	 */
	public int getStartX() {return(start_x);}
	
	/**
	 * Renvoie le Y du point de départ de l'agent
	 * @return
	 */
	public int getStartY() {return(start_y);}

	/**
	 * Renvoie la position X de l'agent
	 * @return
	 */
	public int getX() {return(x);}
	
	/**
	 * Renvoie la position Y de l'agent
	 * @return
	 */
	public int getY() {return(y);}
	
	/**
	 * Renvoie la derniere position avant celle la
	 */
	public int getLastX() {return(last_x);}
	public int getLastY() {return(last_y);}
	public int getLastMovement()
	{
		
		if (last_x==x-1) return(Maze.EAST);
		if (last_x==x+1) return(Maze.WEST);
		if (last_y==y-1) return(Maze.SOUTH);
		if (last_y==y+1) return(Maze.NORTH);
		return(-1);
	}
	
	
	/**
	 * Renvoie si l'agent est ''effraye''
	 * @return
	 */
	public boolean isScarred() {return(scarredTimer>0);}
	
	/**
	 * Renvoie le nombre de coup pendant lequel l'agent restera efferay
	 * @return
	 */
	public int getScarredTimer() {return(scarredTimer);}

	/**
	 * Renvoie si l'agent est mort ou non
	 * @return
	 */
	public boolean isDead() {return(isdead);}
}
