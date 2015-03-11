package core_objects_abstract;

import djbot.DJBot;

public abstract class Stream {

	/** Local Data */
	// List of songs to stream.
	protected DJBot bot;

	/** Generic constructor. Calls final constructor with an empty list. */
	public Stream() {
		this(new DJBot());
	}

	/** Constructor sets the list of songs based on input parameters. */
	public Stream(DJBot bot) {
		this.bot = bot;
	}

}
