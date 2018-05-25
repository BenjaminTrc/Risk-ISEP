import edu.princeton.cs.introcs.StdDraw;

public class Main {

	
	public static void main(String[] args) {
		Board Plateautest = new Board(1,2,0);
		
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

		Plateautest.addRegion(North_America);
		Plateautest.addRegion(South_America);
		Plateautest.addRegion(Europe);
		Plateautest.addRegion(Africa);
		Plateautest.addRegion(Asia);
		Plateautest.addRegion(Australia);
		
		Plateautest.printRegions();
		
		System.out.println("La première page est en train d'être chargée");
		show_first_page();
		configuration();
		launch_game();
		
	}
	
	// cette fonction permet d'afficher la première "page" lorsqu'on executera le jeu
	public static void show_first_page() {
		boolean play_game = false;
		int width = 1227;
		int height = 628;
		double posX, posY;
		
		StdDraw.setCanvasSize(width,height);
		StdDraw.setXscale(0,width);
		StdDraw.setYscale(0,height);
		StdDraw.clear(StdDraw.WHITE);
		StdDraw.show();
		
		StdDraw.picture(width/2, height/2, "./src/ressources/risk_first_page.png");
		
		while (!play_game) { // tant que le joueur n'a pas cliqué sur le bouton "jouer", on reste sur la toute première page
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(150);
				
				
				if (posX > 440 && posX < 786 && posY > 175 && posY < 333) { // S'il clique sur le bouton
					play_game = true;
				}
				
			}
		}
	}
	
	
	
	// fonction qui affiche la page de configuration de la partie
	public static void configuration() {
		System.out.println("La configuration a été lancée");
		boolean launch_game = false;
		int width = 1227;
		int height = 628;
		double posX;
		double posY;
		int nb_players = 2; // par défaut, on a 2 joueurs
		boolean IA = false; // par défaut, on ne joue pas contre l'IA
		
		StdDraw.picture(width/2, height/2, "./src/ressources/risk_configuration.png");
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		
		StdDraw.rectangle(358, 408, 50, 50); // par défaut, on encadre "2 joueurs"
		StdDraw.rectangle(740, 233, 95, 45); // par défaut, on encadre "NON"
		
		while (!launch_game) {
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(150);
				
				/* ---------- CHOIX DU NOMBRE DE JOUEURS ---------- */
				
				// 1ère bulle
				if (posX > 323 && posX < 400 && posY > 367 && posY < 447) {
					choice_nb_players(2);
					nb_players = 2;
				}
				
				// 2ème bulle
				if (posX > 446 && posX < 524 && posY > 367 && posY < 447) {
					choice_nb_players(3);
					nb_players = 3;
				}
				
				// 3ème bulle
				if (posX > 574 && posX < 652 && posY > 367 && posY < 447) {
					choice_nb_players(4);
					nb_players = 4;
				}
				
				// 4ème bulle
				if (posX > 702 && posX < 780 && posY > 367 && posY < 447) {
					choice_nb_players(5);
					nb_players = 5;
				}
				
				// 5ème bulle
				if (posX > 830 && posX < 908 && posY > 367 && posY < 447) {
					choice_nb_players(6);
					nb_players = 6;
				}
				
				/* ---------- JOUER CONTRE L'IA ---------- */
				
				// Si on clique sur "OUI"
				if (posX > 400 && posX < 580 && posY > 190 && posY < 265) {
					choice_IA(1);
					IA = true;
				}
				
				// Si on clique sur "NON"
				if (posX > 655 && posX < 835 && posY > 190 && posY < 265) {
					choice_IA(2);
					IA = false;
				}
				
				
				/* ---------- LANCER LA PARTIE ---------- */
				
				if (posX > 375 && posX < 853 && posY > 87 && posY < 167) { // S'il clique sur le bouton "Lancer la partie"
					launch_game = true;
				}
			
			}
		}
	}
	
	
	
	
	public static void launch_game() {
		System.out.println("Le jeu est en train d'être lancé");
	}
	
	
	
	
	
	// cette fonction gère l'affichage du contour bleu pour le choix du nombre de joueurs
	// on lui indique le nombre du joueur et il s'occupe de dessiner le contour bleu autour des bonnes coordonnées
	public static void choice_nb_players(int nb) {
		switch(nb) {
			case 2:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(358, 408, 50, 50);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(486, 408, 50, 50);
				StdDraw.rectangle(614, 408, 50, 50);
				StdDraw.rectangle(741, 408, 50, 50);
				StdDraw.rectangle(870, 408, 50, 50);
				break;
			
			case 3:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(486, 408, 50, 50);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(358, 408, 50, 50);
				StdDraw.rectangle(614, 408, 50, 50);
				StdDraw.rectangle(741, 408, 50, 50);
				StdDraw.rectangle(870, 408, 50, 50);
				break;
				
			case 4:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(614, 408, 50, 50);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(358, 408, 50, 50);
				StdDraw.rectangle(486, 408, 50, 50);
				StdDraw.rectangle(741, 408, 50, 50);
				StdDraw.rectangle(870, 408, 50, 50);
				break;
				
			case 5:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(741, 408, 50, 50);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(358, 408, 50, 50);
				StdDraw.rectangle(486, 408, 50, 50);
				StdDraw.rectangle(614, 408, 50, 50);
				StdDraw.rectangle(870, 408, 50, 50);
				break;
				
			case 6:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(870, 408, 50, 50);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(358, 408, 50, 50);
				StdDraw.rectangle(486, 408, 50, 50);
				StdDraw.rectangle(614, 408, 50, 50);
				StdDraw.rectangle(741, 408, 50, 50);
				break;
		}
	}
	
	// cette fonction gère l'affichage du contour bleu pour le choix du nombre de joueurs
	public static void choice_IA(int choice) {
		switch(choice) {
			case 1:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(486, 233, 95, 45);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.rectangle(740, 233, 95, 45);
				break;
				
			case 2:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(740, 233, 95, 45);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.rectangle(486, 233, 95, 45);
				break;
		}
		
	}
	


}
