package channel;

public class PauseState extends State {
	
	/**
	 * This is the function that the PlayState runs
	 */
	@Override
	public void function(Channel channel) {
		channel.getThread().control.pause();
	}

	/**
	 * These are the allowed states to change from the PlayState
	 */
	protected void addAllowedStates() {
		this.allowedStates.add(Channel.PLAY_STATE);
		this.allowedStates.add(Channel.STOP_STATE);
	}

}
