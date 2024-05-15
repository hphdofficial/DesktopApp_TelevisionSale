package GUI;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;

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
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import static javax.swing.BorderFactory.createLineBorder;

public class NhaCungCapGUI extends JPanel implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    private final int Default_Width;
    private final int Default_Height;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnConfirm, btnBack, btnIMG;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JTable table;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model;
    private boolean AddOrEdit = true;   // Cờ cho button Cofirm True:Add || False:Edit
    private Font font0, font1;
    private JPanel searchBox;
    private JTextField txtSearch;
    private JLabel searchIcon;
    private JPanel pButton;
    private JPanel pSearch;
    private JButton btnBackSearch;
    private JButton btnSearchTable;
    private JTextField sortMaNCC;
    private JTextField sortTenNCC;
    private JTextField sortFax;
    private int lenArr;
    private JButton btnImport;
    private JButton btnExport;

    public NhaCungCapGUI(int width, int height) {
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

        JLabel lbIMG = new JLabel();
        lbIMG.setIcon(new ImageIcon("./img/imgSupplier.png"));
        lbIMG.setHorizontalAlignment(JLabel.CENTER);
//        lbIMG.setBackground(Color.red);
//        lbIMG.setOpaque(true);
        lbIMG.setBounds(30, 20, 200, Default_Height / 2 - 60);
        pInfo.add(lbIMG);

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
        header.add("Mã nhà cung cấp");
        header.add("Tên nhà cung cấp");
        header.add("Địa chỉ");
        header.add("Điện thoại");
        header.add("Số Fax");

        model = new DefaultTableModel(header, 0) {
//            public Class getColumnClass(int column) {
//                switch (column) {
//                    case 3:
//                        return Integer.class;
//                    case 7:
//                        return Double.class;
//                    default:
//                        return String.class;
//                }
//            }
        };

        table = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(rowSorter);
        listNCC(); // Đọc từ database lên table

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
        table.getColumnModel().getColumn(1).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(2).setCellRenderer(leftAlign);
        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);

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

        String[] arrInfo = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Điện thoại", "Số Fax"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 270, toadoyLabel = 60;
        int toadoxTextField = 420, toadoyTextField = 60;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(font1);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 150, 30);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 50;

            textfields[i] = new JTextField();
            textfields[i].setBounds(toadoxTextField, toadoyTextField, 300, 30);
            p.add(textfields[i]);
            toadoyTextField = toadoyTextField + 50;
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        btnAdd = new JButton("Thêm nhà cung cấp");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setHorizontalAlignment(JButton.LEFT);
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(180, 45));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa nhà cung cấp");
        btnEdit.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit.setHorizontalAlignment(JButton.LEFT);
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(180, 45));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa nhà cung cấp");
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

