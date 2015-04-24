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
    
        String section = request.getHeader("section");
        if (section == null) {
            return new FailResponse("Invalid section provided");
        }
        
        Channel channelInstance = VibeKey.manager.getChannels().get(section);
        Response result = new SuccessResponse();
        result.addToReturnData("name", channelInstance.getName());
        result.addToReturnData("channelState", channelInstance.getState().getClass().getName());
        
        // TODO Auto-generated method stub
        return null;
    }
}
