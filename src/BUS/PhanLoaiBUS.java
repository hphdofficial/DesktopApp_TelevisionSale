package BUS;

import DAO.PhanLoaiDAO;
import DTO.PhanLoaiDTO;

import java.util.ArrayList;

public class PhanLoaiBUS {
    private ArrayList<PhanLoaiDTO> dsPL;

    public PhanLoaiBUS() {
        list();
    }

    public void list() {
        PhanLoaiDAO loaiDAO = new PhanLoaiDAO();
        dsPL = new ArrayList<>();
        dsPL = loaiDAO.list();
    }

    public void add(PhanLoaiDTO loai) {
        dsPL.add(loai);
        PhanLoaiDAO loaiDAO = new PhanLoaiDAO();
        loaiDAO.add(loai);
    }

    public void update(PhanLoaiDTO loai) {
        for (int i = 0; i < dsPL.size(); i++) {
            if (dsPL.get(i).getMaLoai().equals(loai.getMaLoai())) {
                dsPL.set(i, loai);
                PhanLoaiDAO loaiDAO = new PhanLoaiDAO();
                loaiDAO.update(loai);
                return;
            }
        }
    }

    public void delete(String maLoai) {
        for (PhanLoaiDTO loai : dsPL) {
            if (loai.getMaLoai().equals(maLoai)) {
                dsPL.remove(loai);
                PhanLoaiDAO loaiDAO = new PhanLoaiDAO();
                loaiDAO.delete(maLoai);
                return;
            }
        }
    }

    public PhanLoaiDTO searchMaLoai(String maLoai) {
        for (PhanLoaiDTO loai : dsPL) {
            if (loai.getMaLoai().equals(maLoai)) {
                return loai;
            }
        }
        return null;
    }

    public ArrayList<PhanLoaiDTO> searchLoai(String maLoai, String tenLoai) {
        ArrayList<PhanLoaiDTO> search = new ArrayList<>();
        maLoai = maLoai.isEmpty() ? "" : maLoai;
        tenLoai = tenLoai.isEmpty() ? "" : tenLoai;
        for (PhanLoaiDTO loai : dsPL) {
            if (loai.getMaLoai().contains(maLoai) && loai.getTenLoai().contains(tenLoai)) {
                search.add(loai);
            }
        }
        return search;
    }

    public ArrayList<PhanLoaiDTO> getList() {
        return dsPL;
    }
}