<?php

$response = array();


require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if (isset($_GET['id'])) {
    $id = $_GET['id'];

    $result = mysql_query("SELECT *FROM autobusy WHERE id = $id");

    if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $bus = array();
            $bus["id"] = $result["id"];
            $bus["linia"] = $result["linia"];
            $bus["kurs"] = $result["kurs"];
            $bus["lat"] = $result["lat"];
            $bus["lng"] = $result["lng"];
			$bus["speed"] = $result["speed"];
            $bus["updated_at"] = $result["updated_at"];

            $response["success"] = 1;

            $response["bus"] = array();

            array_push($response["bus"], $bus);

            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "No bus found";

            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "No bus found";

        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}
?>