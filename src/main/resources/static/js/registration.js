function sendUser() {

jQuery(function ($) {

    var userInf = JSON.stringify({
                  idNumber: $("#idNumber").val(),
                  firstName: $("#firstName").val(),
                  lastName: $("#lastName").val(),
                  fathersName: $("#fathersName").val(),
                  number: $("#number").val(),
                  dateOfBirth: $("#dateOfBirth").val(),
                  gender: $("#gender-select").val(),
                  dateOfIssue: $("#dateOfIssue").val(),
                  authority: $("#authority").val(),
                  user: {
                        phoneNumber: $("#phoneNumber").val(),
                        password: $("#password").val(),
                        addresses: [{
                                  city: $("#city").val(),
                                  street: $("#street").val(),
                                  house: $("#house").val(),
                                  flat: $("#flat").val(),
                                  }]
                        }
                  });

    $.ajax({
      url: '/polyclinic/user',
      type: 'POST',
      contentType: 'application/json',
      cache: false,
      data: userInf,
      success: function(data, textStatus, jqXHR) {
          if(jqXHR.status==201) {
            alert('User saved!');
          }
      },
      error: function(jqXHR, textStatus, errorThrown) {
          if(jqXHR.status==400) {
            alert('Not all fields are filled!' + "\n error: " + errorThrown);
          }
          if(jqXHR.status==500) {
            alert("Error!" + "\n error: " + errorThrown);
          }
      }
  });
});
}
