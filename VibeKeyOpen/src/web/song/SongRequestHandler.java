package web.song;

import javax.servlet.http.HttpServletRequest;

import channel.Channel;
import primary_manager.VibeKey;
import web.Response.*;

/**
 * Helper for SongServlet - RequestHandler
 * 
 * @author Benedict Wong
 */

public class SongRequestHandler {

	public static String handleGetSongDetails(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	public static String handleGetSongList(HttpServletRequest request) {

        if (request.getHeader("channel") == null) {
            return new FailResponse("Invalid key").toString();
        }
        
        Channel channel = VibeKey.manager.getChannels().get(Integer.valueOf(request.getHeader("channel")));
        
//        channel.getPlayerRunnable()
        
		return null;
	}

}
