package pacman.sma;

import pacman.eleves.Agent;
import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;
import pacman.eleves.GameState;
import pacman.eleves.Maze;

public class IntelligentGhost_Agent2  implements Agent
{
	protected int last_action;
	protected double proba_changement_direction;
	
	public IntelligentGhost_Agent2(double proba_changement_direction)
	{
		last_action=-1;
		this.proba_changement_direction=proba_changement_direction;
	}

	@Override
	public AgentAction getAction(AgentState as,GameState state) 
	{
		if (last_action==-1)
		{
			last_action=(int)(Math.random()*4);
			return(new AgentAction(last_action));
		}
		
		if (Math.random()<proba_changement_direction)
		{
			int t=last_action/2;		
			t=1-t;
			int a=Math.random()<0.5 ? t*2 : t*2+1; 
			a=a%4;
			if (state.isLegalMove(new AgentAction(a),as))
			{
				last_action=a;
				return(new AgentAction(a));
			}
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