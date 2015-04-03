package runnables;

import java.io.IOException;
import java.util.ListIterator;

import channel.Channel;
import core_objects_abstract.Song;

/**
 * Runnable that handles all song information and sends to icecast
 * 
 * @author Benedict Wong
 * Created: 12/03/2015, Last modified: 4/3/2015
 */
public class PlayerRunnable implements Runnable {

	Channel channel;
	private byte[] curr;

	Song currentSong;

	public final PlayerControl control = new PlayerControl();

	public PlayerRunnable(Channel channel) throws InterruptedException {
		this.channel = channel;
		this.curr = null;
		currentSong = channel.getDJBot().getNextSong();
		
		// //make sure the first song is finished buffering before starting to
		// play
		// while (!currentSong.finishedBuffering()) {
		// synchronized (currentSong) {
		// currentSong.wait();
		// }
		// }
	}

	public void run() {
		try {
			// loop until the end of time
			while (true) {
				synchronized (currentSong) {
					// if not at end of song
					if (!currentSong.atEndOfSong()) {
						// if there is data in the song buffer
						if (currentSong.hasNextBufferItem()) {

							// Get byte array to send to icecast
							curr = currentSong.getNextBufferItem();
							
							// send to icecast
							channel.getIcecast().send(curr, Song.BUFFER_SIZE);
						}
						// else wait for next buffer item to become available
						else {
							currentSong.wait();
						}
					}
					// if at end of song, get next song
					else {
						currentSong = channel.getDJBot().getNextSong();
					}
				}
				control.pauseCheck();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	// public void pause() {
	// play = false;
	// }
	//
	// public void play() {
	// play = true;
	// this.run(); // This is bad...
	// }
}
