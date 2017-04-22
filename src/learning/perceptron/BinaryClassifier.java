package learning.perceptron;

import learning.perceptron.SparseVector;

public interface BinaryClassifier 
{

	/**
	 * Renvoie le score du vecteur v
	 * @param v
	 * @return
	 */
	public double getScore(SparseVector v);
	
	public void train(LabeledSet training_set);
}
