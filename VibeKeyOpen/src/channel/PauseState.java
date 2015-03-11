package channel;

public class PauseState implements State {
	
	Channel channel;
	
	public PauseState(Channel channel) {
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void resume() {
		
		channel.setState(channel.getPlayState());
	}

	@Override
	public void stop() {
		channel.setState(channel.getIdleState());
	}

}
