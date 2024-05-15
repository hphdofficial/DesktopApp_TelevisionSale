package BUS;

import DAO.QuyenDAO;
import DTO.QuyenDTO;

import java.util.ArrayList;

public class QuyenBUS {
    private ArrayList<QuyenDTO> dsQuyen;

    public QuyenBUS() {
        list();
    }

    public void list() {
        QuyenDAO quyenDAO = new QuyenDAO();
        dsQuyen = new ArrayList<>();
        dsQuyen = quyenDAO.list();
    }

    public void add(QuyenDTO quyen) {
        dsQuyen.add(quyen);
        QuyenDAO quyenDAO = new QuyenDAO();
        quyenDAO.add(quyen);
    }

    public void update(QuyenDTO quyen) {
        for (int i = 0; i < dsQuyen.size(); i++) {
            if (dsQuyen.get(i).getMaQuyen().equals(quyen.getMaQuyen())) {
                dsQuyen.set(i, quyen);
                QuyenDAO quyenDAO = new QuyenDAO();
                quyenDAO.update(quyen);
                return;
            }
        }
    }

    public void delete(String maQuyen) {
        for (QuyenDTO quyen : dsQuyen) {
            if (quyen.getMaQuyen().equals(maQuyen)) {
                dsQuyen.remove(quyen);
                QuyenDAO quyenDAO = new QuyenDAO();
                quyenDAO.delete(maQuyen);
                return;
            }
        }
    }

    public QuyenDTO searchMaQuyen(String maQuyen) {
        for (QuyenDTO quyen : dsQuyen) {
            if (quyen.getMaQuyen().equals(maQuyen)) {
                return quyen;
            }
        }
        return null;
    }

    public ArrayList<QuyenDTO> searchQuyen(String maQuyen, String tenQuyen) {
        ArrayList<QuyenDTO> search = new ArrayList<>();
        maQuyen = maQuyen.isEmpty() ? "" : maQuyen;
        tenQuyen = tenQuyen.isEmpty() ? "" : tenQuyen;
        for (QuyenDTO quyen : dsQuyen) {
            if (quyen.getMaQuyen().contains(maQuyen) && quyen.getTenQuyen().contains(tenQuyen)) {
                search.add(quyen);
            }
        }
        return search;
    }

    public ArrayList<QuyenDTO> getList() {
        return dsQuyen;
    }
}