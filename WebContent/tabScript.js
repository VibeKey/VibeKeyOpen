$(document).ready(function() {
		$('#search').sidr({
		name: 'searchFunc',
		source:'#searchTab',
		side: 'left' 
		});
		$('#news').sidr({
		name: 'newsFunc',
		source:'#newsTab',
		side: 'left' 
		});
	});