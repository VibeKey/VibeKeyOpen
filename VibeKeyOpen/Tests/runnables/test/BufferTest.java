package runnables.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

import primary_manager.VibeKey;
import core_objects_abstract.Song;

public class BufferTest {

	@Test
	public void test() throws InterruptedException, IOException {
		
		File songFile = new File("Songs/LoseMyself.mp3");
		
		Song song = new Song(songFile.getAbsolutePath(), 201, 
				"Lose Myself", (int) songFile.length());
		
		VibeKey.manager.bufferSong(song);
		
		while(!song.finishedBuffering()){
			synchronized(song){
				song.wait();
			}
		}
		long size = (long) Math.ceil(3217030 / Song.BUFFER_SIZE);
		assertTrue("The estimated size is not the actual size.\nActual Size: " 
				+ song.getBuffer().size() + "\nEstimated Size: " + size, song.getBuffer().size() == size);
	}

}
