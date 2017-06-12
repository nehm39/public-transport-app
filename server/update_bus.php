<?php

$response = array();

if (isset($_POST['id']) && isset($_POST['lat']) && isset($_POST['lng']) && isset($_POST['speed'])) {

    $id = $_POST['id'];
    $lat = $_POST['lat'];
    $lng = $_POST['lng'];
	$speed = $_POST['speed'];

    require_once __DIR__ . '/db_connect.php';

    $db = new DB_CONNECT();

    $result = mysql_query("UPDATE autobusy SET lat = '$lat', lng = '$lng', speed = '$speed' WHERE id = $id");

    if ($result) {
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
        
        echo json_encode($response);
    } else {
        
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}
?>
