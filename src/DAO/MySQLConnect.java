package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnect {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlytivi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private Connection connect = null;
    private Statement st = null;

    public void Connect() {
        try {
            Class.forName(DB_DRIVER);
            connect = (Connection) DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            if (connect != null) {
                System.out.println("DAO.MySQLConnect.Connect()");
            } else {
                System.out.println("DAO.MySQLConnect.Fail()");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disConnect() {
        try {
            st.close();
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Connect();
            st = connect.createStatement();
            rs = st.executeQuery(sql);
//            disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void executeUpdate(String sql) {
        try {
            Connect();
            st = connect.createStatement();
            st.executeUpdate(sql);
            disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        Connect();
        return connect;
    }

    public boolean isConnect() {
        return connect != null;
    }
}