import java.util.*;
import edu.princeton.cs.introcs.StdDraw;

public class Territory {

	// ***** Attributs *****
	
	private int territory_id;
	private String territory_name;
	private ArrayList<Territory> neighbour_list = new ArrayList<>(); 
	private int owner;
	private ArrayList<Unit> units_list = new ArrayList<Unit>();
	
		
	// ***** Constructeurs *****
		
	public Territory(int id, String name) {
		this.neighbour_list = new ArrayList<>();
		this.territory_id = id;
		this.territory_name = name;		
	}
	
		
	// ***** Methodes *****
	
	
	public void drawCircle(int [] c) {
		StdDraw.setPenRadius(0.004);
		StdDraw.filledCircle(c[0], c[1], c[2]+5);
		StdDraw.setPenColor();
		StdDraw.circle(c[0], c[1], c[2]+5);
		StdDraw.text(c[0], c[1], ""+getNbUnits());
	}
	
	// Cette fonction permet de récupérer les coordonnées d'une bulle de territoire à l'aide de son ID
	public int[] getCoordinates() {
		
		// on dessine en fonction de l'ID fourni
		switch(this.getTerritoryId()) {
			case 101:
				int[] tab101 = {83,553,14};
				return tab101;
			
			case 102:
				int[] tab102 = {142,509,14};
				return tab102;

				
			case 103:
				int[] tab103 = {131,364,16};
				return tab103;
				
			case 104:
				int[] tab104 = {219,426,17};
				return tab104;
				
			case 105:
				int[] tab105 = {437,586,17};
				return tab105;
				
			case 106:
				int[] tab106 = {178,560,14};
				return tab106;
				
			case 107:
				int[] tab107 = {236,506,14};
				return tab107;
				
			case 108:
				int[] tab108 = {319,509,14};
				return tab108;
								
			case 109:
				int[] tab109 = {130,451,17};
				return tab109;
				
			case 201:
				int[] tab201 = {298,120,16};
				return tab201;
			
			case 202:
				int[] tab202 = {340,211,19};
				return tab202;
				
			case 203:
				int[] tab203 = {280,197,12};
				return tab203;
				
			case 204:
				int[] tab204 = {264,297,14};
				return tab204;
				
			case 301:
				int[] tab301 = {513,493,14};
				return tab301;
			
			case 302:
				int[] tab302 = {491,532,14};
				return tab302;
				
			case 303:
				int[] tab303 = {606,499,13};
				return tab303;
				
			case 304:
				int[] tab304 = {613,543,13};
				return tab304;
				
			case 305:
				int[] tab305 = {640,457,13};
				return tab305;
				
			case 306:
				int[] tab306 = {693,512,18};
				return tab306;
				
			case 307:
				int[] tab307 = {561,461,14};
				return tab307;
			
			case 401:
				int[] tab401 = {645,262,16};
				return tab401;
			
			case 402:
				int[] tab402 = {726,290,17};
				return tab402;
			
			case 403:
				int[] tab403 = {651,385,14};
				return tab403;
			
			case 404:
				int[] tab404 = {771,167,17};
				return tab404;
			
			case 405:
				int[] tab405 = {549,348,17};
				return tab405;
			
			case 406:
				int[] tab406 = {646,176,19};
				return tab406;
				
			case 501:
				int[] tab501 = {803,465,17};
				return tab501;
				
			case 502:
				int[] tab502 = {966,413,16};
				return tab502;
				
			case 503:
				int[] tab503 = {879,371,18};
				return tab503;
				
			case 504:
				int[] tab504 = {949,509,13};
				return tab504;
				
			case 505:
				int[] tab505 = {1125,431,16};
				return tab505;
		
			case 506:
				int[] tab506 = {1077,537,16};
				return tab506;
			
			case 507:
				int[] tab507 = {726,415,14};
				return tab507;
			
			case 508:
				int[] tab508 = {964,463,14};
				return tab508;
			
			case 509:
				int[] tab509 = {980,329,14};
				return tab509;
				
			case 510:
				int[] tab510 = {868,557,17};
				return tab510;
			
			case 511:
				int[] tab511 = {792,537,15};
				return tab511;
			
			case 512:
				int[] tab512 = {964,557,17};
				return tab512;
				
			case 601:
				int[] tab601 = {1147,146,17};
				return tab601;
			
			case 602:
				int[] tab602 = {1018,259,14};
				return tab602;
				
			case 603:
				int[] tab603 = {1150,259,14};
				return tab603;
			
			case 604:
				int[] tab604 = {1049,141,19};
				return tab604;
				
		}
		int[] empty_tab = {0,0,0};
		return empty_tab;
	}
	
	// cette méthode prend en argument le territoire que l'on souhaite attaquer
	// elle renvoie "true" si le territoire sélectionné est bien un territoire voisin
	public boolean canAttack(Territory T) {
		for (Territory Neighbour : neighbour_list) {
			if (T.getTerritoryId() == Neighbour.getTerritoryId()) {
				return true;
			}
		}
		
		return false;
	}
	


	public void addNeighbour(Territory T) {
		neighbour_list.add(T);
	}
	
	public void addUnits(ArrayList<Unit> units) {
		for (Unit u : units) {
			this.units_list.add(u);
		}
		
	}
	
	public void addUnit(Unit unit) {
		this.units_list.add(unit);
	}
	
	// cette méthode permet d'afficher le nombre d'unités dans ce territoire

	
	// ***** Getters / Setters *****
	
	public String getTerritoryName() {
		return this.territory_name;
	}
		
	public void setTerritoryName(String n) {
		this.territory_name = n;
	}
	
	public int getTerritoryId() {
		return this.territory_id;
	}
	
	public void setTerritoryId(int id) {
		this.territory_id = id;
	}
	
	public int getOwner() {
		return this.owner;
	}
	
	public void setOwner(Player P) {
		this.owner = P.getPlayerNb();
		int[] coordinates = getCoordinates();
		P.changeColor();
		drawCircle(coordinates);
	}
	
	public ArrayList<Unit> getUnits() {
		return this.units_list;
	}
	
	public void setUnits(ArrayList<Unit> units) {
		this.units_list = units;
	}
	
	// ce getter permet de renvoyer le nombre d'unités présentes dans le territoire actuel
	public int getNbUnits() {
		return units_list.size();
	}
	
	public List<Territory> getNeighbourTerritories() {
        return this.neighbour_list;
    }
	
}
