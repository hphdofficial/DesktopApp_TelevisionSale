package GUI;

import BUS.ChiTietHDBUS;
import DTO.ChiTietHDDTO;

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
import java.util.ArrayList;
import java.util.Vector;

import static javax.swing.BorderFactory.createLineBorder;

public class ChiTietHDGUI extends JDialog implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final ChiTietHDBUS cthdBUS = new ChiTietHDBUS();

    private final int Default_Width;
    private final int Default_Height;
    private final String maHD;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnConfirm, btnBack, btnIMG;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JTable table;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model;
    private String imgName = "Null";
    private JLabel img;
    private BufferedImage image = null;
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
    private JTextField sortHoNV;
    private JTextField sortTenNV;
    private Choice sortGT;
    private JTextField sortMaKH;
    private JTextField sortHoKH;
    private JTextField sortTenKH;
    private Choice sortEnable;
    private Choice sortRole;
    private JTextField sortSoLuong;
    private JTextField sortMaSP;
    private JTextField txtMinPrice;
    private JTextField txtMaxPrice;
    private int lenArr;

    public ChiTietHDGUI(int width, int height, String maHD) {
        Default_Width = width;
        Default_Height = height;
        this.maHD = maHD;
        setModal(true);
        init();
    }

    public void init() {
        setTitle("Chi tiết hóa đơn");
        setLayout(null);
        setSize(Default_Width, Default_Height);
        setBackground(new Color(205, 205, 255));

        font0 = new Font("Segoe UI", Font.PLAIN, 13);
        font1 = new Font("Segoe UI", Font.BOLD, 13);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(null);
        p1.setBounds(0, 0, 450, 750);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(null);
        p2.setBounds(450, 0, 700, 750);
        add(p2);

        JPanel pInfo = panelInfo();
        pInfo.setLayout(null);
        pInfo.setBackground(null);
        pInfo.setBounds(0, 0, 450, 300);
        p1.add(pInfo);

        pButton = panelButton();
//        pButton.setLayout(null);
        pButton.setBackground(null);
        pButton.setBounds(30, 300, 400, Default_Height / 2);
        p1.add(pButton);

        pSearch = panelSearch();
        pSearch.setLayout(null);
        pSearch.setBackground(null);
        pSearch.setBounds(80, 300, 400, Default_Height / 2);
        p1.add(pSearch);
        pSearch.setVisible(false);

        /******************** Tạo table ********************/

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
        rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        listCTHD(); // Đọc từ database lên table

        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        centerAlign.setHorizontalAlignment(JLabel.CENTER);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(1).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
//        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);

        // Custom table
        table.setFocusable(false);
        table.setRowHeight(30);
        table.getTableHeader().setFont(font0);
        table.getTableHeader().setBackground(new Color(18, 88, 210));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(151, 250, 59));
        table.addMouseListener(this);

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(new Rectangle(0, 30, 650, Default_Height / 2));
        scroll.setBackground(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        p2.add(scroll);

        cleanView();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JPanel panelInfo() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(null);

        String[] arrInfo = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 30, toadoyLabel = 30;
        int toadoxTextField = 150, toadoyTextField = 30;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(font1);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 120, 30);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 40;

            textfields[i] = new JTextField();
            textfields[i].setBounds(toadoxTextField, toadoyTextField, 250, 30);
            p.add(textfields[i]);
            toadoyTextField = toadoyTextField + 40;
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(120, 40));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa");
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(120, 40));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(Color.yellow);
        btnDelete.setFont(font1);
        btnDelete.setPreferredSize(new Dimension(120, 40));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(this);
        p.add(btnDelete);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(Color.yellow);
        btnSearch.setFont(font1);
        btnSearch.setPreferredSize(new Dimension(120, 40));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(this);
        p.add(btnSearch);

//        btnIMG = new JButton("Chọn ảnh");
//        btnIMG.setBackground(Color.yellow);
//        btnIMG.setFont(font1);
//        btnIMG.setPreferredSize(new Dimension(120, 40));
//        btnIMG.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnIMG.addMouseListener(this);
//        p.add(btnIMG);
//        btnIMG.setVisible(false);

        btnBack = new JButton("Quay lại");
        btnBack.setBackground(Color.yellow);
        btnBack.setFont(font1);
        btnBack.setPreferredSize(new Dimension(120, 40));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(this);
        p.add(btnBack);
        btnBack.setVisible(false);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(Color.yellow);
        btnConfirm.setFont(font1);
        btnConfirm.setPreferredSize(new Dimension(120, 40));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addMouseListener(this);
        p.add(btnConfirm);
        btnConfirm.setVisible(false);

        return p;
    }

    public JPanel panelSearch() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(null);

        JLabel searchTitle = new JLabel("Tìm kiếm");
        searchTitle.setFont(font1);
        searchTitle.setHorizontalAlignment(JLabel.CENTER);
        searchTitle.setBounds(30, 0, 250, 30);
        p.add(searchTitle);

        searchBox = new JPanel(null);
        searchBox.setBackground(null);
        searchBox.setBounds(new Rectangle(30, 30, 250, 30));
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
        boloc.setBounds(30, 60, 250, 30);
        p.add(boloc);

        /********** SORT MASP **********/

        JLabel lblMaSP = new JLabel("Mã sản phẩm  :");
        lblMaSP.setFont(font0);
        lblMaSP.setBounds(30, 100, 90, 30);
        p.add(lblMaSP);

        sortMaSP = new JTextField();
        sortMaSP.setFont(font0);
        sortMaSP.setBounds(new Rectangle(130, 100, 150, 30));
        sortMaSP.addKeyListener(this);
        p.add(sortMaSP);

        /********** SORT DONGIA  **********/

        JLabel lblGia = new JLabel("Giá :");
        lblGia.setFont(font0);
        lblGia.setBounds(30, 180, 90, 30);
        p.add(lblGia);

        txtMinPrice = new JTextField();
        txtMinPrice.setFont(font0);
        txtMinPrice.setBounds(new Rectangle(130, 180, 150, 30));
        txtMinPrice.addKeyListener(this);
        p.add(txtMinPrice);

