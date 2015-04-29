$(document).ready(function() {
		$('.tabLinkContainer').hover(function(){
			$(this).addClass('tabLinkContainerHover');
		}, function() {
			$(this).removeClass('tabLinkContainerHover');
		});
		//makes the search tab pull out
		$('#search').sidr({
			name: 'searchFunc',
			source: '#searchTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("searchSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("searchSpan").style.width = "";
			}
		});
		//makes the news tab pull out
		$('#news').sidr({
			name: 'newsFunc',
			source: '#newsTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("newsSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("newsSpan").style.width = "";
			}
		});
		//makes the soundboard tab pull out
		$('#soundboard').sidr({
			name: 'soundboardFunc',
			source: '#soundboardTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("soundboardSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("soundboardSpan").style.width = "";
			}
		});
		//makes the calendar tab pull out
		$('#calendar').sidr({
			name: 'calendarFunc',
			source: '#calendarTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("calendarSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("calendarSpan").style.width = "";
			}
		});
		//makes the djChat tab pull out
		$('#djChat').sidr({
			name: 'djChatFunc',
			source: '#djChatTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("djChatSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("djChatSpan").style.width = "";
			}
		});
		//makes the audience tab pull out
		$('#audience').sidr({
			name: 'audienceFunc',
			source: '#audienceTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("audienceSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("audienceSpan").style.width = "";
			}
		});
		//makes the settings tab pull out
		$('#settings').sidr({
			name: 'settingsFunc',
			source: '#settingsTab',
			renaming: false,
			onOpen: function(){
				document.getElementById("settingsSpan").style.width = "75%";
				
			},
			onClose: function(){
				document.getElementById("settingsSpan").style.width = "";
			}
		});
		
	});