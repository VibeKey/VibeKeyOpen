package channel;

public class PlayState extends State {
	@Override
	public void function(Channel channel) {
		channel.getThread().control.resume();
	}

	
	protected void addAllowedStates() {
		this.allowedStates.add(Channel.PAUSE_STATE);
		this.allowedStates.add(Channel.STOP_STATE);
	}

}
