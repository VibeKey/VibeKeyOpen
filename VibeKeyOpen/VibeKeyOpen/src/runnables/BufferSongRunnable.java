package runnables;

import core_objects_abstract.Song;

/**
 * Runnable to submit to executor to buffer songs
 * 
 * @author Benedict
 *
 */
public class BufferSongRunnable implements Runnable {

	private final Song songToBuffer;
	
	public BufferSongRunnable(Song songToBuffer) {
		this.songToBuffer = songToBuffer;
	}

	@Override
	public void run() {
		
		while (!songToBuffer.finishedBuffering()){
			songToBuffer.buffer();
		}
	}

}
