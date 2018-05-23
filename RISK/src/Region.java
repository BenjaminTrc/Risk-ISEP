import java.util.*;

public class Region {

	// ***** Attributs *****
	
	private int region_id;
	private String region_name;
	private ArrayList<Territory> territory_list = new ArrayList<>();
	
	// ***** Constructeurs *****
	
	public Region(int id, String name) {
		this.territory_list = new ArrayList<>();
		this.region_id = id;
		this.region_name = name;		
	}
	
	// ***** Methodes *****
	
	public void addTerritory(Territory T) {
		territory_list.add(T);
	}
	
	public List<Territory> getTerritories() {
        return this.territory_list;
    }
	
	// ***** Getters / Setters *****
	
	public String getRegionName() {
		return this.region_name;
	}
	
	public void setRegionName(String n) {
		this.region_name = n;
	}
	
	public int getRegionId() {
		return this.region_id;
	}
	
	public void setRegionId(int id) {
		this.region_id = id;
	}
	
	public ArrayList<Territory> getTerritoryList() {
		return this.territory_list;
	}
	
}
