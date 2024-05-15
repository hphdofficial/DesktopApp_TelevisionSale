package DAO;

import DTO.ChiTietNHDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietNHDAO {
    MySQLConnect mysql = new MySQLConnect();

    public ChiTietNHDAO() {

    }

    public ArrayList<ChiTietNHDTO> list() {
        ArrayList<ChiTietNHDTO> detailorderlist = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CHITIETNH";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNH = rs.getString("MANH");
                String maSP = rs.getString("MASP");
                String tenSP = rs.getString("TENSP");
                int soLuong = rs.getInt("SOLUONG");
                double donGia = rs.getDouble("DONGIA");

                ChiTietNHDTO detailorder = new ChiTietNHDTO(maNH, maSP, tenSP, soLuong, donGia);
                detailorderlist.add(detailorder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailorderlist;
    }

    public void add(ChiTietNHDTO ctnh) {
        String sql = "INSERT INTO CHITIETNH VALUES('" + ctnh.getMaNH() + "', "
                + "'" + ctnh.getMaSP() + "', "
                + "'" + ctnh.getTenSP() + "', "
                + "'" + ctnh.getSoLuong() + "', "
                + "'" + ctnh.getDonGia() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(ChiTietNHDTO ctnh) {
        String sql = "UPDATE CHITIETNH SET TENSP = '" + ctnh.getTenSP() + "', "
                + " SOLUONG = '" + ctnh.getSoLuong() + "', "
                + " DONGIA = '" + ctnh.getDonGia() + "'"
                + " WHERE MANH = '" + ctnh.getMaNH() + "' AND MASP = '" + ctnh.getMaSP() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String MaNH) {
        String sql = "DELETE FROM CHITIETNH WHERE MANH = '" + MaNH + "'";
        mysql.executeUpdate(sql);
    }

    public void deleteSP(String MaNH, String MaSP) {
        String sql = "DELETE FROM CHITIETNH WHERE MANH = '" + MaNH + "' AND MASP = '" + MaSP + "'";
        mysql.executeUpdate(sql);
    }
}