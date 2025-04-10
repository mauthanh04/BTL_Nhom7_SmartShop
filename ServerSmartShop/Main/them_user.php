<?php
include "connect.php";

$email = $_POST['email'];
$matkhau = password_hash($_POST['matkhau'], PASSWORD_DEFAULT); // Mã hóa mật khẩu
$hoten = $_POST['hoten'];
$vaitro = $_POST['vaitro'];

// Kiểm tra email đã tồn tại chưa
$checkEmailQuery = "SELECT * FROM users WHERE email = '$email'";
$checkEmailResult = mysqli_query($conn, $checkEmailQuery);
if (mysqli_num_rows($checkEmailResult) > 0) {
    echo "error: Email đã tồn tại";
    exit();
}

$query = "INSERT INTO users (email, matkhau, hoten, vaitro) VALUES ('$email', '$matkhau', '$hoten', '$vaitro')";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Thêm tài khoản thất bại";
}

mysqli_close($conn);
?>