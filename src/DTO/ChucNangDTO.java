package DTO;

public class ChucNangDTO {
    private String maCN, tenCN;

    public ChucNangDTO() {
    }

    public ChucNangDTO(String maCN, String tenCN) {
        this.maCN = maCN;
        this.tenCN = tenCN;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    @Override
    public String toString() {
        return tenCN;
    }
}