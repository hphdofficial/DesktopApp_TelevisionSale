package BUS;

import DAO.ChiTietNHDAO;
import DTO.ChiTietNHDTO;

import java.util.ArrayList;

public class ChiTietNHBUS {
    private ArrayList<ChiTietNHDTO> dsCTNH;

    public ChiTietNHBUS() {
        list();
    }

    public void list() {
        ChiTietNHDAO ctnhDAO = new ChiTietNHDAO();
        dsCTNH = new ArrayList<>();
        dsCTNH = ctnhDAO.list();
    }

    public void add(ChiTietNHDTO ctnh) {
        dsCTNH.add(ctnh);
        ChiTietNHDAO ctnhDAO = new ChiTietNHDAO();
        ctnhDAO.add(ctnh);
    }

    public void update(ChiTietNHDTO ctnh) {
        for (int i = 0; i < dsCTNH.size(); i++) {
            if (dsCTNH.get(i).getMaNH().equals(ctnh.getMaNH())) {
                dsCTNH.set(i, ctnh);
                ChiTietNHDAO ctnhDAO = new ChiTietNHDAO();
                ctnhDAO.update(ctnh);
                return;
            }
        }
    }

    public void delete(String maNH) {
        for (ChiTietNHDTO ctnh : dsCTNH) {
            if (ctnh.getMaNH().equals(maNH)) {
                dsCTNH.remove(ctnh);
                ChiTietNHDAO ctnhDAO = new ChiTietNHDAO();
                ctnhDAO.delete(maNH);
                return;
            }
        }
    }

    public void deleteSP(String maNH, String maSP) {
        for (ChiTietNHDTO ctnh : dsCTNH) {
            if (ctnh.getMaNH().equals(maNH) && ctnh.getMaSP().equals(maSP)) {
                dsCTNH.remove(ctnh);
                ChiTietNHDAO ctnhDAO = new ChiTietNHDAO();
                ctnhDAO.deleteSP(maNH, maSP);
                return;
            }
        }
    }

    public ChiTietNHDTO search(String maNH) {
        for (ChiTietNHDTO ctnh : dsCTNH) {
            if (ctnh.getMaNH().equals(maNH)) {
                return ctnh;
            }
        }
        return null;
    }

    public ArrayList<ChiTietNHDTO> search(String maSP, double max, double min) {
        ArrayList<ChiTietNHDTO> search = new ArrayList<>();
        maSP = maSP.isEmpty() ? "" : maSP;
        for (ChiTietNHDTO ct : dsCTNH) {
            if (ct.getMaSP().contains(maSP) && ct.getDonGia() >= min && ct.getDonGia() <= max) {
                search.add(ct);
            }
        }
        return search;
    }

    public ArrayList<String> getNH(String maSP) {
        ArrayList<String> s = new ArrayList<>();
        if (maSP.isEmpty()) return null;
        for (ChiTietNHDTO ctnh : dsCTNH) {
            if (ctnh.getMaSP().equals(maSP)) {
                s.add(ctnh.getMaNH());
            }
        }
        return s;
    }

    public ArrayList<ChiTietNHDTO> getListNH(String maNH) {
        ArrayList<ChiTietNHDTO> ds = new ArrayList<>();
        for (ChiTietNHDTO ctnh : dsCTNH) {
            if (ctnh.getMaNH().equals(maNH)) {
                ds.add(ctnh);
            }
        }
        return ds;
    }

    public ArrayList<ChiTietNHDTO> getList() {
        return dsCTNH;
    }
}