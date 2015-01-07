package core_objects_abstract;

import java.util.ArrayList;
import java.util.List;

public abstract class Stream {

	/** Local Data */
	// List of songs to stream.
	@SuppressWarnings("unused")
	private List<Song> songs;

	/** Generic constructor. Calls final constructor with an empty list. */
	public Stream() {
		this(new ArrayList<Song>());
	}

	/** Constructor sets the list of songs based on input parameters. */
	public Stream(List<Song> songList) {
		this.songs = songList;
	}

	/** Abstract methods for stream control. Functionality to be determined
	 * by concrete class. */
	
	public abstract void play();

	public abstract void pause();

	public abstract void resume();

	public abstract void stop();

}
