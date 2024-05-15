package DAO;

import DTO.NhaSanXuatDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaSanXuatDAO {
    MySQLConnect mysql = new MySQLConnect();

    public NhaSanXuatDAO() {

    }

    public ArrayList<NhaSanXuatDTO> list() {
        ArrayList<NhaSanXuatDTO> producerlist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM NHASANXUAT";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNSX = rs.getString("MANSX");
                String tenNSX = rs.getString("TENNSX");

                NhaSanXuatDTO prolist = new NhaSanXuatDTO(maNSX, tenNSX);
                producerlist.add(prolist);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhaSanXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producerlist;
    }

    public void add(NhaSanXuatDTO nsx) {
        String sql = "INSERT INTO NHASANXUAT VALUES('" + nsx.getMaNSX() + "', " + "'" + nsx.getTenNSX() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(NhaSanXuatDTO nsx) {
        String sql = "UPDATE NHASANXUAT SET TENNSX = '" + nsx.getTenNSX() + "' WHERE MANSX = '" + nsx.getMaNSX() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maNSX) {
        String sql = "DELETE FROM NHASANXUAT WHERE MANSX = '" + maNSX + "'";
        mysql.executeUpdate(sql);
    }
}