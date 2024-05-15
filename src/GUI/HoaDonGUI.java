package GUI;

import BUS.ChiTietHDBUS;
import BUS.HoaDonBUS;
import BUS.OutBill;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import static javax.swing.BorderFactory.createLineBorder;

public class HoaDonGUI extends JPanel implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final HoaDonBUS hdBUS = new HoaDonBUS();
    private final ChiTietHDBUS cthdBUS = new ChiTietHDBUS();

    private final int Default_Width;
    private final int Default_Height;
    private final String imgName = "Null";
    private final BufferedImage image = null;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnConfirm, btnBack, btnIMG;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JTable table;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model;
    private JLabel img;
    private boolean AddOrEdit = true;   // Cờ cho button Cofirm True:Add || False:Edit
    private Font font0, font1;
    private JPanel searchBox;
    private JTextField txtSearch;
    private JLabel searchIcon;
    private JTextField sortMaNV;
    private JPanel pButton;
    private JPanel pSearch;
    private JButton btnBackSearch;
    private JButton btnSearchTable;
    private JTextField sortMaSP;
    private JTextField txtMinTotal;
    private JTextField txtMaxTotal;
    private Choice monthChoice;
    private Choice yearChoice;
    private JButton btnBill;
    private JButton btnView;
    private int lenArr;

    public HoaDonGUI(int width, int height) {
        Default_Width = width;
        Default_Height = height;
        init();
    }

    public void init() {
        setLayout(new GridLayout(2, 1, 0, 0));
        setSize(Default_Width, Default_Height);
        setBackground(new Color(205, 205, 255));

        font0 = new Font("Segoe UI", Font.PLAIN, 13);
        font1 = new Font("Segoe UI", Font.BOLD, 13);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(null);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(null);
        add(p2);

        JPanel pInfo = panelInfo();
        pInfo.setLayout(null);
        pInfo.setBackground(Color.white);
        pInfo.setBounds(20, 20, 790, Default_Height / 2 - 20);
        pInfo.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pInfo);

        JLabel lbIMG = new JLabel();
        lbIMG.setIcon(new ImageIcon("./img/imgBill1.png"));
        lbIMG.setHorizontalAlignment(JLabel.CENTER);
//        lbIMG.setBackground(Color.red);
//        lbIMG.setOpaque(true);
        lbIMG.setBounds(30, 20, 200, Default_Height / 2 - 60);
        pInfo.add(lbIMG);

        pButton = panelButton();
        pButton.setBackground(Color.white);
        pButton.setBounds(830, 20, 300, Default_Height / 2 - 20);
        pButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pButton);

        pSearch = panelSearch();
        pSearch.setLayout(null);
        pSearch.setBackground(Color.white);
        pSearch.setBounds(830, 20, 300, Default_Height / 2 - 20);
        pSearch.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pSearch);
        pSearch.setVisible(false);

        /******************** Tạo table ********************/

        Vector header = new Vector();
        header.add("Mã hóa đơn");
        header.add("Mã khách hàng");
        header.add("Mã nhân viên");
        header.add("Ngày hóa đơn");
        header.add("Tổng tiền");

        model = new DefaultTableModel(header, 0) {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 4:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        table = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        listHD(); // Đọc từ database lên table

        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        centerAlign.setHorizontalAlignment(JLabel.CENTER);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(1).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(2).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
//        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);

        // Custom table
        table.setFocusable(false);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font0);
        table.getTableHeader().setBackground(Color.red);
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setOpaque(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(24, 85, 252));
        table.addMouseListener(this);

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(new Rectangle(20, 20, Default_Width - 50, Default_Height / 2 - 40));
        scroll.setBackground(null);
        scroll.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        p2.add(scroll);

        cleanView();
    }

    public JPanel panelInfo() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(null);

        String[] arrInfo = {"Mã hóa dơn", "Mã khách hàng", "Mã nhân viên", "Ngày hóa đơn", "Tổng tiền"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 270, toadoyLabel = 30;
        int toadoxTextField = 420, toadoyTextField = 30;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(font1);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 150, 40);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 60;

            textfields[i] = new JTextField();
            textfields[i].setBounds(toadoxTextField, toadoyTextField, 300, 40);
            p.add(textfields[i]);
            toadoyTextField = toadoyTextField + 60;
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        btnAdd = new JButton("Thêm hóa đơn");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setHorizontalAlignment(JButton.LEFT);
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(170, 45));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa hóa đơn");
        btnEdit.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit.setHorizontalAlignment(JButton.LEFT);
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(170, 45));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa hóa đơn");
        btnDelete.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete.setHorizontalAlignment(JButton.LEFT);
        btnDelete.setBackground(Color.yellow);
        btnDelete.setFont(font1);
        btnDelete.setPreferredSize(new Dimension(170, 45));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(this);
        p.add(btnDelete);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setIcon(new ImageIcon("./img/search-icon.png"));
        btnSearch.setHorizontalAlignment(JButton.LEFT);
        btnSearch.setBackground(Color.yellow);
        btnSearch.setFont(font1);
        btnSearch.setPreferredSize(new Dimension(170, 45));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(this);
        p.add(btnSearch);

