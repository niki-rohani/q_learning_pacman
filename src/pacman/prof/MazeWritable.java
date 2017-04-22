package pacman.prof;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pacman.eleves.Maze;
import pacman.eleves.MazeException;

/**
 * Cette classe décrit un labyrinthe de pacman
 * @author denoyer
 *
 */
public class MazeWritable extends Maze 
{
	 	
	/**
	 * Permet de construire un lmabyrinthe à partir d'un fichier
	 * @param filename
	 */
	public MazeWritable(String filename) throws MazeException
	{
		super(filename);
	}

	public MazeWritable(int sx,int sy)
	{
		super(sx,sy);		
	}
	
	public void setFood(int x,int y,boolean b) {food[x][y]=b;}
	public void setCapsule(int x,int y,boolean b) {capsules[x][y]=b;}
	public void setWall(int x,int y,boolean b) {walls[x][y]=b;}
	public void setSizeX(int x) {size_x=x;}
	public void setSizeY(int x) {size_x=x;}
	public void addPacmanStart(int x,int y) {pacman_start_x.add(x); pacman_start_y.add(y);}
	public void addGhostStart(int x,int y) {ghosts_start_x.add(x); ghosts_start_x.add(y);}
	
	
	public MazeWritable copy()
	{
		MazeWritable m=new MazeWritable(getSizeX(),getSizeY());
		for(int x=0;x<size_x;x++)
			for(int y=0;y<size_y;y++)
			{
				m.setCapsule(x, y, capsules[x][y]);
				m.setWall(x, y, walls[x][y]);
				m.setFood(x, y, food[x][y]);
			}
		
		for(int i=0;i<pacman_start_x.size();i++)
			m.addPacmanStart(pacman_start_x.get(i),pacman_start_y.get(i));

		for(int i=0;i<ghosts_start_x.size();i++)
			m.addPacmanStart(ghosts_start_x.get(i),ghosts_start_y.get(i));		
		
		return(m);
	}
}
