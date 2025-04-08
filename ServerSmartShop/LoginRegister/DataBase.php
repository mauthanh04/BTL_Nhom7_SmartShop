<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $email, $matkhau)
    {
        $email = $this->prepareData($email);
        $matkhau = $this->prepareData($matkhau);
        $this->sql = "SELECT * FROM " . $table . " WHERE Email = '" . $email . "'";
        $result = mysqli_query($this->connect, $this->sql);

        if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            $dbMatKhau = $row['matkhau'];
            $vaitro = $row['vaitro'];
            
            if (password_verify($matkhau, $dbMatKhau)) {
                return "Login Success" . $vaitro;
            } else {
                return "Invalid Email or Password";
            }
        } else {
            return "Invalid Email or Password";
        }
    }

    function signUp($table, $email, $matkhau, $vaitro, $hoten)
    {
        $email = $this->prepareData($email);
        $matkhau = $this->prepareData($matkhau);
        $vaitro = $this->prepareData($vaitro);
        $hoten = $this->prepareData($hoten);
        $matkhau = password_hash($matkhau, PASSWORD_DEFAULT);
        $this->sql = "INSERT INTO " . $table . " (email, matkhau, vaitro, hoten) VALUES ('" . $email . "', '" . $matkhau . "', '" . $vaitro . "', '" . $hoten . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

}

?>
