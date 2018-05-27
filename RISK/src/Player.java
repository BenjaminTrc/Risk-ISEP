
public class Player {

	// ***** Attributs *****
	
	private int player_nb;
	private String player_name;
	private int player_color; // une couleur associée à chaque joueur
	private int army_points; //points d'armée à utiliser pour recruter des unités
	private Mission player_mission;
	private int last_turn_territories_won;
	
	
	
	// ***** Constructeurs *****
	
	public Player(int number, String name, int color) {
		this.player_nb = number;
		this.player_name = name;
		this.player_color = color;
		
		//player_mission = new Mission(player_nb, Plateautest.getNbPlayers()); //game = nom du board
	}
	
	// ***** Methodes *****
	
	//Réception des armée lors de l'initialisation de la partie, en fonction du nombre de joueurs
	public void initArmyPoints(Board b) {
		if (b.getNbPlayers() == 2) {
			this.army_points = 40;
		}
		else if (b.getNbPlayers() == 3) {
			this.army_points = 35;
		}
		else if (b.getNbPlayers() == 4) {
			this.army_points = 30;
		}
		else if (b.getNbPlayers() == 5) {
			this.army_points = 25;
		}
		else if (b.getNbPlayers() == 6) {
			this.army_points = 20;
		}
	}
	
	public void addTerritory() {
		this.last_turn_territories_won += 1;
	}
	
	// ***** Getters / Setters *****
	
	public int getPlayerNb() {
		return this.player_nb;
	}
	
	public void setPlayerNb(int Nb) {
		this.player_nb = Nb;
	}
	
	public String getPlayerName() {
		return this.player_name;
	}
	
	public void setPlayerName(String n) {
		this.player_name = n;
	}
	
	public int getPlayerColor() {
		return this.player_color;
	}
	
	public void setPlayerColor(int col) {
		this.player_color = col;
	}
	
	public int getArmyPoints() {
		return this.army_points;
	}
	
	public void setArmyPoints(int ap) {
		this.army_points = ap;
	}
	
	public int getLastTurnTerritories() {
		return last_turn_territories_won;
	}
	
	public void setLastTurnTerritories(int territories_won) {
		this.last_turn_territories_won = territories_won;
	}
}
