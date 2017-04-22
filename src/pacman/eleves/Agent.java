package pacman.eleves;

import pacman.prof.GameStateWritable;

/**
 * Cette Classe d�crit un agent (pacman ou fantome)
 * @author denoyer
 *
 */

public interface Agent 
{
	/**
	 * Permet de renvoyer l'action effectu�e par l'agent dans un �tat du jeu donn�
	 * @param as l'�tat qui correspond � l'agent (permet de trouver les coordonn�es de l'agent)
	 * @param state l'�tat courant du jeu
	 * @return l'action � choisir. 
	 */
	public AgentAction getAction(AgentState as,GameState state);


}
