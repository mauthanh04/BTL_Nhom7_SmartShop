<?php
header('Content-Type: application/json');
include "connect.php";

$query = "SELECT * FROM sanpham";
$result = mysqli_query($conn, $query);

$arraySanPham = array();
while ($row = mysqli_fetch_assoc($result)) {
    $arraySanPham[] = $row;
}

echo json_encode($arraySanPham);

mysqli_close($conn);
?>