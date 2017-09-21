<?php
error_reporting(E_ALL ^ E_DEPRECATED);
if(isset($_GET['tag']) && $_GET['tag'] != '')
{
  $tag = $_GET['tag'];
  require_once 'DB_Functions.php';
  $db = new DB_Functions();
  $response = array("tag" => $tag, "error" => FALSE);
  //registration
  if ($tag == 'register')
  {
    $name = $_GET['FullName'];
    $username = $_GET['Username'];
    $email = $_GET['Email'];
    $password = $_GET['Password'];
    $user = $db->storeUser($name, $username , $email , $password);
    echo $user;
    if ($user=="true")
    {
      $response["error"] = FALSE;
      $response["error_msg"] = "Registration Successful";
      echo json_encode($response);
    }
    else if($user=="Duplicate Username")
    {
      $response["error"] = TRUE;
      $response["error_msg"] = "Duplicate Username";
      echo json_encode($response);
    }
    else if($user=="Duplicate Email")
    {
      $response["error"] = TRUE;
      $response["error_msg"] = "Duplicate Email";
      echo json_encode($response);
    }
    else
    {
      $response["error"] = TRUE;
      $response["error_msg"] = "Registration Failed";
      echo json_encode($response);
    }
  }
  //login
  else if ($tag == 'login')
  {
    $user = $_GET['Username'];
    $password = $_GET['Password'];
    $user_check = $db->getUserByUserAndPassword($user , $password);
    if ($user_check != false)
    {
      $response["Success"] = TRUE;
      $response["error"] = FALSE;
      $response["error_msg"] = "User Exists";
      $response["Username"] = $user;
      echo json_encode($response);
    }
    else
    {
      $response["error"] = TRUE;
      $response["error_msg"] = "No such User";
      echo json_encode($response);
    }
  }
  else
  {
      $response["error"] = TRUE;
      $response["error_msg"] = "Incorrect token or password!";
      echo json_encode($response);
  }
}
else
{
	$response["error"] = TRUE;
	$response["error_msg"] = "Recieved No Data";
	echo json_encode($response);
}
?>
