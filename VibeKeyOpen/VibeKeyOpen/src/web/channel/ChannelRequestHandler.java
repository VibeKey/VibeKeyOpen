package web.channel;

import javax.servlet.http.HttpServletRequest;

import primary_manager.VibeKey;
import web.Response;
import web.Response.FailResponse;
import web.Response.SuccessResponse;
import channel.Channel;

/**
 * Helper for ChannelServlet - RequestHandler
 * 
 * @author Benedict Wong
 */

public class ChannelRequestHandler {
    
    public static Response handleGetChannelList() {
    
        Response result = new SuccessResponse();
        result.addToReturnData("channelList", VibeKey.manager.getChannelList());
        
        return result;
    }
    
    public static Response handleGetChannelStatus(HttpServletRequest request) {
    
        String channelId = request.getHeader("channelId");
        if (channelId == null) {
            return new FailResponse("Invalid channelId provided");
        }
        
        Channel channelInstance = VibeKey.manager.getChannel(Integer.valueOf(channelId));
        if (channelInstance == null) {
            return new FailResponse("Invalid channelId provided: " + Integer.valueOf(channelId));
        }
        
        Response result = new SuccessResponse();
        result.addToReturnData("name", channelInstance.getName());
        result.addToReturnData("channelState", channelInstance.getState().getClass().getName());
        
        // TODO Auto-generated method stub
        return result;
    }
}
