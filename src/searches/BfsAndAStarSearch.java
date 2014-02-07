package searches;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import states.AllowedStates;
import states.State;

public class BfsAndAStarSearch {

	@Test
	public void Run_Lab(){
		AllowedStates states = new AllowedStates();
		
		System.out.println("** LIST OF ALL ALLOWED STATES AND THEIR ALLOWED TRANSITIONS **\n");
		for (State s : states.list_of_allowed_states){
			System.out.println("                     ** STATE **");
			System.out.println("(id# wifeA  husbA  wifeB  husbB  wifeC  husbC  Boat)");
			System.out.println("  "+ s.id_number + ": " + s.wifeA_side() + ", " + s.husbandA_side()+ ", " + s.wifeB_side()+ ", " + s.husbandB_side()+ ", " + s.wifeC_side()+ ", " + s.husbandC_side()+ ", " + s.boat_side());
			System.out.println("                   ** TRANSITIONS **");
			for (State s2 : s.children_states){
				System.out.println("  " + s2.id_number + ": " + s2.wifeA_side() + ", " + s2.husbandA_side()+ ", " + s2.wifeB_side()+ ", " + s2.husbandB_side()+ ", " + s2.wifeC_side()+ ", " + s2.husbandC_side()+ ", " + s2.boat_side());
			}
			System.out.println("\n");
		}
		BFS(0,35);
		a_star_search(0,35);
	}
	
	public void BFS(int start_state_index, int goal_state_index){
		/*
		 * procedure BFS(start,goal) is
		     create a queue Q
		     start.search_distance = 0
		     enqueue start onto Q
		 
		     while Q is not empty loop
		         t ??? Q.dequeue()
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
		int expansion_count = 0;
		
		State start_state = states.list_of_allowed_states.get(start_state_index);
		start_state.search_distance = 0;
		
		queue.add(start_state);
		
		int blocker = 0;
		
		while (!queue.isEmpty() && blocker < 150){
			State hold_state = queue.pop();
			expansion_count++;
			
			if (hold_state.id_number == goal_state_index){
				for (State child : hold_state.children_states){
					if (child.search_distance>0 && child.search_distance+1<hold_state.search_distance){
						hold_state.search_distance = child.search_distance+1;
						hold_state.parent_id = child.id_number;
					}
				}
				System.out.println("**BFS SEARCH**");
				System.out.println("Expansion Count: " + expansion_count);
				print_path(states.list_of_allowed_states, start_state_index, goal_state_index);
				return;
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
	}

	public void a_star_search(int start_state_index, int goal_state_index) {
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
		 * 				set index tracking variable to the position for this state
		 * 			end if
		 * 		end for
		 * 		Take the state at the position with the recorded index
		 * 		for each of the state's children 
		 * 			child's search distance = held state search distance + 1
		 * 			add child to linked_list
		 * 		end for
		 * end while
		 */
		
		int shortest_distance =0, expansion_count=0;
		AllowedStates states = new AllowedStates();	
		LinkedList<State> queue = new LinkedList<State>();
		
		State start_state = states.list_of_allowed_states.get(start_state_index);
		start_state.search_distance = 0;
		
		queue.add(start_state);
		
		int blocker = 0;
		int distance_to_compare = 0;
		int index_for_shortest_distance;
		
		while (!queue.isEmpty() && blocker < 1000){
			index_for_shortest_distance = 0;
			shortest_distance = -1;

			for (int i=0; i < queue.size(); i++){
				distance_to_compare = queue.get(i).heuristic_distance + queue.get(i).search_distance;
				if ( distance_to_compare < shortest_distance || shortest_distance < 0){
					shortest_distance = distance_to_compare;
					index_for_shortest_distance = i;
				}
			}	

			State state_to_explore = queue.remove(index_for_shortest_distance);
			expansion_count++;
			if (state_to_explore.id_number == goal_state_index){
				for (State child : state_to_explore.children_states){
					if (child.search_distance>0 && child.search_distance+child.heuristic_distance+1<state_to_explore.search_distance+state_to_explore.heuristic_distance){
						state_to_explore.search_distance = child.search_distance+1;
						state_to_explore.parent_id = child.id_number;
					}
				}
				System.out.println("**A_STAR SEARCH**");
				System.out.println("Expansion Count: " + expansion_count);
				print_path(states.list_of_allowed_states, start_state_index, goal_state_index);
				return;
			}
			
			for (State child : state_to_explore.children_states){
				child = states.list_of_allowed_states.get(child.id_number);
				if (state_to_explore.search_distance+1 < child.search_distance || child.search_distance < 0){
					child.search_distance = state_to_explore.search_distance +1;
					child.parent_id = state_to_explore.id_number;
					queue.add(child);
				}
			}
			blocker++;
		}
	}

	private void print_path(ArrayList<State> list, int start_index, int goal_index){ 
		LinkedList<Integer> path_in_reverse = new LinkedList<Integer>();
		int place_tracker = goal_index;

		while(place_tracker != start_index){
			path_in_reverse.add(place_tracker);
			place_tracker=list.get(place_tracker).parent_id;
		}
		path_in_reverse.add(place_tracker);
		int path_length = path_in_reverse.size();
	
		System.out.print("Path: ");
		for (int print_index = 1; print_index < path_in_reverse.size(); print_index++){
			System.out.print(path_in_reverse.get(path_length-print_index) + "->");
		}
		System.out.print(path_in_reverse.get(0) + "\n\n");

	}
}
