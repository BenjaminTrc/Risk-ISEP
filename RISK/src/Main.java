import edu.princeton.cs.introcs.StdDraw;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test 123");
		show_first_page();
		
	}
	
	// cette fonction permet d'afficher la premi�re "page" lorsqu'on executera le jeu
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
		
		while (!play_game) { // tant que le joueur n'a pas cliqu� sur le bouton "jouer", on reste sur la toute premi�re page
			if (StdDraw.isMousePressed()) {
				posX = StdDraw.mouseX();
				posY = StdDraw.mouseY();
				System.out.println("Position X : " + posX + " +++ Position Y : " + posY);
				StdDraw.pause(250);
				
				/*
				if (posX > ### && posX < ### && posY > ### && posY < ###) {
					play_game = true;
				}
				*/
				
			}
		}
		
	}

}
