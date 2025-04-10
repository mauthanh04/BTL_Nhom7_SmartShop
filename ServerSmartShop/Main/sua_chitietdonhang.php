<?php
include "connect.php";

$id = $_POST['id'];
$masanpham = $_POST['masanpham'];
$tensanpham = $_POST['tensanpham'];
$giasanpham = $_POST['giasanpham'];
$soluongsanpham = $_POST['soluongsanpham'];

$query = "UPDATE chitietdonhang SET masanpham='$masanpham', tensanpham='$tensanpham', giasanpham='$giasanpham', soluongsanpham='$soluongsanpham' WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Sửa chi tiết đơn hàng thất bại";
}

mysqli_close($conn);
?>