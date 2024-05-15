package DAO;

import DTO.KhachHangDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {
    MySQLConnect mysql = new MySQLConnect();

    public KhachHangDAO() {

    }

    public ArrayList<KhachHangDTO> list() {
        ArrayList<KhachHangDTO> customerlist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM KHACHHANG";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maKH = rs.getString("MAKH");
                String hoKH = rs.getString("HOKH");
                String tenKH = rs.getString("TENKH");
                String gioiTinh = rs.getString("GIOITINH");
                String dienThoai = rs.getString("DIENTHOAI");
                String diaChi = rs.getString("DIACHI");

                KhachHangDTO khlist = new KhachHangDTO(maKH, hoKH, tenKH, gioiTinh, dienThoai, diaChi);
                customerlist.add(khlist);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerlist;
    }

    public void add(KhachHangDTO kh) {
        String sql = "INSERT INTO KHACHHANG VALUES('" + kh.getMaKH() + "', "
                + "'" + kh.getHoKH() + "', "
                + "'" + kh.getTenKH() + "', "
                + "'" + kh.getGioiTinh() + "', "
                + "'" + kh.getDienThoai() + "', "
                + "'" + kh.getDiaChi() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(KhachHangDTO kh) {
        String sql = "UPDATE KHACHHANG SET HOKH = '" + kh.getHoKH() + "', "
                + " TENKH = '" + kh.getTenKH() + "', "
                + " GIOITINH = '" + kh.getGioiTinh() + "', "
                + " DIENTHOAI = '" + kh.getDienThoai() + "', "
                + " DIACHI = '" + kh.getDiaChi() + "' WHERE MAKH = '" + kh.getMaKH() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maKH) {
        String sql = "DELETE FROM KHACHHANG WHERE MAKH = '" + maKH + "'"; // or UPDATE KHACHANG SET enable = 0 WHERE MAKH = ?
        mysql.executeUpdate(sql);
    }

    public void ImportExcelDatabase(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                String makh = row.getCell(0).getStringCellValue();
                String hokh = row.getCell(1).getStringCellValue();
                String tenkh = row.getCell(2).getStringCellValue();
                String gt = row.getCell(3).getStringCellValue();
                String dt = row.getCell(4).getStringCellValue();
                String diachi = row.getCell(5).getStringCellValue();

                String sql_check = "SELECT * FROM KHACHHANG WHERE MAKH = '" + makh + "'";
                ResultSet rs = mysql.executeQuery(sql_check);
                if (!rs.next()) {
                    String sql = "INSERT INTO KHACHHANG VALUES (";
                    sql += "N'" + makh + "',";
                    sql += "N'" + hokh + "',";
                    sql += "N'" + tenkh + "',";
                    sql += "N'" + gt + "',";
                    sql += "N'" + dt + "',";
                    sql += "N'" + diachi + "')";
                    mysql.executeUpdate(sql);
                } else {
                    String sql = "UPDATE KHACHHANG SET ";
                    sql += "HOKH='" + hokh + "', ";
                    sql += "TENKH='" + tenkh + "', ";
                    sql += "GIOITINH='" + gt + "', ";
                    sql += "DIENTHOAI='" + dt + "', ";
                    sql += "DIACHI='" + diachi + "', ";
                    sql += "WHERE MAKH='" + makh + "'";
                    mysql.executeUpdate(sql);
                }
            }
            in.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExportExcelDatabase() {
        try {
            String sql = "SELECT * FROM KHACHHANG";
            ResultSet rs = mysql.executeQuery(sql);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("KhachHangData");

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue("MAKH");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("HOKH");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("TENKH");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("GIOITINH");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("DIENTHOAI");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("DIACHI");
            cell.setCellStyle(style);
            int i = 1;

            while (rs.next()) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(rs.getString("MAKH"));
                cell = row.createCell(1);
                cell.setCellValue(rs.getString("HOKH"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getString("TENKH"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getString("GIOITINH"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getString("DIENTHOAI"));
                cell = row.createCell(5);
                cell.setCellValue(rs.getString("DIACHI"));
                i++;
            }

            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.autoSizeColumn((short) (colNum));
            }

            FileOutputStream out = new FileOutputStream(new File("./report/KhachHangData.xlsx"));
            workbook.write(out);
            out.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}