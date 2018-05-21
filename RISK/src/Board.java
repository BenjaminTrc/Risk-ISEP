import java.util.*;
import java.util.Scanner;

public class Board {

	
	// ***** Attributs *****
	
	private int map_number; // Choix de la carte
	private int nbr_players;
	private int nbr_AI;
	private ArrayList<Player> players_list = new ArrayList<Player>();
	private ArrayList<Region> regions_list = new ArrayList<Region>();
	
	
	// ***** Constructeurs *****
	
	public Board(int map, int nbr_players, int nbr_AI) {
		
		Scanner scan = new Scanner(System.in);
		
		this.map_number = map;
		this.nbr_players = nbr_players;
		this.nbr_AI = nbr_AI;
		String name = "";
		
		int [] used_colors = new int[6];
		
		for (int i=1; i<=nbr_players; i++) {
			int color = 0;
			if (i<=nbr_players-nbr_AI) {
				
				System.out.println("Joueur " + i);
				System.out.println("Nom de joueur : ");  // temporaire en attendant la mise en place graphique
				name = scan.nextLine();
				scan.nextLine();
				
				System.out.println("Couleur : ");
				color = scan.nextInt();
				scan.nextLine();
				
			}
			else {
				name = "Computer " + (i-nbr_AI+1) + "";
				boolean unique_color = false;
				while (unique_color = false) {
					color = (int) (Math.random()*6+1);
					int compteur = 0;
					for (int k=0; k<i; k++) {
						if (used_colors[i] == color) {
							break;
						}
						else {
							compteur+=1;
						}
					}
					if (compteur==i-1) {
						unique_color = true;
					}
				}
			}
			used_colors[i] = color;
			players_list.add(new Player(i, name, color));
		}
	}
	
	// ***** Methodes *****
	
public boolean verifyMission(Mission mission) {
		
		switch(mission.getMissionNb()) {
			case 1:
			case 2:
			case 3:
			case 4:
				int territories = 0;
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					for (Territory t : territories_list) {
						if (t.getOwner() == mission.getPlayerNb()) {
							territories+=1;
						}
					}
				}
				if (territories >= mission.getNbTerritories()) {
					mission.setMissionComplete(true);
				}
				break;
				
			case 5:
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					for (Territory t : territories_list) {
						
						//DEUX FONCTIONS A CREER DANS TERRITORY
						if (t.getOwner() == mission.getPlayerNb() && t.getUnits()) {
							territories+=1;
						}
					}
				}
				if (territories >= mission.getNbTerritories()) {
					mission.setMissionComplete(true);
				}
				break;
				
			case 6:
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					for (Territory t : territories_list) {
						if (t.getOwner() == mission.getPlayerToDestroy()) {
							territories+=1;
						}
					}
				}
				if (territories == 0) {
					mission.setMissionComplete(true);
				}
				break;
				
			case 7:
				int nb_regions = 0;
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					int territories_region = 0;
					for (Territory t : territories_list) {
						if (t.getOwner() == mission.getPlayerNb()) {
							territories+=1;
							territories_region+=1;
						}
					}
					if (territories_region == territories_list.size()) {
						nb_regions+=1;
					}
				}
				if (territories >= mission.getNbTerritories() && nb_regions>=3) {
					mission.setMissionComplete(true);
				}
				break;
				
			case 8:
				int biggest_region = 0;
				int record = 0;
				int count = 0;
				int nb_regions2 = 0;
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					int max_territories = 0;
					int territories_region = 0;
					for (Territory t : territories_list) {
						max_territories+=1;
						if (t.getOwner() == mission.getPlayerNb()) {
							territories+=1;
							territories_region+=1;
						}
						
					}
					if (territories_region == territories_list.size()) {
						nb_regions+=1;
					}
					
					if (max_territories> record) {
						record = max_territories;
						biggest_region = count;
					}
					count+=1;
				}
				ArrayList<Territory> territories_region = regions_list[biggest_region].getTerritoryList();
				
				territories = 0;
				for (Territory t : territories_region) {
					if (t.getOwner() == mission.getPlayerNb()) {
						territories+=1;
						if (nb_regions2>1) {
							mission.setMissionComplete(true);
						}
					}
				}
				
				break;
		}
		
		return mission.getMissionComplete();
	}
	
	
	// ***** Getters / Setters *****
	
	public int getNbPlayers() {
		return this.nbr_players+nbr_AI;
	}
	
	public void setNbPlayers(int nb) {
		this.nbr_players = nb;
	}
	
	public ArrayList getRegionsList() {
		return this.regions_list;
	}
	
}
