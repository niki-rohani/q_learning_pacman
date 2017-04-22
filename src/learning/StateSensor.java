package learning;

import learning.perceptron.SparseVector;
import pacman.eleves.AgentAction;
import pacman.eleves.GameState;

public interface StateSensor
{
	public int size();	
	public SparseVector getVector(GameState s);
}
