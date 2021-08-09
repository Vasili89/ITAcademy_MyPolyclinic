function showUser() {

    jQuery(function ($) {
        var phone = $("#phoneNumberForFindUser").val();
        $.getJSON("/polyclinic/user/phone/" + phone, function(data, textStatus, jqXHR) {
            var out = "<a href=/polyclinic/user/" + data.user.id + "> " + data.firstName + " " + data.lastName + " </a>";
            $("#findUser").html(out);
                }).fail(function(jqxhr, textStatus, error) {
                    alert( "Phone number is wrong.\nError detail:\nHTTP status " + jqxhr.status + "\n error: " + error);
                });
    });
}

