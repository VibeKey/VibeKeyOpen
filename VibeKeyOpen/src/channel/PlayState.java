package channel;

public class PlayState implements State {
	
	Channel channel;
	
	public PlayState(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void open() {
		throw new UnsupportedOperationException();
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
		channel.setState(channel.getPauseState());
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
