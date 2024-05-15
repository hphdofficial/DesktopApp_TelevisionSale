package GUI;

import BUS.*;
import DTO.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class DatHangGUI extends JPanel implements MouseListener, KeyListener, ActionListener {

    private final int Default_Width;
    private final int Default_Height;

    private final ArrayList<ChiTietNHDTO> dsct = new ArrayList<>();
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private final NhanVienBUS nvBUS = new NhanVienBUS();
    private final NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private final NhapHangBUS nhBUS = new NhapHangBUS();
    private final ChiTietNHBUS ctBUS = new ChiTietNHBUS();
    private final String userID;

    // Panel main
    private JPanel mainPanel;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelTableSP;
    private JPanel panelInfo;
    private JPanel panelIntro;

    // Label
    private JLabel lbIDBill;
    private JLabel lbIDProvider;
    private JLabel lbIconCustomer;
    private JLabel lbIDStaff;
    private JLabel lbIconStaff;
    private JLabel lbDateBill;
    private JLabel lbTotal;
    private JLabel lbIDProduct;
    private JLabel lbNameProduct;
    private JLabel lbIconProduct;
    private JLabel lbAmount;
    private JLabel lbprice;
    private JLabel imageProduct;

    // TextField
    private JTextField txtIDBill;
    private JTextField txtIDProvider;
    private JTextField txtIDStaff;
    private JTextField txtDateBill;
    private JTextField txtTotal;
    private JTextField txtIDProduct;
    private JTextField txtNameProduct;
    private JTextField txtAmount;
    private JTextField txtPrice;

    // Button
    private JButton btnDelete;
    private JButton btnSubmit;
    private JButton btnAdd;
    private JButton btnConfirm;
    private JButton btnDeleteProduct;
    private JButton btnFix;

    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollpane;
    private JTextArea txtInfo;
    private JTextArea txtIntro;

    public DatHangGUI(int width, int height, String userID) {
        this.userID = userID;
        Default_Width = width;
        Default_Height = height;
        nhBUS.list();
        spBUS.list();
        init();
    }

    public void init() {
        setBackground(new Color(205, 205, 255));
        setBounds(new Rectangle(0, 0, Default_Width - 5, Default_Height));
        Font font0 = new Font("Segoe UI", Font.BOLD, 13);
        Font font1 = new Font("Segoe UI", Font.PLAIN, 13);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1130, Default_Height - 50));
        mainPanel.setBackground(new Color(205, 205, 255));
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(690, Default_Height));
        panelLeft.setBackground(null);
        panelLeft.setLayout(new BorderLayout(0, 10));
        mainPanel.add(panelLeft, BorderLayout.WEST);

        panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(430, Default_Height));
        panelRight.setBackground(Color.white);
        panelRight.setLayout(null);
        panelRight.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        mainPanel.add(panelRight, BorderLayout.EAST);

        /*************************************************************/

        JLabel lbTitle = new JLabel("HÓA ĐƠN NHẬP HÀNG");
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbTitle.setForeground(Color.white);
        lbTitle.setBackground(new Color(140, 140, 255));
        lbTitle.setOpaque(true);
        lbTitle.setHorizontalAlignment(JLabel.CENTER);
        lbTitle.setBounds(0, 0, 430, 30);

        lbIDBill = new JLabel("Mã nhập hàng");
        lbIDBill.setFont(font0);
        lbIDBill.setBounds(20, 50, 120, 30);

        txtIDBill = new JTextField(nhBUS.remindMaNH());
        txtIDBill.setBounds(new Rectangle(150, 50, 240, 30));
        txtIDBill.setEditable(false);
        txtIDBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        txtIDBill.setHorizontalAlignment(JTextField.CENTER);

        lbIDProvider = new JLabel("Mã nhà cung cấp");
        lbIDProvider.setFont(font0);
        lbIDProvider.setBounds(20, 90, 120, 30);

        txtIDProvider = new JTextField();
        txtIDProvider.setBounds(new Rectangle(150, 90, 240, 30));
        txtIDProvider.setText("");
        txtIDProvider.setEditable(true);
        txtIDProvider.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        txtIDProvider.setHorizontalAlignment(JTextField.CENTER);

        lbIconCustomer = new JLabel();
        lbIconCustomer.setBounds(400, 90, 50, 30);
        lbIconCustomer.setIcon(new ImageIcon("./img/btnPlus.png"));
        lbIconCustomer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbIconCustomer.addMouseListener(this);

        lbIDStaff = new JLabel("Mã nhân viên");
        lbIDStaff.setFont(font0);
        lbIDStaff.setBounds(20, 130, 120, 30);

        txtIDStaff = new JTextField();
        txtIDStaff.setBounds(new Rectangle(150, 130, 240, 30));
        txtIDStaff.setText("");
        txtIDStaff.setEditable(true);
        if (userID != null) {
            txtIDStaff.setText(userID);
            txtIDStaff.setEditable(false);
        }
        txtIDStaff.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        txtIDStaff.setHorizontalAlignment(JTextField.CENTER);

        lbIconStaff = new JLabel();
        lbIconStaff.setBounds(400, 130, 50, 30);
        lbIconStaff.setIcon(new ImageIcon("./img/btnPlus.png"));
        lbIconStaff.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (userID != null) {
            lbIconStaff.setEnabled(false);
        }
        lbIconStaff.addMouseListener(this);

        lbDateBill = new JLabel("Ngày nhập hàng");
        lbDateBill.setFont(font0);
        lbDateBill.setBounds(20, 170, 120, 30);

        txtDateBill = new JTextField();
        txtDateBill.setBounds(new Rectangle(150, 170, 240, 30));
        txtDateBill.setText("Automatic");
        txtDateBill.setEditable(false);
        txtDateBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        txtDateBill.setHorizontalAlignment(JTextField.CENTER);

        lbTotal = new JLabel("Tổng tiền");
        lbTotal.setFont(font0);
        lbTotal.setBounds(20, 210, 120, 30);

        txtTotal = new JTextField();
        txtTotal.setBounds(new Rectangle(150, 210, 240, 30));
        txtTotal.setText("0");
        txtTotal.setEditable(false);
        txtTotal.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        txtTotal.setHorizontalAlignment(JTextField.CENTER);

        btnSubmit = new JButton("Tạo hóa đơn");
        btnSubmit.setIcon(new ImageIcon("./img/add-icon.png"));
        btnSubmit.setBounds(220, 260, 170, 50);
        btnSubmit.setBackground(Color.blue);
        btnSubmit.setForeground(Color.white);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(this);

        btnConfirm = new JButton("Xác nhận và in");
        btnConfirm.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm.setBounds(220, 260, 170, 50);
        btnConfirm.setBackground(Color.blue);
        btnConfirm.setForeground(Color.white);
        btnConfirm.setFocusPainted(false);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setVisible(false);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addActionListener(this);

        btnDelete = new JButton("Xóa hóa đơn");
        btnDelete.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete.setBounds(40, 260, 160, 50);
        btnDelete.setBackground(Color.blue);
        btnDelete.setForeground(Color.white);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setVisible(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(this);

        JLabel lbTitleCT = new JLabel("CHI TIẾT SẢN PHẨM");
        lbTitleCT.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbTitleCT.setForeground(Color.white);
        lbTitleCT.setBackground(new Color(140, 140, 255));
        lbTitleCT.setOpaque(true);
        lbTitleCT.setHorizontalAlignment(JLabel.CENTER);
        lbTitleCT.setBounds(0, 330, 430, 30);

        imageProduct = new JLabel();
        imageProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(155, 155, 155)));
        imageProduct.setBounds(new Rectangle(20, 410, 180, 210));
        imageProduct.setHorizontalAlignment(JLabel.CENTER);

        lbIDProduct = new JLabel("Mã sản phẩm");
        lbIDProduct.setBounds(220, 380, 100, 30);
        lbIDProduct.setFont(font0);

        txtIDProduct = new JTextField();
        txtIDProduct.setBounds(220, 410, 170, 30);
        txtIDProduct.setText("");
        txtIDProduct.setEditable(false);
        txtIDProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
        txtIDProduct.setHorizontalAlignment(JTextField.CENTER);

        lbIconProduct = new JLabel();
        lbIconProduct.setBounds(400, 410, 50, 30);
        lbIconProduct.setIcon(new ImageIcon("./img/btnPlus.png"));
        lbIconProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbIconProduct.setEnabled(false);
        lbIconProduct.addMouseListener(this);

        lbNameProduct = new JLabel("Tên sản phẩm");
        lbNameProduct.setBounds(220, 440, 100, 30);
        lbNameProduct.setFont(font0);

        txtNameProduct = new JTextField();
        txtNameProduct.setBounds(220, 470, 170, 30);
        txtNameProduct.setText("");
        txtNameProduct.setEditable(false);
        txtNameProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
        txtNameProduct.setHorizontalAlignment(JTextField.CENTER);

        lbAmount = new JLabel("Số lượng");
        lbAmount.setBounds(220, 500, 100, 30);
        lbAmount.setFont(font0);

        txtAmount = new JTextField();
        txtAmount.setBounds(220, 530, 170, 30);
        txtAmount.setText("");
        txtAmount.setEditable(false);
        txtAmount.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
        txtAmount.setHorizontalAlignment(JTextField.CENTER);
        txtAmount.addKeyListener(this);

        lbprice = new JLabel("Đơn giá");
        lbprice.setBounds(220, 560, 100, 30);
        lbprice.setFont(font0);

        txtPrice = new JTextField();
        txtPrice.setBounds(220, 590, 170, 30);
        txtPrice.setText("0");
        txtPrice.setEditable(false);
        txtPrice.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
        txtPrice.setHorizontalAlignment(JTextField.CENTER);

        btnAdd = new JButton("Thêm sản phẩm");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setBounds(220, 650, 170, 50);
        btnAdd.setBackground(Color.blue);
        btnAdd.setForeground(Color.white);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setEnabled(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(this);

        btnDeleteProduct = new JButton("Xóa sản phẩm");
        btnDeleteProduct.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDeleteProduct.setBounds(170, 470, 150, 50);
        btnDeleteProduct.setBackground(Color.blue);
        btnDeleteProduct.setForeground(Color.white);
        btnDeleteProduct.setFocusPainted(false);
        btnDeleteProduct.setBorderPainted(false);
        btnDeleteProduct.setEnabled(false);
        btnDeleteProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteProduct.addActionListener(this);

        btnFix = new JButton("Sửa số lượng");
        btnFix.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnFix.setBounds(370, 470, 150, 50);
        btnFix.setBackground(Color.blue);
        btnFix.setForeground(Color.white);
        btnFix.setFocusPainted(false);
        btnFix.setBorderPainted(false);
        btnFix.setEnabled(false);
        btnFix.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFix.addActionListener(this);

        panelRight.add(lbTitle);
        panelRight.add(lbIDBill);
        panelRight.add(txtIDBill);
        panelRight.add(lbIDProvider);
        panelRight.add(txtIDProvider);
        panelRight.add(lbIconCustomer);
        panelRight.add(lbIDStaff);
        panelRight.add(txtIDStaff);
        panelRight.add(lbIconStaff);
        panelRight.add(lbDateBill);
        panelRight.add(txtDateBill);
        panelRight.add(lbTotal);
        panelRight.add(txtTotal);
        panelRight.add(btnSubmit);
        panelRight.add(btnDelete);
        panelRight.add(btnConfirm);
        panelRight.add(lbTitleCT);
        panelRight.add(imageProduct);
        panelRight.add(lbIDProduct);
        panelRight.add(txtIDProduct);
        panelRight.add(lbIconProduct);
        panelRight.add(lbNameProduct);
        panelRight.add(txtNameProduct);
        panelRight.add(lbAmount);
        panelRight.add(txtAmount);
        panelRight.add(lbprice);
        panelRight.add(txtPrice);
        panelRight.add(btnAdd);

//        --------------- Table ----------------

        Vector header = new Vector();
        header.add("Mã sản phẩm");
        header.add("Tên sản phẩm");
        header.add("Số lượng");
        header.add("Đơn giá");
        model = new DefaultTableModel(header, 0) {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 2:
                        return Integer.class;
                    case 3:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };
        table = new JTable(model);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);

        // Chỉnh width các cột
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        centerAlign.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
//        table.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
//        table.getColumnModel().getColumn(3).setCellRenderer(leftAlign);

        // Custom table
        table.setFocusable(false);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font1);
        table.getTableHeader().setBackground(Color.red);
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setOpaque(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(24, 85, 252));
//        table.addMouseListener(this);

        scrollpane = new JScrollPane(table);
