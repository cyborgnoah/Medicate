firebase.auth().onAuthStateChanged(firebaseUser =>{
  if(firebaseUser)
  {
    alert(firebaseUser.uid);

    window.location = 'demo.html';
  }
  else {
    //window.location = 'index.html';
  }
});
