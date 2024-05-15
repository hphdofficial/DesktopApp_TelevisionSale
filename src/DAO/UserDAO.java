package DAO;

import DTO.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    MySQLConnect mysql = new MySQLConnect();

    public UserDAO() {

    }

    public ArrayList<UserDTO> list() {
        ArrayList<UserDTO> userlist = new ArrayList<>();
        try {
            String sql = "SELECT * FROM USER";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String userID = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String email = rs.getString("EMAIL");
                String role = rs.getString("ROLEID");
                String enable = rs.getString("ENABLE");

                UserDTO uselist = new UserDTO(userID, userName, password, email, role, enable);
                userlist.add(uselist);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userlist;
    }

    public void add(UserDTO user) {
        String sql = "INSERT INTO USER VALUES('" + user.getUserID() + "', "
                + "'" + user.getUserName() + "', "
                + "'" + user.getPassWord() + "', "
                + "'" + user.getEmail() + "', "
                + "'" + user.getRoleID() + "',"
                + "'" + user.getEnable() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(UserDTO user) {
        String sql = "UPDATE USER SET USERNAME = '" + user.getUserName() + "', "
                + " PASSWORD = '" + user.getPassWord() + "', "
                + " EMAIL = '" + user.getEmail() + "', "
                + " ROLEID = '" + user.getRoleID() + "', "
                + " ENABLE = '" + user.getEnable() + "' WHERE USERID = '" + user.getUserID() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String userid) {
        String sql = "DELETE FROM USER WHERE USERID = '" + userid + "'";
        mysql.executeUpdate(sql);
    }
}