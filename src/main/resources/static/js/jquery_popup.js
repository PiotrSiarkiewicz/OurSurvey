$(document).ready(function() {
$("#onclick").click(function() {
    $("#contactdiv").css("display", "block");
});
$("#contact #cancel").click(function() {
    $(this).parent().parent().hide();
});
    $("#name").click(function() {
        $(this).parent().parent().hide();
    });
// Contact form popup send-button click event.
$("#create").click(function() {
    var name = $("#name").val();
    var desc = $("#desc").val();
if (name == "" || desc == ""){
alert("Please Fill All Fields");
}

});

});
