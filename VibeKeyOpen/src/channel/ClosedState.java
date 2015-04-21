package channel;

import java.io.IOException;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class ClosedState extends State {

	/**
	 * This is the function that the PlayState runs
	 */
	@Override
	public void function(Channel channel) {
		Libshout icecast = null;
		try {
			icecast = new Libshout();
			icecast.setHost("localhost");
			icecast.setPort(8000);
			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
			icecast.setPassword("MotherDJBot");
			icecast.setMount("/" + channel.getName());
			icecast.setFormat(Libshout.FORMAT_MP3);
			icecast.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		channel.setIcecast(icecast);
	}

	/**
	 * These are the allowed states to change from the PlayState
	 */
	protected void addAllowedStates() {
		this.allowedStates.add(Channel.OPEN_STATE);
	}

}
