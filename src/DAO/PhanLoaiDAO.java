package DAO;

import DTO.PhanLoaiDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhanLoaiDAO {
    MySQLConnect mysql = new MySQLConnect();

    public PhanLoaiDAO() {

    }

    public ArrayList<PhanLoaiDTO> list() {
        ArrayList<PhanLoaiDTO> typelist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PHANLOAI";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maLoai = rs.getString("MALOAI");
                String tenLoai = rs.getString("TENLOAI");

                PhanLoaiDTO tylist = new PhanLoaiDTO(maLoai, tenLoai);
                typelist.add(tylist);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhaSanXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typelist;
    }

    public void add(PhanLoaiDTO type) {
        String sql = "INSERT INTO PHANLOAI VALUES('" + type.getMaLoai() + "', " + "'" + type.getTenLoai() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(PhanLoaiDTO type) {
        String sql = "UPDATE PHANLOAI SET TENLOAI = '" + type.getTenLoai() + "' WHERE MALOAI = '" + type.getMaLoai() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maLoai) {
        String sql = "DELETE FROM PHANLOAI WHERE MALOAI = '" + maLoai + "'";
        mysql.executeUpdate(sql);
    }
}