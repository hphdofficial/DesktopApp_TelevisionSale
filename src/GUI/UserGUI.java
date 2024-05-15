package GUI;

import BUS.QuyenBUS;
import BUS.UserBUS;
import DTO.QuyenDTO;
import DTO.UserDTO;

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
import java.util.ArrayList;
import java.util.Vector;

import static javax.swing.BorderFactory.createLineBorder;

public class UserGUI extends JPanel implements MouseListener, FocusListener, DocumentListener, KeyListener {
    private final UserBUS userBUS = new UserBUS();
    private final QuyenBUS roleBUS = new QuyenBUS();

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

    private Choice sortEnable;
    private Choice sortRole;
    private JTextField sortUserName;
    private JTextField sortUserID;
    private int lenArr;
    private JComboBox cmbRole;

    public UserGUI(int width, int height) {
        Default_Width = width;
        Default_Height = height;
        init();
    }

    public void init() {
        setLayout(null);
        setSize(Default_Width, Default_Height);
        setBackground(new Color(205, 205, 255));

        font0 = new Font("Segoe UI", Font.PLAIN, 13);
        font1 = new Font("Segoe UI", Font.BOLD, 13);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(null);
        p1.setBounds(0, 0, 480, Default_Height);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(null);
        p2.setBounds(480, 0, Default_Width - 450, Default_Height);
        add(p2);

        JPanel pInfo = panelInfo();
        pInfo.setLayout(null);
        pInfo.setBackground(Color.white);
        pInfo.setBounds(20, 20, 440, 330);
        pInfo.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pInfo);

