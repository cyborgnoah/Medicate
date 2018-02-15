// Initialize Firebase after validation
var config = {
  apiKey: "AIzaSyB0BwBC6aiv2oB4d-esbkPiRxHvzumFYOY",
  authDomain: "medicate-c8086.firebaseapp.com",
  databaseURL: "https://medicate-c8086-ee60d.firebaseio.com",
  projectId: "medicate-c8086",
  storageBucket: "medicate-c8086.appspot.com",
  messagingSenderId: "71702109131"
};
firebase.initializeApp(config);
/*var dbRef=firebase.database().ref().child("Hello");
dbRef.set({name:"name"});*/					//Testing Purpose
var database = firebase.database().ref('/Hosital_Data/');
function login()
{
  if(validate_login())
  {

  /*var hospital_email=document.getElementById('login_email').value;
  var password=document.getElementById('login_password').value;*/
  database.on('value',function(snapshot)
  {
    snapshot.forEach(function(childSnapshot) {
    var childKey = childSnapshot.key;
    var childData = childSnapshot.val();
    alert(childKey+":"+childData);
  });
});
  }
}
function validate_login()
{

  var hospital_email=document.getElementById('login_email').value;
  var password=document.getElementById('login_password').value;
  if(hospital_email=="")
  {return false;}
  if(password=="")
  {return false;}
  return true;
}
