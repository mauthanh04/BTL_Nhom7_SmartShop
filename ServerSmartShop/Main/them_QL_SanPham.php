<?php
header('Content-Type: text/plain');

// Include file kết nối database
include "connect.php";

$tensanpham = $_POST['tensanpham'];
$giasanpham = $_POST['giasanpham'];
$hinhanhsanpham = $_POST['hinhanhsanpham'];
$motasanpham = $_POST['motasanpham'];
$idsanpham = $_POST['idsanpham'];

$query = "INSERT INTO sanpham (tensanpham, giasanpham, hinhanhsanpham, motasanpham, idsanpham) 
          VALUES ('$tensanpham', '$giasanpham', '$hinhanhsanpham', '$motasanpham', '$idsanpham')";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error";
}

mysqli_close($conn);
?>