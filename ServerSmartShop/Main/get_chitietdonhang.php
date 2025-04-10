<?php
include "connect.php";

$madonhang = $_GET['madonhang'];

$query = "SELECT * FROM chitietdonhang WHERE madonhang='$madonhang'";
$result = mysqli_query($conn, $query);

$chitietdonhang = array();
while ($row = mysqli_fetch_assoc($result)) {
    $chitietdonhang[] = $row;
}

echo json_encode($chitietdonhang);

mysqli_close($conn);
?>