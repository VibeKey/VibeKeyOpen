package web.song;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.Response;
import web.Response.FailResponse;

/**
 * Servlet implementation class SongServlet
 * 
 * @author Benedict Wong
 */

@WebServlet("/api/song")
public class SongServlet extends HttpServlet {
    
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
            case "getSongDetails":
                responseObject = SongRequestHandler.handleGetSongDetails(request);
                break;
            case "getSongList":
                responseObject = SongRequestHandler.handleGetSongList(request);
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