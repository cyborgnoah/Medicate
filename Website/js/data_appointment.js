
var database = firebase.database().ref('/Hosital_Data/');
database.once('value', function(snapshot)
{
  alert("Test2");
});
