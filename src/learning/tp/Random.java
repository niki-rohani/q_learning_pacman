package learning.tp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.tools.Tool;

import pacman.eleves.Agent;
import pacman.eleves.GameState;
import pacman.sma.IntelligentGhost_Agent1;

import learning.Elt;
import learning.PerceptronAgent;
import learning.RewardTools;
import learning.Sensor;
import learning.SimpleReward;
import learning.SimpleStateSensor;
import learning.StateSensor;
import learning.perceptron.Perceptron;


/**
 * Question 3.2
 * @author Dantidot
 *
 */
public class Random {

	
	
	
	private static ArrayList <PerceptronAgent> agent = new ArrayList <PerceptronAgent> ();
	
	
	 static ArrayList <Elt> elt = new ArrayList <Elt> ();
	public static void run (String  [] arg ) {
		int N =   Integer.parseInt ( arg[0] )  ;
		
		
		int SIZE = 3;
		int size = SIZE;
		
		int N0 = 100;
		
		
		// VARIANCE
		double var = 0.01;
		
		if (arg.length > 0)
		{
			N =  Integer.parseInt ( arg[0]  );
		}
		else
			N = 300;
		
		Sensor s = new Sensor (new SimpleStateSensor (3));
			
		// On crée des perceptron
		System.out.print ("CREATING RANDOM PERCEPTRON ");
		for (int i = 0; i < N; i++) {
			PerceptronAgent random =   MainTool.randomPerc(s.size(), 0.01, null, 100, s, "")  ;
			agent.add ( random );
			elt.add (new Elt());
			elt.get (   elt.size() - 1 ).perceptron = random;
			
		}
		System.out.println ("        PERCEPTRON CREATE ");
		
		// On calcul leur score
		System.out.print ("CREATING SCORE FOR PERCEPTRON ");
		for (int i = 0; i < N; i++ ) {
			ArrayList <Agent> ghost = new ArrayList <Agent> ();
			ghost.add (new IntelligentGhost_Agent1 ());
			
			
			GameState state = MainTool.initMaze (arg[1]);
			
			elt.get(i).score = RewardTools.getAverageReward (state,  elt.get(i).perceptron , ghost, new SimpleReward(),   100, 1000);
			
		}
		
		
		System.out.println("            SCORE CREATE ");
		
		
		
	
		
		
		
		Collections.sort (elt);
		
		// Suprime les perceptron, garde N0 percep
		System.out.print (" REMOVE " + ( N-N0 ) + " PERCEPTRON " );
		for (int i = N-1 ; i >  N0 ; i-- ) {
			elt.remove (i);
		}
		
		
		System.out.println ("         PERCEPTRON REMOVE ");
		
		
		
		System.out.print (" CREATING PEREPTRON " );
		for (int i = N - N0; i < N; i++ ) {
			
			 Elt random = elt.get( (int) ( Math.random() * N0 ) );
			 // varie les poids
			 random.perceptron.bruit(var);
			 elt.add (random);
			 
			 
			 
		}
		
		
		System.out.println ("     PERCEPTRON CREATE ");
		
		
		
		System.out.println ( " -------------------------- \n PROCESS IS FINISH ");
		
	}
	
	
	public static void print() {
		for (int i = 0; i < elt.size(); i++) {
			System.out.print ("          Score : " + elt.get(i).score + " pacman : " + i);
		}
		System.out.println();
	}
	
}