//        scrollpane.setBorder(null);
        scrollpane.setBackground(null);
        scrollpane.setBounds(new Rectangle(20, 60, 650, 400));
        scrollpane.getInsets().set(0, 0, 0, 0);
        scrollpane.setViewportBorder(null);
        scrollpane.getViewport().setBorder(null);
        scrollpane.getViewport().getInsets().set(0, 0, 0, 0);
        scrollpane.getViewport().setOpaque(true);

        panelTableSP = new JPanel(null);
        panelTableSP.setPreferredSize(new Dimension(0, 0));
        panelTableSP.setBackground(Color.white);
        panelTableSP.setVisible(false);
        panelTableSP.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelTableSP.add(scrollpane);
        panelTableSP.add(btnDeleteProduct);
        panelTableSP.add(btnFix);

        JLabel lbCart = new JLabel("Giỏ hàng");
        lbCart.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbCart.setForeground(Color.red);
        lbCart.setBounds(0, 0, 690, 60);
        lbCart.setHorizontalAlignment(JTextField.CENTER);
        panelTableSP.add(lbCart);

        panelInfo = new JPanel(null);
        panelInfo.setBackground(Color.white);
        panelInfo.setPreferredSize(new Dimension(0, 200));
        panelInfo.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelLeft.add(panelInfo, BorderLayout.NORTH);

        txtInfo = new JTextArea();
        txtInfo.setBounds(10, 10, 660, 170);
        txtInfo.setBackground(null);
        txtInfo.setBorder(null);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtInfo.setText("\t\tCỬA HÀNG TIVI SÀI GÒN\n" +
                "\tĐịa chỉ : An Dương Vương, Quận 5, Thành phố Hồ Chí Minh\n" +
                "\tĐiện thoại liện hệ : 0123456789\n" +
                "\tEmail : managertvsgu@gmail.com\n" +
                "---------------------------------------------------------------------------------------------------------------------------------------\n");
        txtInfo.setEditable(false);
        panelInfo.add(txtInfo);

        panelIntro = new JPanel(null);
        panelIntro.setBackground(Color.white);
        panelIntro.setPreferredSize(new Dimension(0, 0));
        panelIntro.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelLeft.add(panelIntro, BorderLayout.CENTER);

        JLabel lbIntro1 = new JLabel("");
        lbIntro1.setIcon(new ImageIcon("./img/banner1.png"));
        lbIntro1.setBounds(5, 5, 680, 180);
        lbIntro1.setVisible(true);
        panelIntro.add(lbIntro1);

        JLabel lbIntro2 = new JLabel("");
        lbIntro2.setIcon(new ImageIcon("./img/banner2.jpeg"));
        lbIntro2.setBounds(5, 185, 680, 350);
        lbIntro2.setVisible(true);
        panelIntro.add(lbIntro2);

        setVisible(true);
    }

    public String outThongTin() {
        String s = "\t\tCỬA HÀNG TIVI SÀI GÒN\n";
        s += "\tĐịa chỉ : An Dương Vương, Quận 5, Thành phố Hồ Chí Minh\n";
        s += "\tĐiện thoại liện hệ : 0123456789\n";
        s += "\tEmail : managertvsgu@gmail.com\n";
        s += "---------------------------------------------------------------------------------------------------------------------------------------\n";

        NhaCungCapDTO kh = new NhaCungCapDTO();
        NhanVienDTO nv = new NhanVienDTO();

        kh = nccBUS.get(txtIDProvider.getText());
        nv = nvBUS.get(txtIDStaff.getText());

        s += "               Nhà cung cấp : " + kh.getTenNCC();
        s += "\t\t\tHọ và tên nhân viên : " + nv.getHoNV().concat(" " + nv.getTenNV()) + "\n";
        s += "               Điện thoại : " + kh.getDienThoai();
        s += "\t\tTuổi : " + (Calendar.getInstance().get(Calendar.YEAR) - nv.getNamSinh()) + "\n";
        s += "               Địa chỉ : " + kh.getDiaChi();
        s += "\t\tGiới tính : " + nv.getGioiTinh() + "\n";

        return s;
    }

    public void outModel(DefaultTableModel model, ArrayList<ChiTietNHDTO> ds) {   // Xuất ra Table từ ArrayList
        Vector data;
        model.setRowCount(0);
        for (ChiTietNHDTO sp : ds) {
            data = new Vector();
            data.add(sp.getMaSP());
            data.add(sp.getTenSP());
            data.add(sp.getSoLuong());
            data.add(sp.getDonGia());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public int sumHD() {
        int total = 0;
        for (ChiTietNHDTO sp : dsct) {
            int amount = sp.getSoLuong();
            double price = sp.getDonGia();
            total += amount * price;
        }
        return total;
    }

    public void clear(boolean flag) {
        if (flag) {
            // PHẦN HÓA ĐƠN
            txtIDBill.setText(nhBUS.remindMaNH());
            txtIDProvider.setText("");
            txtIDStaff.setText("");
            if (userID != null) {
                txtIDStaff.setText(userID);
            }
            txtDateBill.setText("Automatic");
            txtTotal.setText("0");

            //PHẦN CHITIET
            dsct.removeAll(dsct);
            txtIDProduct.setText("");
            txtNameProduct.setText("");
            txtAmount.setText("");
            txtPrice.setText("0");
            imageProduct.setIcon(null);
            model.getDataVector().removeAllElements(); //Xóa trằng table

            txtInfo.setText("\t\tCỬA HÀNG TIVI SÀI GÒN\n" +
                    "\tĐịa chỉ : An Dương Vương, Quận 5, Thành phố Hồ Chí Minh\n" +
                    "\tĐiện thoại liện hệ : 0123456789\n" +
                    "\tEmail : managertvsgu@gmail.com\n" +
                    "---------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }

    public void blockHD(boolean flag) {
        txtIDBill.setEditable(flag);
        txtIDProvider.setEditable(!flag);
        if (userID != null) {
            txtIDStaff.setEditable(flag);
            lbIconStaff.setEnabled(flag);
        }
        txtDateBill.setEditable(flag);
        txtTotal.setEditable(flag);

        txtIDProduct.setEditable(!flag);
        lbIconProduct.setEnabled(!flag);
        txtAmount.setEditable(!flag);

        btnAdd.setEnabled(!flag);
        btnDeleteProduct.setEnabled(!flag);
        btnFix.setEnabled(!flag);

        if (!flag) {
            txtIDBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtIDProvider.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtIDStaff.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtDateBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtTotal.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));

            txtIDProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtNameProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtAmount.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtPrice.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
        } else {
            txtIDBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtIDProvider.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtIDStaff.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtDateBill.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));
            txtTotal.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 222, 255)));

            txtIDProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtNameProduct.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtAmount.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
            txtPrice.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(154, 154, 154)));
        }
    }

    public void reload(boolean flag) {
        btnSubmit.setVisible(flag);
        btnConfirm.setVisible(!flag);
        btnDelete.setVisible(!flag);
        clear(flag);
        blockHD(flag);
        panelLeft.add(panelTableSP, BorderLayout.CENTER);
        panelTableSP.setVisible(!flag);
        panelIntro.setVisible(flag);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSubmit)) {
            if (txtIDBill.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hóa đơn");
                txtIDBill.requestFocus();
                return;
            } else if (nhBUS.check(txtIDBill.getText())) {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn đă tồn tại");
                txtIDBill.requestFocus();
                txtIDBill.setText(nhBUS.remindMaNH());
                return;
            }

            if (txtIDProvider.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập hoặc chọn mã nhà cung cấp");
                txtIDProvider.requestFocus();
                return;
            } else if (!nccBUS.check(txtIDProvider.getText())) {
                JOptionPane.showMessageDialog(null, "Mã nhà cung cấp không tồn tại");
                txtIDProvider.requestFocus();
                return;
            }

            if (txtIDStaff.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập hoặc chọn mã nhân viên");
                txtIDStaff.requestFocus();
                return;
            } else if (!nvBUS.check(txtIDStaff.getText())) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại");
                txtIDStaff.requestFocus();
                return;
            }

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            txtDateBill.setText(myDateObj.format(myFormatObj));

            reload(false);

            txtIDProduct.requestFocus();
            txtInfo.setText(outThongTin());
        }

        if (e.getSource().equals(btnAdd)) {
            if (txtIDProduct.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập sản phẩm");
                txtIDProduct.requestFocus();
                return;
            }
            int sl = 0;
            try {
                sl = Integer.parseInt(txtAmount.getText());
            } catch (NumberFormatException E) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng");
                txtAmount.requestFocus();
                return;
            }

            // Kiểm tra số lượng
            double gia = Double.parseDouble(txtPrice.getText().replace(",", ""));

            //Kiểm tra đã có trong giỏ chưa
            boolean flag = true;
            for (ChiTietNHDTO sp : dsct) {
                if (sp.getMaSP().equals(txtIDProduct.getText())) {
                    int old = sp.getSoLuong();
                    if (!spBUS.checkSL(txtIDProduct.getText(), sl + old)) {
                        return;
                    }
                    sp.setSoLuong(sl + old);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (!spBUS.checkSL(txtIDProduct.getText(), sl)) {
                    return;
                }
                dsct.add(new ChiTietNHDTO(txtIDBill.getText(), txtIDProduct.getText(), txtNameProduct.getText(), sl, gia));
            }
            outModel(model, dsct);
            NumberFormat num = NumberFormat.getInstance();
            txtTotal.setText(num.format(sumHD()));
            txtIDProduct.setText("");
            txtNameProduct.setText("");
            txtAmount.setText("");
            txtPrice.setText("0");
            imageProduct.setIcon(null);
        }

        if (e.getSource().equals(btnDelete)) {
            int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messEdit == 0) {
                reload(true);
                blockHD(false);
            }
        }

        if (e.getSource().equals(btnConfirm)) //Xác nhận
        {
            if (dsct.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập sản phẩm");
                return;
            }
            String maHD = txtIDBill.getText();
            String maNCC = txtIDProvider.getText();
            String maNV = txtIDStaff.getText();
            String ngayHD = txtDateBill.getText();
            double tongTien = Double.parseDouble(txtTotal.getText().replace(",", ""));
            NhapHangDTO nh = new NhapHangDTO(maHD, maNCC, maNV, ngayHD, tongTien);
            nhBUS.add(nh);
            for (ChiTietNHDTO ct : dsct) {
                ctBUS.add(ct);
            }
            OutBill bill = new OutBill(nh, dsct);
            bill.printNH();
            reload(true);
        }
        if (e.getSource().equals(btnFix)) { //Sửa sl trong Chitiet sp
            try {
                int i = table.getSelectedRow();
                if (table.getRowSorter() != null) {
                    i = table.getRowSorter().convertRowIndexToModel(i);
                }
                String masp = table.getModel().getValueAt(i, 0).toString();
                int sl = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng sản phẩm :"));
                while (!spBUS.checkSL(masp, sl)) {
                    sl = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng sản phẩm :"));
                }
                for (ChiTietNHDTO ct : dsct) {
                    if (ct.getMaSP().equals(masp)) {
                        ct.setSoLuong(sl);
                    }
                }
                outModel(model, dsct);
                NumberFormat num = NumberFormat.getInstance();
                txtTotal.setText(num.format(sumHD()));
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Chưa chọn sản phầm cần sửa");
            }
        }

        if (e.getSource().equals(btnDeleteProduct)) { // Xóa SP trong CT SP
            try {
                int i = table.getSelectedRow();
                if (table.getRowSorter() != null) {
                    i = table.getRowSorter().convertRowIndexToModel(i);
                }
                dsct.remove(i);
                model.removeRow(i);
                NumberFormat num = NumberFormat.getInstance();
                txtTotal.setText(num.format(sumHD()));
                txtIDProduct.setText("");
                txtNameProduct.setText("");
                txtAmount.setText("");
                txtPrice.setText("0");
                imageProduct.setIcon(null);
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Chưa chọn sản phầm cần xóa");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lbIconCustomer) {
            if (lbIconCustomer.isEnabled()) {
                ListNhaCungCap nv = new ListNhaCungCap(1150, 750);
                String s = nv.getInfoNCC();
                txtIDProvider.setText(s);
            }

        }
        if (e.getSource() == lbIconStaff) {
            if (lbIconStaff.isEnabled()) {
                ListNhanVien kh = new ListNhanVien(1150, 750);
                String s = kh.getInfoNV();
                txtIDStaff.setText(s);
            }
        }
        if (e.getSource().equals(lbIconProduct)) {
            if (lbIconProduct.isEnabled()) {
                ListSanPham sp = new ListSanPham(1150, 750, txtIDProduct.getText());
                String[] s = sp.getInfoSP().split("%");
                txtIDProduct.setText(s[0]);
                txtNameProduct.setText(s[1]);
                txtPrice.setText(s[2]);
                Image newImage;
                try {
                    newImage = new ImageIcon("./img/SanPham/" + s[3]).getImage().getScaledInstance(180, 210, Image.SCALE_DEFAULT);
                } catch (NullPointerException E) {
                    newImage = new ImageIcon("./img/SanPham/NoImage.jpg").getImage().getScaledInstance(180, 210, Image.SCALE_DEFAULT);
                }
                imageProduct.setIcon(new ImageIcon(newImage));
                txtAmount.requestFocus();
            }
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}