<?php
require_once 'db_config.php';
this->con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(echo mysqli_error($this->con));
$username=$_GET['username'];
$email=$_GET['email'];
$hash = md5(rand(0,1000));
$sql = "UPDATE userinfo set hash='$hash' where email='$email'";
echo "Hello";
if(mysqli_query($this->db->con,$sql))
{
  $to      = $email; // Send email to our user
  $subject = 'Verify your Account';
  $headers = 'Medicate a complete care' . "\r\n";
  $message = '

  Thanks for registering with MEDICATE.Your account has been made. You can login with the following credentials after you have activated your account by pressing the url below.

  ------------------------
  Username: '.$username.'
  ------------------------

  Please click this link or copy this link to your browser to activate your account:
  https://104.131.88.175/Server/validate.php?email='.$email.'&hash='.$hash;
  mail($to, $subject, $message, $headers);
  echo"Email Send";
}
else
{
  echo "Error: " . $sql . "<br>" . $this->con->error;
}
?>