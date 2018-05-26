
public class Unit {

	// ***** Attributs *****
	
	private int type; // type de l'unité 1 = soldat, 2 = cavalier, 3 = canon
	private int cost; // coût de l'unité
	private int min_power; // puissance minimale de l'unité
	private int max_power; // puissance maximale de l'unité
	private int ATT_priority;
	private int DEF_priority;
	private int movement; // points de mouvement de l'unité
	
	// ***** Constructeurs *****
	
	public void Unit(int type) {
		if (type == 1) {
			this.type = type;
			this.cost = 1;
			this.min_power = 1;
			this.max_power = 6;
			this.ATT_priority = 2;
			this.DEF_priority = 1;
			this.movement = 2;
		}
		else if (type == 2) {
			this.type = type;
			this.cost = 3;
			this.min_power = 2;
			this.max_power = 7;
			this.ATT_priority = 1;
			this.DEF_priority = 3;
			this.movement = 3;
		}
		else {
			this.type = type;
			this.cost = 7;
			this.min_power = 4;
			this.max_power = 9;
			this.ATT_priority = 3;
			this.DEF_priority = 2;
			this.movement = 1;
		}
	}
	
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
	
	public int getMinPower() {
		return this.min_power;
	}
	
	public void setMinPower(int p) {
		this.min_power = p;
	}
	
	public int getMaxPower() {
		return this.max_power;
	}
	
	public void setMaxPower(int p) {
		this.max_power = p;
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
