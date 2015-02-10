package channel;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class IdleState implements State {
	
	Channel channel;
	
	public IdleState(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void open() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		Libshout icecast = channel.getIcecast();
		icecast.close();
		channel.setIcecast(null);
		channel.setState(channel.getClosedState());
	}

	@Override
	public void play() {
		if (!channel.getThread().isAlive()) {
			channel.getThread().start();
		}
		channel.setState(channel.getPlayState());
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
		channel.setState(channel.getIdleState());
	}

}
