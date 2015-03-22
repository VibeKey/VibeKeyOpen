<%@page import="web.ChatRoom"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ChatRoom activeRoom = (ChatRoom) request.getAttribute("activeChat");
	session.setAttribute("activeChat", activeRoom);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chat</title>

</head>
<body>
	<iframe src = "chatbox.jsp">
	</iframe>
	<form name = "sendpost" action = "<%=request.getContextPath()%>/chatservlet" method = "post">
		<input type = "text" name = "messagetext">
		<input type = "hidden" name = "username" value = "<%= session.getAttribute("username") %>">
		<input type = "submit" value = "Send">
	</form>
</body>
<script language="JavaScript" type="text/javascript">
	window.onload = function() { // Should automatically focus on the text post box
		document.sendpost.messagetext.focus();
	}
</script>
</html>