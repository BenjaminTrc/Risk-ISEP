import edu.princeton.cs.introcs.StdDraw;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test 123");
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
		
		StdDraw.picture(width/2, height/2, "risk_first_page.png");
		
		while (!play_game) { // tant que le joueur n'a pas cliqué sur le bouton "jouer", on reste sur la toute première page
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(250);
				
				
				if (posX > 440 && posX < 786 && posY > 175 && posY < 333) {
					play_game = true;
				}
				
				
			}
		}
	}
	
	
	public static void configuration() {
		System.out.println("La configuration a été lancée");
	}
	
	public static void launch_game() {
		System.out.println("Le jeu est en train d'être lancé");
	}

}
