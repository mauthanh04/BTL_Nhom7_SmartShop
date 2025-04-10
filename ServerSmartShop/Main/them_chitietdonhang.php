<?php
include 'connect.php';

// Kiểm tra kết nối cơ sở dữ liệu
if (!$conn) {
    echo "error: Không thể kết nối đến cơ sở dữ liệu: " . mysqli_connect_error();
    exit();
}

$madonhang = $_POST['madonhang'];
$masanpham = $_POST['masanpham'];
$tensanpham = $_POST['tensanpham'];
$giasanpham = $_POST['giasanpham'];
$soluongsanpham = $_POST['soluongsanpham'];

// Kiểm tra dữ liệu đầu vào
if (empty($madonhang) || empty($masanpham) || empty($tensanpham) || empty($giasanpham) || empty($soluongsanpham)) {
    echo "error: Dữ liệu đầu vào không đầy đủ";
    exit();
}

// Kiểm tra mã đơn hàng có tồn tại không
$checkQuery = "SELECT * FROM donhang WHERE id='$madonhang'";
$checkResult = mysqli_query($conn, $checkQuery);
if (!$checkResult) {
    echo "error: Lỗi truy vấn kiểm tra mã đơn hàng: " . mysqli_error($conn);
    exit();
}
if (mysqli_num_rows($checkResult) == 0) {
    echo "error: Mã đơn hàng không tồn tại";
    exit();
}

// Thoát các ký tự đặc biệt để tránh SQL Injection
$madonhang = mysqli_real_escape_string($conn, $madonhang);
$masanpham = mysqli_real_escape_string($conn, $masanpham);
$tensanpham = mysqli_real_escape_string($conn, $tensanpham);
$giasanpham = mysqli_real_escape_string($conn, $giasanpham);
$soluongsanpham = mysqli_real_escape_string($conn, $soluongsanpham);

// Thêm chi tiết đơn hàng vào bảng chitietdonhang
$query = "INSERT INTO chitietdonhang (madonhang, masanpham, tensanpham, giasanpham, soluongsanpham) 
          VALUES ('$madonhang', '$masanpham', '$tensanpham', '$giasanpham', '$soluongsanpham')";
$result = mysqli_query($conn, $query);

if ($result) {
    echo "success";
} else {
    echo "error: Thêm chi tiết đơn hàng thất bại: " . mysqli_error($conn);
}

mysqli_close($conn);
?>