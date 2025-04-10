<?php
header('Content-Type: application/json');
include "connect.php";

$query = "SELECT * FROM users";
$result = mysqli_query($conn, $query);

$arrayUsers = array();
while ($row = mysqli_fetch_assoc($result)) {
    $arrayUsers[] = $row;
}

echo json_encode($arrayUsers);

mysqli_close($conn);
?>