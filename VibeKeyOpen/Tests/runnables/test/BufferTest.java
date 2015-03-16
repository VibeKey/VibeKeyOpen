package runnables.test;

import static org.junit.Assert.*;

import java.util.concurrent.Future;

import org.junit.Test;

import primary_manager.PrimaryManager;
import core_objects_abstract.Song;

public class BufferTest {

	@Test
	public void test() throws InterruptedException {
		Song song = new Song("C:\\Users\\reatherl\\git\\VibeKeyOpen\\VibeKeyOpen\\VibeKeyOpen\\Songs\\LoseMyself.mp3", 201, 
				"Lose Myself", 3219456);
		
		PrimaryManager manager = new PrimaryManager();
		Future<?> future = manager.bufferSong(song);
		while (!song.finishedBuffering()) {
			Thread.sleep(1000);
		}
		
		long size = (long) Math.ceil(3219456 / Song.BUFFER_SIZE);
		assertTrue("The estimated size is not the actual size.\nActual Size: " 
				+ song.getBuffer().size() + "\nEstimated Size: " + size, song.getBuffer().size() == size);
	}

}
