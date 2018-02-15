var database = firebase.database().ref().child("Hospital_Data");
/*var authorization=firebase.auth();*/
function signup()
{
  if(validater()==true)
  {
  var hospital_name=document.getElementById('Name');
  var hospital_email=document.getElementById('Email');
  var password=document.getElementById('password1');
  firebase.auth().createUserWithEmailAndPassword(email, password).catch(function(error)
  {
  // Handle Errors here.
  var errorCode = error.code;
  var errorMessage = error.message;
  // ...
});
  database.push().set({Hospital_Name: hospital_name.value,Hospital_Email: hospital_email.value});
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
