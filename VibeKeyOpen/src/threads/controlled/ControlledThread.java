package threads.controlled;

import primary_manager.VibeKey;

public class ControlledThread extends Thread {
	public void run(ControlledRunner runner) {
		System.out.println("I am thread: " + this.getId());
		runner.execute();
		VibeKey.manager.returnThread(this);
	}
}
