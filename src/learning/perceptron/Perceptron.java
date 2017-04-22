package learning.perceptron;

import learning.gui.ExperimentalQLearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import learning.perceptron.SparseVector;

/**
 * Perceptron non binair
 * 
 * @author Dantidot
 * 
 */
public class Perceptron implements BinaryClassifier {

	private double e;
	private int nbIteration;
	private SparseVector w;
	private String type;
	private int n;
	
	public double getE() {
		return e;
	}

	public int getNbIteration() {
		return nbIteration;
	}

	public void setE(double e) {
		this.e = e;
	}

	public void setNbIteration(int nbIteration) {
		this.nbIteration = nbIteration;
	}

	private ExperimentalQLearning f;

	public Perceptron(int nbIteration, double e, int size) {
		this.nbIteration = nbIteration;
		this.e = e;
		w = new SparseVector(size);
		for (int i = 0; i < size; i++) {
			w.setValue(i, (int) (Math.random() * 2));
		}

		f = null;
	}
	
	public Perceptron(int nbIteration, double e, int size, ExperimentalQLearning obs, String type, int n) {
		this.nbIteration = nbIteration;
		this.e = e;
		w = new SparseVector (size);
		for (int i = 0; i < size; i++) {
			w.setValue(i, (int) (Math.random() * 2));
		}
		
		f = obs;
		
		
		this.type = type;
		this.n = n;
	
		
	}
		
	@Override
	public double getScore(SparseVector v) {

		return w.computeDOT(v);
	}

	public void train(LabeledSet training_set) {
		// TODO Auto-generated method stub

		// PROBLEME EXPONENTIELLE !!!!!!!
		System.out.println("Perceptron is train ... ");
		System.out.println("Perceptron as epsilon " + e);

		double errMoy = 0;

		for (int i = 0; i < nbIteration; i++) {
			double T = 0;
			double r = 0;

			
			double err = 0;

			for (int z = 0; z < training_set.size(); z++) {
				T = this.getScore(training_set.getVector(z));
				r = training_set.getLabel(z);
				w.addVector(training_set.getVector(z), e * (r - T));
				double t = err;
				// Mettre l'ecart dans les réel
				err = Math.abs(Math.min(r, T));
				T = T + err;
				r = r + err;
				
				// err = max (reel, predicted) - min (reel, predicted) = Math abs ecart
				err = t + Math.max(r, T) - Math.min(r, T);
				
				
				if (f == null)   {}
				else
				f.train((i / (double) nbIteration), errMoy, type, n);
				
			}
			err = err / (double) training_set.size();
			if (errMoy > 0)
				errMoy = (errMoy + err) / 2;
			else
				errMoy = err;

			
			
		
		}
		if (f == null)
		System.out.println(" System Appris en " + nbIteration + " iteration");
		else
			f.info ("DONE ....    System Appris en " + nbIteration + "  tour ");
		
		
		
	}

	public void bruit(double variance) {

		for (int f : w) {
			w.setValue(f, w.getValue(f) *  ( Math.random () - 0.5 ) * variance  );
		}
	}

	public SparseVector getW() {
		return w;
	}

	public String save() {
		/* Old Version */
		/*
		try {
			FileWriter file = new FileWriter(new File(f));
			String s = "";

			for (int i = 0; i < w.size(); i++)
				s = s + i + ":" + w.getValue(i) + " ";

			file.write(s);

			file.flush();
			file.close();
		} catch (IOException e) {
		}
	*/
		String s = "";

		for (int i = 0; i < w.size(); i++)
			s = s + i + ":" + w.getValue(i) + " ";
		return s;
	}

	public void load(String f) {
		/* Old Version */
		/*
		try {
			BufferedReader option = new BufferedReader(new FileReader(new File(
					f)));
			 w.load(option);
			option.close();

		} catch (IOException e) {

		}
		*/
		
		
		w.load  (f);
	}

	protected double getAccuracy(LabeledSet s) {
		int nbok = 0;
		for (int i = 0; i < s.size(); i++) {
			double reel = s.getLabel(i);
			double predicted = getScore(s.getVector(i));
			if (reel == predicted)
				nbok++;
		}
		return (nbok / (double) s.size());
	}
	
	public double getError (LabeledSet s) {
		double err = 0;

		for (int z = 0; z < s.size(); z++) {
			double T = this.getScore(s.getVector(z));
			double r = s.getLabel(z);
			
			double t = err;
			
			// Mettre l'ecart dans les réel
			err = Math.abs(Math.min(r, T));
			T = T + err;
			r = r + err;
			
			// err = max (reel, predicted) - min (reel, predicted) = Math abs ecart
			err = t + Math.max(r, T) - Math.min(r, T);

		}
		
		err = err / (double) s.size();
		
		
		return err;
	}

}
