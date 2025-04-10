<?php
include "connect.php";

$query = "SELECT * FROM donhang";
$result = mysqli_query($conn, $query);

$donhang = array();
while ($row = mysqli_fetch_assoc($result)) {
    $donhang[] = $row;
}

echo json_encode($donhang);

mysqli_close($conn);
?>