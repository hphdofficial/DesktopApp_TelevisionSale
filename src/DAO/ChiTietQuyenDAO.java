package DAO;

import DTO.ChiTietQuyenDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietQuyenDAO {
    MySQLConnect mysql = new MySQLConnect();

    public ChiTietQuyenDAO() {

    }

    public ArrayList<ChiTietQuyenDTO> list() {
        ArrayList<ChiTietQuyenDTO> detailrolelist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM CHITIETQUYEN";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maQuyen = rs.getString("MAQUYEN");
                String maCN = rs.getString("MACN");

                ChiTietQuyenDTO ctquyen = new ChiTietQuyenDTO(maQuyen, maCN);
                detailrolelist.add(ctquyen);
            }
            rs.close();
            mysql.disConnect();

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietQuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailrolelist;
    }

    public void add(ChiTietQuyenDTO ctquyen) {
        String sql = "INSERT INTO CHITIETQUYEN VALUES ('" + ctquyen.getMaQuyen() + "'," + "'" + ctquyen.getMaCN() + "') ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maQuyen) {
        String sql = "DELETE FROM CHITIETQUYEN WHERE MAQUYEN = '" + maQuyen + "'";
        mysql.executeUpdate(sql);
    }
}