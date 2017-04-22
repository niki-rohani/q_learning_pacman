package learning;

import pacman.eleves.GameState;


/**
 * SimpleReward
 * Classe retournant un reward en fonction de l'etat de Pacman
 * 
 * +100 si gagne
 * -100 si perd
 * +0 si mange
 * -1    
 * @author Dantidot
 *
 */
public class SimpleReward implements Reward {

	
	public double getReward (GameState from, GameState to) {
		int [] inv = {1,0,3,2};
		double r = 0;
		if (to.isWin())
			r = r + 100.0;
		else if (to.isLose())
			r = r  -10.0;
		else if (to.getFoodEaten() > from.getFoodEaten())
			r = r + 1.0;
		
		else if (to.getMaze().isCapsule(to.getPacmanState(0).getX(), to.getPacmanState(0).getY()))
			r = r + 1;
		
		return r;
	}
}
