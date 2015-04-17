package primary_manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
	private ExecutorService bufferExecutor;
	
	// List of streams to play music from.
	private Map<Integer, Stream> streams;

	public PrimaryManager() {		
		bufferExecutor = VibeKey.getNewExecutor(5);
		
		// Creates a new list of streams.
		this.streams = new HashMap<Integer, Stream>();
		
	}
	
	public Future<?> bufferSong(Song song) {
		return bufferExecutor.submit(new BufferSongRunnable(song));
	}

	// TODO: Question, do we want them to pass in a stream to add or give them a
	// new stream to add and return it? Also how to we want to go about removing
	// streams?

	public void addStream(int id, Stream stream) {
		this.streams.put(id, stream);
	}

	public void removeStream(int id) {
		this.streams.remove(id);
	}
	
	public Collection<Stream> getChannelList() {
		return streams.values();
	}
	
	public ExecutorService getBufferExecutor(){
		return bufferExecutor;
	}
	
	/**
	 * Sets up all the things so that the whole of the program can begin to run.
	 */
	public void run() {

	}

	public void display() {

	}

}
