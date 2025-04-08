<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['email']) && isset($_POST['matkhau'])) {
    if ($db->dbConnect()) {
        $result = $db->logIn("users", $_POST['email'], $_POST['matkhau']);
        echo $result;
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
