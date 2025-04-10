<?php
header('Content-Type: text/plain');

// Include file kết nối database
include "connect.php";

$id = $_POST['id'];

$query = "DELETE FROM sanpham WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error";
}

mysqli_close($conn);
?>