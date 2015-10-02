<%@page import="web.TextPost"%>
<%@page import="web.ChatRoom"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ChatRoom activeRoom = (ChatRoom) session.getAttribute("activeChat");
	Iterable<TextPost> posts = null;
	
	if(activeRoom != null) {
		posts = activeRoom.getPosts();	
	}
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Audience Chat</title>
</head>
<body onLoad="window.location.hash='#current'">
	<%
	if(posts != null) {
		for(TextPost post : posts) {
			out.print("<p>" + post.getPosterName() + ": " + post.getPostContent() + "\n" + "</p>");
		}
	}
		
	%>
	
</body>
<script language="JavaScript" type="text/javascript">

	var refreshInterval = 2000; // reload every 2 seconds
	
	setInterval(refreshChat, refreshInterval); // Sets the chat to continually refresh itself
	
	// reload this page
	function refreshChat() {
		window.location.reload();
	}
</script>
</html>