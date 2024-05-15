package GUI.Model;

import javax.swing.*;

public class ModelMenu {

    // Một menu cần list bao gồm icon + name
    private String icon;
    private String name;
    private MenuType type;

    // Một ModelMenu gồm 3 thành phần
    public ModelMenu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    // Dùng để lấy icon trong thư mục ICON
    public Icon toIcon() {
        return new ImageIcon(getClass().getResource("/ICON/" + icon + ".png"));
    }

    // Định nghĩa tập hợp các hằng số
    // 1.
    // 2. MENU hiển thị icon và title
    // 3. EMPTY tạo 1 khoảng trống để phân chia thành 2 vùng
    public static enum MenuType {
        TITLE, MENU, EMPTY;
    }
}