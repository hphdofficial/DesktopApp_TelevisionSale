package DAO;

import DTO.NhaCungCapDTO;
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

public class NhaCungCapDAO {
    MySQLConnect mysql = new MySQLConnect();

    public NhaCungCapDAO() {

    }

    public ArrayList<NhaCungCapDTO> list() {
        ArrayList<NhaCungCapDTO> supplierlist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM NHACUNGCAP";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNCC = rs.getString("MANCC");
                String tenNCC = rs.getString("TENNCC");
                String diaChi = rs.getString("DIACHI");
                String dienThoai = rs.getString("DIENTHOAI");
                String soFax = rs.getString("SOFAX");

                NhaCungCapDTO supplist = new NhaCungCapDTO(maNCC, tenNCC, diaChi, dienThoai, soFax);
                supplierlist.add(supplist);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return supplierlist;
    }

    public void add(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO NHACUNGCAP VALUES('" + ncc.getMaNCC() + "', "
                + "'" + ncc.getTenNCC() + "', "
                + "'" + ncc.getDiaChi() + "', "
                + "'" + ncc.getDienThoai() + "', "
                + "'" + ncc.getSoFax() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(NhaCungCapDTO ncc) {
        String sql = "UPDATE NHACUNGCAP SET TENNCC = '" + ncc.getTenNCC() + "', "
                + " DIACHI = '" + ncc.getDiaChi() + "', "
                + " DIENTHOAI = '" + ncc.getDienThoai() + "', "
                + " SOFAX = '" + ncc.getSoFax() + "' WHERE MANCC = '" + ncc.getMaNCC() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maNCC) {
        String sql = "DELETE FROM NHACUNGCAP WHERE MANCC = '" + maNCC + "'"; // or UPDATE NHACUNGCAP SET enable = 0 WHERE MANCC = ?
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
                String mancc = row.getCell(0).getStringCellValue();
                String tenncc = row.getCell(1).getStringCellValue();
                String diachi = row.getCell(2).getStringCellValue();
                String dt = row.getCell(3).getStringCellValue();
                String sofax = row.getCell(4).getStringCellValue();

                String sql_check = "SELECT * FROM NHACUNGCAP WHERE MANCC = '" + mancc + "'";
                ResultSet rs = mysql.executeQuery(sql_check);
                if (!rs.next()) {
                    String sql = "INSERT INTO NHACUNGCAP VALUES (";
                    sql += "N'" + mancc + "',";
                    sql += "N'" + tenncc + "',";
                    sql += "N'" + diachi + "',";
                    sql += "N'" + dt + "',";
                    sql += "N'" + sofax + "')";
                    mysql.executeUpdate(sql);
                } else {
                    String sql = "UPDATE NHACUNGCAP SET ";
                    sql += "TENNCC='" + tenncc + "', ";
                    sql += "DIACHI='" + diachi + "', ";
                    sql += "DIENTHOAI='" + dt + "', ";
                    sql += "SOFAX='" + sofax + "', ";
                    sql += "WHERE MANCC='" + mancc + "'";
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
            String sql = "SELECT * FROM NHACUNGCAP";
            ResultSet rs = mysql.executeQuery(sql);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("NhaCungCapData");

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue("MANCC");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("TENNCC");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("DIACHI");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("DIENTHOAI");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("SOFAX");
            cell.setCellStyle(style);
            int i = 1;

            while (rs.next()) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(rs.getString("MANCC"));
                cell = row.createCell(1);
                cell.setCellValue(rs.getString("TENNCC"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getString("DIACHI"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getString("DIENTHOAI"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getString("SOFAX"));
                i++;
            }

            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.autoSizeColumn((short) (colNum));
            }

            FileOutputStream out = new FileOutputStream(new File("./report/NhaCungCapData.xlsx"));
            workbook.write(out);
            out.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}