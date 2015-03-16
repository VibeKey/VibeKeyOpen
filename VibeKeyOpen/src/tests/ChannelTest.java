package tests;

import channel.Channel;
import djbot.DJBot;

public class ChannelTest {

	public static void main(String[] args) {
		Channel channel = new Channel("radio", new DJBot());
		
		channel.open();
		channel.play();
		
		
	}

}
