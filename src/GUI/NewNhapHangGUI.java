//package GUI;
//
//import BUS.*;
//import DTO.ChiTietNHDTO;
//import DTO.NhapHangDTO;
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
//public class NewNhapHangGUI extends JPanel implements ActionListener, KeyListener {
//    private final NhapHangBUS nhBUS = new NhapHangBUS();
//    private final ChiTietNHBUS ctBUS = new ChiTietNHBUS();
//    private final SanPhamBUS spBUS = new SanPhamBUS();
//    private final NhaCungCapBUS nccBUS = new NhaCungCapBUS();
//
//    private final ArrayList<ChiTietNHDTO> dsCT = new ArrayList<>();
//    private final String userID;
//    private final int Default_Width;
//    private final int Default_Height;
//    private JTextField txtMaNH;
//    private JTextField txtMaNCC;
//    private JTextField txtNgayNH;
//    private JTextField txtTongTien;
//    private JTextField txtMaSP;
//    private JTextField txtTenSP;
//    private JTextField txtSL;
//    private JTextField txtGia;
//    private JButton btnMaNCC;
//    private JButton btnMaSP;
//    private JButton btnNewNH;
//    private JButton btnConfirm;
//    private JButton btnDeleteNH;
//    private JButton btnAdd;
//    private JButton btnEdit;
//    private JButton btnRemove;
//    private JLabel imgSP;
//    private JPanel detailView;
//    private DefaultTableModel model;
//    private JTable table;
//    private Page404 page;
//
//    public NewNhapHangGUI(int width, int height, String userID) {
//        this.userID = userID;
//        Default_Width = width;
//        Default_Height = height;
//        nhBUS.list();
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
//        /******************** VIEW NHAPHANG ********************/
//
//        JPanel billView = new JPanel(null);
//        billView.setBackground(new Color(0, 255, 211));
//        billView.setBounds(new Rectangle(0, 0, Default_Width, 150));
//
//        JLabel lbMaNH = new JLabel("Mã nhập hàng");
//        lbMaNH.setFont(fontPlain);
//        lbMaNH.setBounds(80, 20, 120, 30);
//        txtMaNH = new JTextField(nhBUS.remindMaNH());
//        txtMaNH.setHorizontalAlignment(JTextField.CENTER);
//        txtMaNH.setFont(fontPlain);
//        txtMaNH.setEditable(false);
//        txtMaNH.setBounds(new Rectangle(200, 20, 150, 30));
//        txtMaNH.addKeyListener(this);
//        billView.add(lbMaNH);
//        billView.add(txtMaNH);
//
//        JLabel lbMaNCC = new JLabel("Mã nhà cung cấp");
//        lbMaNCC.setFont(fontPlain);
//        lbMaNCC.setBounds(80, 60, 120, 30);
//        txtMaNCC = new JTextField();
//        txtMaNCC.setHorizontalAlignment(JTextField.CENTER);
//        txtMaNCC.setFont(fontPlain);
//        txtMaNCC.setBounds(new Rectangle(200, 60, 150, 30));
//        txtMaNCC.addKeyListener(this);
//        billView.add(lbMaNCC);
//        billView.add(txtMaNCC);
//        btnMaNCC = new JButton("...");
//        btnMaNCC.setBounds(new Rectangle(350, 60, 30, 30));
//        btnMaNCC.addActionListener(this);
//        billView.add(btnMaNCC);
//
//        JLabel lbNgayNH = new JLabel("Ngày nhập hàng");
//        lbNgayNH.setFont(fontPlain);
//        lbNgayNH.setBounds(450, 30, 120, 40);
//        txtNgayNH = new JTextField("Automatic");
//        txtNgayNH.setEditable(false);
//        txtNgayNH.setHorizontalAlignment(JTextField.CENTER);
//        txtNgayNH.setFont(fontPlain);
//        txtNgayNH.setBounds(new Rectangle(570, 30, 300, 40));
//        billView.add(lbNgayNH);
//        billView.add(txtNgayNH);
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
//        btnNewNH = new JButton("Tạo phiếu nhập hàng");
//        btnNewNH.setFont(fontPlain);
//        btnNewNH.setBounds(new Rectangle(950, 50, 150, 50));
//        btnNewNH.addActionListener(this);
//        billView.add(btnNewNH);
//
//        btnConfirm = new JButton("Xác nhận");
//        btnConfirm.setFont(fontPlain);
//        btnConfirm.setBounds(new Rectangle(950, 20, 150, 50));
//        btnConfirm.setVisible(false);
//        btnConfirm.addActionListener(this);
//        billView.add(btnConfirm);
//
//        btnDeleteNH = new JButton("Xóa phiếu nhập hàng");
//        btnDeleteNH.setFont(fontPlain);
//        btnDeleteNH.setBounds(new Rectangle(950, 80, 150, 50));
//        btnDeleteNH.addActionListener(this);
//        btnDeleteNH.setVisible(false);
//        billView.add(btnDeleteNH);
//
//        JSeparator sepNH = new JSeparator(0);
//        sepNH.setBounds(new Rectangle(0, 145, Default_Width, 5));
//        billView.add(sepNH);
//
//        add(billView);
//
//        /******************** VIEW CHITIETNHAPHANG ********************/
//
//        page = new Page404(500, 200, "Tạo phiếu nhập hàng");
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
//    public void outModel(DefaultTableModel model, ArrayList<ChiTietNHDTO> ds) {   // Xuất ra Table từ ArrayList
//        Vector data;
//        model.setRowCount(0);
//        for (ChiTietNHDTO sp : ds) {
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
//    public int sumNH() {
//        int total = 0;
//        for (ChiTietNHDTO sp : dsCT) {
//            int amount = sp.getSoLuong();
//            double price = sp.getDonGia();
//            total += amount * price;
//        }
//        return total;
//    }
//
//    public void blockNH(boolean flag) {
//        txtMaNH.setEditable(flag);
//        txtMaNCC.setEditable(flag);
//        txtNgayNH.setEditable(flag);
//        btnMaNCC.setEnabled(flag);
//    }
//
//    public void clear(boolean flag) {
//        if (flag) {
//            //  Hóa đơn
//            txtMaNH.setText(nhBUS.remindMaNH());
//            txtMaNCC.setText("");
//            txtNgayNH.setText("");
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
//        btnNewNH.setVisible(flag);
//        btnConfirm.setVisible(!flag);
//        btnDeleteNH.setVisible(!flag);
//        clear(flag);
//        blockNH(flag);
//
//        detailView.setVisible(!flag);
//        page.setVisible(flag);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnMaNCC) {
//            ListNhaCungCap kh = new ListNhaCungCap(1150, 750);
//            String s = kh.getInfoNCC();
//            txtMaNCC.setText(s);
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
//            for (ChiTietNHDTO sp : dsCT) {
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
//                dsCT.add(new ChiTietNHDTO(txtMaNH.getText(), txtMaSP.getText(), txtTenSP.getText(), amount, price));
//            }
//            outModel(model, dsCT);
//            NumberFormat num = NumberFormat.getInstance();
//            txtTongTien.setText(num.format(sumNH()));
//        }
//
//        if (e.getSource().equals(btnNewNH)) {
//            if (txtMaNH.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hóa đơn");
//                txtMaNH.requestFocus();
//                return;
//            } else if (nhBUS.check(txtMaNH.getText())) {
//                JOptionPane.showMessageDialog(null, "Mã hóa đơn đã tồn tại");
//                txtMaNH.requestFocus();
//                txtMaNH.setText(nhBUS.remindMaNH());
//                return;
//            }
//            if (txtMaNCC.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhà cung cấp");
//                txtMaNCC.requestFocus();
//                return;
//            } else if (!nccBUS.check(txtMaNCC.getText())) {
//                JOptionPane.showMessageDialog(null, "Mã nhà cung cấp không tồn tại");
//                txtMaNCC.requestFocus();
//                return;
//            }
//            LocalDateTime myDateObj = LocalDateTime.now();
//            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            txtNgayNH.setText(myDateObj.format(myFormatObj));
//
//            reset(false);
//
//            txtMaSP.requestFocus();
//        }
//        if (e.getSource().equals(btnDeleteNH)) {
//            reset(true);
//        }
//        if (e.getSource().equals(btnConfirm)) {
//            if (dsCT.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập sản phẩm");
//                return;
//            }
//            String maNH = txtMaNH.getText();
//            String maNCC = txtMaNCC.getText();
//            String ngayNH = txtNgayNH.getText();
//            double tongTien = Double.parseDouble(txtTongTien.getText().replace(",", ""));
//            NhapHangDTO hd = new NhapHangDTO(maNH, maNCC, ngayNH, tongTien);
//            nhBUS.add(hd);
//            for (ChiTietNHDTO ct : dsCT) {
//                ctBUS.add(ct);
//            }
//            OutBill bill = new OutBill(hd, dsCT);
//            bill.printNH();
//            reset(true);
//        }
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
//                for (ChiTietNHDTO ct : dsCT) {
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
//                txtTongTien.setText(num.format(sumNH()));
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
//            if (a.equals(txtMaNH) || a.equals(txtMaNCC)) {
//                btnNewNH.doClick();
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