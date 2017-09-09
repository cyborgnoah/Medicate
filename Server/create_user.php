<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['reg_FullName']) && isset($_POST['reg_Username']) && isset($_POST['reg_Email']) && isset($_POST['reg_Password']))
{

    $reg_FullName = $_POST['reg_FullName'];
    $reg_Username = $_POST['reg_Username'];
    $reg_Email = $_POST['reg_Email'];
    $reg_Password = $_POST['reg_Password'];

    // include db connect class
    require_once __DIR__ . '/Include/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO users(reg_FullName, reg_Username, reg_Email, reg_Password) VALUES('$reg_FullName', '$reg_Username', '$reg_Email','$reg_Password')");

    // check if row inserted or not
    if ($result)
    {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";

        // echoing JSON response
        echo json_encode($response);
    }
    else
    {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

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
