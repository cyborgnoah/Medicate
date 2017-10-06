<?php
		require_once 'DB_Functions.php';
		$email=$_GET['email'];
		$hash=$_GET['hash'];
		$sql = "SELECT email, hash FROM userinfo WHERE email='".$email."' AND hash='".$hash."' AND active='0'";
		$result = $conn->query($sql);	
		if ($result->num_rows == 1)
		{
			$conn->query("UPDATE userinfo SET active='1' WHERE email='".$email."' AND hash='".$hash."' AND active='0'");
			
		}
		else
		{
					
		}
	
	?>
