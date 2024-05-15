package GUI;

import BUS.NhaSanXuatBUS;
import BUS.PhanLoaiBUS;
import BUS.SanPhamBUS;
import DTO.NhaSanXuatDTO;
import DTO.PhanLoaiDTO;
import DTO.SanPhamDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.BorderFactory.createLineBorder;

public class SanPhamGUI extends JPanel implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private final PhanLoaiBUS loaiBUS = new PhanLoaiBUS();
    private final NhaSanXuatBUS nsxBUS = new NhaSanXuatBUS();

    private final int Default_Width;
    private final int Default_Height;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnConfirm, btnBack, btnIMG, btnImport, btnExport;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JTable table;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model;
    private JComboBox cmbNSX, cmbLoai;
    private String imgName = "Null";
    private JLabel img;
    private BufferedImage image = null;
    private boolean AddOrEdit = true;   // Cờ cho button Cofirm True:Add || False:Edit
    private Font font0, font1;
    private JPanel searchBox;
    private JTextField txtSearch;
    private JLabel searchIcon;
    private JTextField sortMaSP;
    private JComboBox cmbSortLoai;
    private JComboBox cmbSortNSX;
    private JTextField txtMinPrice;
    private JTextField txtMaxPrice;
    private JPanel pButton;
    private JPanel pSearch;
    private JButton btnBackSearch;
    private JButton btnSearchTable;
    private int lenArr;

    public SanPhamGUI(int width, int height) {
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
        pInfo.setBounds(20, 20, 780, Default_Height / 2 - 20);
        pInfo.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pInfo);

        pButton = panelButton();
        pButton.setBackground(Color.white);
        pButton.setBounds(820, 20, 310, Default_Height / 2 - 20);
        pButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pButton);

        pSearch = panelSearch();
        pSearch.setLayout(null);
        pSearch.setBackground(Color.white);
        pSearch.setBounds(820, 20, 310, Default_Height / 2 - 20);
        pSearch.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pSearch);
        pSearch.setVisible(false);

        /******************** Tạo table ********************/

        Vector header = new Vector();
        header.add("Mã sản phẩm");
        header.add("Tên sản phẩm");
        header.add("Kích thước");
        header.add("Số lượng");
        header.add("Đơn giá");
        header.add("Mã loại");
        header.add("Mã nhà sản xuất");
        header.add("Hình ảnh");

        model = new DefaultTableModel(header, 0) {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 3:
                        return Integer.class;
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
        listSP(); // Đọc từ database lên table

        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(40);
        table.getColumnModel().getColumn(6).setPreferredWidth(40);

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        centerAlign.setHorizontalAlignment(JLabel.CENTER);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
//        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(5).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(6).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(7).setCellRenderer(rightAlign);

        // Custom table
        table.setFocusable(false);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font0);
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

        img = new JLabel();
        img.setHorizontalAlignment(JLabel.CENTER);
        img.setBackground(Color.white);
        img.setOpaque(true);
        img.setBounds(new Rectangle(50, 50, 200, 200));
        img.setBorder(createLineBorder(Color.black));
        p.add(img);

        String[] arrInfo = {"Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Đơn giá", "Mã loại", "Mã nhà sản xuất"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 300, toadoyLabel = 50;
        int toadoxTextField = 430, toadoyTextField = 50;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(font1);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 120, 30);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 40;

            if (i != 5 && i != 6) {
                textfields[i] = new JTextField();
                textfields[i].setBounds(toadoxTextField, toadoyTextField, 300, 30);
                p.add(textfields[i]);
                toadoyTextField = toadoyTextField + 40;
            } else if (i == 5) {
                cmbLoai = new JComboBox();
                cmbLoai.setBackground(Color.white);
                cmbLoai.setBounds(new Rectangle(toadoxTextField, toadoyTextField, 300, 30));
                cmbLoai.addItem("Chọn...");
                listLoai(cmbLoai);
                p.add(cmbLoai);
                toadoyTextField = toadoyTextField + 40;
            } else {
                cmbNSX = new JComboBox();
                cmbNSX.setBackground(Color.white);
                cmbNSX.setBounds(new Rectangle(toadoxTextField, toadoyTextField, 300, 30));
                cmbNSX.addItem("Chọn...");
                listNSX(cmbNSX);
                p.add(cmbNSX);
                toadoyTextField = toadoyTextField + 40;
            }
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        btnAdd = new JButton("Thêm sản phẩm");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setHorizontalAlignment(JButton.LEFT);
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(180, 45));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa sản phẩm");
        btnEdit.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit.setHorizontalAlignment(JButton.LEFT);
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(180, 45));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa sản phẩm");
        btnDelete.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete.setHorizontalAlignment(JButton.LEFT);
        btnDelete.setBackground(Color.yellow);
        btnDelete.setFont(font1);
        btnDelete.setPreferredSize(new Dimension(180, 45));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(this);
        p.add(btnDelete);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setIcon(new ImageIcon("./img/search-icon.png"));
        btnSearch.setHorizontalAlignment(JButton.LEFT);
        btnSearch.setBackground(Color.yellow);
        btnSearch.setFont(font1);
        btnSearch.setPreferredSize(new Dimension(180, 45));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(this);
        p.add(btnSearch);

        btnIMG = new JButton("Chọn ảnh");
        btnIMG.setIcon(new ImageIcon("./img/img-icon.png"));
        btnIMG.setHorizontalAlignment(JButton.LEFT);
        btnIMG.setBackground(Color.yellow);
        btnIMG.setFont(font1);
        btnIMG.setPreferredSize(new Dimension(180, 45));
        btnIMG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIMG.addMouseListener(this);
        btnIMG.setVisible(false);
        p.add(btnIMG);

        btnBack = new JButton("Quay lại");
        btnBack.setIcon(new ImageIcon("./img/back-icon.png"));
        btnBack.setHorizontalAlignment(JButton.LEFT);
        btnBack.setBackground(Color.yellow);
        btnBack.setFont(font1);
        btnBack.setPreferredSize(new Dimension(180, 45));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(this);
        btnBack.setVisible(false);
        p.add(btnBack);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm.setHorizontalAlignment(JButton.LEFT);
        btnConfirm.setBackground(Color.yellow);
        btnConfirm.setFont(font1);
        btnConfirm.setPreferredSize(new Dimension(180, 45));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addMouseListener(this);
        btnConfirm.setVisible(false);
        p.add(btnConfirm);


        btnImport = new JButton("Nhập Excel");
        btnImport.setIcon(new ImageIcon("./img/excel-icon.png"));
        btnImport.setHorizontalAlignment(JButton.LEFT);
        btnImport.setBackground(Color.yellow);
        btnImport.setFont(font1);
        btnImport.setPreferredSize(new Dimension(180, 45));
        btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnImport.addMouseListener(this);
        btnImport.setVisible(true);
        p.add(btnImport);

        btnExport = new JButton("Xuất Excel");
        btnExport.setIcon(new ImageIcon("./img/excel-icon.png"));
        btnExport.setHorizontalAlignment(JButton.LEFT);
        btnExport.setBackground(Color.yellow);
        btnExport.setFont(font1);
        btnExport.setPreferredSize(new Dimension(180, 45));
        btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExport.addMouseListener(this);
        btnExport.setVisible(true);
        p.add(btnExport);

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

        JLabel lblSortMaSP = new JLabel("Mã sản phẩm :");
        lblSortMaSP.setFont(font0);
        lblSortMaSP.setBounds(30, 100, 90, 30);
        p.add(lblSortMaSP);

        sortMaSP = new JTextField();
        sortMaSP.setFont(font0);
        sortMaSP.setBounds(new Rectangle(130, 100, 150, 30));
        sortMaSP.addKeyListener(this);
        p.add(sortMaSP);

        /********** SORT MALOAI **********/

        JLabel lblSortLoai = new JLabel("Loại :");
        lblSortLoai.setFont(font0);
        lblSortLoai.setBounds(30, 140, 90, 30);
        p.add(lblSortLoai);

        cmbSortLoai = new JComboBox();
        cmbSortLoai.setFont(font0);
        cmbSortLoai.setBounds(new Rectangle(130, 140, 150, 30));
        cmbSortLoai.addItem("Không");
        cmbSortLoai.addKeyListener(this);
        listLoai(cmbSortLoai);
        p.add(cmbSortLoai);

        /********** SORT MANSX **********/

        JLabel lblSortNSX = new JLabel("Nhà sản xuất :");
        lblSortNSX.setFont(font0);
        lblSortNSX.setBounds(30, 180, 90, 30);
        p.add(lblSortNSX);

        cmbSortNSX = new JComboBox();
        cmbSortNSX.setFont(font0);
        cmbSortNSX.setBounds(new Rectangle(130, 180, 150, 30));
        cmbSortNSX.addItem("Không");
        cmbSortNSX.addKeyListener(this);
        listNSX(cmbSortNSX);
        p.add(cmbSortNSX);

        /********** SORT THEO GIÁ **********/

        JLabel lblSortPrice = new JLabel("Giá (VNĐ) :");
        lblSortPrice.setFont(font0);
        lblSortPrice.setBounds(30, 220, 90, 30);
        p.add(lblSortPrice);

        txtMinPrice = new JTextField();
        txtMinPrice.setFont(font0);
        txtMinPrice.setBounds(new Rectangle(130, 220, 150, 30));
        txtMinPrice.addKeyListener(this);
        p.add(txtMinPrice);

