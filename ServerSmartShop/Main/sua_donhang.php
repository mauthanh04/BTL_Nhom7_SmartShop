<?php
include "connect.php";

$id = $_POST['id'];
$tenkhachhang = $_POST['tenkhachhang'];
$sodienthoai = $_POST['sodienthoai'];
$email = $_POST['email'];

// Kiểm tra email hoặc số điện thoại có trùng với đơn hàng khác không
$checkQuery = "SELECT * FROM donhang WHERE (email='$email' OR sodienthoai='$sodienthoai') AND id != '$id'";
$checkResult = mysqli_query($conn, $checkQuery);
if (mysqli_num_rows($checkResult) > 0) {
    echo "error: Email hoặc số điện thoại đã tồn tại";
    exit();
}

$query = "UPDATE donhang SET tenkhachhang='$tenkhachhang', sodienthoai='$sodienthoai', email='$email' WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Sửa đơn hàng thất bại";
}

mysqli_close($conn);
?>