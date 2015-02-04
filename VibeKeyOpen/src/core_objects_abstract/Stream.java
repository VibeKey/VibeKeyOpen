package core_objects_abstract;

import java.util.ArrayList;
import java.util.List;

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


	/** Abstract methods for stream control. Functionality to be determined
	 * by concrete class. */ 
	
	public abstract void open();
	
	public abstract void play();

	public abstract void pause();

	public abstract void resume();

	public abstract void stop();

}
