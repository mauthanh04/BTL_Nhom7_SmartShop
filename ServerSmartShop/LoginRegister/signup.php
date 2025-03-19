<?php
require "DataBase.php";
$db = new DataBase();

try {
    // Kiểm tra các trường có được gửi lên không
    if (isset($_POST['fullname']) && isset($_POST['email']) && isset($_POST['username']) && isset($_POST['password'])) {
        $fullname = $_POST['fullname'];
        $email = $_POST['email'];
        $username = $_POST['username'];
        $password = $_POST['password'];

        // Kiểm tra dữ liệu không được để trống
        if (empty($fullname) || empty($email) || empty($username) || empty($password)) {
            throw new Exception("All fields are required");
        }

        // Kiểm tra kết nối database
        if (!$db->dbConnect()) {
            throw new Exception("Error: Database connection");
        }

        // Kiểm tra tên đăng nhập đã tồn tại chưa
        $checkUserQuery = "SELECT * FROM users WHERE username = '$username'";
        $checkUserResult = mysqli_query($db->connect, $checkUserQuery);
        if (mysqli_num_rows($checkUserResult) > 0) {
            throw new Exception("Username already exists");
        }

        // Kiểm tra email đã tồn tại chưa
        $checkEmailQuery = "SELECT * FROM users WHERE email = '$email'";
        $checkEmailResult = mysqli_query($db->connect, $checkEmailQuery);
        if (mysqli_num_rows($checkEmailResult) > 0) {
            throw new Exception("Email already in use");
        }

        // Thực hiện đăng ký tài khoản
        if ($db->signUp("users", $fullname, $email, $username, $password)) {
            echo "Sign Up Success";
        } else {
            throw new Exception("Sign up Failed");
        }
    } else {
        throw new Exception("All fields are required");
    }
} catch (Exception $e) {
    // Xử lý ngoại lệ và trả về thông báo lỗi
    echo $e->getMessage();
}
?>
