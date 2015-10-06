package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.ChatRoom;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/chatservlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//The chat room this servlet is posting to
	private ChatRoom chatRoom;

    /**
     * Default constructor. 
     */
    public ChatServlet() {
        chatRoom = new ChatRoom(5);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// By default, handle as a post request
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Retrieve the message that has been posted to the chat box
		String messagetopost = request.getParameter("messagetext");
		String user = request.getParameter("username");
		if(messagetopost != null && !messagetopost.equals("")) {
			messagetopost = messagetopost.trim();
			chatRoom.addPost(user, messagetopost);		
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/chattab.jsp");
		request.setAttribute("activeChat", chatRoom);
		dispatcher.forward(request, response);
	}

}
