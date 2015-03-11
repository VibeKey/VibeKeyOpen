package threads;

import java.util.PriorityQueue;
import java.util.Queue;

import core_objects_abstract.Song;

/**
 * Manages the buffer threads and the songs that need to be buffered.
 * 
 * @author Rose Reatherford
 * @created 3/11/2015
 */
public class BufferManagerThread extends Thread {
	// Static Variables
	/** The max number of buffering threads. */
	private static int MAX_THREADS = 3;
	
	// Local Variables
	/** The queue of songs that need to be buffered. */
	private Queue<Song> bufferList;
	/** The number of threads in the pool. */
	private int threadPool;

	public BufferManagerThread() {
		bufferList = new PriorityQueue<Song>();
		threadPool = MAX_THREADS;
	}
	
	/**
	 * Tells the manager that a song has finished buffering a chunk.
	 * @param song The song that has finished a chunk.
	 */
	public void finishBuffer(Song song) {
		if (!song.finishedBuffering()) bufferList.add(song);
		if (threadPool < MAX_THREADS) threadPool++; 
		if (!bufferList.isEmpty()) buffer();
	}
	
	/**
	 * Adds the song to the list of songs to be buffered.
	 * @param song The song to buffer.
	 */
	public void bufferSong(Song song) {
		bufferList.add(song);
		buffer();
	}
	
	/**
	 * Creates a new thread to buffer the song at the top of the queue. 
	 */
	private void buffer() {
		// Checks that the thread pool and buffer list contain items.
		if (threadPool <= 0 || bufferList.size() <= 0) return;
		
		// Creates a new thread to buffer the top song.
		threadPool--;
		Thread thread = new BufferThread(bufferList.poll());
		thread.run();
	}
}
