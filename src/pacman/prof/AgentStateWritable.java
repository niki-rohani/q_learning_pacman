package pacman.prof;

import java.util.ArrayList;

import pacman.eleves.AgentAction;
import pacman.eleves.AgentState;

/**
 * This class describes the state of an agent (pacman or ghost)
* @author denoyer
 *
 */
public class AgentStateWritable extends AgentState
{

	
	public AgentStateWritable(int start_x,int start_y)
	{
		super(start_x,start_y);	
	}
	
	public void setX(int x) {this.x=x;}
	public void setY(int y) {this.y=y;}
	public void setLastX(int x) {last_x=x;}
	public void setLastY(int y) {last_y=y;}
	public void setScarredTimer(int t) {this.scarredTimer=t;}
	public void setDirection(int d) {this.direction=d;}
	public void setDead(boolean v) {isdead=v;}
	
	public AgentStateWritable copy()
	{
		AgentStateWritable a=new AgentStateWritable(getStartX(),getStartY());
		a.setX(getX());
		a.setY(getY());
		a.setLastX(getLastX());
		a.setLastY(getLastY());
		a.setScarredTimer(getScarredTimer());
		a.setDirection(direction);
		a.setDead(isdead);
		return(a);
	}
	

}
