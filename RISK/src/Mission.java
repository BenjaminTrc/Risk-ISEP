
public class Mission {
	
	// ***** Attributs *****
	
	private int associated_player;
	private boolean mission_complete = false;
	private int mission_nb;
	//private int type;
	private int player_to_destroy;
	private int nb_territories;
	private String description;
	
	
	// ***** Constructeurs *****
	
	public Mission(int player_nb, int nb_players) {
		this.associated_player = player_nb;
		choseMission(nb_players);
		
		switch(mission_nb) {
		
			case 1:
				nb_territories = 42;
				description = "Conquérir tous les territoires";
				break;
				
			case 2:				
				nb_territories = 30;
				description = "Contrôler 30 territoires";
				break;
				
			case 3:
				nb_territories = 24;
				description = "Contrôler 24 territoires";
				break;
				
			case 4:
				nb_territories = 21;
				description = "Contrôler 21 territoires";
				break;
				
			case 5:
				nb_territories = 18;
				description = "Contrôler 18 territoires avec au moins 2 unités";
				break;
				
			case 7:
				nb_territories = 18;
				description = "Contrôler 3 régions et au moins 18 territoires";
				break;
				
			case 6: 
				player_to_destroy=associated_player;
				while (player_to_destroy == associated_player) {
					 player_to_destroy = (int) (Math.random()*6+1);
				}
				break;
				
			case 8:
				description = "Contrôler l'Asie et une autre région";
				break;
		}
	}
	
	// ***** Methodes *****
	
	/*
	 * Décide du numéro de la mission donnée au joueur en fonction du nombre total de joueurs
	 */
	public void choseMission(int nb_players) {
		switch(nb_players) {
		
		//On a donné un numéro à chaque mission possible, le nombre de joueurs indique 
		//quelles missions sont possibles et on change donc les missions obtenues
		
			case 2:		
				mission_nb = (int) (Math.random()*4+1);
				if (mission_nb>2) {
					mission_nb+=4;
				}
				break;
		
			case 3:
				mission_nb = (int) (Math.random()*6+1);
				if (mission_nb>2 && mission_nb<5) {
					 mission_nb+=2;
				 }
				break;
				
			case 4:
			case 5:
				mission_nb = (int) (Math.random()*5+3);
				if (mission_nb==4) {
					 mission_nb+=1;
				 }
				break;
				
			case 6:
				mission_nb = (int) (Math.random()*5+4);
				break;
		}
		return ;
	}
	
	
	
	// ***** Getters Setters *****
	
	public boolean getMissionComplete() {
		return this.mission_complete;
	}
	
	public void setMissionComplete(boolean completed) {
		this.mission_complete = completed;
	}
	
	public int getMissionNb() {
		return this.mission_nb;
	}
	
	public int getPlayerNb() {
		return this.associated_player;
	}
	
	public int getNbTerritories() {
		return this.nb_territories;
	}
	
	public int getPlayerToDestroy() {
		return this.player_to_destroy;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String d) {
		this.description = d;
	}
	
}
