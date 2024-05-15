package BUS;

import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.NhanVienDTO;

import java.io.File;
import java.util.ArrayList;

public class NhanVienBUS {
    private ArrayList<NhanVienDTO> dsNV;

    public NhanVienBUS() {
        list();
    }

    public void list() {
        NhanVienDAO nvDAO = new NhanVienDAO();
        dsNV = new ArrayList<>();
        dsNV = nvDAO.list();
    }

    public NhanVienDTO get(String MaNV) {
        for (NhanVienDTO nv : dsNV) {
            if (nv.getMaNV().equals(MaNV)) {
                return nv;
            }
        }
        return null;
    }

    public void add(NhanVienDTO nv) {
        dsNV.add(nv);
        NhanVienDAO nvDAO = new NhanVienDAO();
        nvDAO.add(nv);
    }

    public void delete(String MaNV) {
        for (NhanVienDTO nv : dsNV) {
            if (nv.getMaNV().equals(MaNV)) {
                dsNV.remove(nv);
                NhanVienDAO nvDAO = new NhanVienDAO();
                nvDAO.delete(MaNV);
                return;
            }
        }
    }

    public void update(NhanVienDTO nv) {
        for (int i = 0; i < dsNV.size(); i++) {
            if (dsNV.get(i).getMaNV().equals(nv.getMaNV())) {
                dsNV.set(i, nv);
                NhanVienDAO nvDAO = new NhanVienDAO();
                nvDAO.update(nv);
                return;
            }
        }
    }

    public boolean check(String maNV) {
        for (NhanVienDTO nv : dsNV) {
            if (nv.getMaNV().equals(maNV)) {
                return true;
            }
        }
        return false;
    }

    public String getHoTenNV(String maNV) {
        for (NhanVienDTO nv : dsNV) {
            if (nv.getMaNV().equals(maNV)) {
                return nv.getHoNV() + " " + nv.getTenNV();
            }
        }
        return "";
    }

    public ArrayList<NhanVienDTO> search(String maNV, String ho, String ten, String gt) {
        ArrayList<NhanVienDTO> search = new ArrayList<>();
        maNV = maNV.isEmpty() ? "" : maNV;
        ho = ho.isEmpty() ? "" : ho;
        ten = ten.isEmpty() ? "" : ten;
        for (NhanVienDTO nv : dsNV) {
            if (nv.getMaNV().contains(maNV) && nv.getHoNV().contains(ho) && nv.getTenNV().contains(ten) && nv.getGioiTinh().contains(gt)) {
                search.add(nv);
            }
        }
        return search;
    }

    public ArrayList<NhanVienDTO> getList() {
        return dsNV;
    }

    public void ImportExcelDatabase(File file) {
        NhanVienDAO nvDAO = new NhanVienDAO();
        nvDAO.ImportExcelDatabase(file);
    }

    public void ExportExcelDatabase() {
        NhanVienDAO nvDAO = new NhanVienDAO();
        nvDAO.ExportExcelDatabase();
    }
}