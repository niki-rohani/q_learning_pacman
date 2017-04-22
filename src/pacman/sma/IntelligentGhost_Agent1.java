package pacman.sma;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;
import pacman.eleves.Maze;

public class IntelligentGhost_Agent1  implements Agent
{
	protected int last_action;
	
	public IntelligentGhost_Agent1()
	{
		last_action=-1;
	}

	@Override
	public AgentAction getAction(AgentState as,GameState state) 
	{
		if (last_action==-1)
		{
			last_action=(int)(Math.random()*4);
			return(new AgentAction(last_action));
		}
		
		if (state.isLegalMove(new AgentAction(last_action), as))
			return(new AgentAction(last_action));
		else
		{
			last_action=(int)(Math.random()*4);
			return(new AgentAction(last_action));
		}
		
	}

}