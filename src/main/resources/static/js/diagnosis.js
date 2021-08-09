$(document).ready(function(){

        $("#button-add-diagnosis").click(function(e) {
            $("#diagnosis-form").show();

            e.preventDefault();
        });

        $("#button-new-diagnosis").click(function(e) {

            var diagnosis = JSON.stringify({
                             name: $("#diagnosis-name").val(),
                             description: $("#diagnosis-description").val(),
                             doctor: { id: doctorId },
                             medicalCard: { id: $("#medicalCard.getId").val() }
                             });
            $.ajax({
                  url: '/polyclinic/user/' + patientId + '/diagnosis',
                  type: 'POST',
                  contentType: 'application/json',
                  cache: false,
                  data: diagnosis,
                  success: function(data, textStatus, jqXHR) {
                      if(jqXHR.status==201) {
                        alert('Ok! Diagnosis saved!');
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

