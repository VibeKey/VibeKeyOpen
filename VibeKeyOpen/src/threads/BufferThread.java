package threads;

import primary_manager.VibeKey;
import core_objects_abstract.Song;

/**
 * A thread that allows the song to buffer.
 * 
 * @author Rose Reatherford
 */
public class BufferThread extends Thread {
	/** The song being buffered. */
	private Song song;
	
	/**
	 * A constructor for this thread.
	 * @param song The song to buffer.
	 */
	public BufferThread(Song song) {
		this.song = song;
	}

	@Override
	public void run() {
		song.buffer();
		VibeKey.manager.finishBuffer(song);
	}

}
