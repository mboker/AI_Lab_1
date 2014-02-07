package states;

import java.util.ArrayList;

public class State {
	protected boolean[] locations;
	public int search_distance;
	public int heuristic_distance;
	public int	id_number;
	public int parent_id;
	public ArrayList<State> children_states;
	//format is {wifeA, wifeB, wifeC, husbandA, husbandB, husbandC, BOAT}
	//true means on the right side of river
	
	public State(){
		this.locations = new boolean[]{false,false,false,false,false,false,false};
		children_states = new ArrayList<State>();
		search_distance = -1;
		heuristic_distance = -1;
	}
	
	public void add_child_state(State child){
		children_states.add(child);
	}
	
	public void copy_from(State source_state){
		for (int i=0; i< locations.length; i++){
			this.locations[i] = source_state.locations[i];
		}
	}
	
	public boolean wifeA_on_right(){
		return locations[0];
	}
	public boolean husbandA_on_right(){
		return locations[1];
	}
	public boolean wifeB_on_right(){
		return locations[2];
	}
	public boolean husbandB_on_right(){
		return locations[3];
	}
	public boolean wifeC_on_right(){
		return locations[4];
	}
	public boolean husbandC_on_right(){
		return locations[5];
	}
	public boolean BOAT_on_right(){
		return locations[6];
	}

	public String wifeA_side() {
		if (locations[0] = false) return "Left";
		else return "Right";
	}

	public String husbandA_side() {
		if (locations[1] = false) return "Left";
		else return "Right";
	}
	
	public String wifeB_side() {
		if (locations[2] = false) return "Left";
		else return "Right";
	}
	
	public String husbandB_side() {
		if (locations[3] = false) return "Left";
		else return "Right";
	}
	
	public String wifeC_side() {
		if (locations[4] = false) return "Left";
		else return "Right";
	}
	
	public String husbandC_side() {
		if (locations[5] = false) return "Left";
		else return "Right";
	}
	
	public String boat_side() {
		if (locations[6] = false) return "Left";
		else return "Right";
	}
}
