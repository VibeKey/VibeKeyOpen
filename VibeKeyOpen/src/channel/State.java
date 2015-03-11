package channel;

import java.util.ArrayList;
import java.util.List;

public abstract class State {
	protected List<State> allowedStates;
	
	/**
	 * Initializes the state object with the allowed transition states
	 */
	protected State() {
		this.allowedStates = new ArrayList<State>();
		this.addAllowedStates();
	}
	
	/**
	 * If the state is an allowed transition state, it will change to the new state.
	 * 
	 * @param channel
	 * @param state
	 */
	public void changeState(Channel channel, State state) {
		if (allowedStates.contains(state)) {
			channel.setState(state);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Runs the state's function.
	 * @param channel
	 */
	public abstract void function(Channel channel);
	
	/**
	 * Adds the allowed states to transition from the current state.
	 */
	protected abstract void addAllowedStates();
}
