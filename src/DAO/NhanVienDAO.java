package DAO;

import DTO.NhanVienDTO;
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

public class NhanVienDAO {
    MySQLConnect mysql = new MySQLConnect();

    public NhanVienDAO() {

    }

    public ArrayList<NhanVienDTO> list() {
        ArrayList<NhanVienDTO> stafflist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM NHANVIEN";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNV = rs.getString("MANV");
                String hoNV = rs.getString("HONV");
                String tenNV = rs.getString("TENNV");
                String gioiTinh = rs.getString("GIOITINH");
                String dienThoai = rs.getString("DIENTHOAI");
                String diaChi = rs.getString("DIACHI");
                String img = rs.getString("IMG");
                int namSinh = rs.getInt("NAMSINH");
                double luong = rs.getDouble("LUONG");

                NhanVienDTO stalist = new NhanVienDTO(maNV, hoNV, tenNV, gioiTinh, dienThoai, diaChi, img, namSinh, luong);
                stafflist.add(stalist);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stafflist;
    }

    public void add(NhanVienDTO nv) {
        String sql = "INSERT INTO NHANVIEN VALUES('" + nv.getMaNV() + "', "
                + "'" + nv.getHoNV() + "', "
                + "'" + nv.getTenNV() + "', "
                + "'" + nv.getNamSinh() + "', "
                + "'" + nv.getGioiTinh() + "', "
                + "'" + nv.getDienThoai() + "', "
                + "'" + nv.getDiaChi() + "', "
                + "'" + nv.getLuong() + "', "
                + "'" + nv.getImg() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(NhanVienDTO nv) {
        String sql = "UPDATE NHANVIEN SET HONV = '" + nv.getHoNV() + "', "
                + " TENNV = '" + nv.getTenNV() + "', "
                + " NAMSINH = '" + nv.getNamSinh() + "', "
                + " GIOITINH = '" + nv.getGioiTinh() + "', "
                + " DIENTHOAI = '" + nv.getDienThoai() + "', "
                + " DIACHI = '" + nv.getDiaChi() + "', "
                + " LUONG = '" + nv.getLuong() + "', "
                + " IMG = '" + nv.getImg() + "' WHERE MANV = '" + nv.getMaNV() + "' ";
        mysql.executeUpdate(sql);
    }

    public void delete(String maNV) {
        String sql = "DELETE FROM NHANVIEN WHERE MANV = '" + maNV + "'"; // or UPDATE KHACHANG SET enable = 0 WHERE MAKH = ?
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
                String manv = row.getCell(0).getStringCellValue();
                String honv = row.getCell(1).getStringCellValue();
                String tennv = row.getCell(2).getStringCellValue();
                int ns = (int) row.getCell(3).getNumericCellValue();
                String gt = row.getCell(4).getStringCellValue();
                String dt = row.getCell(5).getStringCellValue();
                String diachi = row.getCell(6).getStringCellValue();
                double luong = row.getCell(7).getNumericCellValue();
                String img = row.getCell(8).getStringCellValue();

                String sql_check = "SELECT * FROM NHANVIEN WHERE MANV = '" + manv + "'";
                ResultSet rs = mysql.executeQuery(sql_check);
                if (!rs.next()) {
                    String sql = "INSERT INTO NHANVIEN VALUES (";
                    sql += "N'" + manv + "',";
                    sql += "N'" + honv + "',";
                    sql += "N'" + tennv + "',";
                    sql += "'" + ns + "',";
                    sql += "N'" + gt + "',";
                    sql += "N'" + dt + "',";
                    sql += "N'" + diachi + "',";
                    sql += "'" + luong + "',";
                    sql += "N'" + img + "')";
                    mysql.executeUpdate(sql);
                } else {
                    String sql = "UPDATE NHANVIEN SET ";
                    sql += "HONV='" + honv + "', ";
                    sql += "TENNV='" + tennv + "', ";
                    sql += "NAMSINH='" + ns + "', ";
                    sql += "GIOITINH='" + gt + "', ";
                    sql += "DIENTHOAI='" + dt + "', ";
                    sql += "DIACHI='" + diachi + "', ";
                    sql += "LUONG='" + luong + "', ";
                    sql += "IMG='" + img + "' ";
                    sql += "WHERE MANV='" + manv + "'";
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
            String sql = "SELECT * FROM NHANVIEN";
            ResultSet rs = mysql.executeQuery(sql);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("NhanVienData");

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue("MANV");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("HONV");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("TENNV");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("NAMSINH");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("GIOITINH");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("DIENTHOAI");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("DIACHI");
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue("LUONG");
            cell.setCellStyle(style);
            cell = row.createCell(8);
            cell.setCellValue("IMG");
            cell.setCellStyle(style);
            int i = 1;

            while (rs.next()) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(rs.getString("MANV"));
                cell = row.createCell(1);
                cell.setCellValue(rs.getString("HONV"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getString("TENNV"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getInt("NAMSINH"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getString("GIOITINH"));
                cell = row.createCell(5);
                cell.setCellValue(rs.getString("DIENTHOAI"));
                cell = row.createCell(6);
                cell.setCellValue(rs.getString("DIACHI"));
                cell = row.createCell(7);
                cell.setCellValue(rs.getDouble("LUONG"));
                cell = row.createCell(8);
                cell.setCellValue(rs.getString("IMG"));
                i++;
            }

            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.autoSizeColumn((short) (colNum));
            }

            FileOutputStream out = new FileOutputStream(new File("./report/NhanVienData.xlsx"));
            workbook.write(out);
            out.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
