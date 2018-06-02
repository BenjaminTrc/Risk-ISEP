import edu.princeton.cs.introcs.StdDraw;


public class Player {

	// ***** Attributs *****
	
	private int player_nb;
	private String player_name;
	private int player_color; // une couleur associée à chaque joueur
	private int army_points; //points d'armée à utiliser pour recruter des unités
	private Mission player_mission;
	private int last_turn_territories_won;
	
	
	
	// ***** Constructeurs *****
	
	public Player(int number, String name, int color, int players) {
		this.player_nb = number;
		this.player_name = name;
		this.player_color = color;
		initArmyPoints(players);
		
		//player_mission = new Mission(player_nb, Plateautest.getNbPlayers()); //game = nom du board
	}
	
	// ***** Methodes *****
	
	//Réception des armée lors de l'initialisation de la partie, en fonction du nombre de joueurs
	public void initArmyPoints(int nb) {
		if (nb == 2) {
			this.army_points = 40;
		}
		else if (nb == 3) {
			this.army_points = 35;
		}
		else if (nb == 4) {
			this.army_points = 30;
		}
		else if (nb == 5) {
			this.army_points = 25;
		}
		else if (nb == 6) {
			this.army_points = 20;
		}
	}
	
	public void addTerritory() {
		this.last_turn_territories_won += 1;
	}
	
	public void changeColor() {
		
		if (player_color == 1) {
			StdDraw.setPenColor(StdDraw.BLUE);
		}
		
		if (player_color == 2) {
			StdDraw.setPenColor(StdDraw.RED);
		}
		
		if (player_color == 3) {
			StdDraw.setPenColor(255,20,147);
		}
		
		if (player_color == 4) {
			StdDraw.setPenColor(34,139,34);
		}
		
		if (player_color == 5) {
			StdDraw.setPenColor(32, 178, 170);
		}
		
		if (player_color == 6) {
			StdDraw.setPenColor(128, 0, 0);
		}
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
