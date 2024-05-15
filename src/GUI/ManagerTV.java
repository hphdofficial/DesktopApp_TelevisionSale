package GUI;

import BUS.ChiTietQuyenBUS;
import DTO.ChiTietQuyenDTO;
import GUI.Model.header;
import GUI.Model.navItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ManagerTV extends JFrame implements MouseListener {
    private final int Default_Width = 1400;
    private final int Default_Height = 800;
    private final ArrayList<navItem> navObj = new ArrayList<>();  //  Chứa cái button trên thanh menu
    private final boolean flag = true;
    private final ChiTietQuyenBUS ctRoleBUS = new ChiTietQuyenBUS();
    private String userID;
    private String userName;
    private String roleID;
    private JPanel header, nav, main;
    private ArrayList<String> navItem = new ArrayList<>();  //  Chứa thông tin có button cho menu gồm
    private int indexItem;

    public ManagerTV() {
        Toolkit screen = Toolkit.getDefaultToolkit();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerTV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        init();
    }

    public ManagerTV(String userID, String userName, String roleID) {
        this.userID = userID;
        this.userName = userName;
        this.roleID = roleID;
        Toolkit screen = Toolkit.getDefaultToolkit();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerTV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        init();
    }

    public static void main(String[] args) {
        new Login();
    }

    public void init() {
        Font font = new Font("Segoe UI", Font.BOLD, 14);
        setTitle("QUẢN LÝ CỬA HÀNG TIVI");
        ImageIcon logo = new ImageIcon("./img/iconTV.png");
        setIconImage(logo.getImage());
        setLayout(new BorderLayout());
        setSize(Default_Width, Default_Height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
//        setShape(new RoundRectangle2D.Double(0, 0, Default_Width, Default_Height, 20, 20));

        /************ PHẦN HEADER *************************************/

        header = new JPanel(null);
        header.setBackground(new Color(0, 0, 0));
        header.setPreferredSize(new Dimension(Default_Width, 40));

        header hmain = new header(Default_Width, 40, this);

        if (userName != null) {
            if (roleID.equals("admin")) userName = "Admin";
            JLabel user = new JLabel("Chào, " + userName);
            user.setFont(font);
            user.setForeground(Color.white);
            user.setHorizontalAlignment(JLabel.RIGHT);
            user.setBounds(new Rectangle(Default_Width - 300, 0, 140, 40));
            hmain.add(user);

            navItem btnLogOut = new navItem("", new Rectangle(Default_Width - 150, -5, 50, 45), "logout_25px.png", "logout_25px.png", "logout_hover_25px.png", new Color(80, 80, 80));
            hmain.add(btnLogOut.isButton());
            btnLogOut.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    new Login();
                    dispose();
                }
            });
        }
        header.add(hmain);

        /************ PHẦN NAVIGATION ( MENU ) **************************/

        nav = new JPanel(null);
        nav.setBackground(new Color(0, 0, 100));
        nav.setPreferredSize(new Dimension(240, Default_Height - 50));

        JScrollPane scroll = new JScrollPane(nav);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.setHorizontalScrollBarPolicy(scroll.HORIZONTAL_SCROLLBAR_NEVER);

        //  Thêm item vào thanh menu (Số chức năng : Tên item : icon : icon hover)
        navItem = new ArrayList<>();
        ArrayList<ChiTietQuyenDTO> ctq = ctRoleBUS.getList();
        for (ChiTietQuyenDTO ct : ctq) {
            if (ct.getMaQuyen().equals(roleID)) {

                if (ct.getMaCN().equals("CN01")) {
                    navItem.add("1:Bán hàng:Shop_20px.png:Shop_20px_active.png");
                }

                if (ct.getMaCN().equals("CN02")) {
                    navItem.add("2:Đặt hàng:Shop_20px.png:Shop_20px_active.png");
                }

                if (ct.getMaCN().equals("CN03")) {
                    navItem.add("3:Quản lý hóa đơn:CongCu_20px.png:CongCu_20px_active.png");
                }

                if (ct.getMaCN().equals("CN04")) {
                    navItem.add("4:Quản lý nhập hàng:CongCu_20px.png:CongCu_20px_active.png");
                }

                if (ct.getMaCN().equals("CN05")) {
                    navItem.add("5:Quản lý sản phẩm:QLSP_20px.png:QLSP_20px_active.png");
                }

                if (ct.getMaCN().equals("CN06")) {
                    navItem.add("6:Quản lý nhân viên:NhanVien_20px.png:NhanVien_20px_active.png");
                }

                if (ct.getMaCN().equals("CN07")) {
                    navItem.add("7:Quản lý khách hàng:KhachHang_20px.png:KhachHang_20px_active.png");
                }

                if (ct.getMaCN().equals("CN08")) {
                    navItem.add("8:Quản lý nhà cung cấp:QLSP_20px.png:QLSP_20px_active.png");
                }

                if (ct.getMaCN().equals("CN09")) {
                    navItem.add("9:Thống kê:ThongKe_20px.png:ThongKe_20px_active.png");
                }

                if (ct.getMaCN().equals("CN10")) {
                    navItem.add("10:Quản lý tài khoản:CaiDat_20px.png:CaiDat_20px_active.png");
                }

                if (ct.getMaCN().equals("CN11")) {
                    navItem.add("11:Quản lý phân quyền:CaiDat_20px.png:CaiDat_20px_active.png");
                }
            }
        }
        outNav();

        /************ PHẦN MAIN ( HIỂN THỊ ) **************************/

        main = new JPanel(null);
        main.setBackground(Color.WHITE);
        navObj.get(0).doActive();
        changeMainInfo(0);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        setVisible(true);
    }

    public void changeMainInfo(int i) {     //  Đổi Phần hiển thị khi bấm btn trên menu
        switch (i) {
            case 0:
                main.removeAll();
                main.add(new BanHangGUI(Default_Width - 240, Default_Height, userID));
                main.repaint();
                main.revalidate();
                break;
            case 1:
                main.removeAll();
                main.add(new DatHangGUI(Default_Width - 240, Default_Height, userID));
                main.repaint();
                main.revalidate();
                break;
            case 2:
                main.removeAll();
                main.add(new HoaDonGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 3:
                main.removeAll();
                main.add(new NhapHangGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 4:
                main.removeAll();
                main.add(new SanPhamGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 5:
                main.removeAll();
                main.add(new NhanVienGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 6:
                main.removeAll();
                main.add(new KhachHangGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 7:
                main.removeAll();
                main.add(new NhaCungCapGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 8:
                main.removeAll();
                main.add(new ThongKeGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 9:
                main.removeAll();
                main.add(new UserGUI(Default_Width - 240, Default_Height - 40));
                main.repaint();
                main.revalidate();
                break;
            case 10:
                main.removeAll();
                main.add(new PhanQuyenGUI(Default_Width - 240, Default_Height));
                main.repaint();
                main.revalidate();
                break;
            default:
                break;
        }
    }

    public void outNav() {
        //  Gắn cái navItem vào navObj
        navObj.clear();
        for (int i = 0; i < navItem.size(); i++) {
            String s = navItem.get(i).split(":")[1];
            String icon = navItem.get(i).split(":")[2];
            String iconActive = navItem.get(i).split(":")[3];
            navObj.add(new navItem(s, new Rectangle(0, 200 + 50 * i, 240, 50), icon, iconActive));
            navObj.get(i).addMouseListener(this);
        }
        //  Xuất ra Naigation
        nav.removeAll();
        JLabel profile = new JLabel(new ImageIcon("./img/NhanVien/NV"+ userID +".png"));
        profile.setBounds(0, 0, 240, 200);
        nav.add(profile);
        for (navItem n : navObj) {
            nav.add(n);
        }
        repaint();
        revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < navObj.size(); i++) {
            navItem item = navObj.get(i); // Lấy vị trí item trong menu
            for (int j = 0; j < navItem.size(); j++) {
                indexItem = Integer.parseInt(navItem.get(i).split(":")[0]);
            }
            if (e.getSource() == item) {
                item.doActive(); // Active NavItem đc chọn
                changeMainInfo(indexItem - 1); // Hiển thị ra phần main
            } else item.noActive();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}