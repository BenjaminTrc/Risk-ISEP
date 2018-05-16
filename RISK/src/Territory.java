import java.util.*;

public class Territory {

	// ***** Attributs *****
	
	private int territory_id;
	private String territory_name;
	private ArrayList<Territory> neighbour_list = new ArrayList<>(); 
	
		
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
}
