package web.channel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.Response;
import web.Response.FailResponse;

/**
 * Servlet implementation class Channel Servlet
 * 
 * @author Benedict Wong
 */

@WebServlet("/api/channel")
public class ChannelServlet extends HttpServlet {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 7525261946708300303L;
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    
        response.setContentType("text/plain");
        String method = request.getParameter("method") != null ? request
                .getParameter("method") : "null";
        
        Response responseObject;
        
        switch (method) {
            case "getChannelList":
                responseObject = ChannelRequestHandler.handleGetChannelList();
                break;
            case "getChannelStatus":
                responseObject = ChannelRequestHandler.handleGetChannelStatus(request);
                break;
            // case "getUniqueID":
            // response.getWriter().print(RequestHandler.handleGetUniqueIDRequest(request));
            // break;
            default:
                responseObject = new FailResponse("Invalid method provided for channel API call - GET: " + method);
                break;
        }
        response.getWriter().print(responseObject.toJson());
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    
        response.setContentType("text/plain");
        
        String method = request.getParameter("method") != null ? request
                .getParameter("method") : "null";
        
        Response responseObject;
        
        switch (method) {
        // case "getUniqueID":
        // response.getWriter().print(RequestHandler.handleGetUniqueIDRequest(request));
        // break;
            default:
                responseObject = new FailResponse("Invalid method provided for channel API call - GET: " + method);
                break;
        }
        response.getWriter().print(responseObject.toJson());
    }
}