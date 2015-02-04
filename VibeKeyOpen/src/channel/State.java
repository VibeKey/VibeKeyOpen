package channel;

public interface State {
	
	public void open();
	
	public void close();
	
	public void play();
	
	public void pause();
	
	public void resume();
	
	public void stop();
}
