import java.util.*;

public class Region {

	// ***** Attributs *****
	
	private int region_id;
	private String region_name;
	private ArrayList<Territory> territory_list = new ArrayList<>();
	
	// ***** Constructeurs *****
	
	
	// ***** Methodes *****
	
	public void addTerritory(Territory T) {
		territory_list.add(T);
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
	
	
}
