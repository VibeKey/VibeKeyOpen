package threads;

import java.util.PriorityQueue;
import java.util.Queue;

import core_objects_abstract.Song;

public class BufferManagerThread extends Thread {
	private Queue<Song> bufferList;
	private static int MAX_THREADS = 3;
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
	
	public void bufferSong(Song song) {
		bufferList.add(song);
		buffer();
	}
	
	private void buffer() {
		if (threadPool <= 0) return;
		
		threadPool--;
		Thread thread = new BufferThread(bufferList.poll());
		thread.run();
	}

	@Override
	public void run() {
		super.run();
		
	}
}
