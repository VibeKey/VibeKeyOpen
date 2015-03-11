package channel;

import com.gmail.kunicins.olegs.libshout.Libshout;

/**
 * Idle State can be active when you stop a track or open a new channel.
 * 
 * @author Jonathan
 */
public class IdleState extends State {

	/**
	 * This is the function that the PlayState runs
	 */
	@Override
	public void function(Channel channel) {
		Libshout icecast = channel.getIcecast();
		icecast.close();
		channel.setIcecast(null);
	}

	/**
	 * These are the allowed states to change from the PlayState
	 */
	protected void addAllowedStates() {
		this.allowedStates.add(Channel.PLAY_STATE);
		this.allowedStates.add(Channel.CLOSED_STATE);
	}

}
