
public class Mission {
	
	// ***** Attributs *****
	
	private int associated_player;
	private boolean mission_complete = false;
	private int mission_nb;
	private int type;
	private int player_to_destroy;
	private int nb_territories;
	
	
	// ***** Constructeurs *****
	
	public Mission(int player_nb, int nb_players) {
		this.associated_player = player_nb;
		choseMission(nb_players);
		
		switch(mission_nb) {
		
			case 1:
				nb_territories = 42;
				break;
				
			case 2:				
				nb_territories = 30;
				break;
				
			case 3:
				nb_territories = 24;
				break;
				
			case 4:
				nb_territories = 21;
				break;
				
			case 5:
			case 7:
				nb_territories = 18;
				break;
				
			case 6: 
				player_to_destroy=associated_player;
				while (player_to_destroy == associated_player) {
					 player_to_destroy = (int) (Math.random()*6+1);
				}
				break;
		}
	}
	
	// ***** Methodes *****
	
	/*
	 * Décide du numéro de la mission donnée au joueur en fonction du nombre total de joueurs
	 */
	public void choseMission(int nb_players) {
		switch(nb_players) {
		
		
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
}
