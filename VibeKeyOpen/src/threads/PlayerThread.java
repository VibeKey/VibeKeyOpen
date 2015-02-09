package threads;

import java.io.IOException;

import channel.Channel;
import core_objects_abstract.Song;

public class PlayerThread extends Thread {
	
	Channel channel;
	byte[] buffer = new byte[1024];
	boolean play = true;
	
	public PlayerThread(Channel channel) {
		this.channel = channel;
	}
	
	public void run() {
		int read;
		try {
			Song song = channel.getDJBot().getSong();
			read = song.read(buffer);
		
			while (play && read > 0) {
			    channel.getIcecast().send(buffer, read);
			    read = song.read(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause() {
		play = false;
	}
	
	public void play() {
		play = true;
	}
}
