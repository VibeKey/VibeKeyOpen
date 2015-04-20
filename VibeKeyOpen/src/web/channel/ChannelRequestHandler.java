package web.channel;

import javax.servlet.http.HttpServletRequest;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import primary_manager.VibeKey;
import web.Response;
import web.Response.SuccessResponse;
import channel.Channel;

/**
 * Helper for ChannelServlet - RequestHandler
 * 
 * @author Benedict Wong
 */

public class ChannelRequestHandler {

	public static String handleGetChannelList() {
		
		Response result = new SuccessResponse();
		result.addToReturnData("channelList", VibeKey.manager.getChannelList());

return new Gson().toJson(result);
	}

	public static String handleGetChannelStatus(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
