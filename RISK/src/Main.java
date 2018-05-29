import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;

public class Main {

	
	public static int nb_players = 2;
	public static int nb_AI = 0;
	
	public static void main(String[] args) {
		
		//System.out.println("La première page est en train d'être chargée");
		//show_first_page();
		//configuration();
		
		//Board Plateautest = new Board(1,nb_players,nb_AI);
		//Plateautest.initialization();
		
		//launch_game();
		
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
		
		StdDraw.picture(width/2, height/2, "./src/ressources/risk_configuration_v3.png");
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		
		StdDraw.rectangle(513, 450, 38, 38); // par défaut, on encadre "2 joueurs"
		StdDraw.rectangle(313, 271, 38, 38); // par défaut, on encadre "0 joueur" pour l'IA
		
		while (!launch_game) {
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(150);
				
				/* ---------- CHOIX DU NOMBRE DE JOUEURS ---------- */
				
				// 1ère bulle
				if (posX > 275 && posX < 351 && posY > 412 && posY < 488) {
					choice_nb_players(0);
					nb_players = 0;
				}
				
				// 2ème bulle
				if (posX > 375 && posX < 451 && posY > 412 && posY < 488) {
					choice_nb_players(1);
					nb_players = 1;
				}
				
				// 3ème bulle
				if (posX > 475 && posX < 551 && posY > 412 && posY < 488) {
					choice_nb_players(2);
					nb_players = 2;
				}
				
				// 4ème bulle
				if (posX > 575 && posX < 651 && posY > 412 && posY < 488) {
					choice_nb_players(3);
					nb_players = 3;
				}
				
				// 5ème bulle
				if (posX > 675 && posX < 751 && posY > 412 && posY < 488) {
					choice_nb_players(4);
					nb_players = 4;
				}
				
				// 6ème bulle
				if (posX > 775 && posX < 851 && posY > 412 && posY < 488) {
					choice_nb_players(5);
					nb_players = 5;
				}
				
				// 7ème bulle
				if (posX > 875 && posX < 951 && posY > 412 && posY < 488) {
					choice_nb_players(6);
					nb_players = 6;
				}
				
				/* ---------- CHOIX DU NOMBRE D'IA ---------- */
				
				// 1ème bulle
				if (posX > 275 && posX < 351 && posY > 233 && posY < 309) {
					choice_nb_AI(0);
					nb_AI = 0;
				}
				
				// 2ème bulle
				if (posX > 375 && posX < 451 && posY > 233 && posY < 309) {
					choice_nb_AI(1);
					nb_AI = 1;
				}
				
				// 3ème bulle
				if (posX > 475 && posX < 551 && posY > 233 && posY < 309) {
					choice_nb_AI(2);
					nb_AI = 2;
				}
				
				// 4ème bulle
				if (posX > 575 && posX < 651 && posY > 233 && posY < 309) {
					choice_nb_AI(3);
					nb_AI = 3;
				}
				
				// 5ème bulle
				if (posX > 675 && posX < 751 && posY > 233 && posY < 309) {
					choice_nb_AI(4);
					nb_AI = 4;
				}
				
				// 6ème bulle
				if (posX > 775 && posX < 851 && posY > 233 && posY < 309) {
					choice_nb_AI(5);
					nb_AI = 5;
				}
				
				// 7ème bulle
				if (posX > 875 && posX < 951 && posY > 233 && posY < 309) {
					choice_nb_AI(6);
					nb_AI = 6;
				}
				
				
				/* ---------- LANCER LA PARTIE ---------- */
				
				if (posX > 375 && posX < 853 && posY > 87 && posY < 167) { // S'il clique sur le bouton "Lancer la partie"
					if ((nb_players + nb_AI) <= 6 && (nb_players + nb_AI) >= 2) { // on souhaite que le nombre de joueur total n'excède pas 6
						launch_game = true;
					}
					else if ((nb_players + nb_AI) < 2) {
						StdDraw.picture(619, 196, "./src/ressources/erreur2.png"); // message d'erreur si le nombre de joueurs est inférieur à 2
					}
					else {
						StdDraw.picture(619, 196, "./src/ressources/erreur.png"); // message d'erreur si le nombre de joueurs est supérieur à 6
					}
					
				}
			
			}
		}
	}
	
	
	
	
	public static void launch_game() {
		System.out.println("Le jeu est en train d'être lancé");
		//Plateautest.play();
	}
	
	
	
	
	
	// cette fonction gère l'affichage du contour bleu pour le choix du nombre de joueurs
	// on lui indique le nombre du joueur et il s'occupe de dessiner le contour bleu autour des bonnes coordonnées
	public static void choice_nb_players(int nb) {
		switch(nb) {
			case 0:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(313, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
				
			case 1:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(413, 450, 38, 38);

				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
				
				
			case 2:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(513, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
			
			case 3:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(613, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
				
			case 4:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(713, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
				
			case 5:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(813, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(913, 450, 38, 38);
				break;
				
			case 6:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(913, 450, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 450, 38, 38);
				StdDraw.rectangle(413, 450, 38, 38);
				StdDraw.rectangle(513, 450, 38, 38);
				StdDraw.rectangle(613, 450, 38, 38);
				StdDraw.rectangle(713, 450, 38, 38);
				StdDraw.rectangle(813, 450, 38, 38);
				break;
		}
	}
	
	// cette fonction gère l'affichage du contour bleu pour le choix du nombre de joueurs
	public static void choice_nb_AI(int choice) {
		switch(choice) {
			case 0:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(313, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 1:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(413, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 2:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(513, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 3:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(613, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 4:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(713, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 5:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(813, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(913, 271, 38, 38);
				break;
				
			case 6:
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.rectangle(913, 271, 38, 38);
				
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.rectangle(313, 271, 38, 38);
				StdDraw.rectangle(413, 271, 38, 38);
				StdDraw.rectangle(513, 271, 38, 38);
				StdDraw.rectangle(613, 271, 38, 38);
				StdDraw.rectangle(713, 271, 38, 38);
				StdDraw.rectangle(813, 271, 38, 38);
				break;
		}
		
	}
	
	
	/* Changement sur la police */
	/*	
	 * 
	 * public static int TAILLE = 100; // A mettre tout en haut
	 * 
	String messageVictoire = "Vainqueur: joueur ";
	
	StdDraw.setXscale(0.0,TAILLE);
	StdDraw.setYscale(0.0,TAILLE);	
	StdDraw.clear(StdDraw.WHITE);
	StdDraw.setPenColor(StdDraw.BLACK);
	   
	StdDraw.text(TAILLE/2, TAILLE/2, messageVictoire);
	StdDraw.pause(500);
	
	Font font = new Font("Arial", Font.PLAIN, 16);
	StdDraw.setFont(font);

	StdDraw.clear(StdDraw.WHITE);
	StdDraw.text(TAILLE/2, TAILLE/2, messageVictoire);
	StdDraw.pause(500);
	
	System.out.println(StdDraw.getFont());
	*/

}
