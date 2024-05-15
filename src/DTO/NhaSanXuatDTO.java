package DTO;

public class NhaSanXuatDTO {
    private String maNSX, tenNSX;

    public NhaSanXuatDTO() {
    }

    public NhaSanXuatDTO(String maNSX, String tenNSX) {
        this.maNSX = maNSX;
        this.tenNSX = tenNSX;
    }

    public String getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(String maNSX) {
        this.maNSX = maNSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    @Override
    public String toString() {
        return tenNSX;
    }
}