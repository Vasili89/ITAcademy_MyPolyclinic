function sendUser() {

  $.ajax({
      url: '/polyclinic/user',
      dataType: 'json',
      type: 'POST',
      contentType: 'application/json',
      cache: false,
      data: JSON.stringify({
          idNumber: $("#idNumber").val(),
          firstName: $("#firstName").val(),
          lastName: $("#lastName").val(),
          fathersName: $("#fathersName").val(),
          number: $("#number").val(),
          dateOfBirth: $("#dateOfBirth").val(),
          gender: $("#gender").val(),
          dateOfIssue: $("#dateOfIssue").val(),
          authority: $("#authority").val(),
          user: {
              phoneNumber: $("#phoneNumber").val(),
              password: $("#password").val(),
              addresses: [
                        {
                        city: $("#city").val(),
                        street: $("#street").val(),
                        house: $("#house").val(),
                        flat: $("#flat").val(),
                        }
                    ]
              }
          }),
      success: function() {
          alert("User saved!");
      }
  })
}

function validate() {

         if( document.myForm.Name.value == "" ) {
            alert( "Please provide your name!" );
            document.myForm.Name.focus() ;
            return false;
         }
         if( document.myForm.EMail.value == "" ) {
            alert( "Please provide your Email!" );
            document.myForm.EMail.focus() ;
            return false;
         }
         if( document.myForm.Zip.value == "" || isNaN( document.myForm.Zip.value ) ||
            document.myForm.Zip.value.length != 5 ) {

            alert( "Please provide a zip in the format #####." );
            document.myForm.Zip.focus() ;
            return false;
         }
         if( document.myForm.Country.value == "-1" ) {
            alert( "Please provide your country!" );
            return false;
         }
         return( true );
      }