<?php
    include "connect.php";
    $json = $_POST['json'];
    $data = json_decode($json, true);
    $all_success = true;

    foreach($data as $key => $value){
        $madonhang = $value['madonhang'];
        $masanpham = $value['masanpham'];
        $tensanpham = $value['tensanpham'];
        $giasanpham = $value['giasanpham'];
        $soluongsanpham = $value['soluongsanpham'];

        $query = "INSERT INTO chitietdonhang (id, madonhang, masanpham, tensanpham, giasanpham, soluongsanpham) 
                  VALUES (null, '$madonhang', '$masanpham', '$tensanpham', '$giasanpham', '$soluongsanpham')";
        $Dta = mysqli_query($conn, $query);

        if(!$Dta){
            $all_success = false;
        }
    }

    if($all_success){
        echo "1";
    }else{
        echo "0";
    }
?>
