import java.util.ArrayList;

public abstract class AI {

	
	// ***** Methodes *****
	
	public void AIInitialization(Board B) {
		
		//On cr�e une liste de tous les territoires apartenant � l'IA
		ArrayList<Territory> ai_territories_list = new ArrayList<Territory>();
		
		for (Region r : B.getRegionsList() ) {			
			for (Territory t : r.getTerritoryList()) {
				//On ajoute chaque territoire � cette liste si l'IA le poss�de
				if (t.getOwner() == B.getPlayerPlaying()) {
					ai_territories_list.add(t);
				}				
			}
		}
				
		//On parcourt les territoires un par un et on y place un soldat
		Unit soldier = new Unit(1);
		for (Territory t : ai_territories_list) {
			t.addUnit(soldier);	
			//On d�cr�mente le nombre de points d'arm�e disponibles pour le joueur
			for (Player p : B.getPlayers()) {
				if(p.getPlayerNb() == t.getOwner()) {
					p.setArmyPoints(p.getArmyPoints()-1);
				}
			}		
		}
		
		//Les unit�s restantes sont plac�es sur des territoires voisins � des ennemis tant que l'IA a des points d'arm�e
		while(true) {
			
		}
	}
	
	public void AIReinforcement() {
		//On place une unit� sur un territoire s'il y a au moins un voisin ennemi
		
		//Am�lioration en fonction des r�gions � conqu�rir?
	}
	
	public void AIAttack() {
		//On bouge les unit�s qui n'ont aucun ennemi sur les territoires adjacents
		
		//L'IA v�rifie qu'elle attaque avec au moins X unit�s contre un territoire ennemi qui d�fend avec X unit�s
	}
}
