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
var database = firebase.database().ref().child("Hospital_Data");
/*var authorization=firebase.auth();*/
function signup()
{
  if(validater()==true)
  {
  var hospital_name=document.getElementById('Name');
  var hospital_email=document.getElementById('Email');
  var password=document.getElementById('password1');
  /*authorization.createUserWithEmailAndPassword(hospital_email, password).catch(function(error) {
  // Handle Errors here.
  var errorCode = error.code;
  var errorMessage = error.message;
  alert(errorCode+"  "+errorMessage);
  // ...
});*/
  database.push().set({Hospital_Name: hospital_name.value,Hospital_Email: hospital_email.value,Hospital_Password: password.value});
  }
}
function validater()
{
  var hospital_name=document.getElementById('Name').value;
  var hospital_email=document.getElementById('Email').value;
  var password1=document.getElementById('password1').value;
  var password2=document.getElementById('password2').value;
  if(hospital_name=="")
  {return false;}
  if(hospital_email=="")
  {return false;}
  if(password1=="")
  {return false;}
  if(password2=="")
  {return false;}
  if(password1!=password2)
  {return false;}
  return true;
}
