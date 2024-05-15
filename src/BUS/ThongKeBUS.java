package BUS;

import DAO.ThongKeDAO;
import DTO.HoaDonDTO;
import DTO.NhapHangDTO;
import DTO.ThongKeDTO;

import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeBUS {
    private final HoaDonBUS hdBUS = new HoaDonBUS();
    private final NhapHangBUS nhBUS = new NhapHangBUS();
    private final ThongKeDAO tkDAO = new ThongKeDAO();

    public ThongKeBUS() {
        hdBUS.list();
        nhBUS.list();
    }

    public String ThongKeSP(String id, Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ArrayList<NhapHangDTO> dsNH = new ArrayList<>();
        dsNH = nhBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeSP(dsHD, dsNH, id);
    }

    public String ThongKeNV(String id, Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeNV(dsHD, id);
    }

    public String ThongKeKH(String id, Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeKH(dsHD, id);
    }

    public ArrayList<String> ThongKeTopSP(Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeTopSP(dsHD);
    }

    public ArrayList<String> ThongKeTopNV(Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeTopNV(dsHD);
    }

    public ArrayList<String> ThongKeTopKH(Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        dsHD = hdBUS.listTime(from, to);

        ThongKeDAO tkDAO = new ThongKeDAO();
        return tkDAO.ThongKeTopKH(dsHD);
    }

    public ThongKeDTO ThongKe() {
        ThongKeDTO thongKe = new ThongKeDTO();
        thongKe.setSoLuongSP(tkDAO.getTongSoLuongSP());
        thongKe.setSoLuongKH(tkDAO.getTongSoLuongKH());
        thongKe.setSoLuongNV(tkDAO.getTongSoLuongNV());
        thongKe.setSoLuongNCC(tkDAO.getTongSoLuongNCC());
        return thongKe;
    }
}