<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['login_Username']) && isset($_POST['login_Password']))
{
    $login_Username = $_POST['login_Username'];
    $login_Password = $_POST['login_Password'];
    define('DB_USER',"root");
    define('DB_PASSWORD',"");
    define('DB_DATABASE',"medicate");
    define('DB_SERVER',"localhost");
    $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

    $sql = "SELECT * from users where reg_Username='$login_Username' and reg_Password='$login_Password'";
    $result = $con->query($sql);
    if ($result->num_rows > 0)
    {
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";

        // echoing JSON response
        echo json_encode($response);
    }
    else
    {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = mysqli_error($con);

        // echoing JSON response
        echo json_encode($response);
    }
}
else
{
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
