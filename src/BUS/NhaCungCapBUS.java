package BUS;

import DAO.NhaCungCapDAO;
import DAO.SanPhamDAO;
import DTO.NhaCungCapDTO;
import DTO.NhaCungCapDTO;

import java.io.File;
import java.util.ArrayList;

public class NhaCungCapBUS {
    public ArrayList<NhaCungCapDTO> dsNCC;

    public NhaCungCapBUS() {
        list();
    }

    public void list() {
        NhaCungCapDAO nccDAO = new NhaCungCapDAO();
        dsNCC = new ArrayList<>();
        dsNCC = nccDAO.list();
    }

    public NhaCungCapDTO get(String MaNCC) {
        for (NhaCungCapDTO ncc : dsNCC) {
            if (ncc.getMaNCC().equals(MaNCC)) {
                return ncc;
            }
        }
        return null;
    }

    public void add(NhaCungCapDTO ncc) {
        dsNCC.add(ncc);
        NhaCungCapDAO nccDAO = new NhaCungCapDAO();
        nccDAO.add(ncc);
    }

    public void update(NhaCungCapDTO ncc) {
        for (int i = 0; i < dsNCC.size(); i++) {
            if (dsNCC.get(i).getMaNCC().equals(ncc.getMaNCC())) {
                dsNCC.set(i, ncc);
                NhaCungCapDAO nccDAO = new NhaCungCapDAO();
                nccDAO.update(ncc);
                return;
            }
        }
    }

    public void delete(String MaNCC) {
        for (NhaCungCapDTO ncc : dsNCC) {
            if (ncc.getMaNCC().equals(MaNCC)) {
                dsNCC.remove(ncc);
                NhaCungCapDAO nccDAO = new NhaCungCapDAO();
                nccDAO.delete(MaNCC);
                return;
            }
        }
    }

    public String getTenNCC(String maNCC) {
        for (NhaCungCapDTO ncc : dsNCC) {
            if (ncc.getMaNCC().equals(maNCC)) {
                return ncc.getTenNCC();
            }
        }
        return "";
    }

    public boolean check(String MaNCC) {
        for (NhaCungCapDTO ncc : dsNCC) {
            if (ncc.getMaNCC().equals(MaNCC)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<NhaCungCapDTO> search(String mancc, String tenncc, String fax) {
        ArrayList<NhaCungCapDTO> search = new ArrayList<>();
        mancc = mancc.isEmpty() ? "" : mancc;
        tenncc = tenncc.isEmpty() ? "" : tenncc;
        fax = fax.isEmpty() ? "" : fax;
        for (NhaCungCapDTO ncc : dsNCC) {
            if (ncc.getMaNCC().contains(mancc) && ncc.getTenNCC().contains(tenncc) && ncc.getSoFax().contains(fax)) {
                search.add(ncc);
            }
        }
        return search;
    }

    public ArrayList<NhaCungCapDTO> getList() {
        return dsNCC;
    }

    public void ImportExcelDatabase(File file) {
        NhaCungCapDAO nccDAO = new NhaCungCapDAO();
        nccDAO.ImportExcelDatabase(file);
    }

    public void ExportExcelDatabase() {
        NhaCungCapDAO nccDAO = new NhaCungCapDAO();
        nccDAO.ExportExcelDatabase();
    }
}