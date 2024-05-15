package DAO;

import DTO.HoaDonDTO;
import DTO.NhapHangDTO;
import DTO.ThongKeDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {
    NumberFormat num = NumberFormat.getInstance();

    public ThongKeDAO() {

    }

    public String ThongKeSP(ArrayList<HoaDonDTO> listbill, ArrayList<NhapHangDTO> listorder, String IDProduct) {
        String s = "";
        int amountIn = 0, amountOut = 0;
        double sumIn = 0, sumOut = 0;
        try {
            MySQLConnect mySQL = new MySQLConnect();

            if (!listbill.isEmpty()) {
                String sqlBill = "SELECT SUM(SOLUONG) AS SOLUONGBAN, SUM(SOLUONG * DONGIA) AS TONGTIENBAN FROM CHITIETHD WHERE (";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sqlBill += "MAHD = '" + IDBill + "') ";
                        break;
                    }
                    sqlBill += "MAHD = '" + IDBill + "' OR ";
                }
                sqlBill += "AND MASP = '" + IDProduct + "' ";
                sqlBill += "GROUP BY MAHD";
                ResultSet rs = mySQL.executeQuery(sqlBill);
                while (rs.next()) {
                    amountIn += rs.getInt("SOLUONGBAN");
                    sumIn += rs.getInt("TONGTIENBAN");

                }
            }

            if (!listorder.isEmpty()) {
                String sqlOrder = "SELECT SUM(SOLUONG) AS SOLUONGNHAP, SUM(SOLUONG*DONGIA) AS TONGTIENNHAP FROM CHITIETNH WHERE (";
                for (int i = 0; i < listorder.size(); i++) {
                    String IDOrder = listorder.get(i).getMaNH();
                    if (i == (listorder.size() - 1)) {
                        sqlOrder += "MANH = '" + IDOrder + "') ";
                        break;
                    }
                    sqlOrder += "MANH = '" + IDOrder + "' OR ";
                }
                sqlOrder += "AND MASP = '" + IDProduct + "' ";
                sqlOrder += "GROUP BY MANH";
                ResultSet rs = mySQL.executeQuery(sqlOrder);
                while (rs.next()) {
                    amountIn += rs.getInt("SOLUONGNHAP");
                    sumIn += rs.getInt("TONGTIENNHAP");
                }
            }

            if (mySQL.isConnect()) mySQL.disConnect();

            s += "Số lượng bán: " + num.format(amountOut) + "\t|| Số lượng nhập: " + num.format(amountIn) + "\n";
            s += "Tổng tiền bán: " + num.format(sumOut) + "\t|| Tổng tiền nhập: " + num.format(sumIn) + "\n";
            s += "---------------------------------------------------------------------------------------\n";
            s += "TỔNG THU NHẬP: " + num.format(sumOut - sumIn) + " VNĐ" + "\n";
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public String ThongKeNV(ArrayList<HoaDonDTO> listbill, String IDStaff) {
        String s = "";
        int sum = 0;
        String listItem = String.format("|\t%s\t|\t%s\t|\n", "Mã sản phẩm", "Số lượng");
        if (!listbill.isEmpty()) {
            try {
                MySQLConnect mySQL = new MySQLConnect();
                String sqlStaff = "SELECT SUM(TONGTIEN) AS TONGTIEN FROM HOADON WHERE (";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size()) - 1) {
                        sqlStaff += "MAHD = '" + IDBill + "') ";
                        break;
                    }
                    sqlStaff += "MAHD = '" + IDBill + "' OR ";
                }
                sqlStaff += "AND MANV = '" + IDStaff + "' ";
                sqlStaff += "GROUP BY MANV";
                ResultSet rs1 = mySQL.executeQuery(sqlStaff);

                while (rs1.next()) {
                    sum += rs1.getInt("TONGTIEN");
                }

                String sqlProduct = "SELECT MASP, SUM(CHITIETHD.SOLUONG) AS AMOUNT FROM CHITIETHD WHERE CHITIETHD.MAHD IN(SELECT MAHD FROM HOADON WHERE (";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sqlProduct += "MAHD = '" + IDBill + "')";
                        break;
                    }
                    sqlProduct += "MAHD = '" + IDBill + "' OR ";
                }
                sqlProduct += "AND MANV = '" + IDStaff + "') ";
                sqlProduct += "GROUP BY MASP";
                ResultSet rs2 = mySQL.executeQuery(sqlProduct);
                while (rs2.next()) {
                    listItem += String.format("|\t%s\t|\t%s\t|\n", rs2.getString("MASP"), rs2.getString("AMOUNT"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s += listItem;
        s += "------------------------------------------------------------------------------ \n";
        s += "TỔNG THU NHẬP: " + num.format(sum) + " VNĐ" + "\n";
        return s;
    }

    public String ThongKeKH(ArrayList<HoaDonDTO> listbill, String IDCustomer) {
        String s = "";
        int sum = 0;
        String listItem = String.format("|%10s|%10s|\n", "ID Product", "Amount");
        if (!listbill.isEmpty()) {
            MySQLConnect mySQL = new MySQLConnect();
            try {
                // Tổng tiền 
                String sql1 = "SELECT SUM(TONGTIEN) AS TOTALBILL FROM hoadon WHERE (";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sql1 += "MAHD = '" + IDBill + "') ";
                        break;
                    }
                    sql1 += "MAHD = '" + IDBill + "' OR ";
                }
                sql1 += "AND MAKH = '" + IDCustomer + "' ";
                sql1 += "GROUP BY MAKH";
                ResultSet rs1 = mySQL.executeQuery(sql1);
                while (rs1.next()) {
                    sum += rs1.getInt("TOTALBILL");

                }

                // Mã SP || Số lượng 
                String sql2 = "SELECT MASP,SUM(CHITIETHD.SOLUONG) AS AMOUNT FROM CHITIETHD WHERE CHITIETHD.MAHD IN (SELECT MAHD FROM HOADON WHERE (";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sql2 += "MAHD = '" + IDBill + "') ";
                        break;
                    }
                    sql2 += "MAHD = '" + IDBill + "' OR ";
                }
                sql2 += "AND MAKH = '" + IDCustomer + "') ";
                sql2 += "GROUP BY MASP";
                System.out.println(sql2);
                ResultSet rs2 = mySQL.executeQuery(sql2);
                while (rs2.next()) {
                    listItem += String.format("|%10s|%10s|\n", rs2.getString("MASP"), rs2.getString("AMOUNT"));

                }

            } catch (SQLException ex) {
                Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s += listItem;
        s += "--------------------------------------------------- \n";
        s += "TỔNG TIỀN : " + sum + " VNĐ" + "\n";
        return s;
    }

    public ArrayList<String> ThongKeTopSP(ArrayList<HoaDonDTO> listbill) {
        ArrayList<String> kq = new ArrayList<>();
        if (!listbill.isEmpty()) {
            try {
                MySQLConnect mySQL = new MySQLConnect();
                String sql = "SELECT MASP,TENSP,SUM(SOLUONG) AS SOLUONG FROM chitiethd WHERE ";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sql += "MAHD ='" + IDBill + "' ";
                        break;
                    }
                    sql += "MAHD ='" + IDBill + "' OR ";
                }
                sql += "GROUP BY MASP ";
                sql += "ORDER BY SUM(SOLUONG) DESC ";
                sql += "LIMIT 5";
                ResultSet rs = mySQL.executeQuery(sql);
                while (rs.next()) {
                    String maSP = rs.getString("MASP");
                    String tenSP = rs.getString("TENSP");
                    int sl = rs.getInt("SOLUONG");
                    String s = String.format("%6s_%20s_%5d", maSP, tenSP, sl);
                    kq.add(s);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kq;
    }

    public ArrayList<String> ThongKeTopNV(ArrayList<HoaDonDTO> listbill) {
        ArrayList<String> kq = new ArrayList<>();
        if (!listbill.isEmpty()) {
            try {
                MySQLConnect mySQL = new MySQLConnect();
                String sql = "SELECT NHANVIEN.MANV, CONCAT(NHANVIEN.HONV,' ',NHANVIEN.TENNV) AS HOTEN ,SUM(TONGTIEN) AS TOTAL ";
                sql += "FROM HOADON INNER JOIN NHANVIEN ON HOADON.MANV = NHANVIEN.MANV WHERE ";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sql += "MAHD = '" + IDBill + "' ";
                        break;
                    }
                    sql += "MAHD = '" + IDBill + "' OR ";
                }
                sql += "GROUP BY MANV ";
                sql += "ORDER BY SUM(TONGTIEN) DESC ";
                sql += "LIMIT 5";
                ResultSet rs = mySQL.executeQuery(sql);
                while (rs.next()) {
                    String maNV = rs.getString("MANV");
                    String tenNV = rs.getString("HOTEN");
                    int tt = rs.getInt("TOTAL");
                    String s = String.format("%6s_%20s_%5d", maNV, tenNV, tt);
                    kq.add(s);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kq;
    }

    public ArrayList<String> ThongKeTopKH(ArrayList<HoaDonDTO> listbill) {
        ArrayList<String> kq = new ArrayList<>();
        if (!listbill.isEmpty()) {
            try {
                MySQLConnect mySQL = new MySQLConnect();
                String sql = "SELECT KHACHHANG.MAKH, CONCAT(KHACHHANG.HOKH,' ',KHACHHANG.TENKH) AS HOTEN ,SUM(TONGTIEN) AS TOTAL ";
                sql += "FROM hoadon INNER JOIN khachhang ON hoadon.MAKH = khachhang.MAKH WHERE ";
                for (int i = 0; i < listbill.size(); i++) {
                    String IDBill = listbill.get(i).getMaHD();
                    if (i == (listbill.size() - 1)) {
                        sql += "MAHD = '" + IDBill + "' ";
                        break;
                    }
                    sql += "MAHD = '" + IDBill + "' OR ";
                }
                sql += "GROUP BY MAKH ";
                sql += "ORDER BY SUM(TONGTIEN) DESC ";
                sql += "LIMIT 5";
                ResultSet rs = mySQL.executeQuery(sql);
                while (rs.next()) {
                    String maNV = rs.getString("MAKH");
                    String tenNV = rs.getString("HOTEN");
                    int tt = rs.getInt("TOTAL");
                    String s = String.format("%6s_%20s_%5d", maNV, tenNV, tt);
                    kq.add(s);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kq;
    }

    public int getTongSoLuongSP() {
        try {
            MySQLConnect mySQL = new MySQLConnect();
            ResultSet rs = mySQL.executeQuery("SELECT COUNT(*) FROM sanpham");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    public int getTongSoLuongKH() {
        try {
            MySQLConnect mySQL = new MySQLConnect();
            ResultSet rs = mySQL.executeQuery("SELECT COUNT(*) FROM khachhang");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    public int getTongSoLuongNV() {
        try {
            MySQLConnect mySQL = new MySQLConnect();
            ResultSet rs = mySQL.executeQuery("SELECT COUNT(*) FROM nhanvien");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    public int getTongSoLuongNCC() {
        try {
            MySQLConnect mySQL = new MySQLConnect();
            ResultSet rs = mySQL.executeQuery("SELECT COUNT(*) FROM NHACUNGCAP");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }
}