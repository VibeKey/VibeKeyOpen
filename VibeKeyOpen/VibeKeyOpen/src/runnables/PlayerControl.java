package runnables;

public class PlayerControl {
	
	private boolean needToPause = false;
	
	public synchronized void pauseCheck() throws InterruptedException {
		while (needToPause) {
			wait();
		}
	}
	
	public synchronized void pause() {
		needToPause = true;
	}
	
	public synchronized void resume() {
		needToPause = false;
		this.notifyAll();
	}
}
