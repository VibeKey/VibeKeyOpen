package channel;

import java.util.ArrayList;
import java.util.List;

public abstract class State {
	protected List<State> allowedStates;
	
	protected State() {
		this.allowedStates = new ArrayList<State>();
		this.addAllowedStates();
	}
	
	public void changeState(Channel channel, State state) {
		if (allowedStates.contains(state)) {
			channel.setState(state);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public abstract void function(Channel channel);
	protected abstract void addAllowedStates();
}
