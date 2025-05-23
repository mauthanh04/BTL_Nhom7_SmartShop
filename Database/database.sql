CREATE DATABASE smartshop;

USE smartshop;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    matkhau VARCHAR(255) NOT NULL,
    vaitro ENUM('user', 'admin') NOT NULL,
    hoten VARCHAR(100) NOT NULL
);

CREATE TABLE loaisanpham (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tenloaisanpham VARCHAR(200) NOT NULL,
    hinhanhloaisanpham VARCHAR(200) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE donhang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tenkhachhang VARCHAR(255) NOT NULL,
    sodienthoai VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE chitietdonhang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    madonhang INT NOT NULL,
    masanpham INT NOT NULL,
    tensanpham VARCHAR(255) NOT NULL,
    giassanpham INT NOT NULL,
    soluongsanpham INT NOT NULL,
    FOREIGN KEY (madonhang) REFERENCES donhang(id),
    FOREIGN KEY (masanpham) REFERENCES sanpham(id)
);

INSERT INTO loaisanpham (id, tenloaisanpham, hinhanhloaisanpham) VALUES 
(1, 'Điện thoại', 'https://cdn3.iconfinder.com/data/icons/flat-set-1/64/flat_set_1-25-512.png'),
(2, 'Máy tính', 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/web-browser-on-laptop-512.png');

CREATE TABLE sanpham (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tensanpham VARCHAR(200) NOT NULL,
    giasanpham INT(15) NOT NULL,
    hinhanhsanpham VARCHAR(200) NOT NULL,
    motasanpham VARCHAR(10000) NOT NULL,
    idsanpham INT(3) NOT NULL,
    CONSTRAINT fk_sanpham_loaisanpham FOREIGN KEY (idsanpham) REFERENCES loaisanpham(id)
    ON DELETE CASCADE 
    ON UPDATE CASCADE
) ENGINE=InnoDB;

INSERT INTO sanpham (tensanpham, giasanpham, hinhanhsanpham, motasanpham, idsanpham) VALUES
('Vsmart Joy 4 64GB', 2990000, 'https://cdn.tgdd.vn/Products/Images/42/228888/vsmart-joy-4-trang-600x600.jpg', 
'Vsmart Joy 4 64GB là smartphone giá rẻ với màn hình 6.53 inch Full HD+, mang lại hình ảnh sắc nét. Máy trang bị chip Snapdragon 665, 4GB RAM, 64GB ROM (hỗ trợ thẻ nhớ), đảm bảo hiệu năng ổn định. Hệ thống 4 camera sau (16MP + 8MP + 2MP + 2MP) cùng camera selfie 13MP cho ảnh chụp đa dạng. Viên pin 5000mAh hỗ trợ sạc nhanh 18W, dùng lâu dài. Cảm biến vân tay mặt lưng, nhận diện khuôn mặt giúp mở khóa nhanh. Chạy VOS 3.0 (Android 10), máy phù hợp với người dùng phổ thông muốn trải nghiệm mượt mà với mức giá hợp lý.', 1),
('Lenovo IdeaPad 3 14ITL6', 8990000, 'https://cdn.tgdd.vn/Products/Images/44/269603/lenovo-ideapad-3-14itl6-i5-82h700wavn-thumb-ko-den-600x600.jpg', 
'Lenovo IdeaPad 3 14ITL6 là chiếc laptop phổ thông với thiết kế gọn nhẹ, màn hình 14 inch Full HD cho hình ảnh sắc nét. Máy trang bị vi xử lý Intel Core i3-1115G4, 8GB RAM và 256GB SSD, đảm bảo hiệu suất ổn định cho học tập và làm việc. Bàn phím có hành trình tốt, touchpad nhạy. Hỗ trợ đầy đủ cổng kết nối USB-A, USB-C, HDMI, jack tai nghe tiện lợi. Viên pin 45Wh cho thời gian sử dụng dài, hỗ trợ sạc nhanh. Hệ điều hành Windows 11 bản quyền mang đến trải nghiệm mượt mà, phù hợp cho sinh viên và nhân viên văn phòng.', 2),
('Redmi Note 12 4G 128GB', 4990000, 'https://cdn.tgdd.vn/Products/Images/42/304182/xiaomi-redmi-note-12-4g-mono-xanh-600x600.jpg', 
'Redmi Note 12 4G 128GB sở hữu thiết kế hiện đại với màn hình 6.67 inch AMOLED Full HD+, tần số quét 120Hz cho trải nghiệm mượt mà. Máy trang bị chip Snapdragon 685, RAM 6GB, bộ nhớ trong 128GB, đảm bảo hiệu suất ổn định khi chơi game và đa nhiệm. Cụm camera sau 50MP + 8MP + 2MP chụp ảnh sắc nét, camera trước 13MP hỗ trợ selfie đẹp. Viên pin 5000mAh kết hợp sạc nhanh 33W, sử dụng thoải mái cả ngày. Chạy hệ điều hành MIUI 14 trên Android 13, có jack tai nghe 3.5mm, mở khóa vân tay cạnh viền, phù hợp với người dùng phổ thông.', 1),
('HP Pavilion 14-dv0007TU', 12990000, 'https://phucanhcdn.com/media/product/43121_pavilion_14_dv_gold_ha1.jpg', 
'HP Pavilion 14-dv0007TU là chiếc laptop mỏng nhẹ, sang trọng với màn hình 14 inch Full HD, viền mỏng cho trải nghiệm hình ảnh sắc nét. Máy được trang bị vi xử lý Intel Core i3-1115G4, RAM 4GB DDR4, SSD 256GB giúp khởi động nhanh và xử lý mượt mà các tác vụ văn phòng, học tập. Bàn phím thoải mái, có đèn nền hỗ trợ làm việc trong điều kiện thiếu sáng. Máy có loa B&O cho âm thanh sống động, hỗ trợ WiFi 6, cổng kết nối đa dạng gồm USB-C, HDMI, jack 3.5mm, cùng pin 3-cell 43Wh cho thời lượng sử dụng ổn định. Phù hợp cho sinh viên, dân văn phòng cần một chiếc laptop nhỏ gọn, hiệu năng tốt.', 2),
('Realme 11 Pro+ 512GB', 8490000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1r-7tXNMVzfPvC2uru6Ozkq5wqZVPIhluFQ&s', 
'Realme 11 Pro+ 512GB sở hữu thiết kế sang trọng với mặt lưng giả da cao cấp, màn hình 6.7 inch AMOLED, 120Hz, độ phân giải Full HD+ cho hình ảnh sắc nét, màu sắc sống động. Máy trang bị chip Dimensity 7050 mạnh mẽ, RAM 12GB, bộ nhớ trong 512GB, đáp ứng tốt đa nhiệm và lưu trữ thoải mái. Camera chính 200MP OIS, hỗ trợ zoom quang học 4x, đi kèm camera góc rộng 8MP và macro 2MP, mang lại trải nghiệm chụp ảnh chất lượng cao. Camera selfie 32MP cho ảnh chụp sắc nét. Pin 5000mAh, sạc nhanh 100W giúp sạc đầy 100% chỉ trong khoảng 26 phút. Máy chạy Realme UI 4.0 trên Android 13, hỗ trợ loa kép Dolby Atmos, cảm biến vân tay dưới màn hình. Đây là lựa chọn tuyệt vời cho người dùng cần smartphone mạnh mẽ, pin trâu, camera cao cấp.', 1),
('Dell Inspiron 15 3511', 14990000, 'https://cdn.tgdd.vn/Products/Images/44/265469/dell-inspiron-15-3511-i5-1135g7-4gb-512gb-600x600.jpg', 
'Dell Inspiron 15 3511 là chiếc laptop văn phòng mạnh mẽ với màn hình 15.6 inch Full HD, tấm nền WVA cho góc nhìn rộng, màu sắc hiển thị tốt. Máy được trang bị vi xử lý Intel Core i5-1135G7, xung nhịp tối đa 4.2GHz, đi kèm 8GB RAM DDR4 (có thể nâng cấp lên 16GB) và 512GB SSD NVMe, giúp xử lý mượt mà các tác vụ văn phòng, học tập và giải trí. Card đồ họa tích hợp Intel Iris Xe Graphics hỗ trợ tốt việc chỉnh sửa ảnh, video nhẹ nhàng. Máy có bàn phím full-size với cụm phím số tiện lợi, thiết kế gọn nhẹ với trọng lượng chỉ 1.85kg, phù hợp để di chuyển. Pin 41Wh cho thời gian sử dụng kéo dài, hỗ trợ sạc nhanh. Dell Inspiron 15 3511 là sự lựa chọn lý tưởng cho sinh viên, nhân viên văn phòng cần một chiếc laptop bền bỉ, hiệu suất tốt.', 2),
('Samsung Galaxy A54 5G 256GB', 9990000, 'https://samcenter.vn/images/thumbs/0001190_galaxy-a54-5g-256gb.jpeg', 
'Samsung Galaxy A54 5G 256GB là mẫu smartphone tầm trung nổi bật với thiết kế cao cấp, khung viền kim loại chắc chắn và mặt lưng kính sang trọng. Máy sở hữu màn hình 6.4 inch Super AMOLED, độ phân giải Full HD+, tần số quét 120Hz, mang đến trải nghiệm hình ảnh mượt mà, sắc nét. Hiệu năng mạnh mẽ nhờ chip Exynos 1380, kết hợp với 8GB RAM và bộ nhớ trong 256GB (hỗ trợ thẻ nhớ ngoài lên đến 1TB), giúp xử lý đa nhiệm tốt và lưu trữ thoải mái. Cụm camera sau gồm chính 50MP có OIS, siêu rộng 12MP và macro 5MP, cho chất lượng ảnh sắc nét, quay video ổn định. Camera selfie 32MP hỗ trợ AI giúp chụp ảnh chân dung ấn tượng. Viên pin 5000mAh đi kèm sạc nhanh 25W đảm bảo thời gian sử dụng dài lâu. Samsung Galaxy A54 5G là lựa chọn lý tưởng cho người dùng yêu thích một chiếc điện thoại đẹp, bền bỉ với hiệu năng ổn định và camera chất lượng.', 1),
('ASUS TUF Gaming F15', 22990000, 'https://ttcenter.com.vn/uploads/photos/1689318911_2058_d9823d1982a40e8dd3ec16a2abe507db.jpg', 
'ASUS TUF Gaming F15 là chiếc laptop gaming mạnh mẽ với thiết kế hầm hố, bền bỉ đạt chuẩn quân đội MIL-STD-810H, phù hợp với game thủ và người dùng cần hiệu năng cao. Máy sở hữu màn hình 15.6 inch Full HD, tần số quét 144Hz, mang lại trải nghiệm hình ảnh mượt mà, hạn chế xé hình khi chơi game. Hiệu năng vượt trội với Intel Core i5-11400H / i7-12700H, kết hợp cùng card đồ họa NVIDIA GeForce GTX 1650 / RTX 3050 / RTX 3060, giúp xử lý tốt các tựa game phổ biến như Valorant, CS:GO, hoặc những tác vụ nặng như đồ họa, chỉnh sửa video. RAM 8GB/16GB DDR4, có thể nâng cấp lên 32GB, cùng SSD 512GB NVMe giúp tốc độ đọc ghi nhanh chóng. Hệ thống tản nhiệt Arc Flow Fans giúp máy hoạt động ổn định trong thời gian dài. Bàn phím RGB nổi bật, hành trình phím tốt, tối ưu cho gaming. Pin 90Wh đảm bảo thời gian sử dụng dài hơn so với nhiều laptop gaming cùng phân khúc. Đây là lựa chọn lý tưởng cho những ai tìm kiếm một laptop chơi game bền bỉ, hiệu năng mạnh mẽ với mức giá hợp lý.', 2),
('iPhone 13 128GB', 14990000, 'https://cdn.tgdd.vn/Products/Images/42/223602/iphone-13-midnight-2-600x600.jpg', 
'iPhone 13 128GB mang đến hiệu năng mạnh mẽ với chip Apple A15 Bionic, giúp xử lý nhanh chóng mọi tác vụ từ chơi game, quay video 4K đến chỉnh sửa hình ảnh. Màn hình Super Retina XDR 6.1 inch hiển thị sắc nét, màu sắc chân thực, độ sáng cao giúp sử dụng tốt dưới ánh sáng mặt trời. Cụm camera kép 12MP cải tiến với cảm biến lớn hơn, hỗ trợ Night Mode, chụp ảnh thiếu sáng tốt hơn và quay video Cinematic Mode, mang lại trải nghiệm quay phim chuyên nghiệp. Pin được nâng cấp so với thế hệ trước, cho thời gian sử dụng lâu hơn, đáp ứng tốt cả ngày dài. Máy hỗ trợ 5G, Face ID nhanh nhạy, cùng hệ điều hành iOS tối ưu mượt mà. Thiết kế nguyên khối với khung nhôm, mặt kính Ceramic Shield bền bỉ, chống trầy xước tốt. Đây là lựa chọn hoàn hảo cho những ai cần một chiếc iPhone mạnh mẽ, bền bỉ với trải nghiệm cao cấp.', 1),
('Acer Predator Helios 300', 29990000, 'https://hanoicomputercdn.com/media/product/67253_laptop_acer_gaming_predator_helios_300_15.png', 
'Acer Predator Helios 300 là chiếc laptop gaming mạnh mẽ, được trang bị CPU Intel Core i7-12700H cùng GPU NVIDIA GeForce RTX 3060 6GB, giúp chiến mượt các tựa game AAA với hiệu suất ổn định. Màn hình 15.6 inch, tấm nền IPS, tần số quét 165Hz, mang lại hình ảnh sắc nét, mượt mà, giảm hiện tượng xé hình khi chơi game tốc độ cao. Hệ thống tản nhiệt AeroBlade 3D thế hệ thứ 5 giúp duy trì hiệu suất ổn định ngay cả khi hoạt động cường độ cao. Máy có 16GB RAM DDR5, hỗ trợ nâng cấp lên 32GB, cùng SSD 512GB NVMe, đảm bảo tốc độ khởi động và load game nhanh chóng. Bàn phím RGB 4 vùng, touchpad nhạy, cổng kết nối đầy đủ (USB-C, HDMI, Ethernet) giúp tối ưu trải nghiệm. Thiết kế hầm hố với vỏ kim loại chắc chắn, thích hợp cho game thủ muốn một chiếc laptop mạnh, bền bỉ và sẵn sàng chiến đấu mọi lúc mọi nơi.', 2),
('OPPO Find X6 Pro 256GB', 19990000, 'https://cdn.xtmobile.vn/vnt_upload/product/04_2023/thumbs/600_Oppo_find_x6_pro_green_xtmobile.jpg', 
'OPPO Find X6 Pro 256GB là một flagship cao cấp với thiết kế sang trọng, hiệu năng mạnh mẽ và hệ thống camera đột phá. Máy được trang bị màn hình LTPO3 AMOLED 6.82 inch, độ phân giải 2K+ (3168 x 1440 pixel), tần số quét 120Hz, mang lại trải nghiệm hình ảnh sắc nét, mượt mà. Hiệu năng vượt trội với chip Snapdragon 8 Gen 2, RAM 12GB và bộ nhớ trong 256GB, đảm bảo xử lý nhanh chóng mọi tác vụ từ chơi game đến chỉnh sửa video. Cụm camera Hasselblad gồm camera chính 50MP (cảm biến 1 inch), camera góc siêu rộng 50MP và camera zoom tiềm vọng 50MP, cho ảnh sắc nét, màu sắc trung thực và khả năng zoom quang học 3x. Viên pin 5000mAh hỗ trợ sạc nhanh SuperVOOC 100W, giúp sạc đầy chỉ trong khoảng 30 phút. Máy còn có cảm biến vân tay dưới màn hình, chuẩn kháng nước IP68 và loa stereo, đem lại trải nghiệm giải trí hoàn hảo.', 1),
('HP Spectre x360 16 inch', 35990000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJ-WpLd-t4CX2gzGAJcotFlpUNXgkun_sXlw&s', 
'HP Spectre x360 16 inch là laptop cao cấp 2 trong 1 với màn hình OLED 16 inch 3K+ 120Hz sắc nét, hỗ trợ cảm ứng và gập 360 độ. Máy trang bị Intel Core i7-12700H, RAM 16GB, SSD 1TB, cùng Intel Arc hoặc RTX 3050, đáp ứng tốt đa nhiệm và đồ họa. Bàn phím có đèn nền, cảm biến vân tay, camera IR hỗ trợ Windows Hello. Pin 83Wh, sạc nhanh 65W, âm thanh Bang & Olufsen. Cổng kết nối đa dạng như Thunderbolt 4, HDMI, thiết kế sang trọng, lý tưởng cho công việc và sáng tạo.', 2),
('Xiaomi 14 Pro 512GB', 23990000, 'https://cdn.xtmobile.vn/vnt_upload/product/11_2023/thumbs/600_14_pro_1ddba11fc2e24b16901443c0c.jpg', 
'Xiaomi 14 Pro 512GB sở hữu màn hình LTPO AMOLED 6.73 inch QHD+ 120Hz, viền siêu mỏng, độ sáng 3000 nits. Hiệu năng mạnh mẽ với chip Snapdragon 8 Gen 3, RAM 16GB, ROM 512GB UFS 4.0, đảm bảo tốc độ xử lý vượt trội. Cụm camera Leica 50MP với cảm biến lớn, hỗ trợ zoom quang học, quay video 8K. Pin 4880mAh, sạc nhanh 120W có dây, 50W không dây. Thiết kế kính và khung titan sang trọng, hỗ trợ Wi-Fi 7, 5G, kháng nước IP68. Một flagship toàn diện cho người dùng yêu cầu cao.', 1),
('Lenovo Legion Pro 7i', 47990000, 'https://laptop88.vn/media/product/8060_6f627a233ef1cba243e67f66e1da0a0d_hi.jpg', 
'Lenovo Legion Pro 7i là laptop gaming cao cấp với màn hình 16 inch WQXGA 240Hz, độ sáng 500 nits, hỗ trợ Dolby Vision. Hiệu năng mạnh mẽ với Intel Core i9-13900HX, GPU RTX 4090 16GB, RAM 32GB DDR5, SSD 1TB NVMe giúp xử lý mọi tác vụ nặng. Hệ thống tản nhiệt Legion ColdFront 5.0 giữ máy mát mẻ khi chơi game. Bàn phím RGB per-key, loa Nahimic 3D, cổng kết nối đa dạng Thunderbolt 4, HDMI 2.1, USB-C. Pin 99.99Wh, sạc nhanh 330W. Một cỗ máy chiến game thực thụ dành cho game thủ chuyên nghiệp.', 2),
('Google Pixel 8 Pro 128GB', 22990000, 'https://sonpixel.vn/wp-content/uploads/2023/08/Google-Pixel-8-Pro-SonPixel.jpg', 
'Google Pixel 8 Pro 128GB sở hữu màn hình 6.7 inch LTPO OLED với độ phân giải QHD+, tần số quét 120Hz, độ sáng lên đến 2400 nits, hiển thị sắc nét. Hiệu năng mạnh mẽ nhờ chip Google Tensor G3, RAM 12GB, bộ nhớ 128GB, tối ưu AI giúp xử lý hình ảnh và tác vụ nhanh chóng. Cụm camera 50MP (OIS) + 48MP (ultrawide) + 48MP (tele 5x) cùng phần mềm AI chụp ảnh chuyên nghiệp. Pin 5050mAh, sạc nhanh 30W, hỗ trợ sạc không dây. Chạy Android 14 gốc, cập nhật lâu dài, bảo mật cao. Thiết kế kính cường lực, khung nhôm, đạt chuẩn IP68 chống nước.', 1),
('ASUS ROG Zephyrus G14', 39990000, 'https://ttcenter.com.vn/uploads/product/iog1yru6-1399-asus-rog-zephyrus-g14-ga403uv-g14-r94060-amd-ryzen-9-8945hs-16gb-1tb-rtx-4060-8gb-14-2k-ips-120hz-new.jpg', 
'ASUS ROG Zephyrus G14 là mẫu laptop gaming nhỏ gọn nhưng mạnh mẽ với màn hình 14 inch QHD+, 165Hz, công nghệ Dolby Vision cho hình ảnh sắc nét, mượt mà. Trang bị vi xử lý AMD Ryzen 9 7940HS, GPU NVIDIA GeForce RTX 4060/4070, RAM 16GB/32GB DDR5, SSD 512GB/1TB NVMe, đảm bảo hiệu suất cao cho gaming và đồ họa. Hệ thống tản nhiệt ROG Intelligent Cooling giúp máy luôn mát mẻ. Pin 76Wh, hỗ trợ sạc nhanh 100W Type-C. Thiết kế cao cấp, bàn phím LED RGB, loa Dolby Atmos, phù hợp cho game thủ cần hiệu năng mạnh mẽ trong một thân máy di động.', 2),
('Samsung Galaxy S24 Ultra 512GB', 33990000, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/s/s/ss-s24-ultra-xam-222_1.png', 
'Samsung Galaxy S24 Ultra 512GB là flagship cao cấp với thiết kế sang trọng, khung viền Titan bền bỉ. Máy trang bị màn hình 6.8 inch Dynamic AMOLED 2X, 120Hz, độ phân giải QHD+, hỗ trợ HDR10+ cho hình ảnh siêu sắc nét. Hiệu năng mạnh mẽ với chip Snapdragon 8 Gen 3 for Galaxy, RAM 12GB, bộ nhớ 512GB, đảm bảo hiệu suất vượt trội. Hệ thống camera ấn tượng với cảm biến chính 200MP, zoom quang 10x, AI cải thiện hình ảnh. Pin 5000mAh, sạc nhanh 45W, bút S Pen hỗ trợ ghi chú tiện lợi. Một lựa chọn hoàn hảo cho công việc, giải trí và nhiếp ảnh di động.', 1),
('Dell XPS 15 9530', 52990000, 'https://nhatminhlaptop.com/upload/products/2023-03-23-13-45-56/9530-1.jpg', 
'Dell XPS 15 9530 là chiếc laptop cao cấp với thiết kế nhôm nguyên khối, viền màn hình siêu mỏng. Máy sở hữu màn hình 15.6 inch OLED 3.5K, hỗ trợ HDR, mang lại hình ảnh sống động. Cấu hình mạnh mẽ với Intel Core i7-13700H, RAM 16GB, SSD 512GB, cùng card đồ họa NVIDIA RTX 4050, đáp ứng tốt nhu cầu đồ họa và gaming. Hệ thống loa Waves Nx 3D Audio cho âm thanh sống động, bàn phím có đèn nền thoải mái gõ phím. Pin 86Wh, hỗ trợ sạc nhanh, giúp làm việc hiệu quả cả ngày. Phù hợp cho dân thiết kế, sáng tạo nội dung và doanh nhân.', 2),
('iPhone 15 Pro Max 512GB', 36990000, 'https://cdn.tgdd.vn/Products/Images/42/305659/iphone-15-pro-max-blue-thumbnew-600x600.jpg', 
'iPhone 15 Pro Max 512GB là flagship cao cấp nhất của Apple với khung viền Titanium siêu nhẹ, màn hình 6.7 inch Super Retina XDR, hỗ trợ ProMotion 120Hz cho trải nghiệm mượt mà. Sức mạnh đến từ chip Apple A17 Pro, kết hợp RAM 8GB, mang lại hiệu năng vượt trội. Camera 48MP nâng cấp với zoom quang học 5x, chụp ảnh sắc nét trong mọi điều kiện. Pin được tối ưu hóa, sạc nhanh qua USB-C, hỗ trợ MagSafe. Trải nghiệm iOS mượt mà, bảo mật Face ID, phù hợp cho người dùng muốn smartphone mạnh mẽ, bền bỉ và cao cấp nhất.', 1),
('MacBook Pro M3 Pro 14 inch', 49990000, 'https://cdn.tgdd.vn/Products/Images/44/318230/apple-macbook-pro-14-inch-m3-pro-2023-silver-1-750x500.jpg', 
'MacBook Pro M3 Pro 14 inch là chiếc laptop cao cấp của Apple, trang bị chip M3 Pro mạnh mẽ, tối ưu cho hiệu suất đồ họa và đa nhiệm. Màn hình Liquid Retina XDR 14.2 inch, độ sáng 1600 nits, tần số quét 120Hz ProMotion, mang lại hình ảnh sắc nét, màu sắc trung thực. Thiết kế nhôm nguyên khối sang trọng, bàn phím Magic Keyboard và trackpad lớn cho trải nghiệm gõ thoải mái. Hỗ trợ 3 cổng Thunderbolt 4, HDMI, MagSafe 3, pin lên đến 18 giờ. Đây là lựa chọn lý tưởng cho người làm sáng tạo, lập trình viên và dân chuyên nghiệp cần hiệu năng mạnh mẽ.', 2);



CREATE TABLE donhang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tenkhachhang VARCHAR(255) NOT NULL,
    sodienthoai VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL
);


CREATE TABLE chitietdonhang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    madonhang INT NOT NULL,
    masanpham INT NOT NULL,
    tensanpham VARCHAR(255) NOT NULL,
    giasanpham INT NOT NULL,
    soluongsanpham INT NOT NULL,
    FOREIGN KEY (madonhang) REFERENCES donhang(id),
    FOREIGN KEY (masanpham) REFERENCES sanpham(id)
);