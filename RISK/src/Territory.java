import java.util.*;

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
	
	public void setOwner(int player) {
		this.owner = player;
	}
	
	public ArrayList<Unit> getUnits() {
		return this.units_list;
	}
	
	public void setUnits(ArrayList<Unit> units) {
		this.units_list = units;
	}
	
	public int getNbUnits() {
		return units_list.size();
	}
	
}
