$(document).ready(function() {
		$('.tabLinkContainer').hover(function(){
			$(this).addClass('tabLinkContainerHover');
		}, function() {
			$(this).removeClass('tabLinkContainerHover');
		});
	
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
	});