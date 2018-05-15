
public class Unit {

	// ***** Attributs *****
	
	private int type; // type de l'unité
	private int cost; // coût de l'unité
	private int power; // puissance de l'unité
	private int ATT_priority;
	private int DEF_priority;
	private int movement;
	
	// ***** Constructeurs *****
	
	
	// ***** Methodes *****
	
	
	// ***** Getters / Setters *****
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int t) {
		this.type = t;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int c) {
		this.cost = c;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int p) {
		this.power = p;
	}
	
	public int getATT() {
		return this.ATT_priority;
	}
	
	public void setATT(int att) {
		this.ATT_priority = att;
	}
	
	public int getDEF() {
		return this.DEF_priority;
	}
	
	public void setDEF(int def) {
		this.DEF_priority = def;
	}
	
	public int getMove() {
		return this.movement;
	}
	
	public void setMove(int m) {
		this.movement = m;
	}
		
}
