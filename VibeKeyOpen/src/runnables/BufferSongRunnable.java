package runnables;

import core_objects_abstract.Song;

public class BufferSongRunnable implements Runnable {

	private final Song songToBuffer;
	
	public BufferSongRunnable(Song songToBuffer) {
		this.songToBuffer = songToBuffer;
	}

	@Override
	public void run() {
		songToBuffer.buffer();
	}

}
