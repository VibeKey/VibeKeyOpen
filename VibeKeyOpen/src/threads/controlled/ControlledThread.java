package threads.controlled;

public class ControlledThread extends Thread {
	public void run(ControlledRunner runner) {
		runner.run();
	}
}
