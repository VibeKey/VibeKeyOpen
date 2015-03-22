<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="web.Tab" %>
<%@ page errorPage="errorpage.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='mainstyle.css'/>
<script type='text/javascript' src='tabs.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Welcome to WMHD</title>
</head>
<%
//TODO: Import tab list
	Tab[] tablist = {new Tab("search", "Search", "audiencechat.jsp"), new Tab("news", "News", "audiencechat.jsp"), new Tab("soundboard", "Soundboard", "audiencechat.jsp"), new Tab("calendar", "Calendar", "audiencechat.jsp"), 
		new Tab("djchat", "DJ Chat", "audiencechat.jsp"), new Tab("audiencechat", "Audience", "audiencechat.jsp"), new Tab("settings", "Settings", "audiencechat.jsp")};
%>
<body>
<div id = "header"></div>
		<div id = "page-frame">
			<div id = "tab-container">
				<iframe id = "tab-content" name = "tab-content">
				
				</iframe>
				<span id = "tab-selector">
					<%
					for(int i = 0; i < tablist.length; i++) {
					%>
						<a href = '<%= tablist[i].getContentUrl() %>' target = "tab-content">
							<div class = "single-tab" value = '<%= tablist[i].getId() %>'>
								<%= tablist[i].getDisplayText() %>
							</div>
						</a>
					<%
					}
					
				%>
				</span>
			</div>
			<div id = "page-content" ></div>
		</div>
</body>
</html>