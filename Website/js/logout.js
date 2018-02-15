function logout()
{
  firebase.auth().signOut().then(function()
  {
  console.log('Signed Out');
  window.location='index.html';
  }, function(error)
  {
  console.error('Sign Out Error', error);
  });
}
