firebase.auth().onAuthStateChanged(firebaseUser =>{
  if(firebaseUser)
  {
    window.location = 'demo.html';
  }
  else {
    console.log("Not Logged In");
  }
});
