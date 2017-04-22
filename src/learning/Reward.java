package learning;

import pacman.eleves.GameState;

public interface Reward 
{
	public double getReward(GameState from,GameState to);
}
