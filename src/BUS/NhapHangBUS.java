package BUS;

import DAO.NhapHangDAO;
import DTO.ChiTietNHDTO;
import DTO.NhapHangDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class NhapHangBUS {
    private ArrayList<NhapHangDTO> dsNH;

    public NhapHangBUS() {
        list();
    }

    public void list() {
        NhapHangDAO nhDAO = new NhapHangDAO();
        dsNH = new ArrayList<>();
        dsNH = nhDAO.list();
    }

    public void add(NhapHangDTO nh) {
//        int id = 0;
//        if (!dsNH.isEmpty()) {
//            id = Integer.parseInt(dsNH.get(dsNH.size() - 1).getMaNH());
//        }
//        nh.setMaNH(String.valueOf(id + 1));
        dsNH.add(nh);
        NhapHangDAO nhDAO = new NhapHangDAO();
        nhDAO.add(nh);
    }

    public int update(NhapHangDTO nh) {
        for (int i = 0; i < dsNH.size(); i++) {
            if (dsNH.get(i).getMaNH().equals(nh.getMaNH())) {
                dsNH.set(i, nh);
                NhapHangDAO nhDAO = new NhapHangDAO();
                nhDAO.update(nh);
                return i;
            }
        }
        return -1;
    }

    public void delete(String idNH) {
        for (NhapHangDTO nh : dsNH) {
            if (nh.getMaNH().equals(idNH)) {
                dsNH.remove(nh);
                NhapHangDAO nhDAO = new NhapHangDAO();
                nhDAO.delete(idNH);
                return;
            }
        }
    }

    public double updateTotalPrice(String maNH, ArrayList<ChiTietNHDTO> ctnh) {
        double totalPrice = 0;
        for (ChiTietNHDTO ct : ctnh) {
            if (ct.getMaNH().equals(maNH)) {
                totalPrice += ct.getSoLuong() * ct.getDonGia();
            }
        }
        return totalPrice;
    }

    public String remindMaNH() {
        int max = 0;
        String s = "NH";
        for (NhapHangDTO nh : dsNH) {
            int id = Integer.parseInt(nh.getMaNH().substring(2, 3));
            if (id > max) {
                max = id;
            }
        }
//        for (int i = 0; i < 3 - String.valueOf(max + 1).length(); i++) {
//            s += "0";
//        }
        return s + (max + 1);
    }

    public boolean check(String maHD) {
        for (NhapHangDTO nh : dsNH) {
            if (nh.getMaNH().equals(maHD)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTime(Calendar from, Calendar to, Calendar time) {
//        System.err.print(from.getTime()+" ");
//        System.err.print(to.getTime()+" ");
//        System.err.println(time.getTime());
        return time.after(from) && time.before(to);
    }

    public ArrayList<NhapHangDTO> listTime(Calendar from, Calendar to) {
        ArrayList<NhapHangDTO> list = new ArrayList<>();
        for (NhapHangDTO nh : dsNH) {
            Timestamp date = Timestamp.valueOf(nh.getNgayNhap());
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(date.getTime());
            if (checkTime(from, to, time)) {
                list.add(nh);
            }
        }
        return list;
    }

    public ArrayList<NhapHangDTO> search(ArrayList<String> manh, String mancc, int month, int year, double min, double max) {
        ArrayList<NhapHangDTO> ds = getListWidthArray(manh);
        ArrayList<NhapHangDTO> search = new ArrayList<>();

        mancc = mancc.isEmpty() ? "" : mancc;
        int monthStart = 0, monthEnd = 12;
        int yearStart = 0, yearEnd = Calendar.getInstance().get(Calendar.YEAR);
        if (month != 0) {
            monthStart = month;
            monthEnd = month;
        }
        if (year != 0) {
            yearStart = year;
            yearEnd = year;
        }

        for (NhapHangDTO nh : ds) {
            Timestamp time = Timestamp.valueOf(nh.getNgayNhap());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time.getTime());
            ;

            int monthNH = calendar.get(Calendar.MONTH) + 1; // Java tính từ tháng 0 đến tháng 11
            int yearNH = calendar.get(Calendar.YEAR);

            if (nh.getMaNCC().contains(mancc) && (monthNH >= monthStart && monthNH <= monthEnd) && (yearNH >= yearStart && yearNH <= yearEnd) && nh.getTongTien() >= min && nh.getTongTien() <= max) {
                search.add(nh);
            }
        }
        return search;
    }

    public ArrayList<NhapHangDTO> getListWidthArray(ArrayList<String> s) {
        ArrayList<NhapHangDTO> ds = new ArrayList<>();
        if (s == null) return dsNH;
        for (NhapHangDTO nh : dsNH) {
            String maNH = nh.getMaNH();
            for (String a : s) {
                if (maNH.equals(a)) {
                    ds.add(nh);
                }
            }
        }
        return ds;
    }

    public ArrayList<NhapHangDTO> getList() {
        return dsNH;
    }
}