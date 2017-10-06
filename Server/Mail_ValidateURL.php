<?php
require_once 'db_config.php';
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error($con));
$email=$_GET['email'];
$hash=$_GET['hash'];
$sql = "SELECT email, hash FROM userinfo WHERE email='".$email."' AND hash='".$hash."' AND active='0'";
$result = mysqli_query($con,$sql) or die(mysqli_error($con));
if ($result->num_rows == 1)
	{
		$sql="UPDATE userinfo SET active='1' WHERE email='".$email."' AND hash='".$hash."' AND active='0'";
		mysqli_query($con,$sql) or die(mysqli_error($con));
	}
	else
	{
		echo "Error";
	}
?>
