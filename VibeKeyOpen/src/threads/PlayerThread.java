package threads;

import java.io.IOException;
import java.util.Iterator;

import channel.Channel;
import core_objects_abstract.Song;

public class PlayerThread extends Thread {
	
	Channel channel;
//	byte[] buffer = new byte[1024];
//	private List<byte[]> buffer;
//	private boolean play = true;
	private Iterator<byte[]> bufferIter;
	private byte[] cur;
	
	public final PlayerControl control = new PlayerControl();
	
	public PlayerThread(Channel channel) {
		this.channel = channel;
//		this.buffer = channel.getNextSongBuffer();
		this.bufferIter = channel.getNextSongBuffer().iterator();
		this.cur = null;
	}
	
	public void run() {
		try {
//			Song song = channel.getDJBot().getSong();
//			buffer = song.getBuffer();
		
//			Iterator<byte[]> it = buffer.iterator();
//			byte[] cur = null;
			
			while (true) {
//				if (play) { // If currently playing
				if (bufferIter.hasNext()) { // If there's more song data to play
					cur = bufferIter.next();
					if (cur != null) { // and it's not null (i.e. the end of the song)
						channel.getIcecast().send(cur, Song.BUFFER_SIZE); // Play it!
					} else {
						// End of stream, get next song buffer (iterator)
//							break;
//							this.buffer = channel.getNextSongBuffer();
						this.bufferIter = channel.getNextSongBuffer().iterator();
					}
				} else {
					// Not done buffering, just wait
					sleep(10);
				}
//				} else { // Else just wait until we actually can play something
//					sleep(10);
//					break;
//				}
				
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
	
//	public void pause() {
//		play = false;
//	}
//	
//	public void play() {
//		play = true;
//		this.run(); // This is bad...
//	}
}
