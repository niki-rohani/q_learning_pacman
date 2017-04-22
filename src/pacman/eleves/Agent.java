package pacman.eleves;

import pacman.prof.GameStateWritable;

/**
 * Cette Classe décrit un agent (pacman ou fantome)
 * @author denoyer
 *
 */

public interface Agent 
{
	/**
	 * Permet de renvoyer l'action effectuée par l'agent dans un état du jeu donné
	 * @param as l'état qui correspond à l'agent (permet de trouver les coordonnées de l'agent)
	 * @param state l'état courant du jeu
	 * @return l'action à choisir. 
	 */
	public AgentAction getAction(AgentState as,GameState state);


}
