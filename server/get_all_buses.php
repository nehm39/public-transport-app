<?php

$response = array();


require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();
mysql_set_charset('utf8');

$result = mysql_query("SELECT *FROM autobusy ORDER BY autobusy.linia ASC") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

    $response["buses"] = array();
    
    while ($row = mysql_fetch_array($result)) {

        $bus = array();
        $bus["id"] = $row["id"];
		$bus["linia"] = $row["linia"];
		$bus["kurs"] = $row["kurs"];
		// $bus["lat"] = $row["lat"];
		// $bus["lng"] = $row["lng"];
		// $bus["speed"] = $row["speed"];
		// $bus["updated_at"] = $row["updated_at"];

        array_push($response["buses"], $bus);
    }
    $response["success"] = 1;

    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "No buses found";

    echo json_encode($response);
}
?>
