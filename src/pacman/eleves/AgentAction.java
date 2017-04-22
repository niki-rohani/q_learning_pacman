package pacman.eleves;


/**
 * Cette classe est une action de pacman
 * @author denoyer
 *
 */
public class AgentAction 
{
	private int vx;
	private int vy;
	private int direction;
	
	/**
	 * Construit une action à partir d'une direction: Maze.NORTH=0, Maze.SOUTH=1, Maze.EAST=2, Maze.WEST=3, Maze.STOP=4
	 * @param direction la direction
	 */
	public AgentAction(int direction)
	{
		this.direction=direction;
		if (direction==Maze.STOP)
		{
			vx=0;
			vy=0;
		}
		else
		if (direction==Maze.NORTH)
		{
			vx=0;
			vy=-1;
		}
		else if (direction==Maze.SOUTH)
		{
			vx=0;
			vy=1;			
		}
		else if (direction==Maze.EAST)
		{
			vy=0;
			vx=+1;
		}
		else if (direction==Maze.WEST)
		{
			vy=0;
			vx=-1;			
		}
		else 
		{
			System.err.println("Unknown direction in AgentAction....");
			this.direction=Maze.STOP;
			vx=0;
			vy=0;
		}
	}
	
	/**
	 *  Permet de connaitre le déplacement correspondant en X
	 * @return
	 */
	public int getX() {return(vx);}	
	
	/**
	 * Permet de connaitre le déplacement correspondant en Y
	 * @return
	 */
	public int getY() {return(vy);}
	
	/**
	 * Permet de connaitre la direction
	 * @return
	 */
	public int getDirection() {return(direction);}
}
