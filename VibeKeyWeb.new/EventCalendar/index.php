﻿<!DOCTYPE html>
<html>
<head>
    <title>Schedule</title>
	<!-- demo stylesheet -->
    <link type="text/css" rel="stylesheet" href="media/layout.css" />    
    <link type="text/css" rel="stylesheet" href="themes/calendar_transparent.css" />      
	<link type="text/css" rel="stylesheet" href="popup.css"/>
	
	<!-- helper libraries -->
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="../js/createCommand.js" type="text/javascript"></script>
	
	<!-- daypilot libraries -->
    <script src="js/daypilot/daypilot-all.min.js" type="text/javascript"></script>	
    
    <script src="https://cdn.firebase.com/js/client/2.3.0/firebase.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>

	<script type="text/javascript" src="js/jquery.popup.min.js"></script>
	
	<script type="text/javascript" src="js/popup-data.js"></script>

	
	<script type="text/javascript">
			$(function() {
    			$('a[rel*=popup]').popup({ top : 200, closeButton: ".modal_close" });		
			});
		</script>
	
	<script type="text/javascript">
		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', 'UA-3318823-14']);
		  _gaq.push(['_trackPageview']);
		
		  (function() {
		    var ga = document.createElement('script');
		    ga.type = 'text/javascript'; ga.async = true;
		    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();
	</script>	

	
</head>

<body>
        <div class="main">
            
            <div style="float:left; width: 160px;">
                <div id="nav"></div>
            </div>
            <div style="margin-left: 160px;">
                <div id="dp"></div>
            </div>

            <script type="text/javascript">
                
                var nav = new DayPilot.Navigator("nav");
                nav.showMonths = 2;
                nav.skipMonths = 2;
                nav.selectMode = "week";
                nav.onTimeRangeSelected = function(args) {
                    dp.startDate = args.day;
                    dp.update();
                    loadEvents();
                };
                nav.init();
                
                var dp = new DayPilot.Calendar("dp");
                dp.viewType = "Week";

                dp.onEventMoved = function (args) {
                    $.post("backend_move.php", 
                            {
                                id: args.e.id(),
                                newStart: args.newStart.toString(),
                                newEnd: args.newEnd.toString()
                            }, 
                            function() {
                                console.log("Moved.");
                            });
                };

                dp.onEventResized = function (args) {
                    $.post("backend_resize.php", 
                            {
                                id: args.e.id(),
                                newStart: args.newStart.toString(),
                                newEnd: args.newEnd.toString()
                            }, 
                            function() {
                                console.log("Resized.");
                            });
                };

                // event creating
                var args_start;
                var args_end;
                var args_resource;
                
                dp.onTimeRangeSelected = function (args) {
                
                	args_start = args.start;
                	args_end = args.end;
                	args_resource = args.resource;
                
					$('#time').html("start from " + args.start + " to " + args.end);
					document.getElementById("go").click();    
                    //console.log("Done");
                    
                    var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	        		var controls = fireRef.child("prod").child("controls");
	        		//var schedule = fireRef.child("schedule");
	        		
// 	        		var playMode = 'No';
//   				var repeatMode = document.getElementsByClassName('repeatModeList')[1].value;
// 	        		var DJName = document.getElementsByClassName('djNameList')[1].value;
// 				var genre = document.getElementsByClassName('scheduleGenresList')[1].value;
// 					var DJName = "dj";
// 					var playlist = null;
					
             	 	//var command = createCommand(true, "addToSchedule", {"playMode":playMode, "repeatMode":sRepeat, "startTime":args.start, "endTime":args.end, "DJName":DJName, "genre":sType, "playlist":playlist});

//                     dp.clearSelection();
//                     if (!event) return;
//                     var e = new DayPilot.Event({
//                         start: args.start,
//                         end: args.end,
//                         id: DayPilot.guid(),
//                         resource: args.resource,
//                         text: sName + " (" + sDescrip + ")"
//                     });
//                     dp.events.add(e);
                    
                   

                    $.post("backend_create.php", 
                            {
                                start: args.start.toString(),
                                end: args.end.toString()//,
                                //name: username
                            }, 
                            function() {
                                console.log("Created.");
                            });

                };

                dp.onEventClick = function(args) {
                    //alert("clicked: " + args.e.id());
                    document.getElementById("go-details").click(); 
                };

                dp.init();

                loadEvents();

                function loadEvents() {
                    var start = dp.visibleStart();
                    var end = dp.visibleEnd();

                    $.post("backend_events.php", 
                    {
                        start: start.toString(),
                        end: end.toString()
                    }, 
                    function(data) {
                        //console.log(data);
                        dp.events.list = data;
                        dp.update();
                    });
                    
                    var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
                    
                    fireRef.on("value", function(snapshot) {
 					 	console.log(snapshot.val());
					}, function (errorObject) {
  						console.log("The read failed: " + errorObject.code);
					});
                }

            </script>
            
            <script type="text/javascript">
   				$(function () {  
					$("#create-Schedule").click(
					function() {	
						var sName = document.getElementById("sName").value;
						var sDescrip = document.getElementById("sDescrip").value;
						var sPlaylist = $('input[name="sPlaylist"]:checked').val();
						
						var playlistSource;
						if (sPlaylist) {
							playlistSource = "TEST";
						} else {
							playlistSource = "no";
						}
						
						var sRepeat = $('input[name="sRepeat"]:checked').val();
						var sType = document.getElementById("sType").value;
		
						console.log(sName + "-" + sDescrip + "-" + sRepeat + "-" + sType);
						
						if (!event) return;
                    	var e = new DayPilot.Event({
                        	start: args_start,
                        	end: args_end,
                       	 	id: DayPilot.guid(),
                        	resource: args_resource,
                        	text: sName + " (" + sDescrip + ")"
                    	});
                    	dp.events.add(e);
                    
                    	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	        			var controls = fireRef.child("prod").child("controls");
	        			//var schedule = fireRef.child("schedule");
	        			var DJName = "testUser";
	        			var playMode = "testplayMode";
                   		var command = createCommand(false, "addToSchedule", {"playMode":playMode, "repeatMode":sRepeat, "startTime":args_start, "endTime":args_end, "DJName":DJName, "genre":sType, "playlist":playlistSource});
  						controls.set(command);

                    	$.post("backend_create.php", 
                            {
                                start: args_start.toString(),
                                end: args_end.toString(),
                                name: sName
                            }, 
                            function() {
                                console.log("Created.");
                            });
					});
 				});
            </script>
            
            <script type="text/javascript">
            	$(document).ready(function() {
                	$("#theme").change(function(e) {
                    	dp.theme = this.value;
                    	dp.update();
                	});
            	});  
            </script>
        
       	 	<br />
        
        	<div class="clear">
        		Show schedules from: 
            	<input type="radio" name="size" value="all" checked/>All
            	<input type="radio" name="size" value="me" />Me
            	<input type="radio" name="size" value="dj" />DJs
            	<input type="radio" name="size" value="manager" />Manager
        	</div>
        	
       		<div class="clear">
        		<p> Show the schedule of 
        			<select class="genresList" id="genresList" style= "display:inline">
						<option value="all">all kinds of</option>
					</select>
				Music</p>
			</div>
		
			<script type="text/javascript">
				var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
				var genreListRef = fireRef.child("prod").child("songs").child("genreList");
			
				//populate genre drop down in schedule genre
				genreListRef.on("value", function(snapshot) {
  					//var genresDropdown = document.getElementById("genresList");
  					snapshot.forEach(function(data) {
    					var genre = data.val();
    					var option = document.createElement("option");
    					option.text = option.value = genre;
    					// genresDropdown.appendChild(option);
    					$('.genresList').append(option);
    					$('#sType').append(option);
  					});
				});
			</script>
		</div>
		
		<div>
			<a id="go" rel="popup" name="signup" href="#signup" hidden>CREAT!</a>
		</div>
		
		<div id="signup">
			<div id="signup-ct">
				<div id="signup-header">
					<h2>Create a new event</h2>
					<p id="time"></p>
					<a class="modal_close" href="#"></a>
				</div>
				
				<form action="">
     
     				<!-- Event Name is not in ScheduleItem now -->
				  	<div class="txt-fld">
				    	<label for="">Event Name</label>
				    	<input id="sName" class="good_input" name="" type="text" />
				  	</div>
				  
				    <!-- Description is not in ScheduleItem now -->
				  	<div class="txt-fld">
				    	<label for="">Description</label>
				    	<input id="sDescrip" name="" type="text" />
				  	</div>
				  
				  	<div class="other-fld">
				  		<label for="">Use Playlist</label>
				  		<input type="radio" name="sPlaylist" value=false onclick="" checked/> No
						<input type="radio" name="sPlaylist" value=true onclick=""/> Yes
			 		</div>
				  
				  	<div class="other-fld">
				  		<label for="">Repeat Mode</label>
				  		<input type="radio" name="sRepeat" value="only_once" onclick="" checked/> Only once
						<input type="radio" name="sRepeat" value="every_week" onclick=""/> Every Week
			 		</div>
			 		
			 		<div class="other-fld">
			 			<label for="">Music Type </label>
			 			<select id="sType">
		  					<option value="none">None</option>
		  				</select>
		  			</div>
				  
				  	<div class="btn-fld">
				  		<button type="submit" id="create-Schedule">Create it! &raquo;</button>
					</div>
					
				</form>
			</div>
		</div>
		
		
		<div>
			<a id="go-details" rel="popup" name="detailInfo" href="#detailInfo" hidden>detailInfo!</a>
		</div>
		
		<div id="detailInfo">
			<div id="signup-ct">
				<div id="signup-header">
					<h2>View this event</h2>
					<p id="time"></p>
					<a class="modal_close" href="#"></a>
				</div>
				
				<form action="">
     
     				<!-- Event Name is not in ScheduleItem now -->
				  	<div class="txt-fld">
				    	<label for="">Event Name</label>
				    	<input id="sName" class="good_input" name="" type="text" />
				  	</div>
				  
				    <!-- Description is not in ScheduleItem now -->
				  	<div class="txt-fld">
				    	<label for="">Description</label>
				    	<input id="sDescrip" name="" type="text" />
				  	</div>
				  
				  	<div class="other-fld">
				  		<label for="">Use Playlist</label>
				  		<input type="radio" name="sPlaylist" value=false onclick=""/> No
						<input type="radio" name="sPlaylist" value=true onclick=""/> Yes
			 		</div>
				  
				  	<div class="other-fld">
				  		<label for="">Repeat Mode</label>
				  		<input type="radio" name="sRepeat" value="only_once" onclick=""/> Only once
						<input type="radio" name="sRepeat" value="every_week" onclick=""/> Every Week
			 		</div>
			 		
			 		<div class="other-fld">
			 			<label for="">Music Type </label>
			 			<select id="sType">
		  					<option value="none">None</option>
		  				</select>
		  			</div>
				  
				  	<div class="btn-fld">
				  		<button type="detele" id="delete-Schedule">Delete &raquo;</button>
				  		<button type="edit" id="edit-Schedule">Edit &raquo;</button>
					</div>
					
				</form>
			</div>
		</div>
		
		
		
</body>
</html>

