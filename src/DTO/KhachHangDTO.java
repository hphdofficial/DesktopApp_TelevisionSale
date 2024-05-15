package DTO;

public class KhachHangDTO {
    private String maKH, hoKH, tenKH, gioiTinh, dienThoai, diaChi;

    public KhachHangDTO() {
    }

    public KhachHangDTO(String maKH, String hoKH, String tenKH, String gioiTinh, String dienThoai, String diaChi) {
        this.maKH = maKH;
        this.hoKH = hoKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoKH() {
        return hoKH;
    }

    public void setHoKH(String hoKH) {
        this.hoKH = hoKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}