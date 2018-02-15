firebase.auth().onAuthStateChanged(firebaseUser =>{
  if(firebaseUser)
  {    
  }
  else {
    window.location = 'index.html';
  }
});
