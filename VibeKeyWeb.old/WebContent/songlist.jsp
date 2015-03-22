<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	//TODO: make "song"
	String[] list = {"Song A", "Song B", "Song C", "Song D"};

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Song List</title>
</head>
<body>
<%
	for(String song : list) {
		//TODO: render this in proper format
		out.print("<div class = 'songdiv'>" + song + "<div>");
	}
%>
</body>
<script language="JavaScript" type="text/javascript">
	
	//Adds the specified song to the list currently being edited
	function addToActiveList(song) {
		
	}
	
	// Hides the specified song from the list
	function hideSong(song) {
		
	}
	
	// Displays a dropdown to edit the selected song
	function editSong(song) {
		
	}
	
	// Displays a dropdown to show the information for the selected song
	function displaySongInfo(song) {
		
	}
	
</script>
</html>