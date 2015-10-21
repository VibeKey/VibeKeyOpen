<!DOCTYPE html>
<html>
<head>
    <title>HTML5 Event Calendar</title>
	<!-- demo stylesheet -->
    	<link type="text/css" rel="stylesheet" href="media/layout.css" />    

        <link type="text/css" rel="stylesheet" href="themes/calendar_g.css" />    
        <link type="text/css" rel="stylesheet" href="themes/calendar_green.css" />    
        <link type="text/css" rel="stylesheet" href="themes/calendar_traditional.css" />    
        <link type="text/css" rel="stylesheet" href="themes/calendar_transparent.css" />    
        <link type="text/css" rel="stylesheet" href="themes/calendar_white.css" />    

	<!-- helper libraries -->
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	
	<!-- daypilot libraries -->
        <script src="js/daypilot/daypilot-all.min.js" type="text/javascript"></script>
	
</head>
<body>
        <div id="header">
			<div class="bg-help">
				<div class="inBox">
					<h1 id="logo"><a href='http://code.daypilot.org/17910/html5-event-calendar-open-source'>HTML5 Event Calendar</a></h1>
					<p id="claim"><a href="http://javascript.daypilot.org/">DayPilot for JavaScript</a> - AJAX Calendar/Scheduling Widgets for JavaScript/HTML5/jQuery</p>
					<hr class="hidden" />
				</div>
			</div>
        </div>
        <div class="shadow"></div>
        <div class="hideSkipLink">
        </div>
        <div class="main">
            
            <div style="float:left; width: 160px;">
                <div id="nav"></div>
            </div>
            <div style="margin-left: 160px;">
                <div id="dp"></div>
            </div>

            <script type="text/javascript">
                
                var nav = new DayPilot.Navigator("nav");
                nav.showMonths = 3;
                nav.skipMonths = 3;
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
                    var name = prompt("New event name:", "Event");
                    dp.clearSelection();
                    if (!name) return;
                    var e = new DayPilot.Event({
                        start: args.start,
                        end: args.end,
                        id: DayPilot.guid(),
                        resource: args.resource,
                        text: name
                    });
                    dp.events.add(e);

                    $.post("backend_create.php", 
                            {
                                start: args.start.toString(),
                                end: args.end.toString(),
                                name: name
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
        <div class="clear">
        </div>
        
</body>
</html>

