package GUI;

import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.QuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.ChucNangDTO;
import DTO.QuyenDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

public class PhanQuyenGUI extends JPanel implements MouseListener, ActionListener {
    private final int Default_Width;
    private final int Default_Height;

    private final QuyenBUS roleBUS = new QuyenBUS();
    private final ChucNangBUS funcBUS = new ChucNangBUS();
    private final ChiTietQuyenBUS ctRoleBUS = new ChiTietQuyenBUS();

    private JPanel mainPanel;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelInfo;
    private JPanel panelRole;
    private JPanel panelFunction;
    private JPanel panelChucNang;

    private DefaultTableModel model1;
    private JTable table1;
    private DefaultTableModel model2;
    private JScrollPane scrollpane1;
    private JTable table2;
    private JScrollPane scrollpane2;

    private JButton btnAdd1;
    private JButton btnEdit1;
    private JButton btnDelete1;
    private JButton btnAdd2;
    private JButton btnEdit2;
    private JButton btnDelete2;
    private JButton btnConfirm1;
    private JButton btnConfirm2;
    private JButton btnSave;
    private JButton btnCancel;

    private JLabel lbID;
    private JTextField txtID;
    private JLabel lbName;
    private JTextField txtName;
    private JComboBox cmbRole;
    private boolean AddOrEdit1 = true;
    private boolean AddOrEdit2 = true;
    private JCheckBox[] checkbox;
    private int lenArr;

    public PhanQuyenGUI(int width, int height) {
        Default_Width = width;
        Default_Height = height;
        roleBUS.list();
        funcBUS.list();
        init();
    }

//    public static void main(String[] args) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
//                 UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ManagerTV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        JFrame f = new JFrame();
//        f.setSize(1160, 800);
//        f.setLocationRelativeTo(null);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.add(new PhanQuyenGUI(1160, 800));
//        f.setVisible(true);
//    }

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
        panelLeft.setPreferredSize(new Dimension(770, Default_Height));
        panelLeft.setBackground(null);
        panelLeft.setLayout(new BorderLayout(0, 10));
        mainPanel.add(panelLeft, BorderLayout.WEST);

        panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(350, Default_Height));
        panelRight.setBackground(Color.white);
        panelRight.setLayout(null);
        panelRight.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        mainPanel.add(panelRight, BorderLayout.EAST);

        panelRole = new JPanel(null);
        panelRole.setBackground(Color.white);
        panelRole.setPreferredSize(new Dimension(0, 320));
        panelRole.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelLeft.add(panelRole, BorderLayout.NORTH);

        JLabel lbRoleTitle = new JLabel("Quyền");
        lbRoleTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbRoleTitle.setForeground(Color.red);
        lbRoleTitle.setBounds(20, 0, 550, 40);
        lbRoleTitle.setHorizontalAlignment(JTextField.LEFT);
        panelRole.add(lbRoleTitle);

        panelFunction = new JPanel(null);
        panelFunction.setBackground(Color.white);
        panelFunction.setPreferredSize(new Dimension(0, 0));
        panelFunction.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelLeft.add(panelFunction, BorderLayout.CENTER);

        JLabel lbFuncTitle = new JLabel("Chức năng");
        lbFuncTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbFuncTitle.setForeground(Color.red);
        lbFuncTitle.setBounds(20, 0, 550, 40);
        lbFuncTitle.setHorizontalAlignment(JTextField.LEFT);
        panelFunction.add(lbFuncTitle);

        panelInfo = new JPanel(null);
        panelInfo.setBackground(Color.white);
        panelInfo.setPreferredSize(new Dimension(0, 80));
        panelInfo.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        panelLeft.add(panelInfo, BorderLayout.SOUTH);

        lbID = new JLabel("Mã");
        lbID.setFont(font0);
        lbID.setForeground(Color.black);
        lbID.setBounds(20, 25, 100, 30);
        lbID.setHorizontalAlignment(JTextField.CENTER);
        panelInfo.add(lbID);

        txtID = new JTextField();
        txtID.setBounds(120, 25, 175, 30);
        txtID.setText("");
        txtID.setEditable(false);
        txtID.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 255, 255)));
        txtID.setHorizontalAlignment(JTextField.CENTER);
        panelInfo.add(txtID);

        lbName = new JLabel("Tên");
        lbName.setFont(font0);
        lbName.setForeground(Color.black);
        lbName.setBounds(295, 25, 100, 30);
        lbName.setHorizontalAlignment(JTextField.CENTER);
        panelInfo.add(lbName);

        txtName = new JTextField();
        txtName.setBounds(395, 25, 175, 30);
        txtName.setText("");
        txtName.setEditable(false);
        txtName.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0, 255, 255)));
        txtName.setHorizontalAlignment(JTextField.CENTER);
        panelInfo.add(txtName);

        btnConfirm1 = new JButton("Xác nhận");
        btnConfirm1.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm1.setBounds(590, 15, 150, 50);
        btnConfirm1.setBackground(Color.red);
        btnConfirm1.setForeground(Color.white);
        btnConfirm1.setFocusPainted(false);
        btnConfirm1.setBorderPainted(false);
        btnConfirm1.setEnabled(false);
        btnConfirm1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm1.addMouseListener(this);
        panelInfo.add(btnConfirm1);

        btnConfirm2 = new JButton("Xác nhận");
        btnConfirm2.setIcon(new ImageIcon("./img/confirm-icon.png"));
        btnConfirm2.setBounds(590, 15, 150, 50);
        btnConfirm2.setBackground(Color.red);
        btnConfirm2.setForeground(Color.white);
        btnConfirm2.setFocusPainted(false);
        btnConfirm2.setBorderPainted(false);
        btnConfirm2.setEnabled(false);
        btnConfirm2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm2.addMouseListener(this);
        panelInfo.add(btnConfirm2);

        /*************************************************************/

        DefaultTableCellRenderer leftAlign = new DefaultTableCellRenderer();
        leftAlign.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
        centerAlign.setHorizontalAlignment(JLabel.CENTER);

        Vector header = new Vector();
        header.add("Mã quyền");
        header.add("Tên quyền");
        model1 = new DefaultTableModel(header, 10);
        table1 = new JTable(model1);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model1);
        table1.setRowSorter(rowSorter);
        listRole();

        ((DefaultTableCellRenderer) table1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        table1.getColumnModel().getColumn(0).setCellRenderer(leftAlign);
        table1.getColumnModel().getColumn(1).setCellRenderer(leftAlign);

        table1.getColumnModel().getColumn(0).setPreferredWidth(20);
        table1.getColumnModel().getColumn(1).setPreferredWidth(180);

        table1.setFocusable(false);
        table1.setRowHeight(40);
        table1.getTableHeader().setFont(font1);
        table1.getTableHeader().setBackground(Color.red);
        table1.getTableHeader().setForeground(Color.black);
        table1.getTableHeader().setOpaque(false);
        table1.setIntercellSpacing(new Dimension(0, 0));
        table1.setShowVerticalLines(false);
        table1.setFillsViewportHeight(true);
        table1.setSelectionBackground(new Color(75, 160, 255));
        table1.addMouseListener(this);

        scrollpane1 = new JScrollPane(table1);
        scrollpane1.setBackground(null);
        scrollpane1.setBounds(new Rectangle(20, 40, 550, 260));
        scrollpane1.getInsets().set(0, 0, 0, 0);
        scrollpane1.setViewportBorder(null);
        scrollpane1.getViewport().setBorder(null);
        scrollpane1.getViewport().getInsets().set(0, 0, 0, 0);
        scrollpane1.getViewport().setOpaque(true);

        Vector header2 = new Vector();
        header2.add("Mã chức năng");
        header2.add("Tên chức năng");
        model2 = new DefaultTableModel(header2, 10);
        table2 = new JTable(model2);
        TableRowSorter<TableModel> rowSorter2 = new TableRowSorter<TableModel>(model2);
        table2.setRowSorter(rowSorter2);
        listFunction();

        ((DefaultTableCellRenderer) table2.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        table2.getColumnModel().getColumn(0).setCellRenderer(centerAlign);
        table2.getColumnModel().getColumn(1).setCellRenderer(leftAlign);

        table2.getColumnModel().getColumn(0).setPreferredWidth(20);
        table2.getColumnModel().getColumn(1).setPreferredWidth(180);

        table2.setFocusable(false);
        table2.setRowHeight(40);
        table2.getTableHeader().setFont(font1);
        table2.getTableHeader().setBackground(Color.red);
        table2.getTableHeader().setForeground(Color.black);
        table2.getTableHeader().setOpaque(false);
        table2.setIntercellSpacing(new Dimension(0, 0));
        table2.setShowVerticalLines(false);
        table2.setFillsViewportHeight(true);
        table2.setSelectionBackground(new Color(75, 160, 255));
        table2.addMouseListener(this);

        scrollpane2 = new JScrollPane(table2);
        scrollpane2.setBackground(null);
        scrollpane2.setBounds(new Rectangle(20, 40, 550, 265));
        scrollpane2.getInsets().set(0, 0, 0, 0);
        scrollpane2.setViewportBorder(null);
        scrollpane2.getViewport().setBorder(null);
        scrollpane2.getViewport().getInsets().set(0, 0, 0, 0);
        scrollpane2.getViewport().setOpaque(true);

        panelRole.add(scrollpane1);
        panelFunction.add(scrollpane2);

        /*************************************************************/

        btnAdd1 = new JButton("Thêm quyền");
        btnAdd1.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd1.setBounds(590, 40, 150, 50);
        btnAdd1.setBackground(new Color(0, 255, 202));
        btnAdd1.setForeground(Color.black);
        btnAdd1.setFocusPainted(false);
        btnAdd1.setBorderPainted(false);
        btnAdd1.setEnabled(true);
        btnAdd1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd1.addMouseListener(this);

        btnEdit1 = new JButton("Sửa quyền");
        btnEdit1.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit1.setBounds(590, 120, 150, 50);
        btnEdit1.setBackground(new Color(0, 255, 202));
        btnEdit1.setForeground(Color.black);
        btnEdit1.setFocusPainted(false);
        btnEdit1.setBorderPainted(false);
        btnEdit1.setEnabled(true);
        btnEdit1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit1.addMouseListener(this);

        btnDelete1 = new JButton("Xóa quyền");
        btnDelete1.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete1.setBounds(590, 200, 150, 50);
        btnDelete1.setBackground(new Color(0, 255, 202));
        btnDelete1.setForeground(Color.black);
        btnDelete1.setFocusPainted(false);
        btnDelete1.setBorderPainted(false);
        btnDelete1.setEnabled(true);
        btnDelete1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete1.addMouseListener(this);

        btnAdd2 = new JButton("Thêm chức năng");
        btnAdd2.setIcon(new ImageIcon("./img/add-icon.png"));
        btnAdd2.setBounds(590, 40, 150, 50);
        btnAdd2.setBackground(new Color(255, 242, 0));
        btnAdd2.setForeground(Color.black);
        btnAdd2.setFocusPainted(false);
        btnAdd2.setBorderPainted(false);
        btnAdd2.setEnabled(true);
        btnAdd2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd2.addMouseListener(this);

        btnEdit2 = new JButton("Sửa chức năng");
        btnEdit2.setIcon(new ImageIcon("./img/edit-icon.png"));
        btnEdit2.setBounds(590, 120, 150, 50);
        btnEdit2.setBackground(new Color(255, 242, 0));
        btnEdit2.setForeground(Color.black);
        btnEdit2.setFocusPainted(false);
        btnEdit2.setBorderPainted(false);
        btnEdit2.setEnabled(true);
        btnEdit2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit2.addMouseListener(this);

        btnDelete2 = new JButton("Xóa chức năng");
        btnDelete2.setIcon(new ImageIcon("./img/delete-icon.png"));
        btnDelete2.setBounds(590, 200, 150, 50);
        btnDelete2.setBackground(new Color(255, 242, 0));
        btnDelete2.setForeground(Color.black);
        btnDelete2.setFocusPainted(false);
        btnDelete2.setBorderPainted(false);
        btnDelete2.setEnabled(true);
        btnDelete2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete2.addMouseListener(this);

        panelRole.add(btnAdd1);
        panelRole.add(btnEdit1);
        panelRole.add(btnDelete1);
        panelFunction.add(btnAdd2);
        panelFunction.add(btnEdit2);
        panelFunction.add(btnDelete2);

        /*************************************************************/

        JLabel lbTitle = new JLabel("QUẢN LÝ PHÂN QUYỀN");
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbTitle.setForeground(Color.white);
        lbTitle.setBackground(new Color(28, 28, 255));
        lbTitle.setOpaque(true);
        lbTitle.setHorizontalAlignment(JLabel.CENTER);
        lbTitle.setBounds(5, 5, 340, 50);
        panelRight.add(lbTitle);

        JLabel lbchoose = new JLabel("Nhóm quyền");
        lbchoose.setFont(font0);
        lbchoose.setForeground(Color.black);
        lbchoose.setBounds(50, 100, 100, 30);
        lbchoose.setHorizontalAlignment(JTextField.CENTER);
        panelRight.add(lbchoose);

        cmbRole = new JComboBox();
        cmbRole.setBackground(Color.white);
        cmbRole.setBounds(new Rectangle(150, 100, 150, 30));
        cmbRole.addItem("Chọn...");
        cmbRole.addActionListener(this);
        listcmbRole(cmbRole);
        panelRight.add(cmbRole);

        panelChucNang = new JPanel(null);
        panelChucNang.setBackground(null);
        panelChucNang.setBounds(new Rectangle(5, 150, 340, Default_Height - 290));
        panelRight.add(panelChucNang);
        showFunction();

        btnSave = new JButton("Lưu");
        btnSave.setIcon(new ImageIcon("./img/save-icon.png"));
        btnSave.setBounds(180, 685, 150, 50);
        btnSave.setBackground(new Color(28, 28, 255));
        btnSave.setForeground(Color.white);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setEnabled(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addMouseListener(this);
        panelRight.add(btnSave);

        btnCancel = new JButton("Hủy");
        btnCancel.setIcon(new ImageIcon("./img/cancel-icon.png"));
        btnCancel.setBounds(20, 685, 150, 50);
        btnCancel.setBackground(new Color(28, 28, 255));
        btnCancel.setForeground(Color.white);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setEnabled(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addMouseListener(this);
        panelRight.add(btnCancel);

        cleanView();
    }

    public void outModel1(DefaultTableModel model, ArrayList<QuyenDTO> quyen) {
        Vector data;
        model.setRowCount(0);
        for (QuyenDTO q : quyen) {
            data = new Vector();
            data.add(q.getMaQuyen());
            data.add(q.getTenQuyen());
            model.addRow(data);
        }
        table1.setModel(model);
    }

    public void outModel2(DefaultTableModel model, ArrayList<ChucNangDTO> function) {
        Vector data;
        model.setRowCount(0);
        for (ChucNangDTO cn : function) {
            data = new Vector();
            data.add(cn.getMaCN());
            data.add(cn.getTenCN());
            model.addRow(data);
        }
        table2.setModel(model);
    }

    public void showFunction() {
        if (funcBUS.getList() == null) {
            funcBUS.list();
        }
        ArrayList<ChucNangDTO> function = funcBUS.getList();
        lenArr = function.size();
        checkbox = new JCheckBox[lenArr];

        int toadox = 100, toadoy = 20;
        for (int i = 0; i < lenArr; i++) {
            checkbox[i] = new JCheckBox(function.get(i).getTenCN());
            checkbox[i].setBounds(toadox, toadoy, 200, 30);
            checkbox[i].setEnabled(false);
            panelChucNang.add(checkbox[i]);
            toadoy = toadoy + 40;
        }
    }

    public void listRole() {
        if (roleBUS.getList() == null)
            roleBUS.list();
        ArrayList<QuyenDTO> quyen = roleBUS.getList();
        model1.setRowCount(0);
        outModel1(model1, quyen);
    }

    public void listFunction() {
        if (funcBUS.getList() == null)
            funcBUS.list();
        ArrayList<ChucNangDTO> function = funcBUS.getList();
        model2.setRowCount(0);
        outModel2(model2, function);
    }

    public void addCombobox(JComboBox cmb, ArrayList list) {
        for (Object a : list) {
            cmb.addItem(a);
        }
    }

    public void listcmbRole(JComboBox cmb) {
        if (roleBUS.getList() == null)
            roleBUS.list();
        ArrayList<QuyenDTO> quyen = roleBUS.getList();
        addCombobox(cmb, quyen);
    }

    public void cleanView() {
        table1.clearSelection();
        table2.clearSelection();
        table1.setEnabled(true);
        table2.setEnabled(true);
        txtID.setEditable(false);
        txtName.setEditable(false);
        txtID.setText("");
        txtName.setText("");
        btnAdd1.setEnabled(true);
        btnEdit1.setEnabled(true);
        btnDelete1.setEnabled(true);
        btnAdd2.setEnabled(true);
        btnEdit2.setEnabled(true);
        btnDelete2.setEnabled(true);
        btnConfirm1.setEnabled(false);
        btnConfirm2.setEnabled(false);
        cmbRole.setSelectedIndex(0);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        resetCmbRole();
    }

    public void roleView() {
        table1.clearSelection();
        table2.clearSelection();
        table1.setEnabled(false);
        table2.setEnabled(false);
        txtID.setEditable(false);
        txtName.setEditable(false);
        lbID.setText("Mã");
        lbName.setText("Tên");
        txtID.setText("");
        txtName.setText("");
        btnAdd1.setEnabled(false);
        btnEdit1.setEnabled(false);
        btnDelete1.setEnabled(false);
        btnAdd2.setEnabled(false);
        btnEdit2.setEnabled(false);
        btnDelete2.setEnabled(false);
        btnConfirm1.setEnabled(false);
        btnConfirm2.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
        resetCmbRole();
    }

    public void resetCmbRole() {
        for (int i = 0; i < lenArr; i++) {
            checkbox[i].setSelected(false);
            checkbox[i].setEnabled(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == table1 && table1.isEnabled()) {
            table2.clearSelection();
            int row = table1.getSelectedRow();
            if (table1.getRowSorter() != null) {
                row = table1.getRowSorter().convertRowIndexToModel(row);
            }
            txtID.setText(table1.getModel().getValueAt(row, 0).toString());
            txtName.setText(table1.getModel().getValueAt(row, 1).toString());
            lbID.setText("Mã quyền");
            lbName.setText("Tên quyền");
        }

        if (e.getSource() == table2 && table2.isEnabled()) {
            table1.clearSelection();
            int row = table2.getSelectedRow();
            if (table2.getRowSorter() != null) {
                row = table2.getRowSorter().convertRowIndexToModel(row);
            }
            txtID.setText(table2.getModel().getValueAt(row, 0).toString());
            txtName.setText(table2.getModel().getValueAt(row, 1).toString());
            lbID.setText("Mã chức năng");
            lbName.setText("Tên chức năng");
        }

        if (e.getSource() == btnAdd1 && btnAdd1.isEnabled()) {
            AddOrEdit1 = true;
            table1.clearSelection();
            table1.setEnabled(false);
            table2.clearSelection();
            table2.setEnabled(false);
            txtID.setEditable(true);
            txtName.setEditable(true);
            btnConfirm1.setEnabled(true);
            btnConfirm1.setVisible(true);
            btnConfirm2.setVisible(false);
            txtID.requestFocus();
            txtID.setText("");
            txtName.setText("");
            lbID.setText("Mã quyền");
            lbName.setText("Tên quyền");
        }

        if (e.getSource() == btnEdit1 && btnEdit1.isEnabled()) {
            AddOrEdit1 = false;
            if (txtID.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền cần sửa !!!");
                return;
            }
            table1.setEnabled(false);
            table2.setEnabled(false);
            txtID.setEditable(false);
            txtName.setEditable(true);
            btnConfirm1.setEnabled(true);
            btnConfirm1.setVisible(true);
            btnConfirm2.setVisible(false);
            txtName.requestFocus();
        }

        if (e.getSource() == btnDelete1 && btnDelete1.isEnabled()) {
            if (txtID.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                roleBUS.delete(txtID.getText());
                table1.clearSelection();
                listRole();
                for (int i = roleBUS.getList().size()-1; i>0; i--) {
                    cmbRole.removeItemAt(i);
                }
                listcmbRole(cmbRole);
                cleanView();
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getSource() == btnAdd2 && btnAdd2.isEnabled()) {
            AddOrEdit2 = true;
            table1.clearSelection();
            table1.setEnabled(false);
            table2.clearSelection();
            table2.setEnabled(false);
            txtID.setEditable(true);
            txtName.setEditable(true);
            btnConfirm1.setVisible(false);
            btnConfirm2.setVisible(true);
            btnConfirm2.setEnabled(true);
            txtID.requestFocus();
            txtID.setText("");
            txtName.setText("");
            lbID.setText("Mã chức năng");
            lbName.setText("Tên chức năng");
        }

        if (e.getSource() == btnEdit2 && btnEdit2.isEnabled()) {
            AddOrEdit2 = false;
            if (txtID.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn chức năng cần sửa !!!");
                return;
            }
            table1.setEnabled(false);
            table2.setEnabled(false);
            txtID.setEditable(false);
            txtName.setEditable(true);
            btnConfirm1.setVisible(false);
            btnConfirm2.setVisible(true);
            btnConfirm2.setEnabled(true);
            txtName.requestFocus();
        }

        if (e.getSource() == btnDelete2 && btnDelete2.isEnabled()) {
            if (txtID.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn chức năng cần xóa !!!");
                return;
            }
            int messDelete = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (messDelete == 0) {
                funcBUS.delete(txtID.getText());
                table2.clearSelection();
                cleanView();
                listFunction();
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            panelChucNang.removeAll();
            showFunction();
            panelChucNang.repaint();
            panelChucNang.validate();
        }

        if (e.getSource() == btnConfirm1 && btnConfirm1.isEnabled()) {
            if (AddOrEdit1) {
                int messAdd = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messAdd == 0) {
                    String maQuyen = txtID.getText();
                    String tenQuyen = txtName.getText();

                    QuyenDTO quyen = new QuyenDTO(maQuyen, tenQuyen);
                    roleBUS.add(quyen);
                    listRole();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maQuyen = txtID.getText();
                    String tenQuyen = txtName.getText();

                    QuyenDTO quyen = new QuyenDTO(maQuyen, tenQuyen);
                    roleBUS.update(quyen);
                    listRole();
                    JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            for (int i = roleBUS.getList().size()-1; i>0; i--) {
                cmbRole.removeItemAt(i);
            }
            listcmbRole(cmbRole);
            cleanView();
        }

        if (e.getSource() == btnConfirm2 && btnConfirm2.isEnabled()) {
            if (AddOrEdit2) {
                int messAdd = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messAdd == 0) {
                    String maCN = txtID.getText();
                    String tenCN = txtName.getText();

                    ChucNangDTO chucnang = new ChucNangDTO(maCN, tenCN);
                    funcBUS.add(chucnang);
                    listFunction();
                    JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int messEdit = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (messEdit == 0) {
                    String maCN = txtID.getText();
                    String tenCN = txtName.getText();

                    ChucNangDTO chucnang = new ChucNangDTO(maCN, tenCN);
                    funcBUS.update(chucnang);
                    listFunction();
                    JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            cleanView();
            panelChucNang.removeAll();
            showFunction();
            panelChucNang.repaint();
            panelChucNang.validate();
        }

        if (e.getSource() == btnSave && btnSave.isEnabled()) {
            if (cmbRole.getSelectedIndex() != 0) {
                QuyenDTO quyen = (QuyenDTO) cmbRole.getSelectedItem();
                String maquyen = quyen.getMaQuyen();
                ctRoleBUS.delete(maquyen);
                for (int i = 0; i < lenArr; i++) {
                    if (checkbox[i].isSelected()) {
                        ArrayList<ChucNangDTO> function = funcBUS.getList();
                        String macn = function.get(i).getMaCN();
                        ChiTietQuyenDTO ct = new ChiTietQuyenDTO(maquyen, macn);
                        ctRoleBUS.add(ct);
                    }
                }
                JOptionPane.showMessageDialog(null, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                cleanView();
            }
        }

        if (e.getSource() == btnCancel && btnCancel.isEnabled()) {
            int messCancel = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy bỏ ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (messCancel == 0) {
                cleanView();
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmbRole) {
            if (cmbRole.getSelectedIndex() != 0) {
                QuyenDTO quyen = (QuyenDTO) cmbRole.getSelectedItem();
                String maquyen = quyen.getMaQuyen();
                ArrayList<ChiTietQuyenDTO> ctq = ctRoleBUS.getList();
                ArrayList<ChucNangDTO> function = funcBUS.getList();
                roleView();
                for (ChiTietQuyenDTO ct : ctq) {
                    if (ct.getMaQuyen().equals(maquyen)) {
                        for (int i = 0; i < lenArr; i++) {
                            checkbox[i].setEnabled(true);
                            if (ct.getMaCN().equals(function.get(i).getMaCN())) {
                                checkbox[i].setSelected(true);
                            }
                        }
                    }
                }
            } else resetCmbRole();
        }
    }
}