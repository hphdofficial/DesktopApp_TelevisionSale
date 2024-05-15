package DAO;

import DTO.HoaDonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDonDAO {
    private MySQLConnect mysql = new MySQLConnect();

    public HoaDonDAO() {

    }

    public ArrayList<HoaDonDTO> list() {
        ArrayList<HoaDonDTO> listbill = new ArrayList<>();

        try {
            String sql = "SELECT * FROM HOADON";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maHD = rs.getString("MAHD");
                String maKH = rs.getString("MAKH");
                String maNV = rs.getString("MANV");
                String ngayHD = rs.getString("NGAYHD");
                double tongTien = rs.getDouble("TONGTIEN");

                HoaDonDTO billDTO = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
                listbill.add(billDTO);
            }
            rs.close();
            mysql.disConnect();

        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listbill;
    }

    public void add(HoaDonDTO bill) {
        String maKH = bill.getMaKH().equals("") ? null : "'" + bill.getMaKH() + "'";
        String sql = "INSERT INTO HOADON VALUES('" + bill.getMaHD() + "',"
                + "" + maKH + ", "
                + "'" + bill.getMaNV() + "', "
                + "'" + bill.getNgayHD() + "', "
                + "'" + bill.getTongTien() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(HoaDonDTO bill) {
        String maKH = bill.getMaKH().equals("") ? null : "'" + bill.getMaKH() + "'";
        String sql = "UPDATE HOADON SET MAKH = '" + maKH + "', "
                + " MANV = '" + bill.getMaNV() + "', "
                + " NGAYHD ='" + bill.getNgayHD() + "', "
                + " TONGTIEN ='" + bill.getTongTien() + "' WHERE MAHD = '" + bill.getMaHD() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String maHD) {
        String sql = "DELETE FROM HOADON WHERE MAHD = '" + maHD + "'";
        mysql.executeUpdate(sql);
    }
}