<?php
error_reporting(E_ALL ^ E_DEPRECATED);
class DB_Connect
{
    public $con;
    //function for connecting to database
    public function connect()
    {
        require_once 'db_config.php';
        $this->con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error($this->con));
        if (mysqli_connect_errno())
        {
            die("Database connection failed");
        }
        // return database handler
        return $this->con;
    }
    // Closing database connection
    public function close()
    {
        mysqli_close($this->con);
    }
}
?>
