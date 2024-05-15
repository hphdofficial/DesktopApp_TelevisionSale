package BUS;

import DAO.KhachHangDAO;
import DAO.SanPhamDAO;
import DTO.KhachHangDTO;

import java.io.File;
import java.util.ArrayList;

public class KhachHangBUS {
    private ArrayList<KhachHangDTO> dsKH;

    public KhachHangBUS() {
        list();
    }

    public void list() {
        KhachHangDAO khDAO = new KhachHangDAO();
        dsKH = new ArrayList<>();
        dsKH = khDAO.list();
    }

    public KhachHangDTO get(String MaKH) {
        for (KhachHangDTO kh : dsKH) {
            if (kh.getMaKH().equals(MaKH)) {
                return kh;
            }
        }
        return null;
    }

    public void add(KhachHangDTO kh) {
        dsKH.add(kh);
        KhachHangDAO khDAO = new KhachHangDAO();
        khDAO.add(kh);
    }

    public void update(KhachHangDTO kh) {
        for (int i = 0; i < dsKH.size(); i++) {
            if (dsKH.get(i).getMaKH().equals(kh.getMaKH())) {
                dsKH.set(i, kh);
                KhachHangDAO khDAO = new KhachHangDAO();
                khDAO.update(kh);
                return;
            }
        }
    }

    public void delete(String MaKH) {
        for (KhachHangDTO kh : dsKH) {
            if (kh.getMaKH().equals(MaKH)) {
                dsKH.remove(kh);
                KhachHangDAO khDAO = new KhachHangDAO();
                khDAO.delete(MaKH);
                return;
            }
        }
    }

    public boolean check(String MaKH) {
        for (KhachHangDTO kh : dsKH) {
            if (kh.getMaKH().equals(MaKH)) {
                return true;
            }
        }
        return false;
    }

    public String getHoTenKH(String maKH) {
        for (KhachHangDTO kh : dsKH) {
            if (kh.getMaKH().equals(maKH)) {
                return kh.getHoKH() + " " + kh.getTenKH();
            }
        }
        return "";
    }

    public ArrayList<KhachHangDTO> search(String MaKH, String Ho, String Ten, String gt) {
        ArrayList<KhachHangDTO> search = new ArrayList<>();
        MaKH = MaKH.isEmpty() ? "" : MaKH;
        Ho = Ho.isEmpty() ? "" : Ho;
        Ten = Ten.isEmpty() ? "" : Ten;
        for (KhachHangDTO kh : dsKH) {
            if (kh.getMaKH().contains(MaKH) && kh.getHoKH().contains(Ho) && kh.getTenKH().contains(Ten) && kh.getGioiTinh().contains(gt)) {
                search.add(kh);
            }
        }
        return search;
    }

    public ArrayList<KhachHangDTO> getList() {
        return dsKH;
    }

    public void ImportExcelDatabase(File file) {
        KhachHangDAO khDAO = new KhachHangDAO();
        khDAO.ImportExcelDatabase(file);
    }

    public void ExportExcelDatabase() {
        KhachHangDAO khDAO = new KhachHangDAO();
        khDAO.ExportExcelDatabase();
    }
}