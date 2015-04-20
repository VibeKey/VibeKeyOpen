package web.channel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		String responseString;

		switch (method) {
		case "getChannelList":
			responseString = ChannelRequestHandler.handleGetChannelList();
			break;
		case "getChannelStatus":
			responseString = ChannelRequestHandler.handleGetChannelStatus(request);
			break;
		// case "getUniqueID":
		// response.getWriter().print(RequestHandler.handleGetUniqueIDRequest(request));
		// break;
		default:
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