//        btnIMG = new JButton("Chọn ảnh");
//        btnIMG.setIcon(new ImageIcon("./img/img-icon.png"));
//        btnIMG.setBackground(Color.yellow);
//        btnIMG.setFont(font1);
//        btnIMG.setPreferredSize(new Dimension(170, 45));
//        btnIMG.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnIMG.addMouseListener(this);
//        btnIMG.setVisible(false);
//        p.add(btnIMG);

        btnBill = new JButton("In hóa đơn");
        btnBill.setIcon(new ImageIcon("./img/print-icon.png"));
        btnBill.setHorizontalAlignment(JButton.LEFT);
        btnBill.setBackground(Color.yellow);
        btnBill.setFont(font1);
        btnBill.setPreferredSize(new Dimension(170, 45));
        btnBill.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBill.addMouseListener(this);
        p.add(btnBill);

        btnView = new JButton("Chi tiết hóa đơn");
        btnView.setIcon(new ImageIcon("./img/detail-icon.png"));
        btnView.setHorizontalAlignment(JButton.LEFT);
        btnView.setBackground(Color.yellow);
        btnView.setFont(font1);
        btnView.setPreferredSize(new Dimension(170, 45));
        btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnView.addMouseListener(this);
        p.add(btnView);

        btnBack = new JButton("Quay lại");
        btnBack.setIcon(new ImageIcon("./img/back-icon.png"));
        btnBack.setHorizontalAlignment(JButton.LEFT);
        btnBack.setBackground(Color.yellow);
        btnBack.setFont(font1);
        btnBack.setPreferredSize(new Dimension(170, 45));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(this);
        btnBack.setVisible(false);
        p.add(btnBack);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm.setHorizontalAlignment(JButton.LEFT);
        btnConfirm.setBackground(Color.yellow);
        btnConfirm.setFont(font1);
        btnConfirm.setPreferredSize(new Dimension(170, 45));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addMouseListener(this);
        btnConfirm.setVisible(false);
        p.add(btnConfirm);

        return p;
    }

    public JPanel panelSearch() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(null);

        JLabel searchTitle = new JLabel("Tìm kiếm");
        searchTitle.setFont(font1);
        searchTitle.setHorizontalAlignment(JLabel.CENTER);
        searchTitle.setBounds(25, 0, 250, 30);
        p.add(searchTitle);

        searchBox = new JPanel(null);
        searchBox.setBackground(null);
        searchBox.setBounds(new Rectangle(25, 30, 250, 30));
        searchBox.setBorder(createLineBorder(Color.BLACK));
        p.add(searchBox);

        txtSearch = new JTextField();
        txtSearch.setBounds(new Rectangle(10, 0, 200, 30));
        txtSearch.setBorder(null);
        txtSearch.setOpaque(false);
        txtSearch.setFont(font0);
        searchBox.add(txtSearch);

        txtSearch.addMouseListener(this);
        txtSearch.addFocusListener(this);
        txtSearch.getDocument().addDocumentListener(this);

        searchIcon = new JLabel(new ImageIcon("./img/search_icon_25px.png"));
        searchIcon.setBounds(new Rectangle(200, 0, 50, 30));
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBox.add(searchIcon);

        JLabel boloc = new JLabel("-------------------- Bộ lọc --------------------");
        boloc.setFont(font1);
        boloc.setHorizontalAlignment(JLabel.CENTER);
        boloc.setBounds(25, 60, 250, 30);
        p.add(boloc);

        /********** SORT MASP **********/

        JLabel lblMaSP = new JLabel("Mã sản phẩm :");
        lblMaSP.setFont(font0);
        lblMaSP.setBounds(25, 100, 90, 30);
        p.add(lblMaSP);

        sortMaSP = new JTextField();
        sortMaSP.setFont(font0);
        sortMaSP.setBounds(new Rectangle(125, 100, 150, 30));
        sortMaSP.addKeyListener(this);
        p.add(sortMaSP);

        /********** SORT MANV **********/

        JLabel lblMaNV = new JLabel("Mã nhân viên :");
        lblMaNV.setFont(font0);
        lblMaNV.setBounds(25, 140, 90, 30);
        p.add(lblMaNV);

        sortMaNV = new JTextField();
        sortMaNV.setFont(font0);
        sortMaNV.setBounds(new Rectangle(125, 140, 150, 30));
        sortMaNV.addKeyListener(this);
        p.add(sortMaNV);

        /********** SORT NGAYHD **********/

        JLabel lblNgayHD = new JLabel("Thời gian :");
        lblNgayHD.setFont(font0);
        lblNgayHD.setBounds(25, 180, 90, 30);
        p.add(lblNgayHD);

        monthChoice = new Choice();
        monthChoice.setFont(font0);
        monthChoice.add("Tháng");
        for (int i = 1; i <= 12; i++) {
            monthChoice.add("Tháng " + i);
        }
        monthChoice.select(0);
        monthChoice.setBounds(new Rectangle(125, 180, 70, 30));
        monthChoice.addKeyListener(this);
        p.add(monthChoice);

        yearChoice = new Choice();
        yearChoice.setFont(font0);
        yearChoice.add("Năm");
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 2000; i--) {
            yearChoice.add(String.valueOf(i));
        }
        yearChoice.select(0);
        yearChoice.setBounds(new Rectangle(205, 180, 70, 30));
        yearChoice.addKeyListener(this);
        p.add(yearChoice);

        /********** SORT TONGTIEN  **********/

        JLabel lblTongTien = new JLabel("Tổng tiền :");
        lblTongTien.setFont(font0);
        lblTongTien.setBounds(25, 220, 90, 30);
        p.add(lblTongTien);

        txtMinTotal = new JTextField();
        txtMinTotal.setFont(font0);
        txtMinTotal.setBounds(new Rectangle(125, 220, 150, 30));
        txtMinTotal.addKeyListener(this);
        p.add(txtMinTotal);

