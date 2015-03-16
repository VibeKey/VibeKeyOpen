package runnables.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import primary_manager.VibeKey;
import core_objects_abstract.Song;

public class BufferTest {

	@Test
	public void test() throws InterruptedException, IOException {
		
		Song song = new Song(new File("Songs/LoseMyself.mp3").getAbsolutePath(), 201, 
				"Lose Myself", 3217030);
		
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
