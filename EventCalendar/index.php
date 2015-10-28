<!DOCTYPE html>
<html>
<head>
    <title>HTML5 Event Calendar</title>
	<!-- demo stylesheet -->
    	<link type="text/css" rel="stylesheet" href="media/layout.css" />    
        <link type="text/css" rel="stylesheet" href="themes/calendar_traditional.css" />      

	<!-- helper libraries -->
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	
	<!-- daypilot libraries -->
        <script src="js/daypilot/daypilot-all.min.js" type="text/javascript"></script>
	
</head>
<body>
        <div class="main">
            
            <div style="float:left; width: 160px;">
                <div id="nav"></div>
            </div>
            <div style="margin-left: 160px;">
                <div id="dp"></div>
            </div>
            
<!--            
		<form method="POST" action="publish.php">
            	<div id="dialog">
  					Username: <input type="text" name="username" /><br />
  					Description: <input type="text" name="description" />
  					<input name="Submit"  type="submit" value="Update" />
				</div>
			</form>
-->

            <script type="text/javascript">
            
        		var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
        		var controls = fireRef.child("controls");
/*            
            	var username;
            
            	$(document).ready(function(){
            		$("#dialog").dialog({
    					autoOpen: false,
    					position: 'center',
    					buttons: {
            				"Ok": function() {
               					username =  $('input[name=username]').val();
								var description =  $('input[name=description]').val();
                				$(this).dialog("close");
            			}}
            		});
            	});
*/                
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
                dp.onTimeRangeSelected = function (args) {
                    var text = prompt("New event name (; description):", "Event ; Description");
                    var event = text.split(/;(.+)?/)[0];
                    var description = text.split(/;(.+)?/)[1];
                    //$("#dialog").dialog('open');
                    
                    dp.clearSelection();
                    if (!event) return;
                    var e = new DayPilot.Event({
                        start: args.start,
                        end: args.end,
                        id: DayPilot.guid(),
                        resource: args.resource,
                        text: event + "\n" + description
                    });
                    dp.events.add(e);

                    $.post("backend_create.php", 
                            {
                                start: args.start.toString(),
                                end: args.end.toString(),
                                name: username
                            }, 
                            function() {
                                console.log("Created.");
                            });

                };

                dp.onEventClick = function(args) {
                    alert("clicked: " + args.e.id());
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
                }

            </script>
            
            <script type="text/javascript">
            $(document).ready(function() {
                $("#theme").change(function(e) {
                    dp.theme = this.value;
                    dp.update();
                });
            });  
            </script>
        </div>
        
        <br />
        
        <div class="clear">
        	Show schedules from: 
            <input type="radio" name="size" value="all" />All
            <input type="radio" name="size" value="me" checked />Me
            <input type="radio" name="size" value="dj" checked />DJs
            <input type="radio" name="size" value="manager" />Manager
        </div>
        <div class="clear">

        	<p> Show the schedule of 
        		<select id="type" style= "display:inline">
					<option value="all">All Kinds of</option>
				</select>
			Music</p>
</body>
</html>

