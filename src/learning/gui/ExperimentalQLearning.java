package learning.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import learning.*;
import learning.perceptron.Perceptron;
import learning.tp.MainTool;

import pacman.eleves.Agent;
import pacman.prof.Game;
import pacman.prof.GameStateWritable;
import pacman.prof.graphics.GamePanel;
import pacman.sma.IntelligentGhost_Agent2;




public class ExperimentalQLearning extends JFrame implements Observer {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2403343222082176737L;
	private JTabbedPane gamePanel;
	private JTabbedPane optionPanel;
	
	
	private JPanel visualise;
	private JPanel stat;
	
	
	
	
	
	private JPanel qlearning;
	
	private JPanel info;
	
	private FSA fsa;
	private int sensor;
	private StateSensor s;
	private Sensor environment;
	
	
	
	private JTextField exemple;
	private JTextField sensorTaille;
	private JList<String> layout;
	private JTextField e;
	private JTextField train;
	


	
	
	
	private PerceptronAgent perceptron;
	
	private PerceptronAgent perceptront;
	
	private ExperimentalQLearning experiemental = this;
	
	
	private boolean run;
	private int time;
	
	
	private Game runing;
	
	
	
	private FSAplus fsatmax;
	
	
	private String pacmanAgent = "pacman.prof.tp1.RandomAgent";
	public ExperimentalQLearning () {
		time = 300;
		setSize(800,600);
		setResizable (false);
		setLocationRelativeTo(null);
		fsa = new FSA ("pacman.prof.tp1.RandomAgent");
		fsatmax = null;
		sensor = 1;
		s = new CapsuleStateSensor(sensor);
		environment = new Sensor (s);
		build();
		run = false;
	}
	
	
	
	
	
	public void build () {
		setContentPane (buildPanel());
		
		
	}
	
	public JPanel buildPanel() {
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		
		gamePanel = new JTabbedPane();
		gamePanel.setPreferredSize(new Dimension (600, 500) );
		
		optionPanel = new JTabbedPane();
		
		optionPanel.setPreferredSize(new Dimension (180, 500) );
		
		
	
		
		info = new JPanel  ();
		info.setPreferredSize ( new Dimension ( 800, 100 )  );
		infoTitre = new JLabel();
		info.add(infoTitre);
		
		
		
		
		buildVisualisation();
		buildOptionPanel();
		
		pan.add (gamePanel);
		pan.add (optionPanel);
		pan.add (info);
		
		buildMenu();
	
		return pan;
	}
	
	
	public void buildMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu();
		file.setText ("Fichier");
		
		
		final JMenuItem nouveau = new JMenuItem();
		nouveau.setText ("Nouveau");
		nouveau.setEnabled (true);
		
		final JMenuItem nouveaut = new JMenuItem();
		nouveaut.setText ("Nouveau Perceptron t");
		nouveaut.setEnabled (true);
		
		JMenuItem charger = new JMenuItem();
		charger.setText ("charger");
		
		JMenuItem save = new JMenuItem();
		save.setText ("Sauvegarde");
		
		
		JMenuItem saveStat = new JMenuItem();
		saveStat.setText ("Sauvegarder Stat Perceptron 0");
		
		
		JMenuItem saveStat0 = new JMenuItem();
		saveStat0.setText ("Sauvegarde Stat Perceptron t");
		

		JMenuItem saveStatPerf = new JMenuItem();
		saveStatPerf.setText ("Sauvegarde Stat Performance Perceptron 0");
		
		JMenuItem saveStatPerf0 = new JMenuItem();
		saveStatPerf0.setText ("Sauvegarde Stat Performance Perceptron t");
		
		
		
		ActionListener action = new ActionListener () {
			public void actionPerformed (ActionEvent erre) {
				perceptron = MainTool.randomPerc (environment.size(), Double.parseDouble ( e.getText() ) , experiemental, 100, environment, "0");
				info("Nouveau perceptron ........ ");
			}
		};
		
