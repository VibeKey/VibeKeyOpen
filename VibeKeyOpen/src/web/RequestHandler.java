package web;

import javax.servlet.http.HttpServletRequest;

import primary_manager.VibeKey;
import channel.Channel;

public class RequestHandler {

	public static String handleGetSongDetails(HttpServletRequest request) {
		// TODO Auto-generated method stub
//		VibeKey.manager.
		return null;
	}
	
	public static String handleGetChannelList() {
		String result = "";
		for(Channel stream : VibeKey.manager.getChannelList()) {
			if (stream instanceof Channel) {
				result += ((Channel) stream).getName() + ",";
			}
		}
		return result.substring(0, result.length() - 2);
	}

}
