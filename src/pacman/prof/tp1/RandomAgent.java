package pacman.prof.tp1;

import java.util.ArrayList;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;

public class RandomAgent implements Agent
{

	@Override
	public AgentAction getAction(AgentState as,GameState state) 
	{
		/**
		 *  5 possible actions defined in Maze.java:
		 *  public static int STOP=0;
			public static int NORTH=1;
			public static int SOUTH=2;
			public static int EAST=3;
			public static int WEST=4;
		 */
		ArrayList<AgentAction> aa=new ArrayList<AgentAction>();
		for(int i=0;i<4;i++)
		{
			if (state.isLegalMove(new AgentAction(i),as))
				aa.add(new AgentAction(i));
		}		
		return(aa.get((int)(Math.random()*aa.size())));
	}

}
