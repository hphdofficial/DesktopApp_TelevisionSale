package BUS;

import DAO.ChiTietHDDAO;
import DTO.ChiTietHDDTO;

import java.util.ArrayList;

public class ChiTietHDBUS {
    private ArrayList<ChiTietHDDTO> dsCTHD;

    public ChiTietHDBUS() {
        list();
    }

    public void list() {
        ChiTietHDDAO cthdDAO = new ChiTietHDDAO();
        dsCTHD = new ArrayList<>();
        dsCTHD = cthdDAO.list();
    }

    public void add(ChiTietHDDTO cthd) {
        dsCTHD.add(cthd);
        ChiTietHDDAO cthdDAO = new ChiTietHDDAO();
        cthdDAO.add(cthd);
    }

    public void update(ChiTietHDDTO cthd) {
        for (int i = 0; i < dsCTHD.size(); i++) {
            if (dsCTHD.get(i).getMaHD().equals(cthd.getMaHD())) {
                dsCTHD.set(i, cthd);
                ChiTietHDDAO cthdDAO = new ChiTietHDDAO();
                cthdDAO.update(cthd);
                return;
            }
        }
    }

    public void delete(String maHD) {
        for (ChiTietHDDTO cthd : dsCTHD) {
            if (cthd.getMaHD().equals(maHD)) {
                dsCTHD.remove(cthd);
                ChiTietHDDAO cthdDAO = new ChiTietHDDAO();
                cthdDAO.delete(maHD);
                return;
            }
        }
    }

    public void deleteSP(String maHD, String maSP) {
        for (ChiTietHDDTO cthd : dsCTHD) {
            if (cthd.getMaHD().equals(maHD) && cthd.getMaSP().equals(maSP)) {
                dsCTHD.remove(cthd);
                ChiTietHDDAO cthdDAO = new ChiTietHDDAO();
                cthdDAO.deleteSP(maHD, maSP);
                return;
            }
        }
    }

    public ChiTietHDDTO search(String maHD) {
        for (ChiTietHDDTO cthd : dsCTHD) {
            if (cthd.getMaHD().equals(maHD)) {
                return cthd;
            }
        }
        return null;
    }

    public ArrayList<ChiTietHDDTO> search(String maSP, double max, double min) {
        ArrayList<ChiTietHDDTO> search = new ArrayList<>();
        maSP = maSP.isEmpty() ? "" : maSP;
        for (ChiTietHDDTO ct : dsCTHD) {
            if (ct.getMaSP().contains(maSP) && ct.getDonGia() >= min && ct.getDonGia() <= max) {
                search.add(ct);
            }
        }
        return search;
    }

    public ArrayList<String> getHD(String maSP) {
        ArrayList<String> s = new ArrayList<>();
        if (maSP.isEmpty()) return null;
        for (ChiTietHDDTO cthd : dsCTHD) {
            if (cthd.getMaSP().equals(maSP)) {
                s.add(cthd.getMaHD());
            }
        }
        return s;
    }

    public ArrayList<ChiTietHDDTO> getListHD(String maHD) {
        ArrayList<ChiTietHDDTO> ds = new ArrayList<>();
        for (ChiTietHDDTO cthd : dsCTHD) {
            if (cthd.getMaHD().equals(maHD)) {
                ds.add(cthd);
            }
        }
        return ds;
    }

    public ArrayList<ChiTietHDDTO> getList() {
        return dsCTHD;
    }
}