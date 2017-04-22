package learning;

import learning.perceptron.SparseVector;
import pacman.eleves.AgentAction;
import pacman.eleves.GameState;

public class Sensor 
{
	protected StateSensor ss;
	
	public Sensor(StateSensor s)
	{
		this.ss=s;
	}
	public int size()
	{
		/* Old version : 1 perceptron */
		/*
		return(ss.size()*4+1);
		*/
		return ss.size()+1;
	}
	
	public SparseVector getVector(GameState s,AgentAction action)
	{
		/* Old version : 1 perceptron */
		/*
		SparseVector r=new SparseVector(size());
		SparseVector v=ss.getVector(s);
		r.setValue(0,1.0);
		int pos=action.getDirection()*ss.size()+1;
		for(int f:v)
		{
			r.setValue(pos+f,v.getValue(f));
		}
		return(r);
		*/
		SparseVector r=new SparseVector(size());
		SparseVector v=ss.getVector(s);
		r.setValue(0,1.0);
		
		for (int f:v)
		{
			r.setValue(f+1, v.getValue(f));
		}
		return r;
		
	}
}
