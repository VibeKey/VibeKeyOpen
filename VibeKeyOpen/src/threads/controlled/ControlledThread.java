package threads.controlled;

import primary_manager.VibeKey;

public class ControlledThread extends Thread {
	private ControlledRunner runner;
	public void run(ControlledRunner runner) {
		System.out.println("I am thread: " + this.getId());
		this.runner = runner;
		runner.execute();
		VibeKey.manager.returnThread(this);
	}
}
