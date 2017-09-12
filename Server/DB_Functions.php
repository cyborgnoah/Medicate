<?php
error_reporting(E_ALL ^ E_DEPRECATED);
class DB_Functions {
    private $db;
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
    // destructor
    function __destruct() { }
    /**
     * Store user details
     */
    public function storeUser($name, $username , $email , $password) {
        $result = mysqli_query($this->db->con,"INSERT INTO user(Name,User,Email, Password) VALUES('$name', '$username' , '$email' , '$password')") or die(mysqli_error($this->db));
        if ($result) {
            return true;
        }
        else {
            return false;
        }
    }
}
?>
