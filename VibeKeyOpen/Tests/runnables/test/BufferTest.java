package runnables.test;

import static org.junit.Assert.*;

import org.junit.Test;

import primary_manager.PrimaryManager;
import core_objects_abstract.Song;

public class BufferTest {

	@Test
	public void test() {
		Song song = new Song("C:\\Users\\reatherl\\git\\VibeKeyOpen\\VibeKeyOpen\\VibeKeyOpen\\Songs", 201, 
				"Lose Myself", 3219456);
		
		PrimaryManager manager = new PrimaryManager();
		manager.bufferSong(song);
	}

}
