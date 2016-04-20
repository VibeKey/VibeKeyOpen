jQuery(document).ready(function(){

	$('#contactform').submit(function(){

	var action = $(this).attr('action');

	$("#notification").slideUp(250,function() {
		$('#notification').hide();

		$('#submit')
		.attr('disabled','disabled');

		$.post(action, {
			name: $('#name').val(),
			email: $('#email').val(),
			message: $('#message').val()
		},

		function(data){
			document.getElementById('notification').innerHTML = data;
			$('#notification').fadeIn('fast');
			$('#submit').removeAttr('disabled');
				jQuery('#notification').has('.error_message').click(function() {
				jQuery(this).hide();
			});

			jQuery('#notification').has('#success_page').click(function() {
				jQuery(this).show();
			});

		});

	});

	return false;

	});

});