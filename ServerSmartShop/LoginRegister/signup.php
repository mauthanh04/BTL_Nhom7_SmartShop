<?php
require "DataBase.php";
$db = new DataBase();

try {
    // Kiểm tra các trường có được gửi lên không
    if (isset($_POST['email']) && isset($_POST['matkhau']) && isset($_POST['vaitro']) && isset($_POST['hoten'])) {
        $email = $_POST['email'];
        $matkhau = $_POST['matkhau'];
        $vaitro = $_POST['vaitro'];
        $hoten = $_POST['hoten'];

        // Kiểm tra dữ liệu không được để trống
        if (empty($email) || empty($matkhau) || empty($vaitro) || empty($hoten)) {
            throw new Exception("All fields are required");
        }

        // Kiểm tra kết nối database
        if (!$db->dbConnect()) {
            throw new Exception("Error: Database connection");
        }

        // Kiểm tra email đã tồn tại chưa
        $checkEmailQuery = "SELECT * FROM users WHERE email = '$email'";
        $checkEmailResult = mysqli_query($db->connect, $checkEmailQuery);
        if (mysqli_num_rows($checkEmailResult) > 0) {
            throw new Exception("Email already in use");
        }

        // Đăng ký tài khoản qua hàm signUp
        if ($db->signUp("users", $email, $matkhau, $vaitro, $hoten)) {
            echo "Sign Up Success";
        } else {
            throw new Exception("Sign Up Failed");
        }
    } else {
        throw new Exception("All fields are required");
    }
} catch (Exception $e) {
    // Xử lý ngoại lệ và trả về thông báo lỗi
    echo $e->getMessage();
}
?>
