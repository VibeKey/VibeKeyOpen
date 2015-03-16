package runnables;

import primary_manager.VibeKey;
import core_objects_abstract.Song;

public class BufferSongRunnable implements Runnable {

	private final Song songToBuffer;
	
	public BufferSongRunnable(Song songToBuffer) {
		this.songToBuffer = songToBuffer;
	}

	@Override
	public void run() {
		System.out.println("Buffering...");
		songToBuffer.buffer();
		if (!songToBuffer.finishedBuffering()) VibeKey.manager.bufferSong(songToBuffer);
	}

}
