package BUS;

import DTO.ChiTietHDDTO;
import DTO.ChiTietNHDTO;
import DTO.HoaDonDTO;
import DTO.NhapHangDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutBill {
    private final NhanVienBUS nvBUS = new NhanVienBUS();
    private final KhachHangBUS khBUS = new KhachHangBUS();
    private final NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private final NumberFormat num = NumberFormat.getInstance();
    private String file = "./report/test.pdf";
    private BaseFont bf;
    private HoaDonDTO hd;
    private ArrayList<ChiTietHDDTO> cthd = new ArrayList<ChiTietHDDTO>();
    private NhapHangDTO nh;
    private ArrayList<ChiTietNHDTO> ctnh = new ArrayList<ChiTietNHDTO>();

    public OutBill(HoaDonDTO hd, ArrayList<ChiTietHDDTO> cthd) {
        this.hd = hd;
        this.cthd = cthd;
        file = "./report/Bill" + hd.getMaHD() + ".pdf";
    }

    public OutBill(NhapHangDTO nh, ArrayList<ChiTietNHDTO> ctnh) {
        this.nh = nh;
        this.ctnh = ctnh;
        file = "./report/Bill" + nh.getMaNH() + ".pdf";
    }

    public void printHD() {
        String uderline = "*";
        try {
            //  Tạo Font
            bf = BaseFont.createFont("./font/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // Tạo tài liệu
            Document bill = new Document(PageSize.A4, 15, 15, 10, 10);

            String line = "";
            for (int i = 0; i < bill.getPageSize().getWidth() / 5; i++) {
                line += uderline;
            }

            //  Tạo đối tượng writter
            PdfWriter.getInstance(bill, new FileOutputStream(file));

            //  Mở document
            bill.open();

            Paragraph header = new Paragraph("CỬA HÀNG TIVI", new Font(bf, 30));
            header.setAlignment(Element.ALIGN_CENTER);
            bill.add(header);

            Paragraph info = new Paragraph("Hóa đơn : " + hd.getMaHD()
                    + "\nNgày : " + hd.getNgayHD()
                    + "\nKhách hàng : " + khBUS.getHoTenKH(hd.getMaKH())
                    + "\nNhân viên : " + nvBUS.getHoTenNV(hd.getMaNV()), new Font(bf, 15));
            info.setAlignment(Element.ALIGN_LEFT);
            bill.add(info);

            Paragraph l = new Paragraph(line);
            l.setAlignment(Element.ALIGN_CENTER);
            bill.add(l);

            String[] cellHeader = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn Giá (VNĐ)"};

            PdfPTable t = new PdfPTable(cellHeader.length);
            t.setSpacingAfter(10);
            t.setSpacingBefore(10);
            t.setWidthPercentage(100);
            int[] relativeWidths = {40, 150, 30, 50};
            t.setWidths(relativeWidths);

            for (String s : cellHeader) {
                t.addCell(createCell(s, new Font(bf, 13)));
            }
            for (ChiTietHDDTO ct : cthd) {
                t.addCell(createCell(ct.getMaSP())).setHorizontalAlignment(Element.ALIGN_CENTER);
                t.addCell(createCell(ct.getTenSP())).setHorizontalAlignment(Element.ALIGN_LEFT);
                t.addCell(createCell(String.valueOf(ct.getSoLuong()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
                t.addCell(createCell(String.valueOf(num.format(ct.getDonGia())))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            }

            bill.add(t);
            bill.add(l);

            Paragraph sum = new Paragraph("Tổng tiền : " + num.format(hd.getTongTien()) + " VNĐ", new Font(bf, 20));
            sum.setAlignment(Element.ALIGN_RIGHT);

            bill.add(sum);
            bill.close();

//            PDDocument document = PDDocument.load(new File(file));
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPageable(new PDFPageable(document));
//            job.print();

            JOptionPane.showMessageDialog(null, "In hoàn tất");
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(OutBill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printNH() {
        String uderline = "*";
        try {
            //  Tạo Font
            bf = BaseFont.createFont("./font/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // Tạo tài liệu
            Document bill = new Document(PageSize.A4, 15, 15, 10, 10);

            String line = "";
            for (int i = 0; i < bill.getPageSize().getWidth() / 5; i++) {
                line += uderline;
            }

            //  Tạo đối tượng writter
            PdfWriter.getInstance(bill, new FileOutputStream(file));

            //  Mở document
            bill.open();

            Paragraph header = new Paragraph("CỬA HÀNG TIVI", new Font(bf, 30));
            header.setAlignment(Element.ALIGN_CENTER);
            bill.add(header);

            Paragraph info = new Paragraph("Phiếu nhập hàng : " + nh.getMaNH()
                    + "\nNgày : " + nh.getNgayNhap()
                    + "\nNhà cung cấp : " + nccBUS.getTenNCC(nh.getMaNCC())
                    + "\nNhân viên : " + nvBUS.getHoTenNV(nh.getMaNV()), new Font(bf, 15));
            info.setAlignment(Element.ALIGN_LEFT);
            bill.add(info);

            Paragraph l = new Paragraph(line);
            l.setAlignment(Element.ALIGN_CENTER);
            bill.add(l);

            String[] cellHeader = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn Giá (VNĐ)"};

            PdfPTable t = new PdfPTable(cellHeader.length);
            t.setSpacingAfter(10);
            t.setSpacingBefore(10);
            t.setWidthPercentage(100);
            int[] relativeWidths = {40, 150, 30, 50};
            t.setWidths(relativeWidths);

            for (String s : cellHeader) {
                t.addCell(createCell(s, new Font(bf, 13)));
            }
            for (ChiTietNHDTO ct : ctnh) {
                t.addCell(createCell(ct.getMaSP())).setHorizontalAlignment(Element.ALIGN_CENTER);
                t.addCell(createCell(ct.getTenSP())).setHorizontalAlignment(Element.ALIGN_LEFT);
                t.addCell(createCell(String.valueOf(ct.getSoLuong()))).setHorizontalAlignment(Element.ALIGN_RIGHT);
                t.addCell(createCell(String.valueOf(num.format(ct.getDonGia())))).setHorizontalAlignment(Element.ALIGN_RIGHT);
            }

            bill.add(t);
            bill.add(l);

            Paragraph sum = new Paragraph("Tổng tiền : " + num.format(nh.getTongTien()) + " VNĐ", new Font(bf, 20));
            sum.setAlignment(Element.ALIGN_RIGHT);

            bill.add(sum);
            bill.close();

//            PDDocument document = PDDocument.load(new File(file));
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPageable(new PDFPageable(document));
//            job.print();

            JOptionPane.showMessageDialog(null, "In hoàn tất");
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(OutBill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPCell createCell(String s) {
        PdfPCell cell = new PdfPCell(new Phrase(s, new Font(bf, 13)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(10);
        return cell;
    }

    public PdfPCell createCell(String s, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(s, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);
        return cell;
    }
}