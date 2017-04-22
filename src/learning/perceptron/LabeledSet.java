package learning.perceptron;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Ensemble d'exemples étiquetés
 * @author denoyer
 *
 */
public class LabeledSet {
	protected ArrayList<SparseVector> vectors;
	protected ArrayList<Double> labels;
	protected int size_vectors;
	
	public LabeledSet(int size_vectors)
	{
		vectors=new ArrayList<SparseVector>();
		labels=new ArrayList<Double>();
		this.size_vectors=size_vectors;
	}
	
	/**
 	* Renvoie la taille des vecteurs
 	*/
	public int getSizeVectors()
	{
		return(size_vectors);
	}
	
	public void addExample(SparseVector v,double  l)
	{
		vectors.add(v);
		labels.add(l);
	}
	
	public int size() 
	{
		return(vectors.size());
	}
	public SparseVector  getVector(int i)
	{
		return(vectors.get(i));		
	}
	
	public double getLabel(int i)
	{
		return(labels.get(i));
	}
	 
}
