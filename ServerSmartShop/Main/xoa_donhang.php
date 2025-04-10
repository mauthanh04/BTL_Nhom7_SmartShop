<?php
include "connect.php";

$id = $_POST['id'];

// Xóa chi tiết đơn hàng liên quan
$deleteChiTietQuery = "DELETE FROM chitietdonhang WHERE madonhang='$id'";
mysqli_query($conn, $deleteChiTietQuery);

// Xóa đơn hàng
$deleteDonHangQuery = "DELETE FROM donhang WHERE id='$id'";
if (mysqli_query($conn, $deleteDonHangQuery)) {
    echo "success";
} else {
    echo "error: Xóa đơn hàng thất bại";
}

mysqli_close($conn);
?>