//        JSeparator sepPrice = new JSeparator(JSeparator.HORIZONTAL);
//        sepPrice.setBounds(120, 260, 100, 100);
//        p.add(sepPrice);

        txtMaxPrice = new JTextField();
        txtMaxPrice.setFont(font0);
        txtMaxPrice.setBounds(new Rectangle(130, 260, 150, 30));
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

    public void outModel(DefaultTableModel model, ArrayList<SanPhamDTO> sp) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (SanPhamDTO s : sp) {
            data = new Vector();
            data.add(s.getMaSP());
            data.add(s.getTenSP());
            data.add(s.getKichThuoc());
            data.add(s.getSoLuong());
            data.add(s.getGiaSP());
            data.add(s.getMaLoai());
            data.add(s.getMaNSX());
            data.add(s.getImg());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listSP() // Chép ArrayList lên table
    {
        if (spBUS.getList() == null)
            spBUS.list();
        ArrayList<SanPhamDTO> sp = spBUS.getList();
        model.setRowCount(0);
        outModel(model, sp);
    }

    public void listLoai(JComboBox cmb) {
        if (loaiBUS.getList() == null)
            loaiBUS.list();
        ArrayList<PhanLoaiDTO> loai = loaiBUS.getList();
        addCombobox(cmb, loai);
    }

    public void listNSX(JComboBox cmb) {
        if (nsxBUS.getList() == null)
            nsxBUS.list();
        ArrayList<NhaSanXuatDTO> nsx = nsxBUS.getList();
        addCombobox(cmb, nsx);
    }

    public void addCombobox(JComboBox cmb, ArrayList list) {
        for (Object a : list) {
            cmb.addItem(a);
        }
    }

    public void saveIMG() {
        try {
            if (image != null) {
                File save = new File("./img/SanPham/" + imgName);    //  Tạo file
                ImageIO.write(image, "png", save);    // Lưu hình "image" vào đường dẫn file save
                image = null;   //  Xóa hình trong bộ nhớ
            }
        } catch (IOException ex) {
            Logger.getLogger(SanPhamGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {
        String masp = sortMaSP.getText();
        String maloai = "";
        String mansx = "";
        if (cmbSortLoai.getSelectedIndex() != 0) {
            PhanLoaiDTO loai = (PhanLoaiDTO) cmbSortLoai.getSelectedItem();
            maloai = loai.getMaLoai();
        }
        if (cmbSortNSX.getSelectedIndex() != 0) {
            NhaSanXuatDTO nsx = (NhaSanXuatDTO) cmbSortNSX.getSelectedItem();
            mansx = nsx.getMaNSX();
        }
        double max = txtMaxPrice.getText().equals("") ? 999999999 : Double.parseDouble(txtMaxPrice.getText().replace(",", ""));
        double min = txtMinPrice.getText().equals("") ? 0 : Double.parseDouble(txtMinPrice.getText().replace(",", ""));

        outModel(model, spBUS.searchSP(masp, maloai, mansx, max, min));
    }

    public void cleanView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 5 && i != 6) {
                textfields[i].setEditable(false);
                textfields[i].setText("");
                textfields[i].setBackground(Color.white);
            }
        }
        cmbLoai.setSelectedIndex(0);
        cmbNSX.setSelectedIndex(0);
        img.setIcon(null);
        img.setBackground(Color.white);
        img.setOpaque(true);
        imgName = "Null";
    }

    public void addView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 5 && i != 6) {
                textfields[i].setEditable(true);
                textfields[i].setText("");
            }
        }
        cmbLoai.setSelectedIndex(0);
        cmbNSX.setSelectedIndex(0);
        img.setIcon(null);
        img.setText("Image");
        imgName = "Null";
    }

    public void editView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 0) {
                if (i != 5 && i != 6) {
                textfields[i].setEditable(true);
                }
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
            imgName = table.getModel().getValueAt(row, 7).toString();
            Image newImage;
            try {
                newImage = new ImageIcon("./img/SanPham/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            } catch (NullPointerException E) {
                newImage = new ImageIcon("./img/SanPham/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            }
            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            textfields[3].setText(table.getModel().getValueAt(row, 3).toString());
            NumberFormat num = NumberFormat.getInstance();
            textfields[4].setText(num.format(table.getModel().getValueAt(row, 4)));
            cmbLoai.setSelectedItem(loaiBUS.searchMaLoai(table.getModel().getValueAt(row, 5).toString()));
            cmbNSX.setSelectedItem(nsxBUS.searchMaNSX(table.getModel().getValueAt(row, 6).toString()));
            img.setIcon(new ImageIcon(newImage));
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON ADD ******************************/

        if (e.getSource() == btnAdd) {
            AddOrEdit = true;
            addView();

            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);
            btnImport.setVisible(false);
            btnExport.setVisible(false);

            btnIMG.setVisible(true);
            btnBack.setVisible(true);
            btnConfirm.setVisible(true);

            table.clearSelection();
            table.setEnabled(false);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON EDIT ******************************/

        if (e.getSource() == btnEdit) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa !!!");
                return;
            }
            AddOrEdit = false;
            editView();

            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);
            btnImport.setVisible(false);
            btnExport.setVisible(false);

            btnIMG.setVisible(true);
            btnBack.setVisible(true);
            btnConfirm.setVisible(true);

            table.setEnabled(false);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON DELETE ******************************/

        if (e.getSource() == btnDelete) {
            if (textfields[0].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                spBUS.delete(textfields[0].getText());
                table.clearSelection();
                cleanView();
                listSP();   //  outModel(model, spBUS.getList());
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON IMG ******************************/

        if (e.getSource() == btnIMG) {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images", "jpg", "png");
            fc.setFileFilter(filter);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();   //  Lấy URL hình
                    image = ImageIO.read(file); //  Lấy hình
                    imgName = textfields[0].getText().concat(".png");   //  Tên hình

                    // Thay đổi hình hiển thị
                    img.setText("");
                    img.setIcon(new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));

                    revalidate();
                    repaint();
                } catch (IOException ex) {
                    Logger.getLogger(SanPhamGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON BACK ******************************/

        if (e.getSource() == btnBack) {
            int messBack = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy bỏ ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messBack == 0) {
                cleanView();

                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                btnSearch.setVisible(true);
                btnImport.setVisible(true);
                btnExport.setVisible(true);

                btnConfirm.setVisible(false);
                btnBack.setVisible(false);
                btnIMG.setVisible(false);

                table.setEnabled(true);
            }
        }
        /****************************** XỬ LÝ SỰ KIỆN BUTTON CONFIRM ******************************/

        if (e.getSource() == btnConfirm) {
            if (AddOrEdit) {    // Add
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maSP = textfields[0].getText();
                    String tenSP = textfields[1].getText();
                    String kt = textfields[2].getText();
                    int sl = Integer.parseInt(textfields[3].getText());
                    double gia = Double.parseDouble(textfields[4].getText().replace(",", ""));
                    PhanLoaiDTO loai = (PhanLoaiDTO) cmbLoai.getSelectedItem();
                    String maLoai = loai.getMaLoai();
                    NhaSanXuatDTO nsx = (NhaSanXuatDTO) cmbNSX.getSelectedItem();
                    String maNsx = nsx.getMaNSX();
                    String IMG = imgName;

                    SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, kt, maLoai, maNsx, IMG, sl, gia);
                    spBUS.add(sp);
                    listSP();   //  outModel(model, spBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maSP = textfields[0].getText();
                    String tenSP = textfields[1].getText();
                    String kt = textfields[2].getText();
                    int sl = Integer.parseInt(textfields[3].getText());
                    double gia = Double.parseDouble(textfields[4].getText().replace(",", ""));
                    PhanLoaiDTO loai = (PhanLoaiDTO) cmbLoai.getSelectedItem();
                    String maLoai = loai.getMaLoai();
                    NhaSanXuatDTO nsx = (NhaSanXuatDTO) cmbNSX.getSelectedItem();
                    String maNsx = nsx.getMaNSX();
                    String IMG = imgName;

                    SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, kt, maLoai, maNsx, IMG, sl, gia);
                    spBUS.update(sp);
                    listSP();   //  outModel(model, spBUS.getList());   // Load lại table
                    cleanView();
                    saveIMG();
                    JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            cleanView();

            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
            btnSearch.setVisible(true);
            btnImport.setVisible(true);
            btnExport.setVisible(true);

            btnConfirm.setVisible(false);
            btnBack.setVisible(false);
            btnIMG.setVisible(false);

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
            outModel(model, spBUS.getList());
        }

        if (e.getSource() == btnSearchTable) {
            search();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, spBUS.getList());
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON IMPORT ******************************/

        if (e.getSource() == btnImport) {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xlsx");
            fc.setFileFilter(filter);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile(); //Lấy URL
                spBUS.ImportExcelDatabase(file);
                spBUS.list();
                outModel(model, spBUS.getList());
                JOptionPane.showMessageDialog(null, "Nhập file excel thành công");
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON EXPORT ******************************/

        if (e.getSource() == btnExport) {
            int messExcel = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xuất file Excel ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messExcel == 0) {
                spBUS.ExportExcelDatabase();
                JOptionPane.showMessageDialog(null, "Xuất file excel thành công");
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
        if (a.equals(sortMaSP) || a.equals(txtMinPrice) || a.equals(txtMaxPrice) || a.equals(cmbSortNSX) || a.equals(cmbSortLoai)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                search();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}