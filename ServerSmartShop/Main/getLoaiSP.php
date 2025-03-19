<?php
include "connect.php";

$query = "SELECT * FROM loaisanpham"; 
$data = mysqli_query($conn, $query);
if (!$data) {
    die("Truy vấn thất bại: " . mysqli_error($conn));
}

$mangloaisp = array();
while ($row = mysqli_fetch_assoc($data)) {
    array_push($mangloaisp, new Loaisp(
        $row['id'],
        $row['tenloaisanpham'],
        $row['hinhanhloaisanpham']
    ));
}

echo json_encode($mangloaisp, JSON_UNESCAPED_UNICODE);

class Loaisp {
    function __construct($id, $tenloaisanpham, $hinhanhloaisanpham) { 
        $this->id = $id;
        $this->tenloaisanpham = $tenloaisanpham;
        $this->hinhanhloaisanpham = $hinhanhloaisanpham;
    }
}
?>
