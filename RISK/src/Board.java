import java.util.*;
import java.awt.Font;
import edu.princeton.cs.introcs.StdDraw;
//import java.util.Scanner;

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
	private int game_phase = 0;
	private int game_turn;
	
	
	// ***** Constructeurs *****
	
	public Board(int map, int nbr_players, int nbr_AI) {
		
		Scanner scan = new Scanner(System.in);
		
		this.map_number = map;
		this.nbr_players = nbr_players;
		this.nbr_AI = nbr_AI;
		String name = "";
		boolean unique_color = false;
		this.game_turn = 1;
		
		int [] used_colors = new int[6];
		
		int color = 0;
		for (int i=1; i<=nbr_players+nbr_AI; i++) {
			
			if (i<=nbr_players) {
				
				name = "Player " + (i) + "";
				color = i;
				
				/*
				System.out.println("Joueur " + i);
				System.out.println("Nom de joueur : ");  // temporaire en attendant la mise en place graphique
				name = scan.nextLine();
				scan.nextLine();
				
				System.out.println("Couleur : ");
				color = scan.nextInt();
				scan.nextLine();
				*/
				
			}
			else {
				System.out.println("test");
				name = "Computer " + (i-nbr_players+1) + "";
				unique_color = false;
				int compteur = 0;
				while (!unique_color) {
					color = (int) (Math.random()*6+1);
					System.out.println(color);
					compteur = 0;
					for (int k=1; k<i; k++) {
						if (used_colors[k-1] == color) {
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
			used_colors[i-1] = color;
			players_list.add(new Player(i, name, color, nbr_players + nbr_AI));
		}

		
		
	}
	
	
	// ***** Methodes *****
	
	
	public void play() {
		
		territoriesDistribution();
		// player_playing = 1;
		
		Territory ally_territory;
		Territory enemy_territory;
		Territory chosen_territory;
		int unit_type = 1;
		boolean AI_playing = false;
		int territory_id = 0;
		
		if (nbr_players == 0) {
			AI_playing = true;
		}
		
		Unit selected_unit;
		
		while (!victory) {
			
			boolean end_turn = false;
			
			// call_reinforcements(); on l'appelle plutot juste après le changement de tour

			drawUnit(1);
			unit_type = 1;
			
			//FAIRE LA FONCTION DE PLACEMENT DES UNITES
			
			while (!end_turn) {
			
			//Click de la barre espace fait changer le type d'unité
				if (game_phase != 2 && StdDraw.isKeyPressed(32)) {
					if (unit_type == 3) {
						unit_type = 1;
					}
					
					//FAIRE FONCTION POUR GARANTIR QUON A AU MOINS 1 SOLDAT PAR TERRITOIRE
					else {
						unit_type += 1;
					}
					drawUnit(unit_type);
					StdDraw.pause(150);
				}
				
				//On détecte un clic
				if (StdDraw.isMousePressed()) { 
					
					double x1 = StdDraw.mouseX();
					double y1 = StdDraw.mouseY();
					
					//On attend la fin du clic
					while (StdDraw.isMousePressed()) {
					}
					
					double x2 = StdDraw.mouseX();
					double y2 = StdDraw.mouseY();
					
					if (x1<929 && x1>365 && y1<744 && y1>635) {
						if (x1<542) {
							drawUnit(1);
							unit_type = 1;
						}
						else if (x1>735) {
							drawUnit(3);
							unit_type = 3;
						}
						else {
							drawUnit(2);
							unit_type = 3;
						}
					}
					
					territory_id = returnTerritoryID(x1, y1);
					if (territory_id != 0) {
						chosen_territory = giveTerritory(territory_id);
						if (chosen_territory.getOwner() == player_playing) {
							ally_territory = chosen_territory;
							
							
							selected_unit = new Unit(unit_type);
							
							if (game_phase != 2 && players_list.get(player_playing-1).getArmyPoints() >= selected_unit.getCost()) {
								
								ally_territory.addUnit(selected_unit);	
								int points = players_list.get(player_playing-1).getArmyPoints();
								players_list.get(player_playing-1).setArmyPoints(points - selected_unit.getCost());
								//On permet à l'utilisateur de changer le placement de son unité tant qu'il ne l'a pas validé
								selected_unit.setThisTurnMove(999);
							}
						}
						else {
							enemy_territory = chosen_territory;
						}
						/* Display du territoire dans le bandeau à droite + territoires adjacents avec
						 * nom, couleur de l'owner et unités présentes
						 */
						
					}
					
					
					
					//If click sur les unités en haut ( + condition sur territoire allié selectionné)
					//Fonction choix des unités (la même que pour le placement des renforts)
					
					//If liste des unités sélectionnées non vide, et que le territoire n'appartient pas au joueur
					// -> battle()
					
					//If click sur la mission -> affichage
					
					
					if (x1<1542 && x1>1292 && y1<70 && y1>20 && Math.abs(x1-x2)<25 && Math.abs(y1-y2)<25) {
						if (game_phase == 0) {						
							if (player_playing == nbr_players+nbr_AI) {
								player_playing = 1;
								game_phase = 1;
								drawButton(1);
								call_reinforcements();
							}
							else if (player_playing < nbr_players) {
								player_playing += 1;
							}
							else {
								player_playing +=1;
								AI_playing = true;
							}						
						}
						
						else if (game_phase == 1) {
							end_turn = true;
							game_phase = 2;
							drawButton(2);
						}
						
						else if (game_phase == 2) {
							game_phase = 1;
							drawButton(1);
							end_turn = true;
							if (player_playing == nbr_players + nbr_AI) {
								player_playing = 1;
							}
							else if (player_playing < nbr_players) {
								player_playing += 1;
							}
							else {
								player_playing +=1;
								AI_playing = true;
							}
							call_reinforcements();
						}
					}
					
					

				}
			}
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
		int i;
		while (all_territories.size()>0) {
			
			//System.out.println(all_territories.size());
			//On détermine un territoire aléatoirement
			i = (int) (Math.random()*all_territories.size());
			//On le donne au joueur
			all_territories.get(i).setOwner(players_list.get(player_playing-1));
			all_territories.remove(i);
			
			//Puis on passe au joueur suivant et on recommence
			if (player_playing >= nbr_players+nbr_AI) {
				player_playing = 1;
			}
			else {
				player_playing+=1;
			}
		}
		player_playing = 1;
		
	}
	
	/*
	 * Calcul les renforts pour le début du tour du joueur
	 */
	public void call_reinforcements() {
		
		int reinforcements = 0;
		
		//On calcule ses territoires ainsi que les régions possédées
		territories = 0;
		nb_regions = 0;
		for (Region r : regions_list) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			int territories_region = 0;
			for (Territory t : territories_list) {
				if (t.getOwner() == player_playing) {
					territories+=1;
					territories_region+=1;
				}
			}
			
			//Ajout pour chaque région contrôlée de la moitié du nombre de territoires de cette région
			if (territories_region == territories_list.size()) {
				reinforcements += (territories_list.size())/2;
			}
		}
		
		//Ajout d'une armée pour chaque tranche de 3 territoires
		reinforcements += territories/3;
		
		//50% de chance de gagner une armée pour chaque territoire gagné au tour précédent
		int last_turn_territories = players_list.get(player_playing-1).getLastTurnTerritories();
		
		for (int i=0; i<last_turn_territories; i++) {
			//L'ajout est de 0 ou 1 (50% de chance)
			reinforcements += (int) (Math.random()*2);
		}
		
		players_list.get(player_playing-1).setArmyPoints(reinforcements);
		players_list.get(player_playing-1).setLastTurnTerritories(0);
	}
	
	public void battle(ArrayList<Unit> attack, ArrayList<Unit> defence, Territory territory_att, Territory territory_def) {
		
		//Listes des résultats de lancés de dés
		int[] score_att = new int[attack.size()];
		int[] score_def = new int[defence.size()];
	
		int higher_att = 0;
		int second_att = 0;
		int higher_def = 0;
		int second_def = 0;
		
		/*
		 * Puissance de l'attaque et détermination priorité des unités dans chaque camp
		 */
		for (int i=0; i<attack.size(); i++) {
			score_att[i] = (int) (Math.random()*(attack.get(i).getMaxPower()-attack.get(i).getMinPower())+1);
			
			if (score_att[i] == score_att[higher_att]) {
				if (attack.get(i).getATT()>attack.get(higher_att).getATT()) {
					second_att = higher_att;
					higher_att = i;
				}
			}
			
			else if (score_att[i] > score_att[higher_att]) {
				second_att = higher_att;
				higher_att = i;
			}
			else if(score_att[i]>score_att[second_att]) {
				second_att = i;
			}
			
			if (i<defence.size()) {
				score_def[i] = (int) (Math.random()*(defence.get(i).getMaxPower()-defence.get(i).getMinPower())+1);
				
				if (score_def[i] == score_def[higher_def]) {
					if (defence.get(i).getDEF()>defence.get(higher_def).getDEF()) {
						second_def = higher_def;
						higher_def = i;
					}
				}
				
				if (score_def[i] > score_def[higher_def]) {
					second_def = higher_def;
					higher_def = i;
				}
				else if(score_def[i]>score_def[second_def]) {
					second_def = i;
				}
			}
		}
		
		/*
		 * Bataille entre les unités
		 */
		
		if (score_att[higher_att] > score_def[higher_def]) {
			defence.remove(higher_def);
		}
		else {
			attack.remove(higher_att);
		}
		
		if (score_att[second_att] > score_def[second_def]) {
			defence.remove(second_def);
		}
		else {
			attack.remove(second_att);
		}
		
		territory_def.addUnits(defence);
		
		if (territory_def.getNbUnits() == 0) {
			territory_def.setOwner(players_list.get(player_playing-1));
			territory_def.addUnits(attack);
			players_list.get(player_playing-1).addTerritory();
		}
		else {
			territory_att.addUnits(attack);
		}
		
	}
	
	
	public int verifPlacement() {
		int empty_territories = 0;
		for (Region r : regions_list) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			for (Territory t : territories_list) {
				if (t.getOwner() == player_playing) {
					if (t.getNbUnits() == 0) {
						empty_territories += 1; 
					}
				}
			}
		}
		return empty_territories;
	}

	
	public void drawUnit(int type) {
		switch(type) {
			case 0:
				StdDraw.picture(427, 693, "./src/ressources/soldat_noir.png");
				StdDraw.picture(601, 693, "./src/ressources/cavalier_noir.png");
				StdDraw.picture(791, 693, "./src/ressources/canon_noir.png");
				break;
				
			case 1:
				StdDraw.picture(427, 693, "./src/ressources/soldat_blanc.png");
				StdDraw.picture(601, 693, "./src/ressources/cavalier_noir.png");
				StdDraw.picture(791, 693, "./src/ressources/canon_noir.png");
				break;
				
			case 2:
				StdDraw.picture(427, 693, "./src/ressources/soldat_noir.png");
				StdDraw.picture(601, 693, "./src/ressources/cavalier_blanc.png");
				StdDraw.picture(791, 693, "./src/ressources/canon_noir.png");
				break;
				
			case 3:
				StdDraw.picture(427, 693, "./src/ressources/soldat_noir.png");
				StdDraw.picture(601, 693, "./src/ressources/cavalier_noir.png");
				StdDraw.picture(791, 693, "./src/ressources/canon_blanc.png");
				break;
		}
	}
	
	public void drawButton(int phase) {
		if (phase == 1) {
			StdDraw.setPenColor(237,195,126);
			StdDraw.filledRectangle(1417, 45, 125, 25);
			StdDraw.setPenColor(0,0,0);
			StdDraw.setPenRadius(0.01);
			StdDraw.rectangle(1417, 45, 125, 25);

		}
		if (phase == 2) {
			StdDraw.setPenColor(241,27,29);
			StdDraw.filledRectangle(1417, 45, 125, 25);
			StdDraw.setPenColor(0,0,0);
			StdDraw.setPenRadius(0.01);
			StdDraw.rectangle(1417, 45, 125, 25);
		}
	}
	
	// cette méthode permet d'afficher le nombre de tours qui se sont déroulés dans la partie (dans la bande supérieure du jeu)
	public void drawTurn(int turn) {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(63, 670, 25);
		
		Font font = new Font("Arial", Font.BOLD, 40);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(63,665, ""+turn);
	}
	
	// cette méthode permet d'afficher le nombre de territoires que le joueur possède actuellement (dans la bande supérieure du jeu)
	public void drawTerritoryCount(int nb_territories) {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(247, 670, 25);
		
		Font font = new Font("Arial", Font.BOLD, 40);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(247,665, ""+nb_territories);
	}
	
	// cette méthode permet d'afficher le nom du joueur et sa couleur en haut à droite
		public void drawName(String name) {
			players_list.get(player_playing-1).changeColor();
			StdDraw.filledRectangle(1418, 692, 180, 53);
			
			Font font = new Font("Arial", Font.BOLD, 40);
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1420,685, name);
		}
	
	public void addRegion(Region R) {
		regions_list.add(R);
	}
	
	public Territory giveTerritory(int id) {
		for (Region r : regions_list ) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			for (Territory t : territories_list) {				
				if (t.getTerritoryId() == id) {
					return t;
					}
				}
		}
		return new Territory(0,"error");
		
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
	
	//Instanciation des régions, territoires et leurs terrritoires voisins
	public void initialization() {
			
		//Amérique du Nord et ses territoires
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
		
		//Amérique du Sud et ses territoires
		Region South_America = new Region(2, "South America");
		Territory Argentina = new Territory(201, "Argentina");
		South_America.addTerritory(Argentina);
		Territory Brazil = new Territory(202, "Brazil");
		South_America.addTerritory(Brazil);
		Territory Peru = new Territory(203, "Peru");
		South_America.addTerritory(Peru);
		Territory Venezuela = new Territory(204, "Venezuela");
		South_America.addTerritory(Venezuela);
		
		//Europe et ses territoires
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
		
		//Afrique et ses territoires
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
		
		//Asie et ses territoires
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
		
		//Australie et ses territoires
		Region Australia = new Region(6, "Australia");
		Territory Eastern_Australia = new Territory(601, "Eastern Australia");
		Australia.addTerritory(Eastern_Australia);
		Territory Indonesia = new Territory(602, "Indonesia");
		Australia.addTerritory(Indonesia);
		Territory New_Guinea = new Territory(603, "New Guinea");
		Australia.addTerritory(New_Guinea);
		Territory Western_Australia = new Territory(604, "Western Australia");
		Australia.addTerritory(Western_Australia);
		
		//Initialisation des voisins de chaque territoire
		Alaska.addNeighbour(Alberta);
		Alaska.addNeighbour(Northwest_Territory);
		Alaska.addNeighbour(Kamchatka);
		Alberta.addNeighbour(Alaska);
		Alberta.addNeighbour(Northwest_Territory);
		Alberta.addNeighbour(Ontario);
		Alberta.addNeighbour(Western_United_States);
		Central_America.addNeighbour(Eastern_United_States);
		Central_America.addNeighbour(Western_United_States);
		Central_America.addNeighbour(Venezuela);
		Eastern_United_States.addNeighbour(Central_America);
		Eastern_United_States.addNeighbour(Ontario);
		Eastern_United_States.addNeighbour(Quebec);
		Eastern_United_States.addNeighbour(Western_United_States);
		Greenland.addNeighbour(Northwest_Territory);
		Greenland.addNeighbour(Ontario);
		Greenland.addNeighbour(Quebec);
		Greenland.addNeighbour(Iceland);
		Northwest_Territory.addNeighbour(Alaska);
		Northwest_Territory.addNeighbour(Alberta);
		Northwest_Territory.addNeighbour(Greenland);
		Northwest_Territory.addNeighbour(Ontario);
		Ontario.addNeighbour(Alberta);
		Ontario.addNeighbour(Eastern_United_States);
		Ontario.addNeighbour(Greenland);
		Ontario.addNeighbour(Northwest_Territory);
		Ontario.addNeighbour(Quebec);
		Ontario.addNeighbour(Western_United_States);
		Quebec.addNeighbour(Eastern_United_States);
		Quebec.addNeighbour(Greenland);
		Quebec.addNeighbour(Ontario);
		Western_United_States.addNeighbour(Alberta);
		Western_United_States.addNeighbour(Central_America);
		Western_United_States.addNeighbour(Eastern_United_States);
		Western_United_States.addNeighbour(Ontario);
		Argentina.addNeighbour(Brazil);
		Argentina.addNeighbour(Peru);
		Brazil.addNeighbour(Argentina);
		Brazil.addNeighbour(Peru);
		Brazil.addNeighbour(Venezuela);
		Brazil.addNeighbour(North_Africa);
		Peru.addNeighbour(Argentina);
		Peru.addNeighbour(Brazil);
		Peru.addNeighbour(Venezuela);
		Venezuela.addNeighbour(Brazil);
		Venezuela.addNeighbour(Peru);
		Venezuela.addNeighbour(Central_America);
		Great_Britain.addNeighbour(Iceland);
		Great_Britain.addNeighbour(Northern_Europe);
		Great_Britain.addNeighbour(Scandinavia);
		Great_Britain.addNeighbour(Western_Europe);
		Iceland.addNeighbour(Great_Britain);
		Iceland.addNeighbour(Scandinavia);
		Iceland.addNeighbour(Greenland);
		Northern_Europe.addNeighbour(Great_Britain);
		Northern_Europe.addNeighbour(Scandinavia);
		Northern_Europe.addNeighbour(Southern_Europe);
		Northern_Europe.addNeighbour(Ukraine);
		Northern_Europe.addNeighbour(Western_Europe);
		Scandinavia.addNeighbour(Great_Britain);
		Scandinavia.addNeighbour(Iceland);
		Scandinavia.addNeighbour(Northern_Europe);
		Scandinavia.addNeighbour(Ukraine);
		Southern_Europe.addNeighbour(Northern_Europe);
		Southern_Europe.addNeighbour(Ukraine);
		Southern_Europe.addNeighbour(Western_Europe);
		Southern_Europe.addNeighbour(Egypt);
		Southern_Europe.addNeighbour(North_Africa);
		Southern_Europe.addNeighbour(Middle_East);
		Ukraine.addNeighbour(Northern_Europe);
		Ukraine.addNeighbour(Scandinavia);
		Ukraine.addNeighbour(Southern_Europe);
		Ukraine.addNeighbour(Afghanistan);
		Ukraine.addNeighbour(Middle_East);
		Ukraine.addNeighbour(Ural);
		Western_Europe.addNeighbour(Great_Britain);
		Western_Europe.addNeighbour(Northern_Europe);
		Western_Europe.addNeighbour(Southern_Europe);
		Western_Europe.addNeighbour(North_Africa);
		Congo.addNeighbour(East_Africa);
		Congo.addNeighbour(North_Africa);
		Congo.addNeighbour(South_Africa);
		East_Africa.addNeighbour(Congo);
		East_Africa.addNeighbour(Egypt);
		East_Africa.addNeighbour(Madagascar);
		East_Africa.addNeighbour(North_Africa);
		East_Africa.addNeighbour(South_Africa);
		East_Africa.addNeighbour(Middle_East);
		Egypt.addNeighbour(East_Africa);
		Egypt.addNeighbour(North_Africa);
		Egypt.addNeighbour(Southern_Europe);
		Egypt.addNeighbour(Middle_East);
		Madagascar.addNeighbour(East_Africa);
		Madagascar.addNeighbour(South_Africa);
		North_Africa.addNeighbour(Congo);
		North_Africa.addNeighbour(East_Africa);
		North_Africa.addNeighbour(Egypt);
		North_Africa.addNeighbour(Brazil);
		North_Africa.addNeighbour(Southern_Europe);
		North_Africa.addNeighbour(Western_Europe);
		South_Africa.addNeighbour(Congo);
		South_Africa.addNeighbour(East_Africa);
		South_Africa.addNeighbour(Madagascar);
		Afghanistan.addNeighbour(China);
		Afghanistan.addNeighbour(India);
		Afghanistan.addNeighbour(Middle_East);
		Afghanistan.addNeighbour(Ural);
		Afghanistan.addNeighbour(Ukraine);
		China.addNeighbour(Afghanistan);
		China.addNeighbour(India);
		China.addNeighbour(Mongolia);
		China.addNeighbour(Siam);
		China.addNeighbour(Siberia);
		China.addNeighbour(Ural);
		India.addNeighbour(Afghanistan);
		India.addNeighbour(China);
		India.addNeighbour(Middle_East);
		India.addNeighbour(Siam);
		Irkutsk.addNeighbour(Kamchatka);
		Irkutsk.addNeighbour(Mongolia);
		Irkutsk.addNeighbour(Siberia);
		Irkutsk.addNeighbour(Yakutsk);
		Japan.addNeighbour(Kamchatka);
		Japan.addNeighbour(Mongolia);
		Kamchatka.addNeighbour(Irkutsk);
		Kamchatka.addNeighbour(Japan);
		Kamchatka.addNeighbour(Mongolia);
		Kamchatka.addNeighbour(Yakutsk);
		Kamchatka.addNeighbour(Alaska);
		Middle_East.addNeighbour(Afghanistan);
		Middle_East.addNeighbour(India);
		Middle_East.addNeighbour(Southern_Europe);
		Middle_East.addNeighbour(Ukraine);
		Middle_East.addNeighbour(East_Africa);
		Middle_East.addNeighbour(Egypt);
		Mongolia.addNeighbour(China);
		Mongolia.addNeighbour(Irkutsk);
		Mongolia.addNeighbour(Japan);
		Mongolia.addNeighbour(Kamchatka);
		Mongolia.addNeighbour(Siberia);
		Siam.addNeighbour(China);
		Siam.addNeighbour(India);
		Siam.addNeighbour(Indonesia);
		Siberia.addNeighbour(China);
		Siberia.addNeighbour(Irkutsk);
		Siberia.addNeighbour(Mongolia);
		Siberia.addNeighbour(Ural);
		Siberia.addNeighbour(Yakutsk);
		Ural.addNeighbour(Afghanistan);
		Ural.addNeighbour(China);
		Ural.addNeighbour(Siberia);
		Ural.addNeighbour(Ukraine);
		Yakutsk.addNeighbour(Irkutsk);
		Yakutsk.addNeighbour(Kamchatka);
		Yakutsk.addNeighbour(Siberia);
		Eastern_Australia.addNeighbour(New_Guinea);
		Eastern_Australia.addNeighbour(Western_Australia);
		Indonesia.addNeighbour(New_Guinea);
		Indonesia.addNeighbour(Western_Australia);
		Indonesia.addNeighbour(Siam);
		New_Guinea.addNeighbour(Eastern_Australia);
		New_Guinea.addNeighbour(Indonesia);
		New_Guinea.addNeighbour(Western_Australia);
		Western_Australia.addNeighbour(Eastern_Australia);
		Western_Australia.addNeighbour(Indonesia);
		Western_Australia.addNeighbour(New_Guinea);				
		
		//Ajout des régions au plateau
		this.addRegion(North_America);
		this.addRegion(South_America);
		this.addRegion(Europe);
		this.addRegion(Africa);
		this.addRegion(Asia);
		this.addRegion(Australia);
		
		//Affichage des régions et leurs territoires
		//this.printRegions();
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
	
	//Affichage des informations extérieur à la carte
	public void print() {
		System.out.println("Le jeu est en train d'être lancé");
		int extended_width = 1598;
		int extended_height = 744;
		double posX, posY;
		
		//Fenêtre
		StdDraw.setCanvasSize(extended_width,extended_height);
		StdDraw.setXscale(0,extended_width);
		StdDraw.setYscale(0,extended_height);
		
		//Carte
		StdDraw.picture(extended_width/2, extended_height/2, "./src/ressources/risk_game_map_v6.png");
		
		//Affichage des informations
		this.drawTurn(game_turn);
		this.drawName(players_list.get(player_playing-1).getPlayerName());
		this.drawTerritoryCount(players_list.get(player_playing-1).getLastTurnTerritories());
		
		drawButton(1);
		StdDraw.show();
		
		play();
		
		
		while(true) {
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(150);
			}
		}
		
		
		
		
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
	
	public int getGameTurn() {
		return this.game_turn;
	}
	
	public void setGameTurn(int turn) {
		this.game_turn = turn;
	}
}
