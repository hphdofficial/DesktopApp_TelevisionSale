package DTO;

public class QuyenDTO {
    private String maQuyen, tenQuyen;

    public QuyenDTO() {
    }

    public QuyenDTO(String maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    @Override
    public String toString() {
        return tenQuyen;
    }
}