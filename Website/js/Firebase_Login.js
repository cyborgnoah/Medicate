/*var dbRef=firebase.database().ref().child("Hello");
dbRef.set({name:"name"});*/					//Testing Purpose
var database = firebase.database().ref('/Hospital_Data/');
function login()
{
  if(validate_login())
  {
  var hospital_email=document.getElementById('login_email').value;
  var password=document.getElementById('login_password').value;
  const promise=firebase.auth().signInWithEmailAndPassword(hospital_email, password);
  promise.catch(e => console.log(e.message));
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
