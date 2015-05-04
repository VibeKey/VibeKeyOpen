package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.Response.FailResponse;
import web.Response.SuccessResponse;

import com.gmail.kunicins.olegs.libshout.Libshout;

/**
 * Servlet implementation class Channel Servlet
 * 
 * @author Benedict Wong
 */

@WebServlet("/")
public class BaseServlet extends HttpServlet {
    
    public BaseServlet() throws ClassNotFoundException, SQLException, IOException, InterruptedException {
    
        super();
    }
    
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
            case "info":
                SuccessResponse resp = new SuccessResponse();
                
                String url = System.getProperty("user.dir") + "/lib/libshout-java.so";
                resp.addToReturnData("path", System.getProperty("user.dir") + "/lib/libshout-java.so");
                
                responseObject = resp;
                break;
            case "test":
                try {
                    new Libshout();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    responseObject = new FailResponse(e);
                }
                responseObject = new SuccessResponse();
                break;
            default:
                responseObject = new FailResponse("Invalid method provided for channel API call - GET: " + method);
                break;
        }
        response.getWriter().print(responseObject);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    
        response.setContentType("text/plain");
        
        String method = request.getParameter("method") != null ? request
                .getParameter("method") : "null";
        
        Response responseObject;
        
        switch (method) {
            default:
                responseObject = new FailResponse("Invalid method provided for channel API call - GET: " + method);
                break;
        }
        response.getWriter().print(responseObject);
    }
}