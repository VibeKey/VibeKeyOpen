// Plugin options and our code
$("#modal_trigger").leanModal({
        top: 100,
        overlay: 0.6,
        closeButton: ".modal_close"
});

$(function() {
        // Calling Register Form
        $("#register_form").click(function() {
                $(".user_login").hide();
                $(".user_register").show();
                $(".header_title").text('Register');
                return false;
        });

        // Going back to Login Form
        $(".back_btn").click(function() {
                $(".user_register").hide();
                $(".user_login").show();
                $(".header_title").text('Login');
                return false;
        });
});