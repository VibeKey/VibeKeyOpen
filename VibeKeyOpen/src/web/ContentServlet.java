package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FileCounter
 */

@WebServlet("/Content")
public class ContentServlet extends HttpServlet {

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

		String responseString;

		System.out.println("GET Request");
		switch (method) {
		case "getSongDetails":
			System.out.println("Called getSongDetails");
			System.out.println(request.getParameterValues("arg1")[0]);
			responseString = RequestHandler.handleGetSongDetails(request);
			break;
		case "getChannelList":
			System.out.println("Called getChannelList");
			responseString = RequestHandler.handleGetChannelList();
		// case "getUniqueID":
		// response.getWriter().print(RequestHandler.handleGetUniqueIDRequest(request));
		// break;
		default:
			System.out.println("Tried to call " + method);
			responseString = "{\"timestamp\":"
					+ System.currentTimeMillis()
					+ ", \"success\":0, \"error\":\"Invalid GET method supplied: "
					+ method + "\"}";
			break;
		}
		response.getWriter().print(responseString);
		System.out.println(responseString);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		response.setContentType("text/plain");

		String method = request.getParameter("method") != null ? request
				.getParameter("method") : "null";

		String responseString;

		switch (method) {
		// case "getUniqueID":
		// response.getWriter().print(RequestHandler.handleGetUniqueIDRequest(request));
		// break;
		default:
			responseString = "{\"timestamp\":"
					+ System.currentTimeMillis()
					+ ", \"success\":0, \"error\":\"Invalid POST method supplied: "
					+ method + "\"}";
			break;
		}
		response.getWriter().print(responseString);
		System.out.println(responseString);
	}
}