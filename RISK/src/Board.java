import java.util.*;
import java.util.Scanner;

public class Board {

	
	// ***** Attributs *****
	
	private int map_number; //Choix de la carte
	private int nbr_players;
	private int nbr_AI;
	private ArrayList<Player> players_list = new ArrayList();
	
	
	// ***** Constructeurs *****
	
	public Board(int map, int nbr_players, int nbr_AI) {
		
		Scanner scan = new Scanner(System.in);
		
		this.map_number = map;
		this.nbr_players = nbr_players;
		this.nbr_AI = nbr_AI;
		
		int [] used_colors = [];
		
		for (int i=1; i<=nbr_players; i++) {
			int color = 0;
			if (i<=nbr_players-nbr_AI) {
				
				System.out.println("Joueur " + i);
				System.out.println("Nom de joueur : ");  //temporaire en attendant la mise en place graphique
				String name = scan.nextLine();
				scan.nextLine();
				
				System.out.println("Couleur : ");
				color = scan.nextInt();
				scan.nextLine();
				
			}
			else {
				String name = "Computer " + (i-nbr_AI+) + "";
				boolean unique_color = false;
				while (unique_color = false) {
					color = (int) (Math.random()*6+1);
					int compteur = 0;
					for (int k=0; k<i; k++) {
						if (used_colors[i] = color) {
							break;
						}
						else {
							compteur+=1;
						}
					}
					if (compteur=i-1) {
						unique_color = true;
					}
				}
			}
			used_colors.add(color);
			players_list.add(new Player(i), name, color);
		}
	}
	
	// ***** Methodes *****
	
	
	
	// ***** Getters / Setters *****
	
	
}
