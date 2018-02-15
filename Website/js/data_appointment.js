firebase.auth().onAuthStateChanged(firebaseUser =>{
  if(firebaseUser)
  {
    var database = firebase.database("https://medicate-c8086-ee60d.firebaseio.com").ref().child("Appointment Requests").child(firebaseUser.uid);
    database.once('value', function(snapshot)
    {
      snapshot.forEach(function(childSnapshot) {
    var uid = childSnapshot.key;
    var childData = childSnapshot.val();
    var name=childSnapshot.val().Fullname;
    var gender=childSnapshot.val().Gender;
    var age=childSnapshot.val().Age;
    var date=childSnapshot.val().Date;
    var time=childSnapshot.val().Time;
    var userid=childSnapshot.val().UserId;
    var tid=childSnapshot.val().Tid;

      $("#parent").append("<div class='custom_main'><div class='custom_left'><ul><li><b>Name :  </b>"+name+"</li><li><b>Gender : </b>"+gender+"</li><li><b>Age : </b>"+age+"</li></ul></div><div class='custom_mid'><ul><li><b>Date : </b>"+date+"</li><li><b>Time : </b>"+time+"</li><li><b>UID : </b>"+uid+"</li></ul> </div> <div class='custom_right'><button class='custom_button_accept' onClick=button_accept('"+uid+"','"+userid+"','"+tid+"')>Accept</button><button class='custom_button_decline' onClick=button_decline('"+uid+"','"+userid+"','"+tid+"')>Decline</button></div>");

  });

    });
  }
  else {
    window.location = 'index.html';
  }
});
