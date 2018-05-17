
public class Main {

	public static void main(String[] args) {
		System.out.println("Bienvenue");

		
<<<<<<< HEAD
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
		
		StdDraw.picture(width/2, height/2, "risk_configuration.png");
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
=======
>>>>>>> 7c576f2793f3b05bfe51db8511b1905c26e3e6ff
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
