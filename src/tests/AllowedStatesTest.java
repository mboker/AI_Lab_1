package tests;

import java.util.LinkedList;

import org.junit.Test;

import states.AllowedStates;
import states.State;

public class AllowedStatesTest {

	@Test
	public void test(){
		AllowedStates states = new AllowedStates();
		//states.print_states();
		for (State s : states.list_of_allowed_states){
			System.out.println(s.id_number + ": " + s.wifeA_on_right() + ", " + s.husbandA_on_right()+ ", " + s.wifeB_on_right()+ ", " + s.husbandB_on_right()+ ", " + s.wifeC_on_right()+ ", " + s.husbandC_on_right()+ ", " + s.BOAT_on_right());
			for (State s2 : s.children_states){
				System.out.println(s2.id_number + ": " + s2.wifeA_on_right() + ", " + s2.husbandA_on_right()+ ", " + s2.wifeB_on_right()+ ", " + s2.husbandB_on_right()+ ", " + s2.wifeC_on_right()+ ", " + s2.husbandC_on_right()+ ", " + s2.BOAT_on_right());
			}
			System.out.println("\n");
		}
		//BFS(0,35);
		a_star_search(0,35);
	}
	
	public void BFS(int start_state_index, int goal_state_index){
		/*
		 * procedure BFS(start,goal) is
		     create a queue Q
		     start.search_distance = 0
		     enqueue start onto Q
		 
		     while Q is not empty loop
		         t ← Q.dequeue()
		         if t is goal state then
		              should still be treated same, need to complete search and then check the path at the end
		         end if
		         for all t's children states loop
						  if t.search_distance < child.search_distance OR child.search_distance < 0
								child.search_distance = t.search_distance + 1
								child.parent_id = t.id_number
		   				  end if
		                  enqueue child onto Q
		         end loop
		     end loop
		     return none
		  end 
		 */
		
		AllowedStates states = new AllowedStates();	
		LinkedList<State> queue = new LinkedList<State>();
		
		State start_state = states.list_of_allowed_states.get(start_state_index);
		State goal_state = states.list_of_allowed_states.get(goal_state_index);
		start_state.search_distance = 0;
		
		queue.add(start_state);
		
		int blocker = 0;
		
		while (!queue.isEmpty() && blocker < 150){
			State hold_state = queue.pop();
			System.out.println("popped: " + hold_state.id_number);

			if (hold_state.id_number == goal_state_index){
				
			}
			for (State child : hold_state.children_states){
				child = states.list_of_allowed_states.get(child.id_number);
				if (hold_state.search_distance+1 < child.search_distance || child.search_distance < 0){
					child.search_distance = hold_state.search_distance +1;
					child.parent_id = hold_state.id_number;
				}
				if (!queue.contains(child))
					queue.add(child);
			}
			blocker++;
		}
		System.out.println(goal_state.search_distance);
		System.out.println(goal_state.parent_id);
		
	}

	public synchronized void a_star_search(int start_state_index, int goal_state_index) {
		/* 
		 * open a linked_list of states
		 * set start distance to 0
		 * add start state to linked_list
		 * 
		 * while linked list is not empty
		 * 		take the first state in the list, and hold it
		 * 		if this is the goal state
		 * 			DO SOMETHING
		 * 		add the search distance of this state to its heuristic distance
		 * 		set the shortest_distance to this distance
		 * 		for each state in the list
		 * 			compare the search distance plus heuristic to the shortest length
		 * 			if the new length is shorter
		 * 				set shortest distance to this new length
		 * 				remove this state and hold it
		 * 				place old state back onto list
		 * 			end if
		 * 		end for
		 * 		for each of the held state's children 
		 * 			child's search distance = held state search distance + 1
		 * 			add child to linked_list
		 * 		end for
		 * end while
		 */
		
		int shortest_distance =0;
		AllowedStates states = new AllowedStates();	
		LinkedList<State> queue = new LinkedList<State>();
		
		State start_state = states.list_of_allowed_states.get(start_state_index);
		State goal_state = states.list_of_allowed_states.get(goal_state_index);
		start_state.search_distance = 0;
		
		queue.add(start_state);
		
		int blocker = 0;
		int distance_to_compare = 0;
		
		while (!queue.isEmpty() && blocker < 150){
			State hold_state = queue.pop();
			System.out.println("popped: " + hold_state.id_number);
			shortest_distance = hold_state.heuristic_distance + hold_state.search_distance;

			if (hold_state.id_number == goal_state_index){
				
			}
			for (State state_to_compare : queue){
				distance_to_compare = state_to_compare.heuristic_distance + state_to_compare.search_distance;
				if ( distance_to_compare < shortest_distance){
					shortest_distance = distance_to_compare;
					queue.add(hold_state);
					hold_state = state_to_compare;
					queue.remove(state_to_compare);
				}
			}	

			
			for (State child : hold_state.children_states){
				child = states.list_of_allowed_states.get(child.id_number);
				if (hold_state.search_distance+1 < child.search_distance || child.search_distance < 0){
					child.search_distance = hold_state.search_distance +1;
					child.parent_id = hold_state.id_number;
				}
				if (queue.contains(child)){
					queue.remove(child);
					queue.add(child);
				}
				else queue.add(child);
			}
			blocker++;
		}
		System.out.println(goal_state.search_distance);
		System.out.println(goal_state.parent_id);
	}
}
