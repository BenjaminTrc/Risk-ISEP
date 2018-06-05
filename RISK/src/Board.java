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
	private int game_turn = 0;
	ArrayList<Unit> ally_army = new ArrayList<Unit>();
	ArrayList<Unit> enemy_army = new ArrayList<Unit>();
	Territory ally_territory = new Territory(0, "error");
	Territory enemy_territory;
	
	
	// ***** Constructeurs *****
	
	public Board(int map, int nbr_players, int nbr_AI) {
		
		Scanner scan = new Scanner(System.in);
		
		this.map_number = map;
		this.nbr_players = nbr_players;
		this.nbr_AI = nbr_AI;
		String name = "";
		boolean unique_color = false;
		
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
				name = "Computer " + (i) + "";
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
		
		//Territory ally_territory = new Territory(0, "error");
		//Territory enemy_territory;
		Territory chosen_territory;
		int unit_type = 1;
		boolean AI_playing = false;
		int territory_id = 0;
		int empty_territories;
		int[] unit_costs = {1,3,7};
		int points;
		boolean army_selected = false;
		boolean action = false; // séparer placement dans un territoire allié de l'armée ou juste une nouvelle unité
		//ArrayList<Unit> ally_army = new ArrayList<Unit>();
		//ArrayList<Unit> enemy_army = new ArrayList<Unit>();
		
		if (nbr_players == 0) {
			AI_playing = true;
		}
		
		Unit selected_unit;
		drawUnit(1);
		unit_type = 1;
		StdDraw.enableDoubleBuffering();
		drawTurn(game_turn);
		drawTerritoryCount(nbTerritoriesFromPlayer());
		drawPossibleUnits(players_list.get(player_playing-1).getArmyPoints());
		drawName(players_list.get(0).getPlayerName());
		drawPlayers(this);
		StdDraw.show();
		StdDraw.disableDoubleBuffering();
		drawButton(0);
		
		while (!victory) {
			
			boolean end_turn = false;
			
			while (!end_turn) {
			
			//Click de la barre espace fait changer le type d'unité
				if (game_phase != 2 && StdDraw.isKeyPressed(32)) {
					if (unit_type == 3) {
						unit_type = 1;
					}
					
					else {
						unit_type += 1;
					}
					
					empty_territories = emptyTerritories();
					
					if (empty_territories > players_list.get(player_playing-1).getArmyPoints() - unit_costs[unit_type -1]+1) {
						unit_type = 1;
					}
					
					drawUnit(unit_type);
					StdDraw.pause(150);
				}
				
				//On détecte un clic
				if (StdDraw.isMousePressed()) { 
					
					// on récupère les coordonnées du clic
					double x1 = StdDraw.mouseX();
					double y1 = StdDraw.mouseY();
					

					// on attend la fin du clic
					while (StdDraw.isMousePressed()) {
					}
					
					// coordonnées de la fin du clic importantes en cas de tentative de "cancel" du clic
					double x2 = StdDraw.mouseX();
					double y2 = StdDraw.mouseY();
					
					
					// clic
					if (x1<929 && x1>365 && y1<744 && y1>635 && game_phase != 2) {
						if (x1<542) {
							unit_type = 1;
						}
						else if (x1>735) {
							unit_type = 3;
						}
						else {
							unit_type = 2;
						}
						
						empty_territories = emptyTerritories();
						
						if (empty_territories > players_list.get(player_playing-1).getArmyPoints() - unit_costs[unit_type -1] +1) {
							unit_type = 1;
						}
						
						drawUnit(unit_type);
					}
					
					territory_id = returnTerritoryID(x1, y1);
					if (territory_id != 0) {
						chosen_territory = giveTerritory(territory_id);
						if (chosen_territory.getOwner() == player_playing) {
							
							if (ally_territory != chosen_territory && army_selected == true) {
								if (game_phase == 0) {
									chosen_territory.addUnits(ally_army);
									ally_army.removeAll(ally_army);
								}
								else if (ally_territory.canAttack(chosen_territory)) {
									for (Unit u : ally_army) {
										if (u.getThisTurnMove()>0) {
											u.lessThisTurnMove();
											chosen_territory.addUnit(u);
										}
									}
									ally_army.removeAll(ally_army);
									
								}
								else {
									resetUnits(ally_territory, ally_army);
								}
								
								army_selected = false;
								action = true;
								ally_territory.setOwner(players_list.get(player_playing-1));
								chosen_territory.setOwner(players_list.get(player_playing-1));
							}
							ally_territory = chosen_territory;
							
							selected_unit = new Unit(unit_type);
							
							if (game_phase != 2 && players_list.get(player_playing-1).getArmyPoints() >= selected_unit.getCost() && !action) {
								empty_territories = emptyTerritories();
								if (empty_territories > players_list.get(player_playing-1).getArmyPoints()-unit_costs[unit_type-1]+1) {
									unit_type = 1;
									drawUnit(unit_type);
									selected_unit = new Unit(unit_type);
								}
								
								if (ally_territory.getNbUnits() == 0 || empty_territories < players_list.get(player_playing-1).getArmyPoints()-selected_unit.getCost()+1) {
									ally_territory.addUnit(selected_unit);
									ally_territory.setOwner(players_list.get(player_playing-1));
									points = players_list.get(player_playing-1).getArmyPoints();
									players_list.get(player_playing-1).setArmyPoints(points - selected_unit.getCost());
									StdDraw.enableDoubleBuffering();
									drawPossibleUnits(players_list.get(player_playing-1).getArmyPoints());
									StdDraw.show();
									StdDraw.disableDoubleBuffering();
									//On permet à l'utilisateur de changer le placement de son unité tant qu'il ne l'a pas validé
									//selected_unit.setThisTurnMove(999);
								}
							}
							StdDraw.enableDoubleBuffering();
							drawTerritoryInformations(territory_id);				
							drawButton(game_phase);
							StdDraw.show();
							StdDraw.disableDoubleBuffering();
							action = false;
						}
						else {
							enemy_territory = chosen_territory;
							
							if (army_selected && game_phase == 2 && ally_territory.canAttack(enemy_territory)) {
								enemy_army = enemy_territory.determineDefence();
								battle();
								army_selected = false;
								ally_army.removeAll(ally_army);
								enemy_army.removeAll(enemy_army);
							}
							StdDraw.enableDoubleBuffering();
							drawTerritoryInformations(territory_id);
							drawButton(game_phase);
							StdDraw.show();
							StdDraw.disableDoubleBuffering();
						}
						/* Display du territoire dans le bandeau à droite + territoires adjacents avec
						 * nom, couleur de l'owner et unités présentes
						 */
						
						
					}
					
					else if (x1<1227 && y1 <628) {
						StdDraw.enableDoubleBuffering();
						drawPlayers(this);
						drawButton(game_phase);
						StdDraw.show();
						StdDraw.disableDoubleBuffering();
					}
					
					//If click sur les unités en haut ( + condition sur territoire allié selectionné)
					//Fonction choix des unités (la même que pour le placement des renforts)
					
					//If liste des unités sélectionnées non vide, et que le territoire n'appartient pas au joueur
					// -> battle()
					
					if (x1>1227) {
						switchUnitButton(x1, y1);
					}
					
					if (ally_army.size() != 0) {
						army_selected = true;
						
					}
					
					
					
					//If click sur la mission -> affichage
					
					
					if (x1<1542 && x1>1292 && y1<70 && y1>20 && Math.abs(x1-x2)<25 && Math.abs(y1-y2)<25) {
						if (game_phase == 0) {
							end_turn = verifyPlacement();
							if (end_turn) {
								if (player_playing == nbr_players+nbr_AI) {
									player_playing = 1;
									game_turn++;
									game_phase = 1;
									StdDraw.enableDoubleBuffering();
									drawTurn(game_turn);
									drawName(players_list.get(0).getPlayerName());
									drawPlayers(this);
									drawButton(1);
									StdDraw.show();
									StdDraw.disableDoubleBuffering();
									call_reinforcements();
								}
								else if (player_playing < nbr_players) {
									player_playing += 1;
									StdDraw.enableDoubleBuffering();
									drawName(players_list.get(player_playing-1).getPlayerName());
									drawPlayers(this);
									drawButton(0);
									StdDraw.show();
									StdDraw.disableDoubleBuffering();
								}
								else {
									player_playing +=1;
									StdDraw.enableDoubleBuffering();
									drawName(players_list.get(player_playing-1).getPlayerName());
									drawPlayers(this);
									drawButton(0);
									StdDraw.show();
									StdDraw.disableDoubleBuffering();
									AI_playing = true;
								}
								StdDraw.enableDoubleBuffering();
								drawTerritoryCount(nbTerritoriesFromPlayer());
								StdDraw.show();
								StdDraw.disableDoubleBuffering();
								unit_type = 1;
							}
							StdDraw.enableDoubleBuffering();
							drawPossibleUnits(players_list.get(player_playing-1).getArmyPoints());
							StdDraw.show();
							StdDraw.disableDoubleBuffering();
						}
						
						else if (game_phase == 1) {
							end_turn = verifyPlacement();
							if (end_turn) {
								game_phase = 2;
								StdDraw.enableDoubleBuffering();
								drawButton(2);
								drawUnit(0);
								StdDraw.show();
								StdDraw.disableDoubleBuffering();
							}
						}
						
						else if (game_phase == 2) {
							game_phase = 1;
							unit_type = 1;
							end_turn = true;
							if (player_playing == nbr_players + nbr_AI) {
								player_playing = 1;
								game_turn++;
								drawTurn(game_turn);
								resetUnitMoves();
							}
							else if (player_playing < nbr_players) {
								player_playing += 1;
							}
							else {
								player_playing +=1;
								AI_playing = true;
							}
							call_reinforcements();
							StdDraw.enableDoubleBuffering();
							drawTerritoryCount(nbTerritoriesFromPlayer());
							drawPossibleUnits(players_list.get(player_playing-1).getArmyPoints());
							drawName(players_list.get(player_playing-1).getPlayerName());
							drawPlayers(this);
							drawButton(1);
							drawUnit(1);
							StdDraw.show();
							StdDraw.disableDoubleBuffering();
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
	
	public void battle() {
		
		//Listes des résultats de lancés de dés
		//Si l'armée est plus grande que 3 on choisit les 3 meilleures unités pour attaquer
		
		ArrayList<Unit> reserve = new ArrayList<Unit>();
		int type_count = 1;
		while (ally_army.size()>3) {
			for (int j=0; j<ally_army.size(); j++) {
				if (ally_army.get(j).getType() == type_count && ally_army.size()>3) {
					reserve.add(ally_army.get(j));
					ally_army.remove(j);
				}
			}
			type_count += 1;
		}
		
		int[] score_att = new int[ally_army.size()];
		int[] score_def = new int[enemy_army.size()];
	
		int higher_att = 0;
		int second_att = 0;
		int higher_def = 0;
		int second_def = 0;
		
		/*
		 * Puissance de l'attaque et détermination priorité des unités dans chaque camp
		 */
		for (int i=0; i<ally_army.size(); i++) {
			score_att[i] = (int) (Math.random()*(ally_army.get(i).getMaxPower()-ally_army.get(i).getMinPower()+1)+ally_army.get(i).getMinPower());
			System.out.println("score attaque pour unité " + i + " = " + score_att[i]);
			if (i == 1) {
				second_att = i;
			}
			
			if (score_att[i] > score_att[higher_att]) {
				second_att = higher_att;
				higher_att = i;
			}
			
			else if(score_att[i]>score_att[second_att] && score_att[i] < score_att[higher_att]) {
				second_att = i;
			}
			
			else if (score_att[i] == score_att[higher_att] && i!=0) {
				if (ally_army.get(i).getATT()>ally_army.get(higher_att).getATT()) {
					second_att = higher_att;
					higher_att = i;
				}
				else if (score_att[i] > score_att[second_att] || ally_army.get(i).getATT()>ally_army.get(second_att).getATT())
					second_att = i;
			}
		}
			
		for (int i=0; i<enemy_army.size(); i++) {
			score_def[i] = (int) (Math.random()*(enemy_army.get(i).getMaxPower()-enemy_army.get(i).getMinPower()+1)+enemy_army.get(i).getMinPower());
			System.out.println("score défense pour unité " + i + " = " + score_def[i]);
			if (i == 0) {
				
			}
				
			else if (score_def[i] == score_def[higher_def]) {
				if (enemy_army.get(i).getDEF()>enemy_army.get(higher_def).getDEF()) {
					//second_def = higher_def;
					higher_def = i;
				}
				else {
					second_def = i;
				}
			}
			
			else if (score_def[i] > score_def[higher_def]) {
				//second_def = higher_def;
				higher_def = i;
			}
			else {
				second_def = i;
			}
		}
		
		
		/*
		 * Bataille entre les unités
		 */
		
		System.out.println("att1 " + score_att[higher_att]);
		System.out.println("att2 " + score_att[second_att]);
		System.out.println("def1 " + score_def[higher_def]);
		System.out.println("def2 " + score_def[second_def]);

		int offset_att = 0;
		int offset_def = 0;
		
		if (enemy_army.size()>1 && ally_army.size()>1) {
			if (score_att[second_att] > score_def[second_def]) {
				enemy_army.remove(second_def);
				if (second_def<higher_def) {
					offset_def -= 1;
				}
			}
			else {
				ally_army.remove(second_att);
				if (second_att<higher_att) {
					offset_att -= 1;
				}
			}
		}
		
		if (score_att[higher_att] > score_def[higher_def]) {
			enemy_army.remove(higher_def+offset_def);
		}
		else {
			ally_army.remove(higher_att+offset_att);
		}
		
		
		
		enemy_territory.addUnits(enemy_army);
		ally_army.addAll(reserve);
		
		if (enemy_territory.getNbUnits() == 0) {
			enemy_territory.addUnits(ally_army);
			enemy_territory.setOwner(players_list.get(player_playing-1));
			ally_territory.setOwner(players_list.get(player_playing-1));
			players_list.get(player_playing-1).addTerritory();
			ally_territory = enemy_territory;
		}
		else {
			ally_territory.addUnits(ally_army);
			ally_territory.setOwner(players_list.get(player_playing-1));
			enemy_territory.setOwner(players_list.get(enemy_territory.getOwner()-1));
		}
		
		if (verifyMission(players_list.get(player_playing-1).getMission())) {
			drawWinner(players_list.get(player_playing-1).getPlayerName());
			victory = true;
		}
		
	}
	
	
	public int emptyTerritories() {
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
	
	public boolean verifyPlacement() {
		if (emptyTerritories() > 0) {
			return false;
		}
		return true;
	}
	
	public void resetUnitMoves() {
		for (Region r : regions_list) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			for (Territory t : territories_list) {
				ArrayList<Unit> units_list = t.getUnits();
				for (Unit u : units_list) {
					u.setThisTurnMove(u.getMove());
				}
			}
		}
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
	
	// dessine le bouton correspondant à la phase en cours
	public void drawButton(int phase) {
		if (phase == 0) {
			StdDraw.setPenColor(51,102,170);
			StdDraw.filledRectangle(1417, 45, 125, 25);
			StdDraw.setPenColor(0,0,0);
			StdDraw.setPenRadius(0.01);
			StdDraw.rectangle(1417, 45, 125, 25);
			
			Font font = new Font("",Font.BOLD,20);
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(1417, 45, "FIN DE L'INITIALISATION");
		}
		
		if (phase == 1) {
			StdDraw.setPenColor(237,195,126);
			StdDraw.filledRectangle(1417, 45, 125, 25);
			StdDraw.setPenColor(0,0,0);
			StdDraw.setPenRadius(0.01);
			StdDraw.rectangle(1417, 45, 125, 25);
			
			Font font = new Font("",Font.BOLD,20);
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(1417, 45, "FIN DU RENFORCEMENT");
		}
		
		if (phase == 2) {
			StdDraw.setPenColor(241,27,29);
			StdDraw.filledRectangle(1417, 45, 125, 25);
			StdDraw.setPenColor(0,0,0);
			StdDraw.setPenRadius(0.01);
			StdDraw.rectangle(1417, 45, 125, 25);
			
			Font font = new Font("",Font.BOLD,20);
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(1417, 45, "FIN DU TOUR");
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
		StdDraw.filledCircle(247, 667, 25);
		
		Font font = new Font("Arial", Font.BOLD, 40);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(247,662, ""+nb_territories);
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
		
	// cette méthode permet d'afficher les unités qu'il est possible de recruter
	public void drawPossibleUnits(int points) {
		
		//Soldat
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(495, 692, 25);
			
		Font font = new Font("Arial", Font.BOLD, 40);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(495,687, ""+points);
		
		//Cavalier
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(682, 692, 25);
			
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(682,687, ""+points/3);
		
		//Canon
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(885, 692, 25);
			
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(885,687, ""+points/7);
	}	
	
	//Méthode permettant d'afficher les informations sur un territoire en sélectionné
		public void drawTerritoryInformations(int territoryId) {
			
			//Territoire sélectionné			
			Territory t = giveTerritory(territoryId);
			String territory_name;
			Player owner;
			
			StdDraw.picture(1418, 313, "./src/ressources/bois_bandeau_droit.png");
					
			Font font = new Font("Arial", Font.BOLD, 40);
			StdDraw.setFont(font);
			players_list.get(t.getOwner()-1).changeColor();
			StdDraw.text(1420,600, ""+t.getTerritoryName());
			
			//Armées possédées sur ce territoire
			Font font2 = new Font("Arial", Font.BOLD, 25);
			StdDraw.setFont(font2);
			
			StdDraw.picture(1300, 530, "./src/ressources/soldat_noir_petit.png");					
			StdDraw.picture(1300, 480, "./src/ressources/cavalier_noir_petit.png");						
			StdDraw.picture(1300, 430, "./src/ressources/canon_noir_petit.png");
			
			//On compte le nombre d'unités de chaque type
			int nb_soldier = 0, nb_horseman = 0, nb_cannon = 0;
			for(Unit U : t.getUnits()) {
				if (U.getType() == 1) {
					nb_soldier++;
				}else if (U.getType() == 2) {
					nb_horseman++;
				}else if (U.getType() == 3) {
					nb_cannon++;
				}				
			}
			
			//On affiche le nombre de soldats, cavaliers et canons
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledCircle(1350, 535, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1350,530, ""+nb_soldier);

			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledCircle(1350, 485, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1350,480, ""+nb_horseman);

			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledCircle(1350, 435, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1350,430, ""+nb_cannon);
			
			//Territoires voisins
			int compteur = 350;
			for(Territory T : t.getNeighbourTerritories())
			{
				players_list.get(T.getOwner()-1).changeColor();
				StdDraw.text(1400,compteur, ""+T.getTerritoryName());
				
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledCircle(1565, compteur+5, 15);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(1565,compteur, ""+T.getNbUnits());
				
				compteur -= 35;
			}
			
			if (t.getOwner() == player_playing) {
				drawAttackingArmies(territoryId);
			}

		}	
		
		//Méthode permettant d'afficher les informations sur un territoire en sélectionné
		public void drawAttackingArmies(int territoryId /*, ArrayList ally_army*/) {
			Territory t = giveTerritory(territoryId);
			
			Font font = new Font("Arial", Font.BOLD, 20);
			Font font2 = new Font("Arial", Font.BOLD, 40);
			
			//StdDraw.picture(1418, 313, "./src/ressources/bois_bandeau_droit.png");

			int[] types_nb = getTypesNb(ally_army);
			
			//Carrés, unités sélectionnées, + et -
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(1500, 535, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1500, 530, ""+types_nb[0]);
			StdDraw.setFont(font2);
			StdDraw.text(1470, 532, "-");
			StdDraw.text(1530, 530, "+");
			
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(1500, 485, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1500, 480, ""+types_nb[1]);
			StdDraw.setFont(font2);
			StdDraw.text(1470, 482, "-");
			StdDraw.text(1530, 480, "+");
			
			StdDraw.setFont(font);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(1500, 435, 15);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(1500, 430, ""+types_nb[2]);
			StdDraw.setFont(font2);
			StdDraw.text(1470, 432, "-");
			StdDraw.text(1530, 430, "+");
						
			
		}
		
		//Méthode permettant d'afficher les informations sur un territoire en sélectionné
		public void drawPlayers(Board b) {
			
			int height = 590;
			Font font = new Font("Arial", Font.BOLD, 20);
			Font font2 = new Font("Arial", Font.BOLD, 40);

			StdDraw.picture(1418, 313, "./src/ressources/bois_bandeau_droit.png");
			
			int i=1;
			for (Player P : players_list)
			{
				P.changeColor();
				StdDraw.setFont(font2);					
				StdDraw.text(1420,height, ""+P.getPlayerName());
				
				StdDraw.setFont(font);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(1420, height-40, "Territoires : " + nbTerritoriesFromPlayers(i));
				
				height -= 88;
				i++;
				
			}
		}
		
		//Méthode permettant d'afficher le nom du vainqueur après la partie
		public static void drawWinner(String name)
		{
			String message = "Vainqueur : " + name;
			
			
			
			StdDraw.setXscale(0.0,1598);
			StdDraw.setYscale(0.0,744);	
			
			StdDraw.setPenColor(StdDraw.WHITE); 
			StdDraw.filledRectangle(1598/2, 744/2, 250, 50);
			
			StdDraw.setPenColor(StdDraw.BLACK);   
			StdDraw.text(1598/2, 744/2, message);
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
		Territory Eastern_United_States = new Territory(104, "Eastern US");
		North_America.addTerritory(Eastern_United_States);
		Territory Greenland = new Territory(105, "Greenland");
		North_America.addTerritory(Greenland);
		Territory Northwest_Territory = new Territory(106, "Northwest Territory");
		North_America.addTerritory(Northwest_Territory);
		Territory Ontario = new Territory(107, "Ontario");
		North_America.addTerritory(Ontario);
		Territory Quebec = new Territory(108, "Quebec");
		North_America.addTerritory(Quebec);
		Territory Western_United_States = new Territory(109, "Western US");
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
	
	/*public int nbArmyAvailable(int army_points, int type_selected) {
		switch(type_selected) {
			case 1:
				return army_points;
				
			case 2:
				int nb_available = army_points/3;
				return nb_available;
				
			case 3:
				int nb_available_2 = army_points/7;
				return nb_available_2;
		}
		return 0;
	}*/
	
	public int nbTerritoriesFromPlayer() {
		int territories_player = 0;
		//On parcourt les regions de la carte
		for (Region r : regions_list) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			//On parcourt les territoires de chaque région
			for (Territory t : territories_list) {
				//On compte le territoire s'il appartient au joueur
				if (t.getOwner() == player_playing) {
					territories_player+=1;
				}
			}
		}
		return territories_player;
	}
	
	public int[] getTypesNb(ArrayList<Unit> selected_army) {
		int[] types = {0,0,0};
		for (int i=0; i<selected_army.size(); i++) {
			types[selected_army.get(i).getType()-1] += 1;
			}
		return types;
		}
		
		public int nbTerritoriesFromPlayers(int player_nb) {
		int territories_player = 0;
		for (Region r : regions_list) {
			ArrayList<Territory> territories_list = r.getTerritoryList();
			//On parcourt les territoires de chaque région
			for (Territory t : territories_list) {
				//On compte le territoire s'il appartient au joueur
				if (t.getOwner() == player_nb) {
					territories_player+=1;
				}
			}
		}
		return territories_player;
	}
	
	public boolean verifyMission(Mission mission) {
		
		//Les conditions de victoire dépendent du type de mission
		switch(mission.getMissionNb()) {
		
		//Les 4 premières missions dépendent juste d'un nombre de territoires
			case 1:
			case 2:
			case 3:
			case 4:
				territories = nbTerritoriesFromPlayer();
				
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
	
	// positif : on ajoute // négatif : on enlève // type d'unité 1 (soldat), 2 (cavalerie) et 3 (canon)
	public void switchUnitButton(double posX, double posY) {
		
		ArrayList<Unit> disposable_units = ally_territory.getUnits();
		int type = 0;
		if (ally_army.size()>0 && posX<1500) {
			if (posX > 1460 && posX < 1485 && posY > 520 && posY < 550) {
				type = 1;
			}
			else if (posX > 1460 && posX < 1485 && posY > 470 && posY < 500) {
				type = 2;
			}
			else if (posX > 1460 && posX < 1485 && posY > 420 && posY < 450) {
				type = 3;
			}
			if (type != 0) {
				for (int i=ally_army.size()-1; i>=0; i--) {	
					if (ally_army.get(i).getType() == type) {
						disposable_units.add(ally_army.get(i));
						ally_army.remove(i);
						ally_territory.setUnits(disposable_units);
						break;
					}
				}
			}
		}
		
		else if (disposable_units.size()>1) {
			if (posX > 1515 && posX < 1540 && posY > 520 && posY < 550) {
				type = 1;
			}
			else if (posX > 1515 && posX < 1540 && posY > 470 && posY < 500) {
				type = 2;
			}
			else if (posX > 1515 && posX < 1540 && posY > 420 && posY < 450) {
				type = 3;
			}
			if (type != 0) {
				Unit u;
				for (int i=0; i<disposable_units.size(); i++) {
					u = disposable_units.get(i);
					if (u.getType() == type && u.getThisTurnMove()>0) {
						ally_army.add(u);
						disposable_units.remove(i);
						ally_territory.setUnits(disposable_units);
						break;
					}
				}
			}
		}
		if (type != 0) {
			StdDraw.enableDoubleBuffering();
			drawTerritoryInformations(ally_territory.getTerritoryId());
			drawButton(game_phase);
			StdDraw.show();
			StdDraw.disableDoubleBuffering();
			
		}
		return ;
	}
	
	public ArrayList<Unit> resetUnits(Territory t, ArrayList<Unit> army_selected) {
		t.addUnits(army_selected);
		army_selected.removeAll(army_selected);
		return army_selected;
	}
	
	public static int returnTerritoryID(double posX, double posY) {
		// AMERIQUE DU NORD
		if (posX > 63 && posX < 101 && posY > 534 && posY < 573) {
			return 101;
		}
		if (posX > 121 && posX < 161 && posY > 491 && posY < 528) {
			return 102;
		}
		if (posX > 109 && posX < 153 && posY > 343 && posY < 384) {
			return 103;
		}
		if (posX > 196 && posX < 240 && posY > 405 && posY < 449) {
			return 104;
		}
		if (posX > 414 && posX < 459 && posY > 564 && posY < 609) {
			return 105;
		}
		if (posX > 158 && posX < 197 && posY > 541 && posY < 579) {
			return 106;
		}
		if (posX > 216 && posX < 255 && posY > 486 && posY < 527) {
			return 107;
		}
		if (posX > 299 && posX < 340 && posY > 489 && posY < 529) {
			return 108;
		}
		if (posX > 108 && posX < 152 && posY > 430 && posY < 474) {
			return 109;
		}
		
		// AMERIQUE DU SUD
		if (posX > 276 && posX < 320 && posY > 99 && posY < 142) {
			return 201;
		}
		if (posX > 316 && posX < 365 && posY > 188 && posY < 234) {
			return 202;
		}
		if (posX > 258 && posX < 300 && posY > 178 && posY < 218) {
			return 203;
		}
		if (posX > 242 && posX < 285 && posY > 277 && posY < 318) {
			return 204;
		}
		
		// EUROPE
		if (posX > 492 && posX < 533 && posY > 473 && posY < 509) {
			return 301;
		}
		if (posX > 470 && posX < 511 && posY > 516 && posY < 544) {
			return 302;
		}
		if (posX > 586 && posX < 626 && posY > 481 && posY < 518) {
			return 303;
		}
		if (posX > 594 && posX < 631 && posY > 525 && posY < 563) {
			return 304;
		}
		if (posX > 621 && posX < 659 && posY > 440 && posY < 477) {
			return 305;
		}
		if (posX > 670 && posX < 717 && posY > 489 && posY < 537) {
			return 306;
		}
		if (posX > 540 && posX < 582 && posY > 441 && posY < 481) {
			return 307;
		}
		
		// AFRIQUE
		if (posX > 622 && posX < 669 && posY > 239 && posY < 285) {
			return 401;
		}
		if (posX > 703 && posX < 750 && posY > 267 && posY < 314) {
			return 402;
		}
		if (posX > 630 && posX < 672 && posY > 365 && posY < 405) {
			return 403;
		}
		if (posX > 747 && posX < 795 && posY > 144 && posY < 191) {
			return 404;
		}
		if (posX > 526 && posX < 572 && posY > 325 && posY < 372) {
			return 405;
		}
		if (posX > 622 && posX < 670 && posY > 152 && posY < 201) {
			return 406;
		}
		
		// ASIE
		if (posX > 780 && posX < 827 && posY > 442 && posY < 489) {
			return 501;
		}
		if (posX > 944 && posX < 989 && posY > 390 && posY < 436) {
			return 502;
		}
		if (posX > 855 && posX < 904 && posY > 347 && posY < 395) {
			return 503;
		}
		if (posX > 929 && posX < 968 && posY > 491 && posY < 529) {
			return 504;
		}
		if (posX > 1102 && posX < 1147 && posY > 410 && posY < 454) {
			return 505;
		}
		if (posX > 1055 && posX < 1099 && posY > 514 && posY < 559) {
			return 506;
		}
		if (posX > 706 && posX < 747 && posY > 395 && posY < 435) {
			return 507;
		}
		if (posX > 944 && posX < 984 && posY > 444 && posY < 483) {
			return 508;
		}
		if (posX > 959 && posX < 1001 && posY > 309 && posY < 350) {
			return 509;
		}
		if (posX > 844 && posX < 892 && posY > 534 && posY < 580) {
			return 510;
		}
		if (posX > 771 && posX < 813 && posY > 516 && posY < 558) {
			return 511;
		}
		if (posX > 940 && posX < 987 && posY > 534 && posY < 581) {
			return 512;
		}
		
		// OCEANIE
		if (posX > 1124 && posX < 1169 && posY > 123 && posY < 169) {
			return 601;
		}
		if (posX > 998 && posX < 1038 && posY > 240 && posY < 279) {
			return 602;
		}
		if (posX > 1129 && posX < 1171 && posY > 241 && posY < 279) {
			return 603;
		}
		if (posX > 1023 && posX < 1074 && posY > 118 && posY < 166) {
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
		
		StdDraw.picture(1418, 313, "./src/ressources/bois_bandeau_droit.png");
		
		
		//Affichage des informations
		drawTurn(game_turn);
		drawName(players_list.get(player_playing-1).getPlayerName());
		drawTerritoryCount(players_list.get(player_playing-1).getLastTurnTerritories());
		drawPossibleUnits(players_list.get(player_playing-1).getArmyPoints());
		//drawTerritoryInformations(107);
		//drawAttackingArmies(107);
		drawPlayers(this);
		drawWinner("Benjamin");
		
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
	
	public ArrayList<Region> getRegionsList() {
		return this.regions_list;
	}
	
	public int getGameTurn() {
		return this.game_turn;
	}
	
	public void setGameTurn(int turn) {
		this.game_turn = turn;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players_list;
	}
	
	public int getPlayerPlaying() {
		return this.player_playing;
	}
}
