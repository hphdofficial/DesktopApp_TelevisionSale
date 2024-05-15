package DAO;

import DTO.ChiTietHDDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietHDDAO {
    MySQLConnect mysql = new MySQLConnect();

    public ChiTietHDDAO() {

    }

    public ArrayList<ChiTietHDDTO> list() {
        ArrayList<ChiTietHDDTO> detaillistbill = new ArrayList<>();

        try {
            String sql = "SELECT * FROM CHITIETHD";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maHD = rs.getString("MAHD");
                String maSP = rs.getString("MASP");
                String tenSP = rs.getString("TENSP");
                int soLuong = rs.getInt("SOLUONG");
                double donGia = rs.getDouble("DONGIA");

                ChiTietHDDTO cthd = new ChiTietHDDTO(maHD, maSP, tenSP, soLuong, donGia);
                detaillistbill.add(cthd);
            }
            rs.close();
            mysql.disConnect();

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detaillistbill;
    }

    public void add(ChiTietHDDTO cthd) {
        String sql = "INSERT INTO CHITIETHD VALUES ('" + cthd.getMaHD() + "',"
                + "'" + cthd.getMaSP() + "', "
                + "'" + cthd.getTenSP() + "', "
                + "'" + cthd.getSoLuong() + "', "
                + "'" + cthd.getDonGia() + "') ";
        mysql.executeUpdate(sql);
    }

    public void update(ChiTietHDDTO cthd) {
        String sql = "UPDATE CHITIETHD SET TENSP = '" + cthd.getTenSP() + "', "
                + " SOLUONG = '" + cthd.getSoLuong() + "', "
                + " DONGIA = '" + cthd.getDonGia() + "'"
                + " WHERE MAHD = '" + cthd.getMaHD() + "' AND MASP = '" + cthd.getMaSP() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String MaHD) {
        String sql = "DELETE FROM CHITIETHD WHERE MAHD = '" + MaHD + "'";
        mysql.executeUpdate(sql);
    }

    public void deleteSP(String MaHD, String MaSP) {
        String sql = "DELETE FROM CHITIETHD WHERE MAHD = '" + MaHD + "' AND MASP = '" + MaSP + "'";
        mysql.executeUpdate(sql);
    }
}