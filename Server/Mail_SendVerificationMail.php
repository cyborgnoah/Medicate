<?php
/*class Mail_SendVerificationMail
{
  public function mail($username,$email)
  {*/
    require_once 'db_config.php';
    $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error($con));
    $hash = md5(rand(0,1000));
    $username=$_GET['username'];
    $email=$_GET['email'];
    $sql = "UPDATE userinfo set hash='$hash' where email='$email'";
    if(mysqli_query($con,$sql))
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
      http://104.131.88.175/Server/Mail_ValidateURL.php?email='.$email.'&hash='.$hash;
      mail($to, $subject, $message, $headers);
      echo "True";
    }
    else
    {
      echo "False";
    }
  /*}
}*/
?>
