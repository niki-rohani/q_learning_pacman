package learning;

import java.util.ArrayList;

import learning.perceptron.SparseVector;
import pacman.eleves.GameState;



/**
 * SimpleStateSensor
 * 						Classe repr�sentant un State Sensor.  Prend en compte la capsule
 *  Taille du sensor    x[ -n, n ], y [-n, n] - 1
 *  Wall, Food, Ghost
 * @author Dantidot
 *
 */
public class CapsuleStateSensor implements StateSensor {

	private int n;
	private int size;

	public CapsuleStateSensor(int N) {
		n = N;
		
		
		/*
		int rangeXmin = n - n;  
		int rangeXmax = n + n;  
		int rangeYmin = n - n;  
		int rangeYmax = n + n;
		int range = (rangeXmax - rangeXmin + 1) * (rangeXmax - rangeXmin + 1)
				* 3 - 3;
	*/
		// Version 2
			int range = 2*n + 1;
			range = range * range;
			range = range * 3;
			range = range - 3;
		//
		SparseVector stateSensor = new SparseVector(range);
		size = range;

	}

	public int getSize() {
		System.out.println("SIIIIZEE ----------------- " + size);
		return size;
	}

	public SparseVector getVector(GameState s) {

		int rangeXmin = s.getPacmanState(0).getX() - n;
		int rangeXmax = s.getPacmanState(0).getX() + n;
		int rangeYmin = s.getPacmanState(0).getY() - n;
		int rangeYmax = s.getPacmanState(0).getY() + n;

		SparseVector stateSensor = new SparseVector(size);

		int indice = 0;
		for (int i = rangeXmin; i < rangeXmax + 1; i++)

			for (int j = rangeYmin; j < rangeYmax + 1; j++) {

				int ti = Math.abs(rangeXmin - i);
				int tj = Math.abs(rangeYmin - j);
				if (i == s.getPacmanState(0).getX()
						&& j == s.getPacmanState(0).getY()) {

				} else {
					if (i >= 0 && i < s.getMaze().getSizeX() && j >= 0
							&& j < s.getMaze().getSizeY()) {
						int w = 0;
						int g = 0;
						int c = 0;

						if (s.getMaze().isWall(i, j))
							w = 1;
						if (s.isGhost(i, j))
							g = 1;
						if (s.getMaze().isFood(i, j) || s.getMaze().isCapsule(i, j))
							c = 1;

						// System.out.println ("PACMAN " +
						// s.getPacmanState(0).getX() + " " +
						// s.getPacmanState(0).getY() + " W " + w + " F " + c +
						// " SENSOR " + i + " " + j );
						stateSensor.setValue(indice, w);
						indice++;
						stateSensor.setValue(indice, c);
						indice++;
						stateSensor.setValue(indice, g);
						indice++;
						
					} else {
						stateSensor.setValue(indice, 1);
						indice++;
						stateSensor.setValue(indice, 0);
						indice++;
						stateSensor.setValue(indice, 0);

						indice++;
						
					}

				}

			}
		return stateSensor;
	}

	@Override
	public int size() {
		return size  ;
	}

}
