package primary_manager;

import djbot.DJBot;

public class VibeKey {
	public static PrimaryManager manager = new PrimaryManager();;

	public static void main(String[] args) {
		manager.takeThread(new DJBot());
	}

}
