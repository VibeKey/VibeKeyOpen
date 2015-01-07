package primary_manager;

import core_objects_abstract.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Primary handler for streaming music and managing multiple streams.
 * 
 * @author Bill Mader
 */
public class PrimaryManager {

	/** Handlers **/
	// List of threads to pull from.
	private List<Thread> threadPool;
	// List of streams to play music from.
	private List<Stream> streams;

	public PrimaryManager() {
		// Creates new thread list and adds ten threads to it.
		this.threadPool = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			this.threadPool.add(new Thread());
		}

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

	// TODO: Question, do we want play, pause etc. to work on all streams at
	// once or one at a time (specified in the parameter perhaps)?

	public void playStream() {
		for (Stream s : this.streams) {
			s.play();
		}
	}

	public void pauseStream() {
		for (Stream s : this.streams) {
			s.pause();
		}
	}

	public void resumeStream() {
		for (Stream s : this.streams) {
			s.resume();
		}
	}

	public void stopStream() {
		for (Stream s : this.streams) {
			s.stop();
		}
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

	public void run() {

	}

	public void display() {

	}

}
