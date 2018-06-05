import java.util.ArrayList;

public abstract class AI {

	
	// ***** Methodes *****
	
	public void AIInitialization(Board B) {
		
		//On crée une liste de tous les territoires apartenant à l'IA
		ArrayList<Territory> ai_territories_list = new ArrayList<Territory>();
		
		for (Region r : B.getRegionsList() ) {			
			for (Territory t : r.getTerritoryList()) {
				//On ajoute chaque territoire à cette liste si l'IA le possède
				if (t.getOwner() == B.getPlayerPlaying()) {
					ai_territories_list.add(t);
				}				
			}
		}
				
		//On parcourt les territoires un par un et on y place un soldat
		Unit soldier = new Unit(1);
		for (Territory t : ai_territories_list) {
			t.addUnit(soldier);	
			//On décrémente le nombre de points d'armée disponibles pour le joueur
			for (Player p : B.getPlayers()) {
				if(p.getPlayerNb() == t.getOwner()) {
					p.setArmyPoints(p.getArmyPoints()-1);
				}
			}		
		}
		
		//Les unités restantes sont placées sur des territoires voisins à des ennemis tant que l'IA a des points d'armée
		while(true) {
			
		}
	}
	
	public void AIReinforcement() {
		//On place une unité sur un territoire s'il y a au moins un voisin ennemi
		
		//Amélioration en fonction des régions à conquérir?
	}
	
	public void AIAttack() {
		//On bouge les unités qui n'ont aucun ennemi sur les territoires adjacents
		
		//L'IA vérifie qu'elle attaque avec au moins X unités contre un territoire ennemi qui défend avec X unités
	}
}