//        JSeparator sepPrice = new JSeparator(JSeparator.HORIZONTAL);
//        sepPrice.setBounds(120, 260, 100, 100);
//        p.add(sepPrice);

        txtMaxTotal = new JTextField();
        txtMaxTotal.setFont(font0);
        txtMaxTotal.setBounds(new Rectangle(125, 260, 150, 30));
        txtMaxTotal.addKeyListener(this);
        p.add(txtMaxTotal);

        btnBackSearch = new JButton("Quay lại");
        btnBackSearch.setBackground(Color.yellow);
        btnBackSearch.setFont(font1);
        btnBackSearch.setBounds(25, 310, 110, 30);
        btnBackSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBackSearch.addMouseListener(this);
        p.add(btnBackSearch);

        btnSearchTable = new JButton("Tìm kiếm");
        btnSearchTable.setBackground(Color.yellow);
        btnSearchTable.setFont(font1);
        btnSearchTable.setBounds(165, 310, 110, 30);
        btnSearchTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearchTable.addMouseListener(this);
        p.add(btnSearchTable);

        return p;
    }

    public void outModel(DefaultTableModel model, ArrayList<HoaDonDTO> hd) {    // Xuất ra Table từ ArrayList
        Vector data;
        model.setRowCount(0);
        for (HoaDonDTO k : hd) {
            data = new Vector();
            data.add(k.getMaHD());
            data.add(k.getMaKH());
            data.add(k.getMaNV());
            data.add(k.getNgayHD());
            data.add(k.getTongTien());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listHD() {  // Chép ArrayList lên table
        if (hdBUS.getList() == null)
            hdBUS.list();
        ArrayList<HoaDonDTO> hd = hdBUS.getList();
        model.setRowCount(0);
        outModel(model, hd);
    }

//    public void addCombobox(JComboBox cmb, ArrayList list) {
//        for (Object a : list) {
//            cmb.addItem(a);
//        }

//    }
//    public void saveIMG() {
//        try {
//            if (image != null) {
//                File save = new File("./img/HoaDon/" + imgName);    //  Tạo file
//                ImageIO.write(image,"png", save);    // Lưu hình "image" vào đường dẫn file save
//                image = null;   //  Xóa hình trong bộ nhớ
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(HoaDonGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void search() {
        String manv = sortMaNV.getText();
        int month = monthChoice.getSelectedIndex();
        int year;
        try {
            year = Integer.parseInt(yearChoice.getSelectedItem());
        } catch (NumberFormatException ex) {
            year = 0;
        }
        double max = txtMaxTotal.getText().equals("") ? Double.MAX_VALUE : Double.parseDouble(txtMaxTotal.getText().replace(",", ""));
        double min = txtMinTotal.getText().equals("") ? 0 : Double.parseDouble(txtMinTotal.getText().replace(",", ""));

        outModel(model, hdBUS.search(cthdBUS.getHD(sortMaSP.getText()), manv, month, year, min, max));
    }

    public void cleanView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            textfields[i].setEditable(false);
            textfields[i].setText("");
            textfields[i].setBackground(Color.white);
        }
    }

    public void addView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 3 && i != 4) {
                textfields[i].setEditable(true);
                textfields[i].setText("");
            } else {    //  TextField NgayHD & TextField TongTien
                textfields[i].setEditable(false);
                textfields[i].setText("Automatic");
                textfields[i].setBackground(null);
            }
        }
    }

    public void editView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i == 1 || i == 2 || i == 3) {
                textfields[i].setEditable(true);
            } else {
                textfields[i].setBackground(null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        /****************************** XỬ LÝ SỰ KIỆN DỮ LIỆU TABLE --> FORM ******************************/

        if (e.getSource() == table) {
            int row = table.getSelectedRow();
            if (table.getRowSorter() != null) {
                row = table.getRowSorter().convertRowIndexToModel(row);
            }
//            imgName = table.getModel().getValueAt(row, 8).toString();
//            Image newImage;
//            try {
//                newImage = new ImageIcon("./img/HoaDon/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            } catch (NullPointerException E) {
//                newImage = new ImageIcon("./img/HoaDon/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            }

            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            textfields[3].setText(table.getModel().getValueAt(row, 3).toString());
            NumberFormat num = NumberFormat.getInstance();
            textfields[4].setText(num.format(table.getModel().getValueAt(row, 4)));
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON ADD ******************************/

        if (e.getSource() == btnAdd) {
            AddOrEdit = true;
            addView();

            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);

            btnBack.setVisible(true);
            btnConfirm.setVisible(true);

            table.clearSelection();
            table.setEnabled(false);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON EDIT ******************************/

        if (e.getSource() == btnEdit) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần sửa !!!");
                return;
            }
            AddOrEdit = false;
            editView();

            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);

            btnBack.setVisible(true);
            btnConfirm.setVisible(true);

            table.setEnabled(false);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON DELETE ******************************/

        if (e.getSource() == btnDelete) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                hdBUS.delete(textfields[0].getText());
                cthdBUS.delete(textfields[0].getText());
                table.clearSelection();
                cleanView();
                listHD();   //  outModel(model, hdBUS.getList());
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON BILL ******************************/

        if (e.getSource() == btnBill) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần in !!!");
                return;
            }
            String maHD = textfields[0].getText();
            String maKH = textfields[1].getText();
            String maNV = textfields[2].getText();
            String ngayHD = textfields[3].getText();
            double tongTien = Double.parseDouble(textfields[4].getText().replace(",", ""));
            HoaDonDTO hd = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
            ChiTietHDBUS cthdBUS = new ChiTietHDBUS();
            ArrayList<ChiTietHDDTO> cthd = cthdBUS.getListHD(maHD);
            OutBill bill = new OutBill(hd, cthd);
            bill.printHD();
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON VIEW ******************************/

        if (e.getSource() == btnView) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần xem chi tiết !!!");
                return;
            }
            ChiTietHDGUI chitiet = new ChiTietHDGUI(Default_Width, Default_Height, textfields[0].getText());
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON IMG ******************************/

