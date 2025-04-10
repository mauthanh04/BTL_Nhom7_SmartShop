<?php
include "connect.php";

$id = $_POST['id'];
$email = $_POST['email'];
$hoten = $_POST['hoten'];
$vaitro = $_POST['vaitro'];

// Kiểm tra xem có gửi mật khẩu mới không
if (isset($_POST['matkhau']) && !empty($_POST['matkhau'])) {
    $matkhau = password_hash($_POST['matkhau'], PASSWORD_DEFAULT); // Mã hóa mật khẩu
    $query = "UPDATE users SET email='$email', matkhau='$matkhau', hoten='$hoten', vaitro='$vaitro' WHERE id='$id'";
} else {
    $query = "UPDATE users SET email='$email', hoten='$hoten', vaitro='$vaitro' WHERE id='$id'";
}

// Kiểm tra email có trùng với tài khoản khác không (nếu email thay đổi)
$checkEmailQuery = "SELECT * FROM users WHERE email='$email' AND id != '$id'";
$checkEmailResult = mysqli_query($conn, $checkEmailQuery);
if (mysqli_num_rows($checkEmailResult) > 0) {
    echo "error: Email đã tồn tại";
    exit();
}

if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Sửa tài khoản thất bại";
}

mysqli_close($conn);
?>