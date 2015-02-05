package threads;

import java.util.PriorityQueue;
import java.util.Queue;

import core_objects_abstract.Song;

public class BufferThread extends Thread {
	private Queue<Song> bufferList;
	private Song current;

	public BufferThread() {
		bufferList = new PriorityQueue<Song>();
	}

	private void buffer() {
		if (current == null) return;
	}
	
	private void switchBuffer() {
		if (current != null) bufferList.add(current);
		current = bufferList.poll();
	}
	
	public void bufferSong(Song song) {
		bufferList.add(song);
	}
	
	@Override
	public void run() {
		super.run();
		
		while (!bufferList.isEmpty() && current != null) {
			buffer();
			switchBuffer();
		}
	}
}
