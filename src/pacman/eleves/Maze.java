package pacman.eleves;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Cette classe décrit un labyrinthe de pacman
 * @author denoyer
 *
 */
public class Maze 
{
	/** 
	 * Les differentes directions possibles pour l es actions
	 */
	public static int NORTH=0;
	public static int SOUTH=1;
	public static int EAST=2;
	public static int WEST=3;
	public static int STOP=4;
	
	protected int size_x;
	protected int size_y;
	protected boolean walls[][];
	protected boolean food[][];
	protected boolean capsules[][];

	protected ArrayList<Integer> pacman_start_x;
	protected ArrayList<Integer> pacman_start_y;
	protected ArrayList<Integer> ghosts_start_x;
	protected ArrayList<Integer> ghosts_start_y;
	
	/**
	 * Permet de construire un labyrinthe d'une certaine taile	 
	 */
	protected Maze(int sx,int sy)
	{
		size_x=sx;
		size_y=sy;
		walls=new boolean[size_x][size_y];
		food=new boolean[size_x][size_y];
		capsules=new boolean[size_x][size_y];
		ghosts_start_x=new ArrayList<Integer>();
		ghosts_start_y=new ArrayList<Integer>();
		pacman_start_x=new ArrayList<Integer>();
		pacman_start_y=new ArrayList<Integer>();
	}
	
	/**
	 * Permet de construire un lmabyrinthe à partir d'un fichier
	 * @param filename
	 */
	public Maze(String filename) throws MazeException
	{
		try{
			//Lecture du fichier pour déterminer la taille du maze
			InputStream ips=new FileInputStream(filename); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int nbX=0;
			int nbY=0;
			while ((ligne=br.readLine())!=null)
			{
				ligne=ligne.trim();
				if (nbX==0) {nbX=ligne.length();}
				else if (nbX!=ligne.length()) throw new MazeException("Wrong Input Format: all lines must have the same size");
				nbY++;
			}			
			br.close(); 
			
			//Initialisation du maze
			size_x=nbX;
			size_y=nbY;
			walls=new boolean[size_x][size_y];
			food=new boolean[size_x][size_y];
			capsules=new boolean[size_x][size_y];
			ghosts_start_x=new ArrayList<Integer>();
			ghosts_start_y=new ArrayList<Integer>();
			pacman_start_x=new ArrayList<Integer>();
			pacman_start_y=new ArrayList<Integer>();
			
			//Lecture du fichier pour MAJ du maze
			 ips=new FileInputStream(filename); 
			 ipsr=new InputStreamReader(ips);
			 br=new BufferedReader(ipsr);
			 int y=0;
			while ((ligne=br.readLine())!=null)
			{
				ligne=ligne.trim();

				for(int x=0;x<ligne.length();x++)
				{
					if (ligne.charAt(x)=='%') walls[x][y]=true; else walls[x][y]=false;
					if (ligne.charAt(x)=='.') food[x][y]=true; else food[x][y]=false;
					if (ligne.charAt(x)=='o') capsules[x][y]=true; else capsules[x][y]=false;
					if (ligne.charAt(x)=='P') {pacman_start_x.add(x); pacman_start_y.add(y);}
					if (ligne.charAt(x)=='G') {ghosts_start_x.add(x); ghosts_start_y.add(y);}
				}
				y++;
			}			
			br.close(); 
			
			if (pacman_start_x.size()==0)throw new MazeException("Wrong input format: must specify a Pacman start");
			
			//On verifie que le labyrinthe est clos			
			for(int x=0;x<size_x;x++) if (!walls[x][0]) throw new MazeException("Wrong input format: the maze must be closed");
			for(int x=0;x<size_x;x++) if (!walls[x][size_y-1]) throw new MazeException("Wrong input format: the maze must be closed");
			for(y=0;y<size_y;y++) if (!walls[0][y]) throw new MazeException("Wrong input format: the maze must be closed");
			for(y=0;y<size_y;y++) if (!walls[size_x-1][y]) throw new MazeException("Wrong input format: the maze must be closed");
			
		}		
		catch (Exception e){
			e.printStackTrace();
			throw new MazeException("Probleme a la lecture du fichier: "+e.getMessage());
		}
	}
	
	/**
	 * Renvoie la taille X du labyrtinhe
	 */
	public int getSizeX() {return(size_x);}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {return(size_y);}
	
	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(walls[x][y]);
	}
	
	/**
	 * Permet de savoir si il y a de la nourriture
	 */
	public boolean isFood(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(food[x][y]);
	}

	/**
	 * Permet de savoir si il y a une capsule
	 */
	public boolean isCapsule(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(capsules[x][y]);
	}
	
	
	/**
	 * Renvoie le nombre de pacmans
	 * @return
	 */
	public int getNumberOfPacmans() 
	{
		return(pacman_start_x.size());	
	}
	
	/**
	 * Renvoie le nombre de fantomes
	 * @return
	 */
	public int getNumberOfGhosts() 
	{
		return(ghosts_start_x.size());
	}
	
	
	
	public int getPacmanStartX(int i) 
	{
		return(pacman_start_x.get(i));
	}
	
	public int getPacmanStartY(int i) 
	{
		return(pacman_start_y.get(i));
	}	

	public int getGhostStartX(int i) 
	{
		return(ghosts_start_x.get(i));
	}

	public int getGhostStartY(int i) 
	{
		return(ghosts_start_y.get(i));
	}
}
