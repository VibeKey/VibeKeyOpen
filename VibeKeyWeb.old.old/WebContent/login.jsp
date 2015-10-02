<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//TODO: make this work for real
	String requestedName = request.getParameter("username");
	if(requestedName != null && !requestedName.equals("")) {
		session.setAttribute("username", requestedName);	
	}

	/* String username = (String) session.getAttribute("username");
	if(username != null && !username.equals("")) {
		response.sendRedirect("chattab.jsp");		
	}
 */
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form name = "login" action = "login.jsp" method = "post">
		<label for="usernameinput">Username: </label>
		<input type = "text" name = "username" id = "usernameinput">
		<br>
		<label for="passwordinput">Password: </label>
		<input type = "password" name = "password" id = "passwordinput">
		<br>
		<input type = "submit" value = "Sign In">
	</form>
</body>
</html>