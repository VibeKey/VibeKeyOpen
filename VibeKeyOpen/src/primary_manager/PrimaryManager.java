package primary_manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import runnables.BufferSongRunnable;
import core_objects_abstract.Song;
import core_objects_abstract.Stream;

/**
 * Primary handler for streaming music and managing multiple streams.
 * 
 * @author Bill Mader
 */
public final class PrimaryManager {

	// See Software design thread pool example.
	// Using normal runnables
	// Takes a text file that can manage all of the plugins
	
	/** ExecutorServices */
	// Cached Thread Executor for buffering of songs
	private ExecutorService bufferExecutor = VibeKey.getNewExecutor(5);
	
	// List of streams to play music from.
	private List<Stream> streams;

	public PrimaryManager() {		

		// Creates a new list of streams.
		this.streams = new ArrayList<Stream>();
	}
	
	public void bufferSong(Song song){
		bufferExecutor.submit(new BufferSongRunnable(song));
	}

	// TODO: Question, do we want them to pass in a stream to add or give them a
	// new stream to add and return it? Also how to we want to go about removing
	// streams?

	public void addStream() {
		this.streams.add(null);
	}

	public void removeStream(Stream toRemove) {
		for (Stream s : this.streams) {
			if (s.equals(toRemove)) {
				this.streams.remove(s);
			}
		}
	}
	
	/**
	 * Sets up all the things so that the whole of the program can begin to run.
	 */
	public void run() {

	}

	public void display() {

	}

}
