$(document).ready(function () {
    $("#onclick").click(function () {
        debugger;
        $("#contactdiv").css("display", "block");
    });
    $("#cancel").click(function () {
        $(this).parent().parent().hide();
    });
// Contact form popup send-button click event.
    $("#create").click(function () {
        var name = $("#name").val();
        var desc = $("#desc").val();
        if (name == "" || desc == "") {
            alert("Please Fill All Fields");
        }

    });

});
