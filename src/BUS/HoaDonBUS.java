package BUS;

import DAO.HoaDonDAO;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class HoaDonBUS {
    private ArrayList<HoaDonDTO> dsHD;

    public HoaDonBUS() {
        list();
    }

    public void list() {
        HoaDonDAO hdDAO = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = hdDAO.list();
    }

    public void add(HoaDonDTO hd) {
        dsHD.add(hd);
        HoaDonDAO hdDAO = new HoaDonDAO();
        hdDAO.add(hd);
    }

    public int update(HoaDonDTO hd) {
        for (int i = 0; i < dsHD.size(); i++) {
            if (dsHD.get(i).getMaHD().equals(hd.getMaHD())) {
                dsHD.set(i, hd);
                HoaDonDAO hdDAO = new HoaDonDAO();
                hdDAO.update(hd);
                return i;
            }
        }
        return -1;
    }

    public void delete(String maHD) {
        for (HoaDonDTO hd : dsHD) {
            if (hd.getMaHD().equals(maHD)) {
                dsHD.remove(hd);
                HoaDonDAO hdDAO = new HoaDonDAO();
                hdDAO.delete(maHD);
                return;
            }
        }
    }

    public double updateTotalPrice(String maHD, ArrayList<ChiTietHDDTO> cthd) {
        double totalPrice = 0;
        for (ChiTietHDDTO ct : cthd) {
            if (ct.getMaHD().equals(maHD)) {
                totalPrice += ct.getSoLuong() * ct.getDonGia();
            }
        }
        return totalPrice;
    }

    public String remindMaHD() {
        int max = 0;
        String s = "HD";
        for (HoaDonDTO hd : dsHD) {
            int id = Integer.parseInt(hd.getMaHD().substring(2, 5));
            if (id > max) {
                max = id;
            }
        }
        for (int i = 0; i < 3 - String.valueOf(max + 1).length(); i++) {
            s += "0";
        }
        return s + (max + 1);
    }

    public boolean checkTime(Calendar from, Calendar to, Calendar time) {
//        System.err.print(from.getTime()+" ");
//        System.err.print(to.getTime()+" ");
//        System.err.println(time.getTime());
        return time.after(from) && time.before(to);
    }

    public ArrayList<HoaDonDTO> listTime(Calendar from, Calendar to) {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        for (HoaDonDTO hd : dsHD) {
            Timestamp date = Timestamp.valueOf(hd.getNgayHD());
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(date.getTime());
            if (checkTime(from, to, time)) {
                list.add(hd);
            }
        }
        return list;
    }

    public ArrayList<HoaDonDTO> search(ArrayList<String> mahd, String manv, int month, int year, double min, double max) {
        ArrayList<HoaDonDTO> ds = getListWidthArray(mahd);
        ArrayList<HoaDonDTO> search = new ArrayList<>();

        manv = manv.isEmpty() ? "" : manv;
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

        for (HoaDonDTO hd : ds) {
            Timestamp time = Timestamp.valueOf(hd.getNgayHD());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time.getTime());
            ;

            int monthHD = calendar.get(Calendar.MONTH) + 1; // Java tíhd từ tháng 0 đến tháng 11
            int yearHD = calendar.get(Calendar.YEAR);

            if (hd.getMaNV().contains(manv) && (monthHD >= monthStart && monthHD <= monthEnd) && (yearHD >= yearStart && yearHD <= yearEnd) && hd.getTongTien() >= min && hd.getTongTien() <= max) {
                search.add(hd);
            }
        }
        return search;
    }

    public boolean check(String maHD) {
        for (HoaDonDTO hd : dsHD) {
            if (hd.getMaHD().equals(maHD)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<HoaDonDTO> getListWidthArray(ArrayList<String> s) {
        ArrayList<HoaDonDTO> ds = new ArrayList<>();
        if (s == null) return dsHD;
        for (HoaDonDTO hd : dsHD) {
            String maHD = hd.getMaHD();
            for (String a : s) {
                if (maHD.equals(a)) {
                    ds.add(hd);
                }
            }
        }
        return ds;
    }

    public ArrayList<HoaDonDTO> getList() {
        return dsHD;
    }
}