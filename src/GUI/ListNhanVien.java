package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

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

public class ListNhanVien extends JDialog implements MouseListener, FocusListener, DocumentListener {
    private final NhanVienBUS nvBUS = new NhanVienBUS();

    private final int Default_Width;
    private final int Default_Height;
    private Font fontPlain, fontBold;
    private JLabel img;
    private JButton btnConfirm, btnBack;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> rowSorter;
    private String imgName = "NoImage";
    private int lenArr;
    private JPanel searchBox;
    private JTextField txtSearch;
    private JLabel searchIcon;

    public ListNhanVien(int width, int height) {
        Default_Width = width;
        Default_Height = height;
        setModal(true);
        init();
    }

    public void init() {
        setTitle("Danh Sách Nhân Viên");
        setLayout(null);
        setSize(Default_Width, Default_Height);
        setBackground(new Color(205, 205, 255));

//        this.setBackground(new Color(187, 196, 238));

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

        JLabel title = new JLabel("Thông tin nhân viên");
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
        pInfo.setBounds(0, 270, 450, 350);
        p1.add(pInfo);

        JPanel pButton = panelButton();
        pButton.setBackground(null);
        pButton.setBounds(0, 620, 450, 100);
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
        header.add("Mã nhân viên");
        header.add("Họ nhân viên");
        header.add("Tên nhân viên");
        header.add("Năm sinh");
        header.add("Giới tính");
        header.add("Điện thoại");
        header.add("Địa chỉ");
        header.add("Lương");
        header.add("Hình ảnh");

        model = new DefaultTableModel(header, 0) {
            public Class getColumnClass(int column) {
                switch (column) {
                    case 3:
                        return Integer.class;
                    case 7:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        table = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        listNV(); // Đọc từ database lên table

        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(40);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);

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
        table.getColumnModel().getColumn(3).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(5).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(6).setCellRenderer(rightAlign);
//        table.getColumnModel().getColumn(7).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(8).setCellRenderer(centerAlign);

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

        String[] arrInfo = {"Mã nhân viên", "Họ nhân viên", "Tên nhân viên", "Năm sinh", "Giới tính", "Điện thoại", "Địa chỉ", "Lương"};
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

            textfields[i] = new JTextField();
            textfields[i].setFont(fontPlain);
            textfields[i].setBounds(toadoxTextField, toadoyTextField, 270, 30);
            p.add(textfields[i]);
            toadoyTextField = toadoyTextField + 40;
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

    public void outModel(DefaultTableModel model, ArrayList<NhanVienDTO> nv) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (NhanVienDTO n : nv) {
            data = new Vector();
            data.add(n.getMaNV());
            data.add(n.getHoNV());
            data.add(n.getTenNV());
            data.add(n.getNamSinh());
            data.add(n.getGioiTinh());
            data.add(n.getDienThoai());
            data.add(n.getDiaChi());
            data.add(n.getLuong());
            data.add(n.getImg());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listNV() // Chép ArrayList lên table
    {
        if (nvBUS.getList() == null)
            nvBUS.list();
        ArrayList<NhanVienDTO> nv = nvBUS.getList();
        model.setRowCount(0);
        outModel(model, nv);
    }

    public void cleanView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            textfields[i].setEditable(false);
            textfields[i].setText("");
            textfields[i].setBackground(Color.white);
        }
        img.setIcon(null);
        img.setBackground(Color.white);
        img.setOpaque(true);
        imgName = "NoImage";
    }

    public String getInfoNV() {
        return textfields[0].getText();    //  MaNV
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        /****************************** XỬ LÝ SỰ KIỆN DỮ LIỆU TABLE --> FORM ******************************/

        if (e.getSource() == table) {
            int row = table.getSelectedRow();
            if (table.getRowSorter() != null) {
                row = table.getRowSorter().convertRowIndexToModel(row);
            }
            imgName = table.getModel().getValueAt(row, 8).toString();
            Image newImage;
            try {
                newImage = new ImageIcon("./img/NhanVien/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            } catch (NullPointerException E) {
                newImage = new ImageIcon("./img/NhanVien/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            }
            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            textfields[3].setText(table.getModel().getValueAt(row, 3).toString());
            textfields[4].setText(table.getModel().getValueAt(row, 4).toString());
            textfields[5].setText(table.getModel().getValueAt(row, 5).toString());
            textfields[6].setText(table.getModel().getValueAt(row, 6).toString());
            NumberFormat currentLocale = NumberFormat.getInstance();
            textfields[7].setText(currentLocale.format(table.getModel().getValueAt(row, 7)));
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
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2, 3, 4, 5));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 0, 1, 2, 3, 4, 5));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}