$(document).ready(function(){

    $("#edit-passport-form-button").click(function(e) {
        $("#edit-passport-form").show();
        e.preventDefault();
    });

    $("#button-passport-edit-ok").click(function(e) {

        var urlPassportEdit = '/polyclinic/user/' + userId + '/passport';
        var passportInf = JSON.stringify({
                      idNumber: passportId,
                      firstName: $("#firstNameEdit").val(),
                      lastName: $("#lastNameEdit").val(),
                      fathersName: $("#fathersNameEdit").val(),
                      number: $("#numberEdit").val(),
                      dateOfBirth: $("#dateOfBirthEdit").val(),
                      dateOfIssue: $("#dateOfIssueEdit").val(),
                      authority: $("#authorityEdit").val()
                      });

        $.ajax({
          url: urlPassportEdit,
          type: 'PUT',
          contentType: 'application/json',
          cache: false,
          data: passportInf,
          success: function(data, textStatus, jqXHR) {
              if(jqXHR.status==200) {
                alert('Passport updated!');
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

    $("#edit-user-form-button").click(function(e) {
            $("#edit-user-form").show();

            e.preventDefault();
        });

        $("#button-user-edit-ok").click(function(e) {

            var urlUserEdit = '/polyclinic/user/' + userId;
            var userInf = JSON.stringify({
                          phoneNumber: $("#phoneNumberEdit").val(),
                          googleEmail: $("#googleEmailEdit").val(),
                          role: $("#role-select").val(),
                          status: $("#status-select").val()
                          });

            $.ajax({
              url: urlUserEdit,
              type: 'PUT',
              contentType: 'application/json',
              cache: false,
              data: userInf,
              success: function(data, textStatus, jqXHR) {
                  if(jqXHR.status==200) {
                    alert('User updated!');
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

        $("#delete-user-button").click(function(e) {

                    var isConfirmed = confirm('Are you sure?')
                    var urlDelete = '/polyclinic/user/' + userId;

                    if (isConfirmed) {
                    $.ajax({
                      url: urlDelete,
                      type: 'DELETE',
                      contentType: 'application/json',
                      cache: false,
                      data: "",
                      success: function(data, textStatus, jqXHR) {
                          if(jqXHR.status==200) {
                            alert('User deleted!');
                            $(location).attr('href','/polyclinic/profile');
                          }
                      },
                      error: function(jqXHR, textStatus, errorThrown) {
                          if(jqXHR.status==400) {
                            alert('Error 400!');
                          }
                          if(jqXHR.status==500) {
                            alert('Error 500!');
                          }
                      }
                  });
                };
                });


        $("#add-address-button").click(function(e) {
                $("#add-address-form").show();
                e.preventDefault();
            });

        $("#address-add-button-ok").click(function(e) {

            var urlAddressAdd = '/polyclinic/user/' + userId + '/address';
            var addressData = JSON.stringify({
              city: $("#addCity").val(),
              street: $("#addStreet").val(),
              house: $("#addHouse").val(),
              flat: $("#addFlat").val()
              });

            $.ajax({
              url: urlAddressAdd,
              type: 'PUT',
              contentType: 'application/json',
              cache: false,
              data: addressData,
              success: function(data, textStatus, jqXHR) {
                    window.location.reload();
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

function deleteAddress(addressId) {
        var isConfirmed = confirm('Are you sure?')
        var urlAddressDelete = '/polyclinic/user/' + userId + '/address/' + addressId;

        if (isConfirmed) {
        $.ajax({
              url: urlAddressDelete,
              type: 'DELETE',
              contentType: 'application/json',
              cache: false,
              data: "",
              success: function(data, textStatus, jqXHR) {
                  window.location.reload();
              },
              error: function(jqXHR, textStatus, errorThrown) {
                  if(jqXHR.status==400) {
                      alert('Error 400!');
                  }
                  if(jqXHR.status==500) {
                      alert('Error 500!');
                  }
              }
          });
          };

    };