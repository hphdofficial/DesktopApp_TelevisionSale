package DAO;

import DTO.NhapHangDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhapHangDAO {
    MySQLConnect mysql = new MySQLConnect();

    public NhapHangDAO() {

    }

    public ArrayList<NhapHangDTO> list() {
        ArrayList<NhapHangDTO> orderlist = new ArrayList<>();
        try {
            String sql = "SELECT * FROM NHAPHANG";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNH = rs.getString("MANH");
                String maNCC = rs.getString("MANCC");
                String maNV = rs.getString("MANV");
                String ngayNhap = rs.getString("NGAYNHAP");
                double tongTien = rs.getDouble("TONGTIEN");

                NhapHangDTO order = new NhapHangDTO(maNH, maNCC, maNV, ngayNhap, tongTien);
                orderlist.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderlist;
    }

    public void add(NhapHangDTO nh) {
        String sql = "INSERT INTO NHAPHANG VALUES('" + nh.getMaNH() + "', "
                + "'" + nh.getMaNCC() + "', "
                + "'" + nh.getMaNV() + "', "
                + "'" + nh.getNgayNhap() + "', "
                + "'" + nh.getTongTien() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(NhapHangDTO nh) {
        String sql = "UPDATE NHAPHANG SET MANCC = '" + nh.getMaNCC() + "', "
                + " MANV = '" + nh.getMaNV() + "', "
                + " NGAYNHAP = '" + nh.getNgayNhap() + "', "
                + " TONGTIEN = '" + nh.getTongTien() + "' WHERE MANH = '" + nh.getMaNH() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String maNH) {
        String sql = "DELETE FROM NHAPHANG WHERE MANH = '" + maNH + "'";
        mysql.executeUpdate(sql);
    }
}