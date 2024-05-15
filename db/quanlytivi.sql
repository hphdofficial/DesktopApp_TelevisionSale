-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 19, 2022 lúc 03:10 PM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlytivi`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethd`
--

CREATE TABLE `chitiethd` (
  `MAHD` varchar(20) NOT NULL,
  `MASP` varchar(20) NOT NULL,
  `TENSP` varchar(60) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `DONGIA` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chitiethd`
--

INSERT INTO `chitiethd` (`MAHD`, `MASP`, `TENSP`, `SOLUONG`, `DONGIA`) VALUES
('HD001', 'TV5', 'Android Tivi OLED Sony 4K 55 inch', 2, 45990000),
('HD002', 'TV4', 'Android Tivi QLED Casper 4K 55 inch', 2, 11490000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietnh`
--

CREATE TABLE `chitietnh` (
  `MANH` varchar(20) NOT NULL,
  `MASP` varchar(20) NOT NULL,
  `TENSP` varchar(60) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `DONGIA` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chitietnh`
--

INSERT INTO `chitietnh` (`MANH`, `MASP`, `TENSP`, `SOLUONG`, `DONGIA`) VALUES
('NH3', 'TV3', 'Smart Tivi Neo QLED Samsung 8K 85 inch QA85QN900AKXXV', 12, 154990000),
('NH4', 'TV4', 'Android Tivi Casper 55 inch 55UG6100', 10, 11490000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietquyen`
--

CREATE TABLE `chitietquyen` (
  `MAQUYEN` varchar(20) NOT NULL,
  `MACN` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chitietquyen`
--

INSERT INTO `chitietquyen` (`MAQUYEN`, `MACN`) VALUES
('admin', 'CN01'),
('admin', 'CN02'),
('admin', 'CN03'),
('admin', 'CN04'),
('admin', 'CN05'),
('admin', 'CN06'),
('admin', 'CN07'),
('admin', 'CN08'),
('admin', 'CN09'),
('admin', 'CN10'),
('admin', 'CN11'),
('nhanvien', 'CN01'),
('nhanvien', 'CN03'),
('nhanvien', 'CN07'),
('nhanvien', 'CN09'),
('nhanvien', 'CN10'),
('quanly', 'CN02'),
('quanly', 'CN03'),
('quanly', 'CN04'),
('quanly', 'CN05'),
('quanly', 'CN06'),
('quanly', 'CN07'),
('quanly', 'CN08'),
('quanly', 'CN09');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chucnang`
--

CREATE TABLE `chucnang` (
  `MACN` varchar(20) NOT NULL,
  `TENCN` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chucnang`
--

INSERT INTO `chucnang` (`MACN`, `TENCN`) VALUES
('CN01', 'Bán hàng'),
('CN02', 'Nhập hàng'),
('CN03', 'Quản lý bán hàng'),
('CN04', 'Quản lý nhập hàng'),
('CN05', 'Quản lý sản phẩm'),
('CN06', 'Quản lý nhân viên'),
('CN07', 'Quản lý khách hàng'),
('CN08', 'Quản lý nhà cung cấp'),
('CN09', 'Thống kê'),
('CN10', 'Quản lý tài khoản'),
('CN11', 'Quản lý phân quyền');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `MAHD` varchar(20) NOT NULL,
  `MAKH` varchar(20) NOT NULL,
  `MANV` varchar(20) NOT NULL,
  `NGAYHD` varchar(20) NOT NULL,
  `TONGTIEN` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`MAHD`, `MAKH`, `MANV`, `NGAYHD`, `TONGTIEN`) VALUES
('HD001', '004', '001', '2022-05-19 19:58:20', 91980000),
('HD002', '004', '001', '2022-05-19 19:58:43', 22980000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MAKH` varchar(20) NOT NULL,
  `HOKH` varchar(20) NOT NULL,
  `TENKH` varchar(20) NOT NULL,
  `GIOITINH` varchar(20) NOT NULL,
  `DIENTHOAI` varchar(20) NOT NULL,
  `DIACHI` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MAKH`, `HOKH`, `TENKH`, `GIOITINH`, `DIENTHOAI`, `DIACHI`) VALUES
('001', 'Le', 'Khoi', 'Nam', '0775132615', '88 An Duong Vuong'),
('002', 'Nguyen', 'Anh', 'Nam', '0771562161', '9 Ly Chinh Thang'),
('003', 'Tran', 'B', 'Nữ', '0135846846', '9 Phan Dinh Phung'),
('004', 'Ly', 'C', 'Nữ', '0994613158', '856 Hai Ba Trung'),
('005', 'Nguyen', 'D', 'Nam', '0131256432', '32 Ba Thang Hai');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MANCC` varchar(20) NOT NULL,
  `TENNCC` varchar(40) NOT NULL,
  `DIACHI` varchar(40) NOT NULL,
  `DIENTHOAI` varchar(40) NOT NULL,
  `SOFAX` char(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`MANCC`, `TENNCC`, `DIACHI`, `DIENTHOAI`, `SOFAX`) VALUES
('NCC1', 'ABC', '55 Nguyễn Văn Cừ', '0777755555', '700000'),
('NCC2', 'XYZ', '5 Hai Bà Trưng', '0778855555', '600000'),
('NCC3', 'GHI', '5 Nguyễn Thị Minh Khai', '0777799955', '500000'),
('NCC4', 'DEF', '6 Nguyễn Trãi', '0777764555', '900000'),
('NCC5', 'LMN', '77 An Dương Vương', '0777756655', '800000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MANV` varchar(20) NOT NULL,
  `HONV` varchar(30) NOT NULL,
  `TENNV` varchar(10) NOT NULL,
  `NAMSINH` int(10) NOT NULL,
  `GIOITINH` varchar(10) NOT NULL,
  `DIENTHOAI` varchar(20) NOT NULL,
  `DIACHI` varchar(30) NOT NULL,
  `LUONG` double NOT NULL,
  `IMG` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`MANV`, `HONV`, `TENNV`, `NAMSINH`, `GIOITINH`, `DIENTHOAI`, `DIACHI`, `LUONG`, `IMG`) VALUES
('001', 'Lê Đăng', 'Khôi', 2002, 'Nam', '0775132615', '88 An Dương Vương', 10000000, 'NV001.png'),
('002', 'Trần Hoàng', 'Khải', 2001, 'Nam', '0193759235', '188 Nguyễn Huệ', 12000000, 'NV002.png'),
('003', 'Trần', 'Thư', 2000, 'Nữ', '0135846846', '9 Phan Đình Phùng', 10000000, 'NV003.png'),
('004', 'Lý Tiểu', 'Nhi', 2003, 'Nữ', '0994613158', '856 Hai Bà Trưng', 10000000, 'NV004.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhaphang`
--

CREATE TABLE `nhaphang` (
  `MANH` varchar(20) NOT NULL,
  `MANCC` varchar(20) NOT NULL,
  `MANV` varchar(20) NOT NULL,
  `NGAYNHAP` varchar(20) NOT NULL,
  `TONGTIEN` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhaphang`
--

INSERT INTO `nhaphang` (`MANH`, `MANCC`, `MANV`, `NGAYNHAP`, `TONGTIEN`) VALUES
('NH3', 'NCC3', '003', '2022-04-28 02:43:17', 1549900000),
('NH4', 'NCC4', '004', '2022-04-28 02:43:22', 114900000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhasanxuat`
--

CREATE TABLE `nhasanxuat` (
  `MANSX` varchar(20) NOT NULL,
  `TENNSX` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhasanxuat`
--

INSERT INTO `nhasanxuat` (`MANSX`, `TENNSX`) VALUES
('AS', 'Asanzo'),
('CP', 'Casper'),
('LG', 'LG'),
('SN', 'Sony'),
('SS', 'Samsung');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phanloai`
--

CREATE TABLE `phanloai` (
  `MALOAI` varchar(20) NOT NULL,
  `TENLOAI` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `phanloai`
--

INSERT INTO `phanloai` (`MALOAI`, `TENLOAI`) VALUES
('001', 'LED TV'),
('002', 'OLED TV'),
('003', 'Smart TV'),
('004', 'Android TV'),
('005', 'QLED TV');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quyen`
--

CREATE TABLE `quyen` (
  `MAQUYEN` varchar(20) NOT NULL,
  `TENQUYEN` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `quyen`
--

INSERT INTO `quyen` (`MAQUYEN`, `TENQUYEN`) VALUES
('admin', 'Admin'),
('nhanvien', 'Nhân viên'),
('quanly', 'Quản lý');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MASP` varchar(20) NOT NULL,
  `TENSP` varchar(60) NOT NULL,
  `KICHTHUOC` varchar(20) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `GIASP` double NOT NULL,
  `MALOAI` varchar(20) NOT NULL,
  `MANSX` varchar(20) NOT NULL,
  `IMG` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MASP`, `TENSP`, `KICHTHUOC`, `SOLUONG`, `GIASP`, `MALOAI`, `MANSX`, `IMG`) VALUES
('TV1', 'Tivi ELED Smart Asanzo 65 inch', '65 inch', 10, 16990000, '003', 'AS', 'TV1.png'),
('TV2', 'Smart Tivi OLED LG 4K 65 inch', '65 inch', 10, 41990000, '002', 'LG', 'TV2.png'),
('TV3', 'Smart Tivi Neo QLED Samsung 8K 85 inch', '85 inch', 10, 154990000, '005', 'SS', 'TV3.png'),
('TV4', 'Android Tivi QLED Casper 4K 55 inch', '55 inch', 10, 11490000, '004', 'CP', 'TV4.png'),
('TV5', 'Android Tivi OLED Sony 4K 55 inch', '55 inch', 10, 45990000, '002', 'SN', 'TV5.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `USERID` varchar(20) NOT NULL,
  `USERNAME` varchar(30) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `ROLEID` varchar(20) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `ENABLE` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`USERID`, `USERNAME`, `PASSWORD`, `ROLEID`, `EMAIL`, `ENABLE`) VALUES
('001', 'admin', 'admin', 'admin', 'admin@gmail.com', '1'),
('003', 'nhanvien', 'nhanvien', 'admin', 'nhanvien@gmail.com', '1');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitiethd`
--
ALTER TABLE `chitiethd`
  ADD PRIMARY KEY (`MAHD`,`MASP`),
  ADD KEY `FK_MASP_CTHD` (`MASP`),
  ADD KEY `FK_MAHD` (`MAHD`) USING BTREE;

--
-- Chỉ mục cho bảng `chitietnh`
--
ALTER TABLE `chitietnh`
  ADD PRIMARY KEY (`MANH`,`MASP`),
  ADD KEY `FK_MANH` (`MANH`) USING BTREE,
  ADD KEY `FK_MASP_CTNH` (`MASP`);

--
-- Chỉ mục cho bảng `chitietquyen`
--
ALTER TABLE `chitietquyen`
  ADD PRIMARY KEY (`MAQUYEN`,`MACN`),
  ADD KEY `FK_MAQUYEN` (`MAQUYEN`) USING BTREE,
  ADD KEY `FK_MACN` (`MACN`);

--
-- Chỉ mục cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  ADD PRIMARY KEY (`MACN`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MAHD`),
  ADD KEY `FK_MANV` (`MANV`) USING BTREE,
  ADD KEY `FK_MAKH` (`MAKH`) USING BTREE;

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MAKH`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MANCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MANV`);

--
-- Chỉ mục cho bảng `nhaphang`
--
ALTER TABLE `nhaphang`
  ADD PRIMARY KEY (`MANH`),
  ADD KEY `FK_MANV` (`MANV`) USING BTREE,
  ADD KEY `FKNCC` (`MANCC`);

--
-- Chỉ mục cho bảng `nhasanxuat`
--
ALTER TABLE `nhasanxuat`
  ADD PRIMARY KEY (`MANSX`);

--
-- Chỉ mục cho bảng `phanloai`
--
ALTER TABLE `phanloai`
  ADD PRIMARY KEY (`MALOAI`);

--
-- Chỉ mục cho bảng `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MAQUYEN`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MASP`),
  ADD KEY `FK_MALOAI` (`MALOAI`),
  ADD KEY `FK_MANSX` (`MANSX`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`USERID`),
  ADD KEY `FK_USERID` (`USERID`) USING BTREE,
  ADD KEY `FK_ROLEID` (`ROLEID`) USING BTREE;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitiethd`
--
ALTER TABLE `chitiethd`
  ADD CONSTRAINT `FK_MAHD` FOREIGN KEY (`MAHD`) REFERENCES `hoadon` (`MAHD`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MASP_CTHD` FOREIGN KEY (`MASP`) REFERENCES `sanpham` (`MASP`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `chitietnh`
--
ALTER TABLE `chitietnh`
  ADD CONSTRAINT `FK_MANH` FOREIGN KEY (`MANH`) REFERENCES `nhaphang` (`MANH`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MASP_CTNH` FOREIGN KEY (`MASP`) REFERENCES `sanpham` (`MASP`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `chitietquyen`
--
ALTER TABLE `chitietquyen`
  ADD CONSTRAINT `FK_MACN` FOREIGN KEY (`MACN`) REFERENCES `chucnang` (`MACN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MAQUYEN` FOREIGN KEY (`MAQUYEN`) REFERENCES `quyen` (`MAQUYEN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `FK_MAKH` FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MANV` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `nhaphang`
--
ALTER TABLE `nhaphang`
  ADD CONSTRAINT `FKMANV` FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MANV`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKNCC` FOREIGN KEY (`MANCC`) REFERENCES `nhacungcap` (`MANCC`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `FK_MALOAI` FOREIGN KEY (`MALOAI`) REFERENCES `phanloai` (`MALOAI`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MANSX` FOREIGN KEY (`MANSX`) REFERENCES `nhasanxuat` (`MANSX`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_ROLEID` FOREIGN KEY (`ROLEID`) REFERENCES `quyen` (`MAQUYEN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_USERID` FOREIGN KEY (`USERID`) REFERENCES `nhanvien` (`MANV`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
