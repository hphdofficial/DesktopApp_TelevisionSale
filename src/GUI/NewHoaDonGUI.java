//package GUI;
//
//import BUS.*;
//import DTO.ChiTietHDDTO;
//import DTO.HoaDonDTO;
//import DTO.SanPhamDTO;
//import GUI.model.Page404;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.text.NumberFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Vector;
//
//public class NewHoaDonGUI extends JPanel implements ActionListener, KeyListener {
//    private final HoaDonBUS hdBUS = new HoaDonBUS();
//    private final ChiTietHDBUS ctBUS = new ChiTietHDBUS();
//    private final SanPhamBUS spBUS = new SanPhamBUS();
//    private final NhanVienBUS nvBUS = new NhanVienBUS();
//    private final KhachHangBUS khBUS = new KhachHangBUS();
//
//    private final ArrayList<ChiTietHDDTO> dsCT = new ArrayList<>();
//    private final String userID;
//    private final int Default_Width;
//    private final int Default_Height;
//    private JTextField txtMaHD;
//    private JTextField txtMaKH;
//    private JTextField txtMaNV;
//    private JTextField txtNgayHD;
//    private JTextField txtTongTien;
//    private JTextField txtMaSP;
//    private JTextField txtTenSP;
//    private JTextField txtSL;
//    private JTextField txtGia;
//    private JButton btnMaKH;
//    private JButton btnMaNV;
//    private JButton btnMaSP;
//    private JButton btnNewHD;
//    private JButton btnConfirm;
//    private JButton btnDeleteHD;
//    private JButton btnAdd;
//    private JButton btnEdit;
//    private JButton btnRemove;
//    private JLabel imgSP;
//    private JPanel detailView;
//    private DefaultTableModel model;
//    private JTable table;
//    private Page404 page;
//
//    public NewHoaDonGUI(int width, int height, String userID) {
//        this.userID = userID;
//        Default_Width = width;
//        Default_Height = height;
//        hdBUS.list();
//        spBUS.list();
//        init();
//    }
//
//    public void init() {
//        setLayout(null);
//        setBackground(null);
//        setBounds(new Rectangle(0, 0, Default_Width, Default_Height));
//        Font fontPlain = new Font("Segoe UI", Font.PLAIN, 14);
//        Font fontBold = new Font("Segoe UI", Font.BOLD, 13);
//
//        /******************** VIEW HOADON ********************/
//
//        JPanel billView = new JPanel(null);
//        billView.setBackground(new Color(0, 255, 211));
//        billView.setBounds(new Rectangle(0, 0, Default_Width, 150));
//
//        JLabel lbMaHD = new JLabel("Mã hóa đơn");
//        lbMaHD.setFont(fontPlain);
//        lbMaHD.setBounds(80, 20, 120, 30);
//        txtMaHD = new JTextField(hdBUS.remindMaHD());
//        txtMaHD.setHorizontalAlignment(JTextField.CENTER);
//        txtMaHD.setFont(fontPlain);
//        txtMaHD.setEditable(false);
//        txtMaHD.setBounds(new Rectangle(200, 20, 150, 30));
//        txtMaHD.addKeyListener(this);
//        billView.add(lbMaHD);
//        billView.add(txtMaHD);
//
//        JLabel lbMaKH = new JLabel("Mã khách hàng");
//        lbMaKH.setFont(fontPlain);
//        lbMaKH.setBounds(80, 60, 120, 30);
//        txtMaKH = new JTextField();
//        txtMaKH.setHorizontalAlignment(JTextField.CENTER);
//        txtMaKH.setFont(fontPlain);
//        txtMaKH.setBounds(new Rectangle(200, 60, 150, 30));
//        txtMaKH.addKeyListener(this);
//        billView.add(lbMaKH);
//        billView.add(txtMaKH);
//        btnMaKH = new JButton("...");
//        btnMaKH.setBounds(new Rectangle(350, 60, 30, 30));
//        btnMaKH.addActionListener(this);
//        billView.add(btnMaKH);
//
//        JLabel lbMaNV = new JLabel("Mã nhân viên");
//        lbMaNV.setFont(fontPlain);
//        lbMaNV.setBounds(80, 100, 120, 30);
//        txtMaNV = new JTextField();
//        if (userID != null) {
//            txtMaNV.setText(userID);
//        }
//        txtMaNV.setHorizontalAlignment(JTextField.CENTER);
//        txtMaNV.setFont(fontPlain);
//        txtMaNV.setBounds(new Rectangle(200, 100, 150, 30));
//        txtMaNV.addKeyListener(this);
//        billView.add(lbMaNV);
//        billView.add(txtMaNV);
//        btnMaNV = new JButton("...");
//        btnMaNV.setBounds(new Rectangle(350, 100, 30, 30));
//        btnMaNV.addActionListener(this);
//        billView.add(btnMaNV);
//
//        JLabel lbNgayHD = new JLabel("Ngày hóa đơn");
//        lbNgayHD.setFont(fontPlain);
//        lbNgayHD.setBounds(450, 30, 120, 40);
//        txtNgayHD = new JTextField("Automatic");
//        txtNgayHD.setEditable(false);
//        txtNgayHD.setHorizontalAlignment(JTextField.CENTER);
//        txtNgayHD.setFont(fontPlain);
//        txtNgayHD.setBounds(new Rectangle(570, 30, 300, 40));
//        billView.add(lbNgayHD);
//        billView.add(txtNgayHD);
//
//        JLabel lbTongTien = new JLabel("Tổng Tiền (VNĐ)");
//        lbTongTien.setFont(fontPlain);
//        lbTongTien.setBounds(450, 80, 120, 40);
//        txtTongTien = new JTextField("0");
//        txtTongTien.setEditable(false);
//        txtTongTien.setHorizontalAlignment(JTextField.CENTER);
//        txtTongTien.setFont(fontPlain);
//        txtTongTien.setBounds(new Rectangle(570, 80, 300, 40));
//        billView.add(lbTongTien);
//        billView.add(txtTongTien);
//
//        btnNewHD = new JButton("Tạo hóa đơn");
//        btnNewHD.setFont(fontPlain);
//        btnNewHD.setBounds(new Rectangle(950, 50, 150, 50));
//        btnNewHD.addActionListener(this);
//        billView.add(btnNewHD);
//
//        btnConfirm = new JButton("Xác nhận");
//        btnConfirm.setFont(fontPlain);
//        btnConfirm.setBounds(new Rectangle(950, 20, 150, 50));
//        btnConfirm.setVisible(false);
//        btnConfirm.addActionListener(this);
//        billView.add(btnConfirm);
//
//        btnDeleteHD = new JButton("Xóa hóa đơn");
//        btnDeleteHD.setFont(fontPlain);
//        btnDeleteHD.setBounds(new Rectangle(950, 80, 150, 50));
//        btnDeleteHD.addActionListener(this);
//        btnDeleteHD.setVisible(false);
//        billView.add(btnDeleteHD);
//
//        JSeparator sepHD = new JSeparator(0);
//        sepHD.setBounds(new Rectangle(0, 145, Default_Width, 5));
//        billView.add(sepHD);
//
//        add(billView);
//
//        /******************** VIEW CHITIETHOADON ********************/
//
//        page = new Page404(500, 200, "Tạo hóa đơn");
//        page.setBounds(new Rectangle(0, 0, Default_Width, Default_Height));
//        add(page);
//
//        detailView = new JPanel(null);
//        detailView.setBackground(new Color(159, 255, 248));
//        detailView.setBounds(new Rectangle(0, 150, Default_Width, Default_Height - 150));
//        detailView.setVisible(false);
//
//        imgSP = new JLabel();
//        imgSP.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(155, 155, 155)));
//        imgSP.setBounds(new Rectangle(100, 50, 250, 200));
//        imgSP.setHorizontalAlignment(JLabel.CENTER);
//        detailView.add(imgSP);
//
//        JLabel lbMaSP = new JLabel("Mã sản phẩm");
//        lbMaSP.setFont(fontPlain);
//        lbMaSP.setBounds(100, 300, 100, 30);
//        txtMaSP = new JTextField();
//        txtMaSP.setHorizontalAlignment(JTextField.CENTER);
//        txtMaSP.setFont(fontPlain);
//        txtMaSP.setBounds(new Rectangle(200, 300, 150, 30));
//        txtMaSP.addKeyListener(this);
//        detailView.add(lbMaSP);
//        detailView.add(txtMaSP);
//        btnMaSP = new JButton("...");
//        btnMaSP.setBounds(new Rectangle(350, 300, 30, 30));
//        btnMaSP.addActionListener(this);
//        detailView.add(btnMaSP);
//
//        JLabel lbTenSP = new JLabel("Tên sản phẩm");
//        lbTenSP.setFont(fontPlain);
//        lbTenSP.setBounds(100, 340, 100, 30);
//        txtTenSP = new JTextField();
//        txtTenSP.setEditable(false);
//        txtTenSP.setHorizontalAlignment(JTextField.CENTER);
//        txtTenSP.setFont(fontPlain);
//        txtTenSP.setBounds(new Rectangle(200, 340, 150, 30));
//        detailView.add(lbTenSP);
//        detailView.add(txtTenSP);
//
//        JLabel lbSL = new JLabel("Số lượng");
//        lbSL.setFont(fontPlain);
//        lbSL.setBounds(100, 380, 100, 30);
//        txtSL = new JTextField();
//        txtSL.setHorizontalAlignment(JTextField.CENTER);
//        txtSL.setFont(fontPlain);
//        txtSL.addKeyListener(this);
//        txtSL.setBounds(new Rectangle(200, 380, 150, 30));
//        detailView.add(lbSL);
//        detailView.add(txtSL);
//
//        JLabel lbGia = new JLabel("Đơn giá");
//        lbGia.setFont(fontPlain);
//        lbGia.setBounds(100, 420, 100, 30);
//        txtGia = new JTextField();
//        txtGia.setEditable(false);
//        txtGia.setHorizontalAlignment(JTextField.CENTER);
//        txtGia.setFont(fontPlain);
//        txtGia.setBounds(new Rectangle(200, 420, 150, 30));
//        detailView.add(lbGia);
//        detailView.add(txtGia);
//
//        btnAdd = new JButton("Thêm sản phẩm vào danh sách");
//        btnAdd.setFont(fontPlain);
//        btnAdd.addActionListener(this);
//        btnAdd.setBounds(new Rectangle(100, 480, 250, 50));
//        detailView.add(btnAdd);
//
//        btnEdit = new JButton("Sửa sản phẩm");
//        btnEdit.setFont(fontPlain);
//        btnEdit.addActionListener(this);
//        btnEdit.setBounds(new Rectangle(780, 480, 150, 50));
//        detailView.add(btnEdit);
//
//        btnRemove = new JButton("Xóa sản phẩm");
//        btnRemove.setFont(fontPlain);
//        btnRemove.addActionListener(this);
//        btnRemove.setBounds(new Rectangle(960, 480, 150, 50));
//        detailView.add(btnRemove);
//
//        /******************** HEADER TABLE ********************/
//
//        Vector header = new Vector();
//        header.add("Mã sản phẩm");
//        header.add("Tên sản phẩm");
//        header.add("Số lượng");
//        header.add("Đơn giá");
//        model = new DefaultTableModel(header, 0) {
//            public Class getColumnClass(int column) {
//                switch (column) {
//                    case 2:
//                        return Integer.class;
//                    case 3:
//                        return Double.class;
//                    default:
//                        return String.class;
//                }
//            }
//        };
//        table = new JTable(model);
//        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
//        table.setRowSorter(rowSorter);
//
//        /******************** CREATE TABLE ********************/
//
//        // Chỉnh width các cột
//        table.getColumnModel().getColumn(0).setPreferredWidth(50);
//        table.getColumnModel().getColumn(1).setPreferredWidth(150);
//        table.getColumnModel().getColumn(2).setPreferredWidth(50);
//        table.getColumnModel().getColumn(3).setPreferredWidth(50);
//
//        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
//
//        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
//        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
//        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
//        leftAlign.setHorizontalAlignment(JLabel.LEFT);
//        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
//        centerAlign.setHorizontalAlignment(JLabel.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
//        table.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
////        table.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
////        table.getColumnModel().getColumn(3).setCellRenderer(leftAlign);
//
//        // Custom table
//        table.setFocusable(false);
//        table.setRowHeight(40);
//        table.getTableHeader().setFont(fontPlain);
//        table.getTableHeader().setBackground(Color.red);
//        table.getTableHeader().setForeground(Color.black);
//        table.getTableHeader().setOpaque(false);
//        table.setIntercellSpacing(new Dimension(0, 0));
//        table.setShowVerticalLines(false);
//        table.setFillsViewportHeight(true);
//        table.setSelectionBackground(new Color(24, 85, 252));
////        table.addMouseListener(this);
//
//        // Add table vào ScrollPane
//        JScrollPane scroll = new JScrollPane(table);
//        scroll.setBounds(new Rectangle(400, 50, Default_Width - 400 - 50, Default_Height - 200 - 200));
//        scroll.setBackground(null);
//
//        detailView.add(scroll);
//        add(detailView);
//        setVisible(true);
//    }
//
//    public void outModel(DefaultTableModel model, ArrayList<ChiTietHDDTO> ds) {   // Xuất ra Table từ ArrayList
//        Vector data;
//        model.setRowCount(0);
//        for (ChiTietHDDTO sp : ds) {
//            data = new Vector();
//            data.add(sp.getMaSP());
//            data.add(sp.getTenSP());
//            data.add(sp.getSoLuong());
//            data.add(sp.getDonGia());
//            model.addRow(data);
//        }
//        table.setModel(model);
//    }
//
//    public int sumHD() {
//        int total = 0;
//        for (ChiTietHDDTO sp : dsCT) {
//            int amount = sp.getSoLuong();
//            double price = sp.getDonGia();
//            total += amount * price;
//        }
//        return total;
//    }
//
//    public void blockHD(boolean flag) {
//        txtMaHD.setEditable(flag);
//        txtMaKH.setEditable(flag);
//        txtMaNV.setEditable(flag);
//        txtNgayHD.setEditable(flag);
//        btnMaNV.setEnabled(flag);
//    }
//
//    public void clear(boolean flag) {
//        if (flag) {
//            //  Hóa đơn
//            txtMaHD.setText(hdBUS.remindMaHD());
//            txtMaKH.setText("");
//            txtMaNV.setText("");
//            if (userID != null) {
//                txtMaNV.setText(userID);
//            }
//            txtNgayHD.setText("");
//            txtTongTien.setText("0");
//
//            //  Chi tiết hóa đơn
//            dsCT.removeAll(dsCT);
//            txtMaSP.setText("");
//            txtTenSP.setText("");
//            txtSL.setText("");
//            txtGia.setText("");
//            imgSP.setIcon(null);
//
//            model.getDataVector().removeAllElements();  //  Xóa trắng table
//        }
//    }
//
//    public void reset(boolean flag) {
//        btnNewHD.setVisible(flag);
//        btnConfirm.setVisible(!flag);
//        btnDeleteHD.setVisible(!flag);
//        clear(flag);
//        blockHD(flag);
//
//        detailView.setVisible(!flag);
//        page.setVisible(flag);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnMaNV) {
//            ListNhanVien nv = new ListNhanVien(1150, 750);
//            String s = nv.getInfoNV();
//            txtMaNV.setText(s);
//        }
//        if (e.getSource() == btnMaKH) {
//            ListKhachHang kh = new ListKhachHang(1150, 750);
//            String s = kh.getInfoKH();
//            txtMaKH.setText(s);
//        }
//        if (e.getSource() == btnMaSP) {
//            ListSanPham sp = new ListSanPham(1150, 750, txtMaSP.getText());
//            String[] s = sp.getInfoSP().split("%");
//            txtMaSP.setText(s[0]);
//            txtTenSP.setText(s[1]);
//            txtGia.setText(s[2]);
//            Image newImage;
//            try {
//                newImage = new ImageIcon("./img/SanPham/" + s[3]).getImage().getScaledInstance(250, 200, Image.SCALE_DEFAULT);
//            } catch (NullPointerException ex) {
//                newImage = new ImageIcon("./img/SanPham/NoImage.jpg").getImage().getScaledInstance(250, 200, Image.SCALE_DEFAULT);
//            }
//            imgSP.setIcon(new ImageIcon(newImage));
//            txtSL.requestFocus();   //  Chỉnh Focus vào txtSL
//        }
//        if (e.getSource().equals(btnAdd)) {
//            int amount = 0;
//            try {
//                amount = Integer.parseInt(txtSL.getText());
//            } catch (NumberFormatException E) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng");
//                return;
//            }
//            double price = Double.parseDouble(txtGia.getText().replace(",", ""));
//
//            //  Kiểm tra SP đã có trong giỏ chưa
//            boolean flag = true;
//            for (ChiTietHDDTO sp : dsCT) {
//                if (sp.getMaSP().equals(txtMaSP.getText())) {
//                    int old = sp.getSoLuong();
//                    if (!spBUS.checkSL(txtMaSP.getText(), amount + old)) {
//                        return;
//                    }
//                    sp.setSoLuong(amount + old);
//                    flag = false;
//                    break;
//                }
//            }
//            if (flag) {
//                if (!spBUS.checkSL(txtMaSP.getText(), amount)) {
//                    return;
//                }
//                dsCT.add(new ChiTietHDDTO(txtMaHD.getText(), txtMaSP.getText(), txtTenSP.getText(), amount, price));
//            }
//            outModel(model, dsCT);
//            NumberFormat num = NumberFormat.getInstance();
//            txtTongTien.setText(num.format(sumHD()));
//        }
//
//        if (e.getSource().equals(btnNewHD)) {
//            if (txtMaHD.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hóa đơn");
//                txtMaHD.requestFocus();
//                return;
//            } else if (hdBUS.check(txtMaHD.getText())) {
//                JOptionPane.showMessageDialog(null, "Mã hóa đơn đă tồn tại");
//                txtMaHD.requestFocus();
//                txtMaHD.setText(hdBUS.remindMaHD());
//                return;
//            }
//            if (!txtMaKH.getText().isEmpty() && !khBUS.check(txtMaKH.getText())) {
//                JOptionPane.showMessageDialog(null, "Mã khách hàng không tồn tại");
//                txtMaKH.requestFocus();
//                return;
//            }
//            if (txtMaNV.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên");
//                txtMaNV.requestFocus();
//                return;
//            } else if (!nvBUS.check(txtMaNV.getText())) {
//                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại");
//                txtMaNV.requestFocus();
//                return;
//            }
//            LocalDateTime myDateObj = LocalDateTime.now();
//            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            txtNgayHD.setText(myDateObj.format(myFormatObj));
//
//            reset(false);
//
//            txtMaSP.requestFocus();
//        }
//
//        if (e.getSource().equals(btnDeleteHD)) {
//            reset(true);
//        }
//
//        if (e.getSource().equals(btnConfirm)) {
//            if (dsCT.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập sản phẩm");
//                return;
//            }
//            String maHD = txtMaHD.getText();
//            String maKH = txtMaKH.getText();
//            String maNV = txtMaNV.getText();
//            String ngayHD = txtNgayHD.getText();
//            double tongTien = Double.parseDouble(txtTongTien.getText().replace(",", ""));
//            HoaDonDTO hd = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
//            hdBUS.add(hd);
//            for (ChiTietHDDTO ct : dsCT) {
//                ctBUS.add(ct);
//            }
//            OutBill bill = new OutBill(hd, dsCT);
//            bill.printHD();
//            reset(true);
//        }
//
//        if (e.getSource().equals(btnEdit)) {
//            try {
//                int i = table.getSelectedRow();
//                if (table.getRowSorter() != null) {
//                    i = table.getRowSorter().convertRowIndexToModel(i);
//                }
//                String masp = table.getModel().getValueAt(i, 0).toString();
//                int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng sản phẩm :"));
//                while (!spBUS.checkSL(masp, amount)) {
//                    amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng sản phẩm :"));
//                }
//                for (ChiTietHDDTO ct : dsCT) {
//                    if (ct.getMaSP().equals(masp)) {
//                        ct.setSoLuong(amount);
//                    }
//                }
//                outModel(model, dsCT);
//            } catch (IndexOutOfBoundsException ex) {
//                JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm cần sửa");
//            }
//        }
//        if (e.getSource().equals(btnRemove)) {
//            try {
//                int i = table.getSelectedRow();
//                if (table.getRowSorter() != null) {
//                    i = table.getRowSorter().convertRowIndexToModel(i);
//                }
//                dsCT.remove(i);
//                model.removeRow(i);
//                NumberFormat num = NumberFormat.getInstance();
//                txtTongTien.setText(num.format(sumHD()));
//            } catch (IndexOutOfBoundsException ex) {
//                JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm cần xóa");
//            }
//        }
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        Object a = e.getSource();
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            if (a.equals(txtMaHD) || a.equals(txtMaKH) || a.equals(txtMaNV)) {
//                btnNewHD.doClick();
//            } else if (a.equals(txtMaSP)) {
//                try {
//                    SanPhamDTO sp = spBUS.get(txtMaSP.getText());
//                    Image img = new ImageIcon("./src/image/SanPham/" + sp.getImg()).getImage().getScaledInstance(200, 230, Image.SCALE_DEFAULT);
//                    imgSP.setIcon(new ImageIcon(img));
//                    txtTenSP.setText(sp.getTenSP());
//                    NumberFormat num = NumberFormat.getInstance();
//                    txtGia.setText(num.format(sp.getGiaSP()));
//                } catch (NullPointerException ex) {
//                    JOptionPane.showMessageDialog(null, "Mã sản phẩm không tồn tại !!");
//                }
//            } else if (a.equals(txtSL)) {
//                btnAdd.doClick();
//            }
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//
//    }
//}