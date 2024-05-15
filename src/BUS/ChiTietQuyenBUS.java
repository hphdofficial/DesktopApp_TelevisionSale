package BUS;

import DAO.ChiTietQuyenDAO;
import DTO.ChiTietQuyenDTO;

import java.util.ArrayList;

public class ChiTietQuyenBUS {
    private ArrayList<ChiTietQuyenDTO> dsCTQuyen;

    public ChiTietQuyenBUS() {
        list();
    }

    public void list() {
        ChiTietQuyenDAO ctquyenDAO = new ChiTietQuyenDAO();
        dsCTQuyen = new ArrayList<>();
        dsCTQuyen = ctquyenDAO.list();
    }

    public void add(ChiTietQuyenDTO ctquyen) {
        dsCTQuyen.add(ctquyen);
        ChiTietQuyenDAO ctquyenDAO = new ChiTietQuyenDAO();
        ctquyenDAO.add(ctquyen);
    }

    public void delete(String maQuyen) {
        for (ChiTietQuyenDTO ctquyen : dsCTQuyen) {
            if (ctquyen.getMaQuyen().equals(maQuyen)) {
                dsCTQuyen.remove(ctquyen);
                ChiTietQuyenDAO ctquyenDAO = new ChiTietQuyenDAO();
                ctquyenDAO.delete(maQuyen);
                return;
            }
        }
    }

    public ArrayList<ChiTietQuyenDTO> getList() {
        return dsCTQuyen;
    }
}