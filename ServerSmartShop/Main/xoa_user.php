<?php
include "connect.php";

$id = $_POST['id'];

$query = "DELETE FROM users WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Xóa tài khoản thất bại";
}

mysqli_close($conn);
?>