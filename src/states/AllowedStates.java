package states;

import java.util.ArrayList;


public class AllowedStates {
	private ArrayList<State> list_of_all_states;
	public ArrayList<State> list_of_allowed_states;
	
	public AllowedStates(){
		list_of_all_states = new ArrayList<State>();
		State start_state = new State();
		list_of_all_states = all_possible_states(0, start_state);
		list_of_allowed_states = filter_allowed_states(list_of_all_states);
		find_children_states_for_each(list_of_allowed_states);
	}

	private boolean one_or_two_move_with_boat(State state1, State state2){
		int count = 0;
		if (state1.locations[6]==false){
			//one or two move from false to true
			for (int i=0; i<6; i++){
				if (state1.locations[i]== false && state2.locations[i] == true) count++;
				if (state1.locations[i]== true && state2.locations[i] == false) return false;
			}
			if ((count == 1 || count == 2) && state2.locations[6]==true) return true;
			else return false;
		}
		else if (state1.locations[6]==true){
			//one or two move from true to false
			for (int i=0; i<6; i++){
				if (state1.locations[i]==true && state2.locations[i] == false) count++;
				if (state1.locations[i]== false && state2.locations[i] == true) return false;
			}
			if ((count == 1 || count == 2) && state2.locations[6]==false) return true;
			else return false;
		}
		//return false;
		return true;
	}
	
	public void find_children_states_for_each(ArrayList<State> allowed_states){
		for (State parent : allowed_states){
			for(State pottential_child : allowed_states){
				//check that 1 or two people have moved, and so has the boat
				if (one_or_two_move_with_boat(parent, pottential_child)){
					parent.add_child_state(pottential_child);
				}
			}
		}
	}
	
	private boolean husbandA_is_jealous (State s)
	{
		if (s.wifeA_on_right()&&!s.husbandA_on_right()&&(s.husbandB_on_right()||s.husbandC_on_right())){
			return true;
		}
		else if(!s.wifeA_on_right()&&s.husbandA_on_right()&&(!s.husbandB_on_right()||!s.husbandC_on_right())){
			return true;
		}
		else return false;
	}
	
	private boolean husbandB_is_jealous (State s)
	{
		if (s.wifeB_on_right()&&!s.husbandB_on_right()&&(s.husbandA_on_right()||s.husbandC_on_right())){
			return true;
		}
		else if(!s.wifeB_on_right()&&s.husbandB_on_right()&&(!s.husbandA_on_right()||!s.husbandC_on_right())){
			return true;
		}
		else return false;
	}
	
	private boolean husbandC_is_jealous (State s)
	{
		if (s.wifeC_on_right()&&!s.husbandC_on_right()&&(s.husbandA_on_right()||s.husbandB_on_right())){
			return true;
		}
		else if(!s.wifeC_on_right()&&s.husbandC_on_right()&&(!s.husbandA_on_right()||!s.husbandB_on_right())){
			return true;
		}
		else return false;
	}
	
	public ArrayList<State> filter_allowed_states(ArrayList<State> input_list){
		int counter = 0;
		ArrayList<State> allowed_states = new ArrayList<State>();
		for(State s : input_list){
			if (!husbandA_is_jealous(s) && !husbandB_is_jealous(s) && !husbandC_is_jealous(s)){
				s.id_number=counter;
				s.heuristic_distance = heuristic_calculator(s);
				counter++;
				allowed_states.add(s.id_number, s);
			}	
		}
		return allowed_states;
	}
	
	private int heuristic_calculator(State state){
		int count = 0, boat_multiplier = 0;
		int calculated_value;
		
		for (int i=0; i<6; i++){
			if (state.locations[i]== false) count++;
		}
		
		if (state.locations[6] == false) boat_multiplier = 1;
		
		calculated_value = count*2-3*(boat_multiplier);
		
		if (calculated_value >= 0)
			return calculated_value;
		else return 1;
	}
	
	public ArrayList<State> all_possible_states(int position, State current_state){
		ArrayList<State> list_to_return = new ArrayList<State>();
		if(position == current_state.locations.length-1){
			State state_to_add = new State();
			State second_state_to_add = new State();
			state_to_add.copy_from(current_state);
			second_state_to_add.copy_from(current_state);
			
			state_to_add.locations[position] = false;				
			second_state_to_add.locations[position] = true;

			list_to_return.add(state_to_add);
			list_to_return.add(second_state_to_add);
			return list_to_return;
		}
		
		list_to_return.addAll(all_possible_states((position+1), current_state));
		current_state.locations[position] = !current_state.locations[position];
		list_to_return.addAll(all_possible_states((position+1), current_state));
		return list_to_return;
	}
	
	
	public void print_states(){
		State some_state = new State();
		
		System.out.println(list_of_all_states.size());
		System.out.println("wA    hA    wB    hB    wC    hC    Boat");
		for (State s : list_of_all_states){
			System.out.println(s.locations[0] + " "+ s.locations[1] + " " + s.locations[2] + " " + s.locations[3] + " " + s.locations[4] + " " + s.locations[5] + " " + s.locations[6] + "\n");
		}
		
		System.out.println(list_of_allowed_states.size());
		System.out.println("wA    hA    wB    hB    wC    hC    Boat");
		for (State s : list_of_allowed_states){
			System.out.println(s.locations[0] + " "+ s.locations[1] + " " + s.locations[2] + " " + s.locations[3] + " " + s.locations[4] + " " + s.locations[5] + " " + s.locations[6] + "\n");
		}
		
	}
	
	
}