//        btnIMG = new JButton("Chọn ảnh");
//        btnIMG.setIcon(new ImageIcon("./img/img-icon.png"));
//        btnIMG.setBackground(Color.yellow);
//        btnIMG.setFont(font1);
//        btnIMG.setPreferredSize(new Dimension(180, 45));
//        btnIMG.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnIMG.addMouseListener(this);
//        btnIMG.setVisible(false);
//        p.add(btnIMG);

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

        /********** SORT MANCC **********/

        JLabel lblMaNCC = new JLabel("Mã nhà cung cấp :");
        lblMaNCC.setFont(font0);
        lblMaNCC.setBounds(30, 100, 90, 30);
        p.add(lblMaNCC);

        sortMaNCC = new JTextField();
        sortMaNCC.setFont(font0);
        sortMaNCC.setBounds(new Rectangle(130, 100, 150, 30));
        sortMaNCC.addKeyListener(this);
        p.add(sortMaNCC);

        /********** SORT TENNCC **********/

        JLabel lblTenNCC = new JLabel("Tên nhà cung cấp :");
        lblTenNCC.setFont(font0);
        lblTenNCC.setBounds(30, 140, 90, 30);
        p.add(lblTenNCC);

        sortTenNCC = new JTextField();
        sortTenNCC.setFont(font0);
        sortTenNCC.setBounds(new Rectangle(130, 140, 150, 30));
        sortTenNCC.addKeyListener(this);
        p.add(sortTenNCC);

        /********** SORT SOFAX **********/

        JLabel lblFax = new JLabel("Số Fax :");
        lblFax.setFont(font0);
        lblFax.setBounds(30, 180, 90, 30);
        p.add(lblFax);

        sortFax = new JTextField();
        sortFax.setFont(font0);
        sortFax.setBounds(new Rectangle(130, 180, 150, 30));
        sortFax.addKeyListener(this);
        p.add(sortFax);

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

    public void outModel(DefaultTableModel model, ArrayList<NhaCungCapDTO> ncc) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (NhaCungCapDTO k : ncc) {
            data = new Vector();
            data.add(k.getMaNCC());
            data.add(k.getTenNCC());
            data.add(k.getDiaChi());
            data.add(k.getDienThoai());
            data.add(k.getSoFax());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listNCC() // Chép ArrayList lên table
    {
        if (nccBUS.getList() == null)
            nccBUS.list();
        ArrayList<NhaCungCapDTO> ncc = nccBUS.getList();
        model.setRowCount(0);
        outModel(model, ncc);
    }

//    public void addCombobox(JComboBox cmb, ArrayList list) {
//        for (Object a : list) {
//            cmb.addItem(a);
//        }
//    }

//    public void saveIMG() {
//        try {
//            if (image != null) {
//                File save = new File("./img/NhaCungCap/" + imgName);    //  Tạo file
//                ImageIO.write(image,"png", save);    // Lưu hình "image" vào đường dẫn file save
//                image = null;   //  Xóa hình trong bộ nhớ
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NhaCungCapGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

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
            if (i != 0) {
                textfields[i].setEditable(true);
            } else {
                textfields[i].setBackground(null);
            }
        }
    }

    public void search() {
        String mancc = sortMaNCC.getText();
        String ten = sortTenNCC.getText();
        String fax = sortFax.getText();

        outModel(model, nccBUS.search(mancc, ten, fax));
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
//                newImage = new ImageIcon("./img/NhaCungCap/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            } catch (NullPointerException E) {
//                newImage = new ImageIcon("./img/NhaCungCap/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            }

            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            textfields[3].setText(table.getModel().getValueAt(row, 3).toString());
            textfields[4].setText(table.getModel().getValueAt(row, 4).toString());
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
                nccBUS.delete(textfields[0].getText());
                table.clearSelection();
                cleanView();
                listNCC();   //  outModel(model, nccBUS.getList());
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
//                    Logger.getLogger(NhaCungCapGUI.class.getName()).log(Level.SEVERE, null, ex);
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
                btnImport.setVisible(true);
                btnExport.setVisible(true);

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
                    String maNCC = textfields[0].getText();
                    String tenNCC = textfields[1].getText();
                    String diaChi = textfields[2].getText();
                    String dienThoai = textfields[3].getText();
                    String soFax = textfields[4].getText();

                    NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, dienThoai, soFax);
                    nccBUS.add(ncc);
                    outModel(model, nccBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maNCC = textfields[0].getText();
                    String tenNCC = textfields[1].getText();
                    String diaChi = textfields[2].getText();
                    String dienThoai = textfields[3].getText();
                    String soFax = textfields[4].getText();

                    NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, dienThoai, soFax);
                    nccBUS.update(ncc);
                    listNCC();   //  outModel(model, nccBUS.getList());   // Load lại table
                    cleanView();
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
            outModel(model, nccBUS.getList());
        }

        if (e.getSource() == btnSearchTable) {
            search();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, nccBUS.getList());
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
                nccBUS.ImportExcelDatabase(file);
                nccBUS.list();
                outModel(model, nccBUS.getList());
                JOptionPane.showMessageDialog(null, "Nhập file excel thành công");
            }
        }

        /****************************** XỬ LÝ SỰ KIỆN BUTTON EXPORT ******************************/

        if (e.getSource() == btnExport) {
            int messExcel = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xuất file Excel ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messExcel == 0) {
                nccBUS.ExportExcelDatabase();
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
        if (a.equals(sortMaNCC) || a.equals(sortTenNCC) || a.equals(sortFax)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                search();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}