        pButton = panelButton();
        pButton.setBackground(Color.white);
        pButton.setBounds(20, 370, 440, Default_Height / 2 - 10);
        pButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pButton);

        pSearch = panelSearch();
        pSearch.setLayout(null);
        pSearch.setBackground(Color.white);
        pSearch.setBounds(20, 370, 440, Default_Height / 2 - 10);
        pSearch.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        p1.add(pSearch);
        pSearch.setVisible(false);

        /******************** Tạo table ********************/

        Vector header = new Vector();
        header.add("UserID");
        header.add("UserName");
        header.add("PassWord");
        header.add("Email");
        header.add("RoleID");
        header.add("Enable");

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
        listUS(); // Đọc từ database lên table

        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(10);

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
        table.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        table.getColumnModel().getColumn(5).setCellRenderer(rightAlign);

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
        scroll.setBounds(new Rectangle(0, 20, Default_Width - 510, Default_Height - 40));
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

        String[] arrInfo = {"UserID", "UserName", "PassWord", "Email", "Role", "Enable"};
        lenArr = arrInfo.length;
        textfields = new JTextField[lenArr];
        labels = new JLabel[lenArr];

        int toadoxLabel = 50, toadoyLabel = 50;
        int toadoxTextField = 150, toadoyTextField = 50;

        for (int i = 0; i < lenArr; i++) {
            labels[i] = new JLabel(arrInfo[i]);
            labels[i].setFont(font1);
            labels[i].setBounds(toadoxLabel, toadoyLabel, 100, 30);
            labels[i].setHorizontalAlignment(JButton.LEFT);
            labels[i].setBackground(null);
            labels[i].setOpaque(true);
            p.add(labels[i]);
            toadoyLabel = toadoyLabel + 40;
            if (i != 4) {
                textfields[i] = new JTextField();
                textfields[i].setBounds(toadoxTextField, toadoyTextField, 250, 30);
                textfields[i].setEditable(false);
                p.add(textfields[i]);
                toadoyTextField = toadoyTextField + 40;
            } else {
                cmbRole = new JComboBox();
                cmbRole.setBackground(Color.white);
                cmbRole.setBounds(new Rectangle(toadoxTextField, toadoyTextField, 250, 30));
                cmbRole.addItem("Chọn...");
                listRole(cmbRole);
                p.add(cmbRole);
                toadoyTextField = toadoyTextField + 40;
            }
        }
        return p;
    }

    public JPanel panelButton() {
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnAdd = new JButton("Thêm tài khoản");
        btnAdd.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd.setBackground(Color.yellow);
        btnAdd.setFont(font1);
        btnAdd.setPreferredSize(new Dimension(170, 50));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(this);
        p.add(btnAdd);

        btnEdit = new JButton("Sửa tài khoản");
        btnEdit.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit.setBackground(Color.yellow);
        btnEdit.setFont(font1);
        btnEdit.setPreferredSize(new Dimension(170, 50));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addMouseListener(this);
        p.add(btnEdit);

        btnDelete = new JButton("Xóa tài khoản");
        btnDelete.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete.setBackground(Color.yellow);
        btnDelete.setFont(font1);
        btnDelete.setPreferredSize(new Dimension(170, 50));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(this);
        p.add(btnDelete);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setIcon(new ImageIcon("./img/search-icon.png"));
        btnSearch.setBackground(Color.yellow);
        btnSearch.setFont(font1);
        btnSearch.setPreferredSize(new Dimension(170, 50));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(this);
        p.add(btnSearch);

        btnBack = new JButton("Quay lại");
        btnBack.setIcon(new ImageIcon("./img/back-icon.png"));
        btnBack.setBackground(Color.yellow);
        btnBack.setFont(font1);
        btnBack.setPreferredSize(new Dimension(170, 50));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(this);
        p.add(btnBack);
        btnBack.setVisible(false);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm.setBackground(Color.yellow);
        btnConfirm.setFont(font1);
        btnConfirm.setPreferredSize(new Dimension(170, 50));
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
        searchTitle.setBounds(90, 0, 250, 30);
        p.add(searchTitle);

        searchBox = new JPanel(null);
        searchBox.setBackground(null);
        searchBox.setBounds(new Rectangle(90, 30, 250, 30));
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
        boloc.setBounds(90, 60, 250, 30);
        p.add(boloc);

        /********** SORT USERID **********/

        JLabel lblUserID = new JLabel("UserID :");
        lblUserID.setFont(font0);
        lblUserID.setBounds(90, 100, 90, 30);
        p.add(lblUserID);

        sortUserID = new JTextField();
        sortUserID.setFont(font0);
        sortUserID.setBounds(new Rectangle(190, 100, 150, 30));
        sortUserID.addKeyListener(this);
        p.add(sortUserID);

        /********** SORT USERNAME **********/

        JLabel lblUserName = new JLabel("UserName :");
        lblUserName.setFont(font0);
        lblUserName.setBounds(90, 140, 90, 30);
        p.add(lblUserName);

        sortUserName = new JTextField();
        sortUserName.setFont(font0);
        sortUserName.setBounds(new Rectangle(190, 140, 150, 30));
        sortUserName.addKeyListener(this);
        p.add(sortUserName);

        /********** SORT ROLE **********/

        JLabel lblRole = new JLabel("Role :");
        lblRole.setFont(font0);
        lblRole.setBounds(90, 180, 90, 30);
        p.add(lblRole);

        sortRole = new Choice();
        sortRole.addItem("Tất cả");
        sortRole.addItem("Admin");
        sortRole.addItem("Nhân viên ");
        sortRole.setFont(font0);
        sortRole.setBounds(new Rectangle(190, 180, 150, 50));
        sortRole.addKeyListener(this);
        p.add(sortRole);

        /********** SORT ENABLE **********/

        JLabel lblEnable = new JLabel("Enable :");
        lblEnable.setFont(font0);
        lblEnable.setBounds(90, 220, 90, 30);
        p.add(lblEnable);

        sortEnable = new Choice();
        sortEnable.addItem("Tất cả");
        sortEnable.addItem("1");
        sortEnable.addItem("0");
        sortEnable.setFont(font0);
        sortEnable.setBounds(new Rectangle(190, 220, 150, 50));
        sortEnable.addKeyListener(this);
        p.add(sortEnable);

        btnBackSearch = new JButton("Quay lại");
        btnBackSearch.setBackground(Color.yellow);
        btnBackSearch.setFont(font1);
        btnBackSearch.setBounds(90, 310, 110, 30);
        btnBackSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBackSearch.addMouseListener(this);
        p.add(btnBackSearch);

        btnSearchTable = new JButton("Tìm kiếm");
        btnSearchTable.setBackground(Color.yellow);
        btnSearchTable.setFont(font1);
        btnSearchTable.setBounds(230, 310, 110, 30);
        btnSearchTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearchTable.addMouseListener(this);
        p.add(btnSearchTable);

        return p;
    }

    public void outModel(DefaultTableModel model, ArrayList<UserDTO> user) {   // Xuất ra Table từ ArrayList
        Vector data;
        model.setRowCount(0);
        for (UserDTO u : user) {
            data = new Vector();
            data.add(u.getUserID());
            data.add(u.getUserName());
            data.add(u.getPassWord());
            data.add(u.getEmail());
            data.add(u.getRoleID());
            data.add(u.getEnable());
            model.addRow(data);
        }
        table.setModel(model);
    }

    public void listUS() {  // Chép ArrayList lên table
        if (userBUS.getList() == null)
            userBUS.list();
        ArrayList<UserDTO> user = userBUS.getList();
        model.setRowCount(0);
        outModel(model, user);
    }
    public void listRole(JComboBox cmb) {
        if (roleBUS.getList() == null)
            roleBUS.list();
        ArrayList<QuyenDTO> nsx = roleBUS.getList();
        addCombobox(cmb, nsx);
    }

    public void addCombobox(JComboBox cmb, ArrayList list) {
        for (Object a : list) {
            cmb.addItem(a);
        }
    }

    public void search() {
        String id = sortUserID.getText();
        String name = sortUserName.getText();
        String role = sortRole.getSelectedIndex() != 0 ? sortRole.getSelectedItem() : "";
        String enable = sortEnable.getSelectedIndex() != 0 ? sortEnable.getSelectedItem() : "";

        outModel(model, userBUS.search(id, name, role, enable));
    }

    public void cleanView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 4) {
                textfields[i].setEditable(false);
                textfields[i].setText("");
                textfields[i].setBackground(Color.white);
            }
        }
        cmbRole.setSelectedIndex(0);
    }

    public void addView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 4) {
                textfields[i].setEditable(true);
                textfields[i].setText("");
                textfields[i].setBackground(Color.white);
            }
        }
        cmbRole.setSelectedIndex(0);
    }

    public void editView() {   //  Xóa trắng các TextField
        for (int i = 0; i < lenArr; i++) {
            if (i != 0) {
                if (i != 4) {
                    textfields[i].setEditable(true);
                } else {
                    cmbRole.setEnabled(true);
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
//            imgName = table.getModel().getValueAt(row, 8).toString();
//            Image newImage;
//            try {
//                newImage = new ImageIcon("./img/User/" + imgName).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            } catch (NullPointerException E) {
//                newImage = new ImageIcon("./img/User/NoImage.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//            }

            textfields[0].setText(table.getModel().getValueAt(row, 0).toString());
            textfields[1].setText(table.getModel().getValueAt(row, 1).toString());
            textfields[2].setText(table.getModel().getValueAt(row, 2).toString());
            textfields[3].setText(table.getModel().getValueAt(row, 3).toString());
            cmbRole.setSelectedItem(roleBUS.searchMaQuyen(table.getModel().getValueAt(row, 4).toString()));
            textfields[5].setText(table.getModel().getValueAt(row, 5).toString());
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
                userBUS.delete(textfields[0].getText());
                table.clearSelection();
                cleanView();
                listUS();   //  outModel(model, userBUS.getList());
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
//                    Logger.getLogger(UserGUI.class.getName()).log(Level.SEVERE, null, ex);
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
                    String id = textfields[0].getText();
                    String name = textfields[1].getText();
                    String pass = textfields[2].getText();
                    String email = textfields[3].getText();
                    QuyenDTO role = (QuyenDTO) cmbRole.getSelectedItem();
                    String quyen = role.getMaQuyen();
                    String enable = textfields[5].getText();

                    UserDTO user = new UserDTO(id, name, pass, email, quyen, enable);
                    userBUS.add(user);
                    outModel(model, userBUS.getList());   // Load lại table
                    cleanView();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {    // Edit
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                QuyenDTO role = (QuyenDTO) cmbRole.getSelectedItem();
                if (messEdit == 0) {
                    String id = textfields[0].getText();
                    String name = textfields[1].getText();
                    String pass = textfields[2].getText();
                    String email = textfields[3].getText();
                    String quyen = role.getMaQuyen();
                    String enable = textfields[5].getText();

                    UserDTO user = new UserDTO(id, name, pass, email, quyen, enable);
                    userBUS.update(user);
                    listUS();   //  outModel(model, userBUS.getList());   // Load lại table
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
            outModel(model, userBUS.getList());
        }

        if (e.getSource() == btnSearchTable) {
            search();
        }

        if (e.getSource() == txtSearch) {
            outModel(model, userBUS.getList());
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
//        if (a.equals(sortMaKH) || a.equals(sortHoKH) || a.equals(sortTenKH) || a.equals(sortGT)) {
//            if (e.getKeyCode() == KeyEvent.VK_ENTER)
//                search();
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}