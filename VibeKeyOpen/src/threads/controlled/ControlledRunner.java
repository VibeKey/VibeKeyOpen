package threads.controlled;

import primary_manager.VibeKey;

public abstract class ControlledRunner {
	public void run() {
		execute();
		VibeKey.manager.returnThread(Thread.currentThread());
	}
	
	public abstract void execute();
}
