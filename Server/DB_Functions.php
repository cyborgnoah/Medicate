<?php
error_reporting(E_ALL ^ E_DEPRECATED);
class DB_Functions
{
    private $db;
    // constructor
    function __construct()
    {
        require_once 'DB_Connect.php';
        $this->db = new DB_Connect();
        $this->db->connect();
    }
    // destructor
    function __destruct()
    {
        $this->db->close();
    }
    //Register User
    public function storeUser($name, $username , $email , $password)
    {
        $sql = "SELECT Username FROM userinfo WHERE Username = '$username'";
        $result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0)
        {
            return "Duplicate Username";
        }
        $sql = "SELECT Email FROM userinfo WHERE Email = '$email'";
        $result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0)
        {
            return "Duplicate Email";
        }
        $sql = "INSERT INTO userinfo(Name,Username,Email, Password) VALUES('$name', '$username' , '$email' , '$password')";
        $result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
        if ($result)
        {
            return "true";
        }
        else
        {
            return false;
        }
    }
    //Log In Check
    public function getUserByUserAndPassword($user, $password)
    {
        $sql = "SELECT * FROM userinfo WHERE Username = '$user' and Password = '$password'";
        $result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0)
        {
            $result = mysqli_fetch_array($result);
            return $result;
        }
        else
        {
            return false;
        }
    }
	//personal information
  //hello
	public function personal_info($Firstname , $Lastname,$Dateofbirth,$Gender,$Mobile)
	{
		$sql = "INSERT INTO form_personal(Firstname,Lastname,Dateofbirth,Gender,Mobile) VALUES('$Firstname', '$Lastname' , '$Dateofbirth' , '$Gender','$Mobile')";
        $result = mysqli_query($this->db->con,$sql) or die(mysqli_error($this->db->con));
        if ($result)
        {
            return "true";
        }
        else
        {
            return false;
        }
	}

	 public function verifymail($username , $email)
	 {
	 $hash = md5( rand(0,1000) );
	$sql = "UPDATE userinfo set hash='$hash' where email='$email'";
	if ($conn->query($sql) === TRUE)
	{
		//echo "Hash has been generated";
	}
	else
	{
		//echo "Error: " . $sql . "<br>" . $conn->error;
	}
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

	 }
}
?>