//        JSeparator sepPrice = new JSeparator(JSeparator.HORIZONTAL);
//        sepPrice.setBounds(120, 260, 100, 100);
//        p.add(sepPrice);

        txtMaxPrice = new JTextField();
        txtMaxPrice.setFont(font0);
        txtMaxPrice.setBounds(new Rectangle(130, 220, 150, 30));
        txtMaxPrice.addKeyListener(this);
        p.add(txtMaxPrice);

        btnBackSearch = new JButton("Quay lại");
        btnBackSearch.setBackground(Color.yellow);
        btnBackSearch.setFont(font1);
        btnBackSearch.setBounds(30, 310, 110, 30);
        btnBackSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBackSearch.addMouseListener(this);
        p.add(btnBackSearch);

        btnSearchTable = new JButton("Tìm kiếm");
        btnSearchTable.setBackground(Color.yellow);
        btnSearchTable.setFont(font1);
        btnSearchTable.setBounds(170, 310, 110, 30);
        btnSearchTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearchTable.addMouseListener(this);
        p.add(btnSearchTable);

        return p;
    }

    public void outModel(DefaultTableModel model, ArrayList<ChiTietHDDTO> cthd) {   // Xuất ra Table từ ArrayList
        Vector data;
        model.setRowCount(0);
        for (ChiTietHDDTO ct : cthd) {
            data = new Vector();
            data.add(ct.getMaSP());
            data.add(ct.getTenSP());
            data.add(ct.getSoLuong());
            data.add(ct.getDonGia());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listCTHD() {    // Chép ArrayList lên table
        if (cthdBUS.getList() == null)
            cthdBUS.list();
        ArrayList<ChiTietHDDTO> cthd = cthdBUS.getListHD(maHD);
        model.setRowCount(0);
        outModel(model, cthd);
    }

    public void search() {
        String id = sortMaSP.getText();
        double max = txtMaxPrice.getText().equals("") ? 999999999 : Double.parseDouble(txtMaxPrice.getText().replace(",", ""));
        double min = txtMinPrice.getText().equals("") ? 0 : Double.parseDouble(txtMinPrice.getText().replace(",", ""));

        outModel(model, cthdBUS.search(id, min, max));
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
            textfields[i].setEditable(true);
            textfields[i].setText("");
        }
    }

    public void editView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            textfields[i].setBackground(null);
            if (i == 2) {
                textfields[i].setEditable(true);
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
//                newImage = new ImageIcon("./img/ChiTietHD/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            } catch (NullPointerException E) {
//                newImage = new ImageIcon("./img/ChiTietHD/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            }

            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            NumberFormat num = NumberFormat.getInstance();
            textfields[3].setText(num.format(table.getModel().getValueAt(row, 3)));
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
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa !!!");
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
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                cthdBUS.deleteSP(maHD, textfields[0].getText());
                table.clearSelection();
                cleanView();
                listCTHD();   //  outModel(model, cthdBUS.getList());
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
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
//                    Logger.getLogger(ChiTietHDGUI.class.getName()).log(Level.SEVERE, null, ex);
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
//                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
//                if (messEdit == 0) {
//                    String maSP = textfields[0].getText();
//                    String tenSP = textfields[1].getText();
//                    int soLuong = Integer.parseInt(textfields[2].getText());
//                    double donGia = Double.parseDouble(textfields[3].getText().replace(",",""));
//
//                    ChiTietHDDTO cthd = new ChiTietHDDTO(maSP,tenSP,soLuong,donGia);
//                    cthdBUS.add(cthd);
//                    outModel(model, cthdBUS.getList());   // Load lại table
//                    cleanView();
//                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maSP = textfields[0].getText();
                    String tenSP = textfields[1].getText();
                    int soLuong = Integer.parseInt(textfields[2].getText());
                    double donGia = Double.parseDouble(textfields[3].getText().replace(",", ""));

                    ChiTietHDDTO cthd = new ChiTietHDDTO(maHD, maSP, tenSP, soLuong, donGia);
                    cthdBUS.update(cthd);
                    listCTHD();   //  outModel(model, cthdBUS.getListHD(maHD));   // Load lại table
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
            cleanView();
            pButton.setVisible(false);
            pSearch.setVisible(true);
            table.clearSelection();
            table.setEnabled(true);
        }

        if (e.getSource() == btnBackSearch) {
            cleanView();
            pButton.setVisible(true);
            pSearch.setVisible(false);
            table.setEnabled(true);
            outModel(model, cthdBUS.getListHD(maHD));
        }

        if (e.getSource() == btnSearchTable) {
            search();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, cthdBUS.getListHD(maHD));
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
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 1));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 1));
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
        if (a.equals(sortMaSP) || a.equals(txtMinPrice) || a.equals(txtMaxPrice)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                search();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}