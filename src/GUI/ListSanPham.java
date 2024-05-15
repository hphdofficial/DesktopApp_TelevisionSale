package GUI;

import BUS.NhaSanXuatBUS;
import BUS.PhanLoaiBUS;
import BUS.SanPhamBUS;
import DTO.NhaSanXuatDTO;
import DTO.PhanLoaiDTO;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import static javax.swing.BorderFactory.createLineBorder;

public class ListSanPham extends JDialog implements MouseListener, FocusListener, DocumentListener {
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private final PhanLoaiBUS loaiBUS = new PhanLoaiBUS();
    private final NhaSanXuatBUS nsxBUS = new NhaSanXuatBUS();

    private final int Default_Width;
    private final int Default_Height;
    private final String maSP;
    private Font fontPlain, fontBold;
    private JLabel img;
    private JButton btnConfirm, btnBack;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JComboBox cmbNSX, cmbLoai;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> rowSorter;
    private String imgName = "NoImage";
    private int lenArr;
    private JPanel searchBox;
    private JTextField txtSearch;
    private JLabel searchIcon;

    public ListSanPham(int width, int height, String maSP) {
        Default_Width = width;
        Default_Height = height;
        this.maSP = maSP;
        setModal(true);
        init();
    }

    public void init() {
        setTitle("Danh Sách Sản Phẩm");
        setLayout(null);
        setSize(Default_Width, Default_Height);
        setBackground(new Color(205, 205, 255));

        fontPlain = new Font("Segoe UI", Font.PLAIN, 13);
        fontBold = new Font("Segoe UI", Font.BOLD, 13);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(null);
        p1.setBounds(700, 0, 450, 750);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(null);
        p2.setBounds(0, 0, 700, 750);
        add(p2);

        JLabel title = new JLabel("Thông tin sản phẩm");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(20, 80, 201));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0, 10, 450, 30);
        p1.add(title);

        img = new JLabel();
        img.setHorizontalAlignment(JLabel.CENTER);
        img.setBackground(Color.white);
        img.setOpaque(true);
        img.setBounds(new Rectangle(125, 60, 200, 200));
        img.setBorder(createLineBorder(Color.black));
        p1.add(img);

        JPanel pInfo = panelInfo();
        pInfo.setLayout(null);
        pInfo.setBackground(null);
        pInfo.setBounds(0, 270, 450, 330);
        p1.add(pInfo);

        JPanel pButton = panelButton();
        pButton.setBackground(null);
        pButton.setBounds(0, 600, 450, 100);
        p1.add(pButton);

        searchBox = new JPanel(null);
        searchBox.setBackground(null);
        searchBox.setBounds(new Rectangle(0, 0, 700, 40));
        searchBox.setBorder(createLineBorder(Color.BLACK));
        p2.add(searchBox);

        txtSearch = new JTextField();
        txtSearch.setBounds(new Rectangle(10, 0, 650, 40));
        txtSearch.setBorder(null);
        txtSearch.setOpaque(false);
        txtSearch.setFont(fontPlain);
        searchBox.add(txtSearch);

        txtSearch.addMouseListener(this);
        txtSearch.addFocusListener(this);
        txtSearch.getDocument().addDocumentListener(this);

        searchIcon = new JLabel(new ImageIcon("./img/search_icon_25px.png"));
        searchIcon.setBounds(new Rectangle(650, 0, 50, 40));
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBox.add(searchIcon);

        /******************** Create table ********************/

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
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
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
        table.getColumnModel().getColumn(5).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(6).setCellRenderer(centerAlign);
        table.getColumnModel().getColumn(7).setCellRenderer(centerAlign);

        // Custom table
        table.setFocusable(false);
        table.setRowHeight(40);
        table.getTableHeader().setFont(fontPlain);
        table.getTableHeader().setBackground(Color.red);
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setOpaque(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(75, 160, 255));
        table.addMouseListener(this);

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(new Rectangle(0, 40, 700, Default_Height - 40));
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

        String[] arrInfo = {"Mã sản phẩm", "Tên sản phẩm", "Kích thước", "Số lượng", "Đơn giá", "Mã loại", "Mã nhà sản xuất"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 30, toadoyLabel = 30;
        int toadoxTextField = 140, toadoyTextField = 30;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(fontBold);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 110, 30);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 40;

            if (i != 5 && i != 6) {
                textfields[i] = new JTextField();
                textfields[i].setFont(fontPlain);
                textfields[i].setBounds(toadoxTextField, toadoyTextField, 270, 30);
                p.add(textfields[i]);
                toadoyTextField = toadoyTextField + 40;
            } else if (i == 5) {
                cmbLoai = new JComboBox();
                cmbLoai.setFont(fontPlain);
                cmbLoai.setBounds(new Rectangle(toadoxTextField, toadoyTextField, 270, 30));
                cmbLoai.addItem("Chọn...");
                listLoai(cmbLoai);
                p.add(cmbLoai);
                toadoyTextField = toadoyTextField + 40;
            } else {
                cmbNSX = new JComboBox();
                cmbNSX.setFont(fontPlain);
                cmbNSX.setBounds(new Rectangle(toadoxTextField, toadoyTextField, 270, 30));
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
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnBack = new JButton("Quay lại");
        btnBack.setBackground(Color.yellow);
        btnBack.setFont(fontBold);
        btnBack.setPreferredSize(new Dimension(120, 40));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(this);
        p.add(btnBack);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(Color.yellow);
        btnConfirm.setFont(fontBold);
        btnConfirm.setPreferredSize(new Dimension(120, 40));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addMouseListener(this);
        p.add(btnConfirm);

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

    public void cleanView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 5 && i != 6) {
                textfields[i].setEditable(false);
                textfields[i].setText("");
                textfields[i].setBackground(Color.white);
            }
        }
        cmbLoai.setSelectedIndex(0);
        cmbLoai.setEnabled(false);
        cmbNSX.setSelectedIndex(0);
        cmbNSX.setEnabled(false);
        img.setIcon(null);
        img.setBackground(Color.white);
        img.setOpaque(true);
        imgName = "NoImage";
    }

    public String getInfoSP() {
        return textfields[0].getText() + "%" +   //  MaSP
                textfields[1].getText() + "%" +   //  TenSP
                textfields[4].getText() + "%" +   //  GiaSP
                imgName;                        //  IMG
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
            NumberFormat currentLocale = NumberFormat.getInstance();
            textfields[4].setText(currentLocale.format(table.getModel().getValueAt(row, 4)));
            cmbLoai.setSelectedItem(loaiBUS.searchMaLoai(table.getModel().getValueAt(row, 5).toString()));
            cmbNSX.setSelectedItem(nsxBUS.searchMaNSX(table.getModel().getValueAt(row, 6).toString()));
            img.setIcon(new ImageIcon(newImage));
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON BACK ******************************/

        if (e.getSource() == btnBack) {
            dispose();
//            setVisible(false);
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON CONFIRM ******************************/

        if (e.getSource() == btnConfirm) {
            dispose();
//            setVisible(false);
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
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtSearch) {
            searchIcon.setIcon(new ImageIcon("./img/search_icon_25px.png"));
            searchBox.setBorder(createLineBorder(Color.BLACK));
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2, 5, 6));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2, 5, 6));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}