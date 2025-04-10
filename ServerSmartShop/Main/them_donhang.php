<?php
include "connect.php";

$tenkhachhang = $_POST['tenkhachhang'];
$sodienthoai = $_POST['sodienthoai'];
$email = $_POST['email'];

// Kiểm tra email hoặc số điện thoại đã tồn tại chưa
$checkQuery = "SELECT * FROM donhang WHERE email='$email' OR sodienthoai='$sodienthoai'";
$checkResult = mysqli_query($conn, $checkQuery);
if (mysqli_num_rows($checkResult) > 0) {
    echo "error: Email hoặc số điện thoại đã tồn tại";
    exit();
}

$query = "INSERT INTO donhang (tenkhachhang, sodienthoai, email) VALUES ('$tenkhachhang', '$sodienthoai', '$email')";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Thêm đơn hàng thất bại";
}

mysqli_close($conn);
?>