package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class SanPhamBUS {
    private ArrayList<SanPhamDTO> dsSP;

    public SanPhamBUS() {
        list();
    }

    public void list() {
        SanPhamDAO spDAO = new SanPhamDAO();
        dsSP = new ArrayList<>();
        dsSP = spDAO.list();
    }

    public SanPhamDTO get(String maSP) {
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().equals(maSP)) {
                return sp;
            }
        }
        return null;
    }

    public void add(SanPhamDTO sp) {
        dsSP.add(sp);
        SanPhamDAO spDAO = new SanPhamDAO();
        spDAO.add(sp);
    }

    public void update(SanPhamDTO sp) {
        for (int i = 0; i < dsSP.size(); i++) {
            if (dsSP.get(i).getMaSP().equals(sp.getMaSP())) {
                dsSP.set(i, sp);
                SanPhamDAO spDAO = new SanPhamDAO();
                spDAO.update(sp);
                return;
            }
        }
    }

    public void delete(String maSP) {
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().equals(maSP)) {
                dsSP.remove(sp);
                SanPhamDAO spDAO = new SanPhamDAO();
                spDAO.delete(maSP);
                return;
            }
        }
    }

    public boolean check(String maSP) {
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().equals(maSP)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateSL(String maSP, int soLuong) {
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().equals(maSP)) {

                int old = sp.getSoLuong();
                if (soLuong > old) {
                    JOptionPane.showMessageDialog(null, "Không đủ hàng");
                    return false;
                }
                old -= soLuong;
                sp.setSoLuong(old);
                SanPhamDAO spDAO = new SanPhamDAO();
                spDAO.update(sp);
                System.out.println(sp.getSoLuong());
                return true;
            }
        }
        return false;
    }

    public boolean checkSL(String maSP, int soLuong) {
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().equals(maSP)) {
                if (soLuong > sp.getSoLuong()) {
                    JOptionPane.showMessageDialog(null, "Không đủ hàng");
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<SanPhamDTO> searchSP(String maSP, String maLoai, String maNSX, double max, double min) {
        ArrayList<SanPhamDTO> search = new ArrayList<>();
        maSP = maSP.isEmpty() ? "" : maSP;
        maLoai = maLoai.isEmpty() ? "" : maLoai;
        maNSX = maNSX.isEmpty() ? "" : maNSX;
        for (SanPhamDTO sp : dsSP) {
            if (sp.getMaSP().contains(maSP) && sp.getMaLoai().contains(maLoai) && sp.getMaNSX().contains(maNSX) && sp.getGiaSP() >= min && sp.getGiaSP() <= max) {
                search.add(sp);
            }
        }
        return search;
    }

    public ArrayList<SanPhamDTO> getList() {
        return dsSP;
    }

    public void ImportExcelDatabase(File file) {
        SanPhamDAO spDAO = new SanPhamDAO();
        spDAO.ImportExcelDatabase(file);
    }

    public void ExportExcelDatabase() {
        SanPhamDAO spDAO = new SanPhamDAO();
        spDAO.ExportExcelDatabase();
    }
}