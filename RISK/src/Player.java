
public class Player {

	// ***** Attributs *****
	
	private int player_nb;
	private String player_name;
	private int player_color; // une couleur associée à chaque joueur
	
	
	
	// ***** Constructeurs *****
	
	public Player(int number) {
		this.player_nb = number;
		
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