		nouveau.addActionListener (action);
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent erre) {
				perceptront = MainTool.randomPerc (environment.size(), Double.parseDouble ( e.getText() ) , experiemental, 100, environment, "t");
				info("Nouveau perceptron ........ ");
			}
		};
		
		nouveaut.addActionListener (action);
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				JFileChooser choix = new JFileChooser();
				int retour=choix.showOpenDialog(experiemental);
				if(retour==JFileChooser.APPROVE_OPTION){
				   // un fichier a Ã©tÃ© choisi (sortie par OK)
				   // nom du fichier  choisi 
				   info ( choix.getSelectedFile().getName() + " chargé " );
				   nouveau.doClick();
				   // chemin absolu du fichier choisi
				  perceptron.load ( choix.getSelectedFile().
				          getAbsolutePath() )  ;
				}
			}
		};
		
		charger.addActionListener (action);
		
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				JFileChooser choix = new JFileChooser();
				int retour=choix.showOpenDialog(experiemental);
				if(retour==JFileChooser.APPROVE_OPTION){
				   // un fichier a Ã©tÃ© choisi (sortie par OK)
				   // nom du fichier  choisi 
				   info ( choix.getSelectedFile().getName() + " okay " );
				   // chemin absolu du fichier choisi
				  perceptron.save ( choix.getSelectedFile().
				          getAbsolutePath() )  ;
				}
			}
		};
		
		save.addActionListener (action);
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				info ( perceptron.getStat().save() );
				}
		};
		
		saveStat.addActionListener (action);
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				info ( perceptront.getStat().save() );
				}
		};
		
		saveStat0.addActionListener (action);
		
		
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				info ( perceptron.getStat().savePerf() );
				}
		};
		
		saveStatPerf.addActionListener (action);
		
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				info ( perceptront.getStat().savePerf() );
				}
		};
		
		saveStatPerf0.addActionListener (action);
		
		
		file.add (nouveau);
		file.add (nouveaut);
		file.add(saveStat);
		file.add(saveStat0);
		file.add(saveStatPerf);
		file.add(saveStatPerf0);
		
		JMenu perceptron = new JMenu();
		perceptron.setText ("Option");
		
		
		JMenuItem exemple = new JMenuItem();
		exemple.setText ("Supprimer les exemples Perceptron 0");
	
		
		
		JMenuItem exemplefsatplus = new JMenuItem();
		exemplefsatplus.setText ("Supprimer les exemples Perceptron t");
	
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent perceptron) {
				info (fsa.getFSA().size () + " Exemples supprimés " );
				fsa.clear();
				
			}
		};
	
		
		
		exemple.addActionListener (action);
		
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent perceptron) {
				if (fsatmax != null) {
				info (fsatmax.getFSA().size () + " Exemples supprimés " );
				fsatmax.clear();
				}
				else
					info ("Il faut créer un perceptron t");
				
				
			}
		};
	
		
		
		exemplefsatplus.addActionListener (action);
		
		perceptron.add (exemple);
		
		
		perceptron.add (exemplefsatplus);
		
		
		
		
		JMenu aff = new JMenu();
		aff.setText ("Graphique");
		JMenuItem onoff = new JMenuItem();
		onoff.setText("DÃ©marer / Masquer un jeu");
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent action) {
				if (run) {
					visualise.removeAll();
					runing = null;
					
				}
				else 
					LaunchVisu();
				
				visualise.setVisible(false);
				visualise.setVisible(true);
				
				run = !run;
			}
		};
		
		
	
		aff.add (onoff);
		
		
		mb.add  (file);
		mb.add (perceptron);
		mb.add (aff);
		setJMenuBar(mb);
		
		
		onoff.addActionListener (action);
	
		JMenuItem onofft = new JMenuItem();
		onofft.setText("Démarer / Masquer un jeu [Super Perceptron]");
		
		action = new ActionListener () {
			public void actionPerformed (ActionEvent action) {
				if (run) {
					visualise.removeAll();
					runing = null;
					
				}
				else 
					LaunchVisuPerceptront();
				
				visualise.setVisible(false);
				visualise.setVisible(true);
				
				run = !run;
			}
		};
		
		
		
		onofft.addActionListener(action);
		
		aff.add (onofft);
	}
	
	
	public void buildVisualisation() {
		visualise = new JPanel();
		visualise.setPreferredSize(new Dimension(600, 500));
		
		stat = new JPanel();
		gamePanel.addTab ("jeu", visualise);
		gamePanel.addTab ("stat", stat);
	}
	
	public void buildOptionPanel () {
		qlearning = new JPanel();
		qlearning.setLayout  (new FlowLayout());
		JPanel visu = new JPanel();
		visu.setLayout (new FlowLayout());
		optionPanel.addTab ("Q learn Avec Exemple", qlearning);
		optionPanel.addTab ("Option", visu  );
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 1000, 15);
		slider.setPaintTicks (true);
		
		ChangeListener action = new ChangeListener () {
			public void stateChanged (ChangeEvent c) {
				  time = (int) ( ( 1 /  (double)   slider.getValue()    ) * 10000 ) ;
				  if (runing == null) {
				  }
				  else
				  runing.setTime  ( time )   ;
			}
		};
		
		slider.addChangeListener (action);
		
		visu.add (slider);
		
		buildQlearn  ();
	}
	
	public void buildQlearn() {
		 exemple = new JTextField();
		exemple.setText("50");
		exemple.setColumns(10);
		
		JLabel exempleTitre = new JLabel();
		exempleTitre.setText("Nombre de parties");
		
		JButton exempleButton = new JButton();
		exempleButton.setText ("Set pour Perceptron 0");
		
		JButton exemplefsatmaxButton = new JButton();
		exemplefsatmaxButton.setText ("Set pour Perceptron t");
		
		buildActionListenerExemple (exempleButton);
		
		buildActionListenerExempleFsaPlus (exemplefsatmaxButton);
		
		 sensorTaille = new JTextField();
		sensorTaille.setText("3");
		sensorTaille.setColumns(2);
		
		KeyListener action = new KeyListener () {
			public void keyPressed (KeyEvent e) {
				 
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode()  <= KeyEvent.VK_9 ) 
					  sensor =   Integer.parseInt ( sensorTaille.getText() ) ;
				
				s = new CapsuleStateSensor(sensor);
				environment = new Sensor (s);
					
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		sensorTaille.addKeyListener (action);
		
		JLabel sensorTitre   = new JLabel();
		sensorTitre.setText("Taille Sensor");
		
		String []    layoutData = { "mediumClassic.lay", "originalClassic.lay", "smallClassic.lay", "capsuleClassic.lay", "oddSearch.lay", "openClassic.lay"     };
		layout = new JList(layoutData);
		layout.setSelectedValue ("mediumClassic.lay", false);
		JLabel lay = new JLabel("Carte de pacman");
		
		
		e = new JTextField();
		e.setText ("0.01");
		e.setColumns (10);
		
		JLabel eTitre = new JLabel();
		eTitre.setText("Epsilon     :");
		
		action = new KeyListener () {
			public void keyPressed (KeyEvent e) {
				 
			}

			@Override
			public void keyReleased(KeyEvent ep) {
				// TODO Auto-generated method stub
				if (ep.getKeyCode() >= KeyEvent.VK_0 && ep.getKeyCode()  <= KeyEvent.VK_9 ) { 
					double eps =   Double.parseDouble ( e.getText() ) ;
				
				if (perceptron != null)
					perceptron.setEpsilon(eps);
				
				if (perceptront != null)
					perceptront.setEpsilon(eps);
				
				System.out.println ("New e " + eps);
				}
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		
		e.addKeyListener (action);
		train = new JTextField();
		train.setText ("100");
		
		
		
		JLabel trainTitre = new JLabel ();
		trainTitre.setText (" tour d'apprentissage");
		
		
		JButton perceptron = new JButton();
		perceptron.setText ("Générer un perceptron 0");
		buildActionListenerLaunch(perceptron);
		
		
		JButton perceptront = new JButton();
		perceptront.setText ("Générer un perceptron t");
		buildActionListenerLaunchPerceptront(perceptront);
		
		
		JButton perceptrontauto = new JButton();
		perceptrontauto.setText ("Perceptron t - v2.0");
		buildActionNewPerceptronMax (perceptrontauto);
		
		
		
		JButton stat = new JButton();
		stat.setText ("Stat");
		buildActionListenerStat(stat);
		
		
		qlearning.add(exempleTitre);
		qlearning.add(exemple);
		qlearning.add(sensorTitre);
		qlearning.add(sensorTaille);
		qlearning.add(exempleButton);
		qlearning.add(exemplefsatmaxButton);
		qlearning.add(lay);
		qlearning.add(layout);
		qlearning.add(eTitre);
		qlearning.add(e);
		qlearning.add(trainTitre);
		qlearning.add(train);
		qlearning.add(perceptron);
		qlearning.add(perceptront);
		qlearning.add(perceptrontauto);
		qlearning.add(stat);
	}
	
	

	
	private void buildActionListenerStat(JButton stat2) {
		// TODO Auto-generated method stub
		ActionListener action = 
				new ActionListener() {
					public void actionPerformed (ActionEvent event) {
						Thread action = new Thread() {
								public void run() {
									MainTool.getStatisticSet(experiemental);
								}
						};
						action.run();
	}
		};
		stat2.addActionListener(action);
	}





	public void buildActionNewPerceptronMax (JButton b) {
		ActionListener action = 
				new ActionListener() {
					public void actionPerformed (ActionEvent event) {
						 sensor = fsa.getSensor()  ;
						 Thread action = new Thread () {
							 	public void run() {
							 		for (int time = 0; time < 100; time++) {
							 			if (fsatmax == null)
								    		  fsatmax = new FSAplus (perceptron, pacmanAgent);
								    		else
								    			System.out.println ("Perceptron déjà existant");
								      for (int i = 0; i < 10   ; i++) {
											// GENERATION des quadruplets, en mettant 5000 le step max de chaque partie
										fsatmax.generate("layouts/" + layout.getSelectedValue(), 2, 5000, sensor);
										info ("Generating exemple ...... " + fsatmax.getFSA().size() + " exemples" );
										}
								      fsatmax.vector();
								      s = new CapsuleStateSensor (sensor);
								      environment = new Sensor (s);
										
								      info ("Exemples generated ......... " + fsatmax.getFSA().size() + " exemple ready to be eat by the pacman " );
								  
								      if (perceptron == null) {
											return;
										}
										
										if (perceptront == null) {
											s = new CapsuleStateSensor (sensor);
											environment = new Sensor (s);
											perceptront = MainTool.randomPerc (environment.size() ,Double.parseDouble (e.getText()), experiemental, Integer.parseInt ( train.getText ()), environment, "t"  );
											
										}
									
										MainTool.perceptron (fsatmax, perceptront,  Double.parseDouble  ( e.getText() )  ,  Integer.parseInt ( train.getText() ), experiemental, true, "testmap", "CapsuleStateReward", "t"  );
										
										fsatmax.clear();
							 		}
										
								      
							 		}
							 		
							 	};
						 action.start();
						 }
					};
					b.addActionListener (action);
		}
	
	
	public void buildActionListenerExemple (JButton b) {
		ActionListener action = 
				   new ActionListener(){
				   public void actionPerformed(ActionEvent e) {
				    sensor = Integer.parseInt ( sensorTaille.getText() )  ;
				    Thread action = new Thread () {
				    	public void run () {
				      for (int i = 0; i < Integer.parseInt   ( exemple.getText()  )   ; i++) {
							// GENERATION des quadruplets, en mettant 5000 le step max de chaque partie
						fsa.generate("layouts/" + layout.getSelectedValue(), 2, 5000, sensor);
						info ("Generating exemple ...... " + fsa.getFSA().size() + " exemples" );
						}
				      fsa.vector();
				      s = new CapsuleStateSensor (sensor);
				      environment = new Sensor (s);
						
				      info ("Exemples generated ......... " + fsa.getFSA().size() + " exemple ready to be eat by the pacman " );
				   }
				    
				   };
				   action.start();
				}
		};
		  b.addActionListener(action);
	}
	
	public void buildActionListenerExempleFsaPlus (JButton b) {
		ActionListener action = 
				   new ActionListener(){
				   public void actionPerformed(ActionEvent e) {
				    sensor = fsa.getSensor()  ;
				    Thread action = new Thread () {
				    	public void run () {
				    		if (fsatmax == null)
				    		  fsatmax = new FSAplus (perceptron, pacmanAgent);
				    		else
				    			System.out.println ("Perceptron déjà existant");
				      for (int i = 0; i < Integer.parseInt   ( exemple.getText()  )   ; i++) {
							// GENERATION des quadruplets, en mettant 5000 le step max de chaque partie
						fsatmax.generate("layouts/" + layout.getSelectedValue(), 2, 5000, sensor);
						info ("Generating exemple ...... " + fsatmax.getFSA().size() + " exemples" );
						}
				      fsatmax.vector();
				      s = new CapsuleStateSensor (sensor);
				      environment = new Sensor (s);
						
				      info ("Exemples generated ......... " + fsatmax.getFSA().size() + " exemple ready to be eat by the pacman " );
				   }
				    
				   };
				   action.start();
				}
		};
		  b.addActionListener(action);
	}
	
	public void buildActionListenerLaunch (JButton b) {
		
		ActionListener action =
				new ActionListener () {
					public void actionPerformed (ActionEvent even) {
						Thread t = new Thread () { public void run () {
						
						if (perceptron == null) {
						s = new CapsuleStateSensor (sensor);
						environment = new Sensor (s);
						perceptron = MainTool.randomPerc (environment.size() ,Double.parseDouble (e.getText()), experiemental, Integer.parseInt ( train.getText ()), environment, "0"  );
						}
						
					
						MainTool.perceptron (fsa, perceptron,  Double.parseDouble  ( e.getText() )  ,  Integer.parseInt ( train.getText() ), experiemental, true, "testmap", "CapsuleStateReward", "0"  );
						}
						};
						t.start();
						infoTitre.setText ("Creating Perceptron ...");
						System.out.println((environment.size() - 1) / 4.0);
						}
					
		};
		
		b.addActionListener (action);
	}
	
	
public void buildActionListenerLaunchPerceptront (JButton b) {
		
		ActionListener action =
				new ActionListener () {
					public void actionPerformed (ActionEvent even) {
						Thread t = new Thread () { public void run () {
						
						if (perceptron == null) {
							return;
						}
						
						if (perceptront == null) {
							s = new CapsuleStateSensor (sensor);
							environment = new Sensor (s);
							perceptront = MainTool.randomPerc (environment.size() ,Double.parseDouble (e.getText()), experiemental, Integer.parseInt ( train.getText ()), environment, "t"  );
							
						}
					
						MainTool.perceptron (fsatmax, perceptront,  Double.parseDouble  ( e.getText() )  ,  Integer.parseInt ( train.getText() ), experiemental, true, "testmap", "CapsuleStateReward", "t"  );
						}
						};
						t.start();
						infoTitre.setText ("Creating Perceptron ...");
						System.out.println((environment.size() - 1) / 4.0);
						}
					
		};
		
		b.addActionListener (action);
	}




	@Override
	public void train(double v, double w, String type, int n) {
		// TODO Auto-generated method stub
		if (v < 0.99)
		infoTitre.setText ("[Creating] Perceptron " + type + " ........... "    +  " [Perceptron n: " + n + " ] PROGRESS " + v * 100 + " % " + "      err " + w );
		else
			infoTitre.setText ("DONE ...");
		
	}
	
	
	private JLabel infoTitre;

	@Override
	public void info(String info) {
		// TODO Auto-generated method stub
		infoTitre.setText (info);
	}
	
	
	public void LaunchVisu() {

		
		
		new Thread () {
			public void run () {
				
			
		 
				
		String pacman="pacman.prof.tp1.PerceptronAgent";
		

		
		Agent agent_pacman = null;
		
		
		agent_pacman = perceptron ;
	
		
		
		
		
		GameStateWritable state;
		 state = MainTool.initMaze (  "layouts/" + layout.getSelectedValue   ());
		
		
		ArrayList <Agent> ghosts = new ArrayList <Agent> ();
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts.add (new IntelligentGhost_Agent2(0.8));
	
		
		
		
		Reward reward = new SimpleReward();
		
		run = true;
		
		
		runing = RewardTools.vizualize(state,        agent_pacman   , ghosts, reward, 30, time, visualise);
		runing.runUntilEnd(time, 30);	
		
		
		
			}
		}.start();

		
	}
	
public void LaunchVisuPerceptront() {

		
		
		new Thread () {
			public void run () {
				
			
		 
				
		String pacman="pacman.prof.tp1.PerceptronAgent";
		

		
		Agent agent_pacman = null;
		
		
		agent_pacman = perceptront ;
	
		
		
		
		
		GameStateWritable state;
		 state = MainTool.initMaze (  "layouts/" + layout.getSelectedValue   ());
		
		
		ArrayList <Agent> ghosts = new ArrayList <Agent> ();
		for(int i=0;i<state.getNumberOfGhosts();i++)
			ghosts.add (new IntelligentGhost_Agent2(0.8));
	
		
		
		
		Reward reward = new SimpleReward();
		
		run = true;
		
		
		runing = RewardTools.vizualize(state,        agent_pacman   , ghosts, reward, 30, time, visualise);
		runing.runUntilEnd(time, 30);	
		
		
		
			}
		}.start();

		
	}
	

	
}