//        if (e.getSource() == btnIMG) {
//            JFileChooser fc = new JFileChooser();
//            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images", "jpg", "png");
//            fc.setFileFilter(filter);
//            int result = fc.showOpenDialog(null);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                try {
//                    File file = fc.getSelectedFile();   //  Lấy URL hình
//                    image = ImageIO.read(file); //  Lấy hình
//                    imgName = textfields[0].getText().concat(".png");   //  Tên hình
//
//                    // Thay đổi hình hiển thị
//                    img.setText("");
//                    img.setIcon(new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
//
//                    revalidate();
//                    repaint();
//                } catch (IOException ex) {
//                    Logger.getLogger(HoaDonGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON BACK ******************************/

        if (e.getSource() == btnBack) {
            int messBack = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy bỏ ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messBack == 0) {
                cleanView();

                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                btnSearch.setVisible(true);

                btnConfirm.setVisible(false);
                btnBack.setVisible(false);

                table.setEnabled(true);
            }
        }
        /****************************** XỬ LÝ SỰ KIỆN BUTTON CONFIRM ******************************/

        if (e.getSource() == btnConfirm) {
            if (AddOrEdit) {    // Add
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maHD = textfields[0].getText();
                    String maKH = textfields[1].getText();
                    String maNV = textfields[2].getText();

                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String ngayHD = myDateObj.format(myFormatObj);

                    double tongTien = 0;

                    HoaDonDTO hd = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
                    hdBUS.add(hd);
                    outModel(model, hdBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maHD = textfields[0].getText();
                    String maKH = textfields[1].getText();
                    String maNV = textfields[2].getText();
                    String ngayHD = textfields[3].getText();
                    double tongTien = Double.parseDouble(textfields[4].getText().replace(",", ""));

                    HoaDonDTO hd = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
                    hdBUS.update(hd);
                    listHD();   //  outModel(model, hdBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            cleanView();

            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
            btnSearch.setVisible(true);

            btnConfirm.setVisible(false);
            btnBack.setVisible(false);

            table.setEnabled(true);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON SEARCH ******************************/

        if (e.getSource() == btnSearch) {
            pButton.setVisible(false);
            pSearch.setVisible(true);
            table.clearSelection();
            table.setEnabled(true);
            cleanView();
        }

        if (e.getSource() == btnBackSearch) {
            pButton.setVisible(true);
            pSearch.setVisible(false);
            table.setEnabled(true);
            outModel(model, hdBUS.getList());
            cleanView();
        }

        if (e.getSource() == btnSearchTable) {
            search();
            cleanView();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, hdBUS.getList());
            cleanView();
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
//        if (e.getSource() == btnView) {
//            String maHD = textfields[0].getText();
//            String maKH = textfields[1].getText();
//            String maNV = textfields[2].getText();
//            String ngayHD = textfields[3].getText();
//            cthdBUS.list();
//            double tongTien = hdBUS.updateTotalPrice(maHD, cthdBUS.getList());
//
//            HoaDonDTO hd = new HoaDonDTO(maHD, maKH, maNV, ngayHD, tongTien);
//            hdBUS.update(hd);
//            listHD();
//        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == txtSearch) {
            searchIcon.setIcon(new ImageIcon("./img/search_icon_focus_25px.png"));
            searchBox.setBorder(createLineBorder(new Color(52, 152, 219)));
        }

//        for (int i=0; i<5; i++) {
//            if (e.getSource() == textfields[i]) {
//                textfields[i].setBorder(createLineBorder(Color.red));
//            }
//        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtSearch) {
            searchIcon.setIcon(new ImageIcon("./img/search_icon_25px.png"));
            searchBox.setBorder(createLineBorder(Color.BLACK));
        }

//        for (int i=0; i<5; i++) {
//            if (e.getSource() == textfields[i]) {
//                textfields[i].setBorder(createLineBorder(Color.black));
//            }
//        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object a = e.getSource();
        if (a.equals(sortMaSP) || a.equals(sortMaNV) || a.equals(txtMinTotal) || a.equals(txtMaxTotal)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                search();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}