package runnables.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import primary_manager.VibeKey;
import core_objects_abstract.Song;

public class BufferTest {

	@Test
	public void testOneSong() throws Exception {
		
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
		
		songFile.setReadOnly();
		FileInputStream in = new FileInputStream(songFile);
		
		for (int i = 0; i < song.getBuffer().size(); i++) {
			byte[] test = song.getBuffer().get(i);
			for (int j = 0; j < 1024; j++) {
				byte expected = (byte) in.read();
				assertTrue("Byte " + (i * 1024 + j) + " is not correct.\n"
						+ "It is " + test[j] +", but we were expecting " 
						+ expected + ".", expected == test[j]);
			}
		}
		
		in.close();
	}
	
	@Test
	public void testTwoSongs() throws InterruptedException {
		File songFile1 = new File("Songs/LoseMyself.mp3");
		File songFile2 = new File("Songs/Rift.mp3");
		
		Song song1 = new Song(songFile1.getAbsolutePath(), 201, 
				"Lose Myself", (int) songFile1.length());
		Song song2 = new Song(songFile2.getAbsolutePath(), 201, 
				"Rift", (int) songFile2.length());
		
		VibeKey.manager.bufferSong(song1);
		VibeKey.manager.bufferSong(song2);
		
		while(!song1.finishedBuffering() && !song2.finishedBuffering()){
			synchronized(song1) {
				song1.wait();
			}
			
			synchronized(song2) {
				song2.wait();
			}
		}
		
		long size = (long) Math.ceil(3217030 / Song.BUFFER_SIZE);
		
		assertTrue("The estimated size of song 1 is not the actual size.\nActual Size: " 
				+ song1.getBuffer().size() + "\nEstimated Size: " + size, song1.getBuffer().size() == size);
		assertTrue("The estimated size of song 2 is not the actual size.\nActual Size: " 
				+ song2.getBuffer().size() + "\nEstimated Size: " + size, song2.getBuffer().size() == size);
	}

}
