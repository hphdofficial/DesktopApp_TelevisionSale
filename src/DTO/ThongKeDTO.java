package DTO;

public class ThongKeDTO {
    private int soLuongSP, soLuongKH, soLuongNV, soLuongNCC;

    public ThongKeDTO() {
    }

    public ThongKeDTO(int soLuongSP, int soLuongKH, int soLuongNV, int soLuongNCC) {
        this.soLuongSP = soLuongSP;
        this.soLuongKH = soLuongKH;
        this.soLuongNV = soLuongNV;
        this.soLuongNCC = soLuongNCC;
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public int getSoLuongKH() {
        return soLuongKH;
    }

    public void setSoLuongKH(int soLuongKH) {
        this.soLuongKH = soLuongKH;
    }

    public int getSoLuongNV() {
        return soLuongNV;
    }

    public void setSoLuongNV(int soLuongNV) {
        this.soLuongNV = soLuongNV;
    }

    public int getSoLuongNCC() {
        return soLuongNCC;
    }

    public void setSoLuongNCC(int soLuongNCC) {
        this.soLuongNCC = soLuongNCC;
    }
}