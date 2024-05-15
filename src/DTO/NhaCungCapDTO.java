package DTO;

public class NhaCungCapDTO {
    private String maNCC, tenNCC, diaChi, dienThoai, soFax;

    public NhaCungCapDTO() {
    }

    public NhaCungCapDTO(String maNCC, String tenNCC, String diaChi, String dienThoai, String soFax) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.soFax = soFax;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getSoFax() {
        return soFax;
    }

    public void setSoFax(String soFax) {
        this.soFax = soFax;
    }
}