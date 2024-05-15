package DTO;

public class ChiTietQuyenDTO {
    private String maQuyen, maCN;

    public ChiTietQuyenDTO() {
    }

    public ChiTietQuyenDTO(String maQuyen, String maCN) {
        this.maQuyen = maQuyen;
        this.maCN = maCN;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }
}