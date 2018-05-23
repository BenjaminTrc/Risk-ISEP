import java.util.*;

public class Territory {

	// ***** Attributs *****
	
	private int territory_id;
	private String territory_name;
	private ArrayList<Territory> neighbour_list = new ArrayList<>(); 
	private int owner;
	private ArrayList<Unit> units_list = new ArrayList<Unit>();
	
		
	// ***** Constructeurs *****
		
	
		
	// ***** Methodes *****
	
	public void addNeighbour(Territory T) {
		neighbour_list.add(T);
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
	
	public void setUnits(Unit unit) {
		this.units_list.add(unit);
	}
	
	public int getNbUnits() {
		return units_list.size();
	}
}
