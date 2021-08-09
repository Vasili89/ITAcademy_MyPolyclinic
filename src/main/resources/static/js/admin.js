function showUser(field) {

    jQuery(function ($) {
        var phone = $("#phoneNumberForFindUser").val();
        $.getJSON("/polyclinic/user/phone/" + phone, function(data, textStatus, jqXHR) {
            var out = "<a href=/polyclinic/admin/user/" + data.user.id + "> " + data.firstName + " " +
                    data.lastName + " </a>";
            $("#findUser").html(out);
                }).fail(function(jqxhr, textStatus, error) {
                    alert( "User with this phone number not found.\nError detail:\nHTTP status " +
                    jqxhr.status + "\n error: " + error);
                });
    });
}


$(document).ready(function(){

    $("#edit-user-form-button").click(function(e) {
        $("#edit-user-form").show();
        e.preventDefault();
    });

    $("#edit-department-button").click(function(e) {
        $("#edit-department-form").show();
        e.preventDefault();
    });

    $("#department-button-ok").click(function(e) {

        var urlDepartmentEdit = '/polyclinic/user/' + userId + '/department';
        var depInf = JSON.stringify({
                      id: $("#department-select").val()
                      });

        $.ajax({
          url: urlDepartmentEdit,
          type: 'PUT',
          contentType: 'application/json',
          cache: false,
          data: depInf,
          success: function(data, textStatus, jqXHR) {
              if(jqXHR.status==200) {
                window.location.reload();
              }
          },
          error: function(jqXHR, textStatus, errorThrown) {
              if(jqXHR.status==400) {
                alert('Not all fields are filled!');
              }
              if(jqXHR.status==500) {
                alert('Server error! Please, try later!');
              }
          }
      });
    });

});

function showDoctors(department) {

    jQuery(function ($) {
        $.getJSON("/polyclinic/admin/department/" + department, function(data, textStatus, jqXHR) {
            var outDoctors = "<ul>\n";
            for (i = 0; i < data.length; i++) {
            outDoctors = outDoctors + "<li><a href=/polyclinic/admin/user/" + data[i].user.id + "> " + data[i].firstName + " " +
                                            data[i].lastName + " </a></li>\n";
            }
            outDoctors = outDoctors + "</ul>\n";
            $("#department-doctors-block").html(outDoctors);
                }).fail(function(jqxhr, textStatus, error) {
                    alert( "Error detail:\nHTTP status " +
                    jqxhr.status + "\n error: " + error);
                });
    });
}

function createDepartment() {
    var department = JSON.stringify({
         name: $("#add-new-department").val()
         });
    $.ajax({
          url: '/polyclinic/admin/department',
          type: 'POST',
          contentType: 'application/json',
          cache: false,
          data: department,
          success: function(data, textStatus, jqXHR) {
                alert('Ok! Department saved!');
                window.location.reload();
          },
          error: function(jqXHR, textStatus, errorThrown) {
              if(jqXHR.status==400) {
                alert('Error 400');
              }
              if(jqXHR.status==500) {
                alert('Error 500');
              }
          }
      });
  };

function getDepartments() {
    jQuery(function ($) {
        $.getJSON("/polyclinic/admin/department", function(data, textStatus, jqXHR) {
            var outDoctors = "<ul>\n";
            for (i = 0; i < data.length; i++) {
            outDoctors = outDoctors + "<li><a href=/polyclinic/admin/user/" + data[i].user.id + "> " + data[i].firstName + " " +
                                            data[i].lastName + " </a></li>\n";
            }
            outDoctors = outDoctors + "</ul>\n";
            $("#department-doctors-block").html(outDoctors);
                }).fail(function(jqxhr, textStatus, error) {
                    alert( "Error detail:\nHTTP status " +
                    jqxhr.status + "\n error: " + error);
                });
    });
}