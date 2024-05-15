package BUS;

import DAO.NhaSanXuatDAO;
import DTO.NhaSanXuatDTO;

import java.util.ArrayList;

public class NhaSanXuatBUS {
    private ArrayList<NhaSanXuatDTO> dsNSX;

    public NhaSanXuatBUS() {
        list();
    }

    public void list() {
        NhaSanXuatDAO nsxDAO = new NhaSanXuatDAO();
        dsNSX = new ArrayList<>();
        dsNSX = nsxDAO.list();
    }

    public void add(NhaSanXuatDTO nsx) {
        dsNSX.add(nsx);
        NhaSanXuatDAO nsxDAO = new NhaSanXuatDAO();
        nsxDAO.add(nsx);
    }

    public void update(NhaSanXuatDTO nsx) {
        for (int i = 0; i < dsNSX.size(); i++) {
            if (dsNSX.get(i).getMaNSX().equals(nsx.getMaNSX())) {
                dsNSX.set(i, nsx);
                NhaSanXuatDAO nsxDAO = new NhaSanXuatDAO();
                nsxDAO.update(nsx);
                return;
            }
        }
    }

    public void delete(String maNSX) {
        for (NhaSanXuatDTO nsx : dsNSX) {
            if (nsx.getMaNSX().equals(maNSX)) {
                dsNSX.remove(nsx);
                NhaSanXuatDAO nsxDAO = new NhaSanXuatDAO();
                nsxDAO.delete(maNSX);
                return;
            }
        }
    }

    public NhaSanXuatDTO searchMaNSX(String maNSX) {
        for (NhaSanXuatDTO nsx : dsNSX) {
            if (nsx.getMaNSX().equals(maNSX)) {
                return nsx;
            }
        }
        return null;
    }

    public ArrayList<NhaSanXuatDTO> searchNSX(String maNSX, String tenNSX) {
        ArrayList<NhaSanXuatDTO> search = new ArrayList<>();
        maNSX = maNSX.isEmpty() ? "" : maNSX;
        tenNSX = tenNSX.isEmpty() ? "" : tenNSX;
        for (NhaSanXuatDTO nsx : dsNSX) {
            if (nsx.getMaNSX().contains(maNSX) && nsx.getTenNSX().contains(tenNSX)) {
                search.add(nsx);
            }
        }
        return search;
    }

    public ArrayList<NhaSanXuatDTO> getList() {
        return dsNSX;
    }
}