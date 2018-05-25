import java.util.*;
import java.util.Scanner;

public class Board {

	
	// ***** Attributs *****
	
	private int map_number; // Choix de la carte
	private int nbr_players;
	private int nbr_AI;
	private ArrayList<Player> players_list = new ArrayList<Player>();
	private ArrayList<Region> regions_list = new ArrayList<Region>();
	private int territories = 0;
	private int nb_regions = 0;
	private boolean victory = false;
	private int player_playing = 1;
	
	
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
	
	
	public void play() {
		
		// player_playing = 1;
		
		while (victory == false) {
			
		}
	}
	
	/*
	 * Distribue aléatoirement les territoires entre les joueurs au début de la partie
	 */
	public void territoriesDistribution() {
		//On démarre par le joueur 1
		player_playing = 1;
		//On crée une liste qui contiendra tous les territoires
		ArrayList<Territory> all_territories = new ArrayList<Territory>();
		for (Region r : regions_list ) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			for (Territory t : territories_list) {
				//On ajoute chaque territoire à cette liste
				all_territories.add(t);
			}
		}
		
		//Tant qu'il reste des territoires à affecter
		while (all_territories.size()>0) {
			
			//On détermine un territoire aléatoirement
			int i = (int) (Math.random()*all_territories.size());
			//On le donne au joueur
			all_territories.get(i).setOwner(player_playing);
			all_territories.remove(i);
			
			//Puis on passe au joueur suivant et on recommence
			if (player_playing >= nbr_players) {
				player_playing = 1;
			}
			else {
				player_playing+=1;
			}
		}
		
	}
	
	public void addRegion(Region R) {
		regions_list.add(R);
	}
	
	public void printRegions() {
		int i = 1;
		for (Region R : regions_list){
			System.out.println(R.getRegionId() + " " + R.getRegionName());
			for (Territory T : R.getTerritoryList()) {
				System.out.println(T.getTerritoryId() + " " + T.getTerritoryName());
			}
		}
	}
	
	public void initialization() {
			
		Region North_America = new Region(1, "North America");
		Territory Alaska = new Territory(101, "Alaska");
		North_America.addTerritory(Alaska);
		Territory Alberta = new Territory(102, "Alberta");
		North_America.addTerritory(Alberta);
		Territory Central_America = new Territory(103, "Central America");
		North_America.addTerritory(Central_America);
		Territory Eastern_United_States = new Territory(104, "Eastern United States");
		North_America.addTerritory(Eastern_United_States);
		Territory Greenland = new Territory(105, "Greenland");
		North_America.addTerritory(Greenland);
		Territory Northwest_Territory = new Territory(106, "Northwest Territory");
		North_America.addTerritory(Northwest_Territory);
		Territory Ontario = new Territory(107, "Ontario");
		North_America.addTerritory(Ontario);
		Territory Quebec = new Territory(108, "Quebec");
		North_America.addTerritory(Quebec);
		Territory Western_United_States = new Territory(109, "Western United States");
		North_America.addTerritory(Western_United_States);
		
		Region South_America = new Region(2, "South America");
		Territory Argentina = new Territory(201, "Argentina");
		South_America.addTerritory(Argentina);
		Territory Brazil = new Territory(202, "Brazil");
		South_America.addTerritory(Brazil);
		Territory Peru = new Territory(203, "Peru");
		South_America.addTerritory(Peru);
		Territory Venezuela = new Territory(204, "Venezuela");
		South_America.addTerritory(Venezuela);
		
		Region Europe = new Region(3, "Europe");
		Territory Great_Britain = new Territory(301, "Great Britain");
		Europe.addTerritory(Great_Britain);
		Territory Iceland = new Territory(302, "Iceland");
		Europe.addTerritory(Iceland);
		Territory Northern_Europe = new Territory(303, "Northern Europe");
		Europe.addTerritory(Northern_Europe);
		Territory Scandinavia = new Territory(304, "Scandinavia");
		Europe.addTerritory(Scandinavia);
		Territory Southern_Europe = new Territory(305, "Southern Europe");
		Europe.addTerritory(Southern_Europe);
		Territory Ukraine = new Territory(306, "Ukraine");
		Europe.addTerritory(Ukraine);
		Territory Western_Europe = new Territory(307, "Western Europe");
		Europe.addTerritory(Western_Europe);
		
		Region Africa = new Region(4, "Africa");
		Territory Congo = new Territory(401, "Congo");
		Africa.addTerritory(Congo);
		Territory East_Africa = new Territory(402, "East Africa");
		Africa.addTerritory(East_Africa);
		Territory Egypt = new Territory(403, "Egypt");
		Africa.addTerritory(Egypt);
		Territory Madagascar = new Territory(404, "Madagascar");
		Africa.addTerritory(Madagascar);
		Territory North_Africa = new Territory(405, "North Africa");
		Africa.addTerritory(North_Africa);
		Territory South_Africa = new Territory(406, "South Africa");
		Africa.addTerritory(South_Africa);
		
		Region Asia = new Region(5, "Asia");
		Territory Afghanistan = new Territory(501, "Afghanistan");
		Asia.addTerritory(Afghanistan);
		Territory China = new Territory(502, "China");
		Asia.addTerritory(China);
		Territory India = new Territory(503, "India");
		Asia.addTerritory(India);
		Territory Irkutsk = new Territory(504, "Irkutsk");
		Asia.addTerritory(Irkutsk);
		Territory Japan = new Territory(505, "Japan");
		Asia.addTerritory(Japan);
		Territory Kamchatka = new Territory(506, "Kamchatka");
		Asia.addTerritory(Kamchatka);
		Territory Middle_East = new Territory(507, "Middle East");
		Asia.addTerritory(Middle_East);
		Territory Mongolia = new Territory(508, "Mongolia");
		Asia.addTerritory(Mongolia);
		Territory Siam = new Territory(509, "Siam");
		Asia.addTerritory(Siam);
		Territory Siberia = new Territory(510, "North Siberia");
		Asia.addTerritory(Siberia);
		Territory Ural = new Territory(511, "Ural");
		Asia.addTerritory(Ural);
		Territory Yakutsk = new Territory(512, "Yakutsk");
		Asia.addTerritory(Yakutsk);
		
		Region Australia = new Region(6, "Australia");
		Territory Eastern_Australia = new Territory(601, "Eastern Australia");
		Australia.addTerritory(Eastern_Australia);
		Territory Indonesia = new Territory(602, "Indonesia");
		Australia.addTerritory(Indonesia);
		Territory New_Guinea = new Territory(603, "New Guinea");
		Australia.addTerritory(New_Guinea);
		Territory Western_Australia = new Territory(604, "Western Australia");
		Australia.addTerritory(Western_Australia);

		this.addRegion(North_America);
		this.addRegion(South_America);
		this.addRegion(Europe);
		this.addRegion(Africa);
		this.addRegion(Asia);
		this.addRegion(Australia);
		
		this.printRegions();
	}
	
	public boolean verifyMission(Mission mission) {
		
		//Les conditions de victoire dépendent du type de mission
		switch(mission.getMissionNb()) {
		
		//Les 4 premières missions dépendent juste d'un nombre de territoires
			case 1:
			case 2:
			case 3:
			case 4:
				territories = 0;
				//On parcourt les regions de la carte
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					//On parcourt les territoires de chaque région
					for (Territory t : territories_list) {
						//On compte le territoire s'il appartient au joueur
						if (t.getOwner() == mission.getPlayerNb()) {
							territories+=1;
						}
					}
				}
				//Si le nombre de territoires décrit par la mission est atteint, le joueur gagne la partie
				if (territories >= mission.getNbTerritories()) {
					mission.setMissionComplete(true);
				}
				break;
				
				
			//Controler 18 territoires avec au moins 2 unités
			case 5:
				territories = 0;
				//On parcourt les regions de la carte
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					//On parcourt les territoires de chaque région
					for (Territory t : territories_list) {
						
						//Si le joueur controle le territoire avec au moins deux unités, il est comptabilisé
						if (t.getOwner() == mission.getPlayerNb() && t.getNbUnits()>=2) {
							territories+=1;
						}
					}
				}
				//Si le nombre de territoires est atteint, le joueur gagne la partie
				if (territories >= mission.getNbTerritories()) {
					mission.setMissionComplete(true);
				}
				break;
				
			//Détruire le joueur donné
			case 6:
				territories = 0;
				//On parcourt les regions de la carte
				for (Region r : regions_list) {
					ArrayList<Territory> territories_list = r.getTerritoryList();
					//On parcourt les territoires de chaque région
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
				
			//Controler 3 regions avec au moins 18 territoires
			case 7:
				territories = 0;
				nb_regions = 0;
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
				
				
			//Controler la plus grande région et un autre
			case 8:
				territories = 0;
				int biggest_region = 0;
				int record = 0;
				int count = 0;
				nb_regions = 0;
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
				ArrayList<Territory> territories_region = regions_list.get(biggest_region).getTerritoryList();
				
				territories = 0;
				for (Territory t : territories_region) {
					if (t.getOwner() == mission.getPlayerNb()) {
						territories+=1;
						if (nb_regions>1) {
							mission.setMissionComplete(true);
						}
					}
				}
				
				break;
		}
		
		return mission.getMissionComplete();
	}
	
	public static int returnTerritoryID(double posX, double posY) {
		// AMERIQUE DU NORD
		if (posX > 67 && posX < 99 && posY > 537 && posY < 569) {
			return 101;
		}
		if (posX > 126 && posX < 158 && posY > 493 && posY < 525) {
			return 102;
		}
		if (posX > 113 && posX < 149 && posY > 346 && posY < 382) {
			return 103;
		}
		if (posX > 200 && posX < 238 && posY > 407 && posY < 445) {
			return 104;
		}
		if (posX > 418 && posX < 456 && posY > 567 && posY < 605) {
			return 105;
		}
		if (posX > 162 && posX < 194 && posY > 544 && posY < 576) {
			return 106;
		}
		if (posX > 220 && posX < 252 && posY > 490 && posY < 522) {
			return 107;
		}
		if (posX > 303 && posX < 335 && posY > 493 && posY < 525) {
			return 108;
		}
		if (posX > 111 && posX < 149 && posY > 432 && posY < 470) {
			return 109;
		}
		
		// AMERIQUE DU SUD
		if (posX > 280 && posX < 316 && posY > 102 && posY < 138) {
			return 201;
		}
		if (posX > 322 && posX < 360 && posY > 191 && posY < 229) {
			return 202;
		}
		if (posX > 266 && posX < 294 && posY > 183 && posY < 211) {
			return 203;
		}
		if (posX > 249 && posX < 281 && posY > 281 && posY < 313) {
			return 204;
		}
		
		// EUROPE
		if (posX > 497 && posX < 529 && posY > 477 && posY < 509) {
			return 301;
		}
		if (posX > 475 && posX < 507 && posY > 516 && posY < 548) {
			return 302;
		}
		if (posX > 591 && posX < 621 && posY > 484 && posY < 514) {
			return 303;
		}
		if (posX > 598 && posX < 628 && posY > 528 && posY < 558) {
			return 304;
		}
		if (posX > 625 && posX < 655 && posY > 442 && posY < 472) {
			return 305;
		}
		if (posX > 673 && posX < 711 && posY > 492 && posY < 530) {
			return 306;
		}
		if (posX > 545 && posX < 577 && posY > 445 && posY < 477) {
			return 307;
		}
		
		// AFRIQUE
		if (posX > 627 && posX < 663 && posY > 244 && posY < 280) {
			return 401;
		}
		if (posX > 707 && posX < 745 && posY > 271 && posY < 309) {
			return 402;
		}
		if (posX > 635 && posX < 667 && posY > 369 && posY < 401) {
			return 403;
		}
		if (posX > 753 && posX < 789 && posY > 149 && posY < 185) {
			return 404;
		}
		if (posX > 530 && posX < 568 && posY > 329 && posY < 367) {
			return 405;
		}
		if (posX > 626 && posX < 664 && posY > 158 && posY < 196) {
			return 406;
		}
		
		// ASIE
		if (posX > 784 && posX < 822 && posY > 446 && posY < 484) {
			return 501;
		}
		if (posX > 948 && posX < 984 && posY > 395 && posY < 431) {
			return 502;
		}
		if (posX > 861 && posX < 899 && posY > 351 && posY < 389) {
			return 503;
		}
		if (posX > 935 && posX < 965 && posY > 493 && posY < 523) {
			return 504;
		}
		if (posX > 1107 && posX < 1143 && posY > 413 && posY < 449) {
			return 505;
		}
		if (posX > 1059 && posX < 1095 && posY > 518 && posY < 554) {
			return 506;
		}
		if (posX > 710 && posX < 742 && posY > 399 && posY < 431) {
			return 507;
		}
		if (posX > 948 && posX < 980 && posY > 446 && posY < 478) {
			return 508;
		}
		if (posX > 964 && posX < 996 && posY > 313 && posY < 345) {
			return 509;
		}
		if (posX > 849 && posX < 887 && posY > 538 && posY < 576) {
			return 510;
		}
		if (posX > 776 && posX < 810 && posY > 519 && posY < 553) {
			return 511;
		}
		if (posX > 945 && posX < 983 && posY > 538 && posY < 576) {
			return 512;
		}
		
		// OCEANIE
		if (posX > 1130 && posX < 1166 && posY > 128 && posY < 164) {
			return 601;
		}
		if (posX > 1002 && posX < 1034 && posY > 243 && posY < 275) {
			return 602;
		}
		if (posX > 1134 && posX < 1166 && posY > 243 && posY < 275) {
			return 603;
		}
		if (posX > 1030 && posX < 1068 && posY > 122 && posY < 160) {
			return 604;
		}
				
		return 0;
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
