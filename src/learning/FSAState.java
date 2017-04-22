package learning;

import learning.perceptron.SparseVector;
import pacman.eleves.*;

/**
 * Classe représentant un quadrup
 * 
 * @author Dantidot
 * 
 */
public class FSAState {

	private GameState s;
	private GameState sgame;
	private int a;
	private double r;
	private int sensor;

	public FSAState(GameState init, GameState state, int action, double rew,
			int size) {
		s = init;
		sgame = state;
		a = action;
		r = rew;
		sensor = size;
	}

	public SparseVector vector() {

		CapsuleStateSensor state = new CapsuleStateSensor(sensor);
		Sensor sensor = new Sensor(state);
		
		/* Old Version : 1 perceptron */
		/*
		SparseVector v = sensor.getVector(s, new AgentAction(a));
		*/
		SparseVector v = sensor.getVector(s, null);
		
		
		return v;
	}

	public double getReward() {
		return r;
	}
	
	public int getA() {
		return a;
	}

}
