package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

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

public class NhanVienGUI extends JPanel implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final NhanVienBUS nvBUS = new NhanVienBUS();

    private final int Default_Width;
    private final int Default_Height;
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
    private int lenArr;
    private JButton btnImport;
    private JButton btnExport;

    public NhanVienGUI(int width, int height) {
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
        table.getColumnModel().getColumn(8).setCellRenderer(rightAlign);

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

        img = new JLabel();
        img.setHorizontalAlignment(JLabel.CENTER);
        img.setBackground(Color.white);
        img.setOpaque(true);
        img.setBounds(new Rectangle(50, 50, 200, 200));
        img.setBorder(createLineBorder(Color.black));
        p.add(img);

        String[] arrInfo = {"Mã nhân viên", "Họ nhân viên", "Tên nhân viên", "Năm sinh", "Giới tính", "Điện thoại", "Địa chỉ", "Lương"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 300, toadoyLabel = 30;
        int toadoxTextField = 430, toadoyTextField = 30;

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
            textfields[i].setBounds(toadoxTextField, toadoyTextField, 300, 30);
            p.add(textfields[i]);
            toadoyTextField = toadoyTextField + 40;
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        btnAdd = new JButton("Thêm nhân viên");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setHorizontalAlignment(JButton.LEFT);
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(180, 45));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa nhân viên");
        btnEdit.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit.setHorizontalAlignment(JButton.LEFT);
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(180, 45));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa nhân viên");
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

        /********** SORT MANV **********/

        JLabel lblSortMaNV = new JLabel("Mã nhân viên :");
        lblSortMaNV.setFont(font0);
        lblSortMaNV.setBounds(30, 100, 90, 30);
        p.add(lblSortMaNV);

        sortMaNV = new JTextField();
        sortMaNV.setFont(font0);
        sortMaNV.setBounds(new Rectangle(130, 100, 150, 30));
        sortMaNV.addKeyListener(this);
        p.add(sortMaNV);

        /********** SORT HONV **********/

        JLabel lblSortLoai = new JLabel("Họ nhân viên :");
        lblSortLoai.setFont(font0);
        lblSortLoai.setBounds(30, 140, 90, 30);
        p.add(lblSortLoai);

        sortHoNV = new JTextField();
        sortHoNV.setFont(font0);
        sortHoNV.setBounds(new Rectangle(130, 140, 150, 30));
        sortHoNV.addKeyListener(this);
        p.add(sortHoNV);

        /********** SORT TENNV **********/

        JLabel lblSortNSX = new JLabel("Tên nhân viên :");
        lblSortNSX.setFont(font0);
        lblSortNSX.setBounds(30, 180, 90, 30);
        p.add(lblSortNSX);

        sortTenNV = new JTextField();
        sortTenNV.setFont(font0);
        sortTenNV.setBounds(new Rectangle(130, 180, 150, 30));
        sortTenNV.addKeyListener(this);
        p.add(sortTenNV);

        /********** SORT GIOITINH **********/

        JLabel lblSortGT = new JLabel("Giới tính :");
        lblSortGT.setFont(font0);
        lblSortGT.setBounds(30, 220, 90, 30);
        p.add(lblSortGT);

        sortGT = new Choice();
        sortGT.addItem("Tất cả");
        sortGT.addItem("Nam");
        sortGT.addItem("Nữ");
        sortGT.setFont(font0);
        sortGT.setBounds(new Rectangle(130, 220, 150, 50));
        sortGT.addKeyListener(this);
        p.add(sortGT);

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

    public void addCombobox(JComboBox cmb, ArrayList list) {
        for (Object a : list) {
            cmb.addItem(a);
        }
    }

    public void saveIMG() {
        try {
            if (image != null) {
                File save = new File("./img/NhanVien/" + imgName);    //  Tạo file
                ImageIO.write(image, "png", save);    // Lưu hình "image" vào đường dẫn file save
                image = null;   //  Xóa hình trong bộ nhớ
            }
        } catch (IOException ex) {
            Logger.getLogger(NhanVienGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {
        String manv = sortMaNV.getText();
        String ho = sortHoNV.getText();
        String ten = sortTenNV.getText();
        String gt = sortGT.getSelectedIndex() != 0 ? sortGT.getSelectedItem() : "";

        outModel(model, nvBUS.search(manv, ho, ten, gt));
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
        imgName = "NoImage.jpg";
    }

    public void addView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            textfields[i].setEditable(true);
            textfields[i].setText("");
        }
        img.setIcon(null);
        img.setText("Image");
        imgName = "NoImage.jpg";
    }

    public void editView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 0) {
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
            imgName = table.getModel().getValueAt(row, 8).toString();
            Image newImage;
            try {
                newImage = new ImageIcon("./img/NhanVien/" + imgName).getImage().getScaledInstance(151, 151, Image.SCALE_DEFAULT);
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
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa !!!");
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
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                nvBUS.delete(textfields[0].getText());
                table.clearSelection();
                cleanView();
                listNV();   //  outModel(model, nvBUS.getList());
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
                    imgName = "NV" + textfields[0].getText().concat(".png");   //  Tên hình

                    // Thay đổi hình hiển thị
                    img.setText("");
                    img.setIcon(new ImageIcon(image.getScaledInstance(151, 151, Image.SCALE_DEFAULT)));

                    revalidate();
                    repaint();
                } catch (IOException ex) {
                    Logger.getLogger(NhanVienGUI.class.getName()).log(Level.SEVERE, null, ex);
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
                    String maNV = textfields[0].getText();
                    String hoNV = textfields[1].getText();
                    String tenNV = textfields[2].getText();
                    int namSinh = Integer.parseInt(textfields[3].getText());
                    String gioiTinh = textfields[4].getText();
                    String dienThoai = textfields[5].getText();
                    String diaChi = textfields[6].getText();
                    double luong = Double.parseDouble(textfields[7].getText().replace(",", ""));
                    String IMG = imgName;

                    NhanVienDTO nv = new NhanVienDTO(maNV, hoNV, tenNV, gioiTinh, dienThoai, diaChi, IMG, namSinh, luong);
                    nvBUS.add(nv);
                    outModel(model, nvBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maNV = textfields[0].getText();
                    String hoNV = textfields[1].getText();
                    String tenNV = textfields[2].getText();
                    int namSinh = Integer.parseInt(textfields[3].getText());
                    String gioiTinh = textfields[4].getText();
                    String dienThoai = textfields[5].getText();
                    String diaChi = textfields[6].getText();
                    double luong = Double.parseDouble(textfields[7].getText().replace(",", ""));
                    String IMG = imgName;

                    NhanVienDTO nv = new NhanVienDTO(maNV, hoNV, tenNV, gioiTinh, dienThoai, diaChi, IMG, namSinh, luong);
                    nvBUS.update(nv);
                    listNV();   //  outModel(model, nvBUS.getList());   // Load lại table
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
            outModel(model, nvBUS.getList());
        }

        if (e.getSource() == btnSearchTable) {
            search();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, nvBUS.getList());
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
                nvBUS.ImportExcelDatabase(file);
                nvBUS.list();
                outModel(model, nvBUS.getList());
                JOptionPane.showMessageDialog(null, "Nhập file excel thành công");
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON EXPORT ******************************/

        if (e.getSource() == btnExport) {
            int messExcel = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xuất file Excel ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messExcel == 0) {
                nvBUS.ExportExcelDatabase();
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
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 1, 2));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = txtSearch.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text + ".*", 1, 2));
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
        if (a.equals(sortMaNV) || a.equals(sortHoNV) || a.equals(sortTenNV) || a.equals(sortGT)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                search();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}