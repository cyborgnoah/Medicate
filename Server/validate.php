<?php
error_reporting(E_ALL ^ E_DEPRECATED);
require_once 'DB_Connect.php';
$this->db = new DB_Connect();
$this->db->connect();
$email=$_GET['email'];
$hash=$_GET['hash'];
$sql = "SELECT email, hash FROM userinfo WHERE email='".$email."' AND hash='".$hash."' AND active='0'";
$result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
if ($result->num_rows == 1)
	{
		$sql="UPDATE userinfo SET active='1' WHERE email='".$email."' AND hash='".$hash."' AND active='0'");
		mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
	}
	else
	{

	}
?>
