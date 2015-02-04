package channel;

import java.io.IOException;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class ClosedState implements State {
	
	Channel channel;
	
	public ClosedState(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void open() {
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
		
		channel.setState(channel.getIdleState());
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void play() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pause() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resume() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException();
	}

}
