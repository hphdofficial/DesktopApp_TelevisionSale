package BUS;

import DAO.ChucNangDAO;
import DTO.ChucNangDTO;

import java.util.ArrayList;

public class ChucNangBUS {
    private ArrayList<ChucNangDTO> dsChucNang;

    public ChucNangBUS() {
        list();
    }

    public void list() {
        ChucNangDAO cnDAO = new ChucNangDAO();
        dsChucNang = new ArrayList<>();
        dsChucNang = cnDAO.list();
    }

    public void add(ChucNangDTO cn) {
        dsChucNang.add(cn);
        ChucNangDAO cnDAO = new ChucNangDAO();
        cnDAO.add(cn);
    }

    public void update(ChucNangDTO cn) {
        for (int i = 0; i < dsChucNang.size(); i++) {
            if (dsChucNang.get(i).getMaCN().equals(cn.getMaCN())) {
                dsChucNang.set(i, cn);
                ChucNangDAO cnDAO = new ChucNangDAO();
                cnDAO.update(cn);
                return;
            }
        }
    }

    public void delete(String maChucNang) {
        for (ChucNangDTO cn : dsChucNang) {
            if (cn.getMaCN().equals(maChucNang)) {
                dsChucNang.remove(cn);
                ChucNangDAO cnDAO = new ChucNangDAO();
                cnDAO.delete(maChucNang);
                return;
            }
        }
    }

    public ChucNangDTO searchMaChucNang(String maChucNang) {
        for (ChucNangDTO cn : dsChucNang) {
            if (cn.getMaCN().equals(maChucNang)) {
                return cn;
            }
        }
        return null;
    }

    public ArrayList<ChucNangDTO> searchChucNang(String maChucNang, String tenChucNang) {
        ArrayList<ChucNangDTO> search = new ArrayList<>();
        maChucNang = maChucNang.isEmpty() ? "" : maChucNang;
        tenChucNang = tenChucNang.isEmpty() ? "" : tenChucNang;
        for (ChucNangDTO cn : dsChucNang) {
            if (cn.getMaCN().contains(maChucNang) && cn.getTenCN().contains(tenChucNang)) {
                search.add(cn);
            }
        }
        return search;
    }

    public ArrayList<ChucNangDTO> getList() {
        return dsChucNang;
    }
}