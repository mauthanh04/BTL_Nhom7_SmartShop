<?php
header('Content-Type: text/plain');

// Include file kết nối database
include "connect.php";

$id = $_POST['id'];
$tensanpham = $_POST['tensanpham'];
$giasanpham = $_POST['giasanpham'];
$hinhanhsanpham = $_POST['hinhanhsanpham'];
$motasanpham = $_POST['motasanpham'];
$idsanpham = $_POST['idsanpham'];

$query = "UPDATE sanpham SET tensanpham='$tensanpham', giasanpham='$giasanpham', 
          hinhanhsanpham='$hinhanhsanpham', motasanpham='$motasanpham', idsanpham='$idsanpham' 
          WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error";
}

mysqli_close($conn);
?>