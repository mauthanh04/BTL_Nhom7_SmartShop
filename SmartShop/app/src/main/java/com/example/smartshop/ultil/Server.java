package com.example.smartshop.ultil;

public class Server {
    public static String localhost = "192.168.90.100";
    //điện thoại 192.168.209.102
    //wifi    192.168.0.135
    //Kết nối localhost/phpmyadmin / localhost:3000 / http://localhost:3000 / php language
    public static String DuongDanLoaiSP = "http://"  + localhost + "/ServerSmartShop/Main/getLoaiSP.php";
    public static String DuongDanLogin = "http://"  + localhost + "/ServerSmartShop/LoginRegister/login.php";
    public static String DuongDanSignUp = "http://"  + localhost + "/ServerSmartShop/LoginRegister/signup.php";
    public static String DuongDanSPMoiNhat = "http://"  + localhost + "/ServerSmartShop/Main/getSPMoiNhat.php";
    public static String Duongdandienthoai = "http://"  + localhost + "/ServerSmartShop/Main/getsanpham.php?page=";
}
