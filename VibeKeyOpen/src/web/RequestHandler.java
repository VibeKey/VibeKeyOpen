package web;

import javax.servlet.http.HttpServletRequest;

import primary_manager.VibeKey;
import channel.Channel;
import core_objects_abstract.Stream;

public class RequestHandler {

	public static String handleGetSongDetails(HttpServletRequest request) {
		// TODO Auto-generated method stub
//		VibeKey.manager.
		return null;
	}
	
	public static String handleGetChannelList() {
		String result = "";
		for(Stream stream : VibeKey.manager.getChannelList()) {
			if (stream instanceof Channel) {
				result += ((Channel) stream).getName() + ",";
			}
		}
		return result.substring(0, result.length() - 2);
	}

}
