package DAO;

import DTO.QuyenDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuyenDAO {
    MySQLConnect mysql = new MySQLConnect();

    public QuyenDAO() {

    }

    public ArrayList<QuyenDTO> list() {
        ArrayList<QuyenDTO> rolelist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM QUYEN";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maQUYEN = rs.getString("MAQUYEN");
                String tenQUYEN = rs.getString("TENQUYEN");

                QuyenDTO quyen = new QuyenDTO(maQUYEN, tenQUYEN);
                rolelist.add(quyen);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(QuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rolelist;
    }

    public void add(QuyenDTO quyen) {
        String sql = "INSERT INTO QUYEN VALUES('" + quyen.getMaQuyen() + "', " + "'" + quyen.getTenQuyen() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(QuyenDTO quyen) {
        String sql = "UPDATE QUYEN SET TENQUYEN = '" + quyen.getTenQuyen() + "' WHERE MAQUYEN = '" + quyen.getMaQuyen() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maQUYEN) {
        String sql = "DELETE FROM QUYEN WHERE MAQUYEN = '" + maQUYEN + "'";
        mysql.executeUpdate(sql);
    }
}