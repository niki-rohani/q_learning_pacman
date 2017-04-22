package pacman.prof.tp1;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import pacman.eleves.Agent;
import pacman.eleves.GameState;
import pacman.eleves.MazeException;
import pacman.prof.Game;
import pacman.prof.GameStateWritable;
import pacman.prof.MazeWritable;
import pacman.prof.graphics.GamePanel;

public class ViewPacmanAgent 
{
	public static void main(String args[]) throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, MazeException
	{
		Options options = new Options();

		options.addOption("layout", true, "layout file");		
		options.addOption("pacman", true, "class of the pacman agent(s)");		
		options.addOption("ghost", true, "class of the ghost agent(s)");
		options.addOption("timestep",true,"Timestep between frames (in ms)");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse( options, args);
		
		int timestep=300;
		if (cmd.hasOption("timestep"))
		{
			timestep=Integer.parseInt(cmd.getOptionValue("timestep"));
		}
		
		if (!cmd.hasOption("layout"))
		{
			System.out.println("##ERROR : You must choose a layout file");
			System.exit(0);
		}
		String layout_file=cmd.getOptionValue("layout");
		
		String pacman="pacman.prof.tp1.RandomAgent";
		if (!cmd.hasOption("pacman"))
		{
			System.out.println("##WARNING : Assuming pacman is a random agent");
		}
		else pacman=cmd.getOptionValue("pacman");
		
		String ghost="pacman.prof.tp1.RandomAgent";
		if (!cmd.hasOption("ghost"))
		{
			System.out.println("##WARNING : Assuming ghost is a random agent");
		}
		else ghost=cmd.getOptionValue("ghost");
		
				
		Class class_agent_pacman = Class.forName(pacman);
		Agent agent_pacman = (Agent) class_agent_pacman.newInstance();
		Class class_agent_ghost = Class.forName(ghost);
		Agent agent_ghost = (Agent) class_agent_ghost.newInstance();
		
		MazeWritable maze=new MazeWritable(layout_file);
		GameStateWritable state=new GameStateWritable(maze);
		
		Game game=new Game(state);
		GamePanel panel=new GamePanel(state);
		game.addObserver(panel);
		
		for(int i=0;i<state.getNumberOfPacmans();i++)
			game.addPacmanAgent(agent_pacman);
		for(int i=0;i<state.getNumberOfGhosts();i++)
			game.addGhostAgent(agent_ghost);
		
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640,480));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		GameState fstate=game.runUntilEnd(timestep);
		
		if (fstate.isLose())			
			System.out.println("Lose !!");
		else 
			System.out.println("Win !!");
		System.out.println("Nb Food = "+fstate.getFoodEaten());
		System.out.println("Nb Capsule = "+fstate.getCapsulesEaten());
		System.out.println("Nb Ghost = "+fstate.getGhostsEaten());
		System.out.println("Duration = "+fstate.getTime());
	}
}
