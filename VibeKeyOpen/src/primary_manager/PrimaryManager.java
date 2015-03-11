package primary_manager;

import core_objects_abstract.Song;
import core_objects_abstract.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import threads.BufferManagerThread;
import threads.BufferThread;

/**
 * Primary handler for streaming music and managing multiple streams.
 * 
 * @author Bill Mader
 */
public final class PrimaryManager {

	// See Software design thread pool example.
	// Using normal runnables
	// Takes a text file that can manage all of the plugins
	
	/** Handlers **/
	// List of threads to pull from.
	private List<Thread> threadPool;
	/** An int that controls the number of buffer threads. */
	private BufferManagerThread buffer;
	// List of streams to play music from.
	private List<Stream> streams;

	public PrimaryManager() {
		// Creates new thread list and adds ten threads to it.
		this.threadPool = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			this.threadPool.add(new Thread());
		}
		
		buffer = new BufferManagerThread();
		buffer.run();

		// Creates a new list of streams.
		this.streams = new ArrayList<Stream>();

	}

	public Thread takeThread() {
		if (this.threadPool.size() > 0) {
			// Thread pool has a thread to give. Remove from pool and return.
			return this.threadPool.remove(0);
		} else {
			// Thread pool does not have a thread return null for now.
			return null;
		}
	}

	public void returnThread(Thread t) {
		// Add thread to the end of the queue.
		this.threadPool.add(t);
	}
	
	public void requestBufferThread(Song song) {
		buffer.bufferSong(song);
	}
	
	public void finishBuffer(Song song) {
		buffer.finishBuffer(song);
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

	// TODO: What exactly do we want these methods to do?
	
	/**
	 * Sets up all the things so that the whole of the program can begin to run.
	 */
	public void run() {

	}

	public void display() {

	}

}
