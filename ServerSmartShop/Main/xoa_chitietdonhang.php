<?php
include "connect.php";

$id = $_POST['id'];

$query = "DELETE FROM chitietdonhang WHERE id='$id'";
if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "error: Xóa chi tiết đơn hàng thất bại";
}

mysqli_close($conn);
?>