package pacman.prof.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import pacman.eleves.GameState;
import pacman.eleves.Maze;
import pacman.prof.GameObserver;
import pacman.prof.GameStateWritable;

public class GamePanel extends JPanel implements GameObserver
{
	protected GameState state;
	protected Color wallColor=Color.BLUE;
	protected Color wallColor2=Color.CYAN;
	
	protected double sizePacman=1.1;	
	protected Color pacmansColors[]={Color.yellow,Color.red,Color.blue,Color.magenta,Color.green,Color.orange,Color.white,Color.gray,Color.cyan};
	protected Color pacmanScarredColor=Color.pink;
	
	protected Color ghostsColors[]={Color.magenta,Color.green,Color.orange,Color.white,Color.gray,Color.cyan};
	protected Color ghostScarredColor=Color.DARK_GRAY;
	
	protected double sizeFood=0.3; 
	protected Color colorFood=Color.white; 
	
	protected double sizeCapsule=0.7;
	protected Color colorCapsule=Color.red;
	
	protected int timestep;
	
	public GamePanel(GameState s)
	{
		super();
		setGameState(s);
		timestep=0;
	}
	
	public GamePanel(GameState gs, int timestep) {
		super();
		setGameState(gs);
		this.timestep=timestep;
	}

	public void setGameState(GameState s)
	{
		state=s;
	}
	
	public void paint(Graphics g)
	{
		int dx=getSize().width;
		int dy=getSize().height;
		g.setColor(Color.black);
		g.fillRect(0, 0,dx,dy);
		
		Maze m=state.getMaze();
		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;
		double posx=0;
		
		for(int x=0;x<sx;x++)
		{
			double posy=0;
			for(int y=0;y<sy;y++)
			{
				if (m.isWall(x, y))
				{
					g.setColor(wallColor2);
					g.fillRect((int)posx, (int)posy, (int)(stepx+1),(int)(stepy+1));
					
					g.setColor(wallColor);
						double nsx=stepx*0.5;
						double nsy=stepy*0.5;
						double npx=(stepx-nsx)/2.0;
						double npy=(stepy-nsy)/2.0;
					g.fillRect((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);						
				}
				if (m.isFood(x, y))
				{
					g.setColor(colorFood);
					
						double nsx=stepx*sizeFood;
						double nsy=stepy*sizeFood;
						double npx=(stepx-nsx)/2.0;
						double npy=(stepy-nsy)/2.0;
					g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
				}
				if (m.isCapsule(x, y))
				{
					g.setColor(colorCapsule);
						double nsx=stepx*sizeCapsule;
						double nsy=stepy*sizeCapsule;
						double npx=(stepx-nsx)/2.0;
						double npy=(stepy-nsy)/2.0;
					g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
				}
				posy+=stepy;				
			}
			posx+=stepx;
		}
		drawPacmans(g);
		drawGhosts(g);
	}
	
	void drawPacmans(Graphics g)
	{
		int dx=getSize().width;
		int dy=getSize().height;
		
		Maze m=state.getMaze();
		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;
		
		for(int i=0;i<state.getNumberOfPacmans();i++)
		{
			int px=state.getPacmanState(i).getX();
			int py=state.getPacmanState(i).getY();
			double posx=px*stepx;
			double posy=py*stepy;
			
			if (state.getPacmanState(i).isDead())
			{
				g.setColor(Color.RED);
				for(double d=0.5;d<1.5;d+=0.1)
				{
					double nsx=stepx*d;
					double nsy=stepy*d;
					double npx=(stepx-nsx)/2.0;
					double npy=(stepy-nsy)/2.0;
					g.drawOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
				}
			}
			else
			{
				if (state.getPacmanState(i).isScarred())
					g.setColor(pacmanScarredColor);
				else 
					g.setColor(pacmansColors[i%pacmansColors.length]);
				double nsx=stepx*sizePacman;
				double nsy=stepy*sizePacman;
				double npx=(stepx-nsx)/2.0;
				double npy=(stepy-nsy)/2.0;
				int sa=0;
				int fa=0;
				int d=state.getPacmanState(i).getDirection();
				if (d==Maze.NORTH)
				{
					sa=70; fa=-320;
				}
				if (d==Maze.SOUTH)
				{
					sa=250; fa=-320;
				}
				if (d==Maze.EAST)
				{
					sa=340; fa=-320;				
				}
				if (d==Maze.WEST)
				{
					sa=160; fa=-320;
				}
				
				g.fillArc((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy,sa,fa);
			}
		}
	}
	
	void drawGhosts(Graphics g)
	{
		int dx=getSize().width;
		int dy=getSize().height;
		
		Maze m=state.getMaze();
		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;
		
		for(int i=0;i<state.getNumberOfGhosts();i++)
		{
			
			int px=state.getGhostState(i).getX();
			int py=state.getGhostState(i).getY();
			double posx=px*stepx;
			double posy=py*stepy;

			if (state.getGhostState(i).isScarred())
				g.setColor(ghostScarredColor);
			else 
				g.setColor(ghostsColors[i%ghostsColors.length]);
			
			double nsx=stepx*sizePacman;
			double nsy=stepy*sizePacman;
			double npx=(stepx-nsx)/2.0;
			double npy=(stepy-nsy)/2.0;
			int sa=0;
			int fa=0;
			
			g.fillArc((int)(posx+npx),(int)(npy+posy),(int)(nsx),(int)(nsy),0,180);
			g.fillRect((int)(posx+npx),(int)(npy+posy+nsy/2.0-1),(int)(nsx),(int)(nsy*0.666));
			g.setColor(Color.BLACK);
			g.fillOval((int)(posx+npx+nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);
			g.fillOval((int)(posx+npx+3*nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);
			//g.fillArc((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy,sa,fa);
			
			g.setColor(Color.black);
		}
	}

	@Override
	public void update(GameState state) {
		setGameState(state);
		repaint();
		
		if (timestep>0)
			try {
				Thread.sleep(timestep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}

