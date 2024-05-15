package DTO;

public class SanPhamDTO {
    private String maSP, tenSP, kichThuoc, maLoai, maNSX, img;
    private int soLuong;
    private double giaSP;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSP, String tenSP, String kichThuoc, String maLoai, String maNSX, String img, int soLuong, double giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.kichThuoc = kichThuoc;
        this.maLoai = maLoai;
        this.maNSX = maNSX;
        this.img = img;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(String maNSX) {
        this.maNSX = maNSX;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(double giaSP) {
        this.giaSP = giaSP;
    }
}