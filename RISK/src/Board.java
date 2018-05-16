import java.util.ArrayList;

public class Board {

	
	// ***** Attributs *****
	
	private int map;
	
	private ArrayList<Player> liste_joueurs = new ArrayList();
	
	
	// ***** Constructeurs *****
	
	public Board(int map, int nbr_joueurs) {
		this.map = map;
		
		for (int i=1; i<=nbr_joueurs; i++) {
			liste_joueurs.add(new Player(i));
		}
	}
	
	// ***** Methodes *****
	
	
	
	// ***** Getters / Setters *****
	
	
}
