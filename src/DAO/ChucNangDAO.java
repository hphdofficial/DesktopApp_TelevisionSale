package DAO;

import DTO.ChucNangDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChucNangDAO {
    MySQLConnect mysql = new MySQLConnect();

    public ChucNangDAO() {

    }

    public ArrayList<ChucNangDTO> list() {
        ArrayList<ChucNangDTO> functionlist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM CHUCNANG";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maCN = rs.getString("MACN");
                String tenCN = rs.getString("TENCN");

                ChucNangDTO cn = new ChucNangDTO(maCN, tenCN);
                functionlist.add(cn);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(ChucNangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return functionlist;
    }

    public void add(ChucNangDTO cn) {
        String sql = "INSERT INTO CHUCNANG VALUES('" + cn.getMaCN() + "', " + "'" + cn.getTenCN() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(ChucNangDTO cn) {
        String sql = "UPDATE CHUCNANG SET TENCN = '" + cn.getTenCN() + "' WHERE MACN = '" + cn.getMaCN() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maCN) {
        String sql = "DELETE FROM CHUCNANG WHERE MACN = '" + maCN + "'";
        mysql.executeUpdate(sql);
    }
}