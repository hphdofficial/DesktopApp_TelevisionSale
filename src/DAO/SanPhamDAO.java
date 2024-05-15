package DAO;

import DTO.SanPhamDTO;
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

public class SanPhamDAO {
    MySQLConnect mysql = new MySQLConnect();

    public SanPhamDAO() {

    }

    public ArrayList<SanPhamDTO> list() {
        ArrayList<SanPhamDTO> productlist = new ArrayList<>();

        try {
            String sql = "SELECT * FROM SANPHAM";
            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maSP = rs.getString("MASP");
                String tenSP = rs.getString("TENSP");
                String kichThuoc = rs.getString("KICHTHUOC");
                String maLoai = rs.getString("MALOAI");
                String maNSX = rs.getString("MANSX");
                String img = rs.getString("IMG");
                int soLuong = rs.getInt("SOLUONG");
                double giaSP = rs.getDouble("GIASP");

                SanPhamDTO prlist = new SanPhamDTO(maSP, tenSP, kichThuoc, maLoai, maNSX, img, soLuong, giaSP);
                productlist.add(prlist);
            }
            rs.close();
            mysql.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productlist;
    }

    public void add(SanPhamDTO sp) {
        String sql = "INSERT INTO SANPHAM VALUES('" + sp.getMaSP() + "', "
                + "'" + sp.getTenSP() + "', "
                + "'" + sp.getKichThuoc() + "', "
                + "'" + sp.getSoLuong() + "', "
                + "'" + sp.getGiaSP() + "',  "
                + "'" + sp.getMaLoai() + "', "
                + "'" + sp.getMaNSX() + "', "
                + "'" + sp.getImg() + "')";
        mysql.executeUpdate(sql);
    }

    public void update(SanPhamDTO sp) {
        String sql = "UPDATE SANPHAM SET TENSP = '" + sp.getTenSP() + "', "
                + " KICHTHUOC = '" + sp.getKichThuoc() + "', "
                + " SOLUONG = '" + sp.getSoLuong() + "', "
                + " GIASP = '" + sp.getGiaSP() + "', "
                + " MALOAI = '" + sp.getMaLoai() + "', "
                + " MANSX = '" + sp.getMaNSX() + "', "
                + " IMG = '" + sp.getImg() + "' WHERE MASP = '" + sp.getMaSP() + "'";
        mysql.executeUpdate(sql);
    }

    public void delete(String maSP) {
        String sql = "DELETE FROM SANPHAM WHERE MASP = '" + maSP + "'"; // or UPDATE SANPHAM SET enable = 0 WHERE MAKH = ?
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
                String maSP = row.getCell(0).getStringCellValue();
                String tenSP = row.getCell(1).getStringCellValue();
                String kichThuoc = row.getCell(2).getStringCellValue();
                int sl = (int) row.getCell(3).getNumericCellValue();
                double gia = row.getCell(4).getNumericCellValue();
                String maLoai = row.getCell(5).getStringCellValue();
                String maNsx = row.getCell(6).getStringCellValue();
                String IMG = row.getCell(7).getStringCellValue();

                String sql_check = "SELECT * FROM SANPHAM WHERE MASP = '" + maSP + "'";
                ResultSet rs = mysql.executeQuery(sql_check);
                if (!rs.next()) {
                    String sql = "INSERT INTO SANPHAM VALUES (";
                    sql += "N'" + maSP + "',";
                    sql += "N'" + tenSP + "',";
                    sql += "N'" + kichThuoc + "',";
                    sql += "'" + sl + "',";
                    sql += "'" + gia + "',";
                    sql += "N'" + maLoai + "',";
                    sql += "N'" + maNsx + "',";
                    sql += "N'" + IMG + "')";
                    mysql.executeUpdate(sql);
                } else {
                    String sql = "UPDATE SANPHAM SET ";
                    sql += "TENSP='" + tenSP + "', ";
                    sql += "SOLUONG='" + sl + "', ";
                    sql += "GIASP='" + gia + "', ";
                    sql += "KICHTHUOC='" + kichThuoc + "', ";
                    sql += "MALOAI='" + maLoai + "', ";
                    sql += "MANSX='" + maNsx + "', ";
                    sql += "IMG='" + IMG + "' ";
                    sql += "WHERE MASP='" + maSP + "'";
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
            String sql = "SELECT * FROM SANPHAM";
            ResultSet rs = mysql.executeQuery(sql);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("SanPhamData");

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue("MASP");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("TENSP");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("KICHTHUOC");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("SOLUONG");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("GIASP");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("MALOAI");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("MANSX");
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue("IMG");
            cell.setCellStyle(style);
            int i = 1;

            while (rs.next()) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(rs.getString("MASP"));
                cell = row.createCell(1);
                cell.setCellValue(rs.getString("TENSP"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getString("KICHTHUOC"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getInt("SOLUONG"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getDouble("GIASP"));
                cell = row.createCell(5);
                cell.setCellValue(rs.getString("MALOAI"));
                cell = row.createCell(6);
                cell.setCellValue(rs.getString("MANSX"));
                cell = row.createCell(7);
                cell.setCellValue(rs.getString("IMG"));
                i++;
            }

            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.autoSizeColumn((short) (colNum));
            }

            FileOutputStream out = new FileOutputStream(new File("./report/SanPhamData.xlsx"));
            workbook.write(out);
            out.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}