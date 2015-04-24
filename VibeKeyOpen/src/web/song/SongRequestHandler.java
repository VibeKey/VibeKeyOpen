package web.song;

import javax.servlet.http.HttpServletRequest;

import primary_manager.VibeKey;
import web.Response;
import web.Response.FailResponse;
import web.Response.SuccessResponse;
import channel.Channel;

/**
 * Helper for SongServlet - RequestHandler
 * 
 * @author Benedict Wong
 */

public class SongRequestHandler {
    
    public static Response handleGetSongDetails(HttpServletRequest request) {
    
        if (request.getHeader("channel") == null) {
            return new FailResponse("Invalid key");
        }
        
        Channel channel = VibeKey.manager.getChannels().get(Integer.valueOf(request.getHeader("channel")));
        Response result = new SuccessResponse();
        result.addToReturnData("currentSong", channel.getPlayerRunnable().getCurrentSong());
        return result;
    }
    
    public static Response handleGetSongList(HttpServletRequest request) {
    
        if (request.getHeader("channel") == null) {
            return new FailResponse("Invalid key");
        }
        
        Channel channel = VibeKey.manager.getChannels().get(Integer.valueOf(request.getHeader("channel")));
        Response result = new SuccessResponse();
        result.addToReturnData("songList", channel.getDJBot().getSongList());
        
        return result;
    }
    
}
