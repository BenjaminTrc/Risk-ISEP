
public class Player {

	// ***** Attributs *****
	
	private int player_nb;
	private String player_name;
	private int player_color; // une couleur associée à chaque joueur
	private Mission player_mission;
	
	
	
	// ***** Constructeurs *****
	
	public Player(int number, String name, int color) {
		this.player_nb = number;
		this.player_name = name;
		this.player_color = color;
		
		player_mission = new Mission(player_nb, Plateautest.getNbPlayers()); //game = nom du board
	}
	
	// ***** Methodes *****
	
	
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
}
