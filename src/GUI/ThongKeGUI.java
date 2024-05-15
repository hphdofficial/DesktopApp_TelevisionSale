package GUI;

import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import BUS.ThongKeBUS;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import DTO.ThongKeDTO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class ThongKeGUI extends JPanel implements ActionListener, ItemListener, KeyListener, ChangeListener {
    private final int Default_Width;
    private final int Default_Height;
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private final NhanVienBUS nvBUS = new NhanVienBUS();
    private final KhachHangBUS khBUS = new KhachHangBUS();
    private final ThongKeBUS tkBUS = new ThongKeBUS();
    private final JLabel lbMa = new JLabel();
    private final JTextField txtMa = new JTextField();
    private final JComboBox<String> cmbFromDate = new JComboBox<>();
    private final JComboBox<String> cmbFromMonth = new JComboBox<>();
    private final JComboBox<String> cmbFromYear = new JComboBox<>();
    private final JComboBox<String> cmbToDate = new JComboBox<>();
    private final JComboBox<String> cmbToMonth = new JComboBox<>();
    private final JComboBox<String> cmbToYear = new JComboBox<>();
    private final JComboBox<String> cmbTrimester = new JComboBox<>();
    private final JComboBox<String> cmbPeriod = new JComboBox<>();
    private final JComboBox<String> cmbYearTrimester = new JComboBox<>();
    private final JComboBox<String> cmbYearPeriod = new JComboBox<>();
    private JPanel panelTime = new JPanel();
    private JPanel panelTrimester = new JPanel();
    private JPanel panelPeriod = new JPanel();
    private JPanel form;
    private JLabel lbFromDate;
    private JLabel lbToDate;
    private JRadioButton chooseMaSP, chooseMaNV, chooseMaKH, chooseDate, chooseTrimester, choosePeriod;
    private JTextArea viewThongKe;
    private JButton btnThongKe;
    private JLabel lbTrimester;
    private JLabel lbPeriod;
    private JLabel lbYearPeriod;
    private JLabel lbYearTrimester;
    private JButton btnList;
    private JButton btnOnOff;
    private JPanel toggle;
    private boolean OnOff = true; // TRUE is ALL || FALSE is TOP
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollViewALL;
    private JScrollPane scrollViewTOP;

    JLabel lblThongKeThucDon, lblThongKeKhachHang, lblThongKeNhanVien, lblThongKeDoanhThu;

    public ThongKeGUI(int width, int height) {
        spBUS.list();
        nvBUS.list();
        khBUS.list();
        Default_Width = width;
        Default_Height = height;
        init();
    }

    public void init() {
        setLayout(null);
        setBackground(new Color(205, 205, 255));
        setSize(Default_Width, Default_Height);

        Font font0 = new Font("Segoe UI", Font.PLAIN, 13);
        Font font1 = new Font("Segoe UI", Font.BOLD, 13);

        /******************** PANEL CHỌN LOẠI MÃ & CHỌN KIỂU THỜI GIAN ********************/

        JPanel control = new JPanel(null);
        control.setBackground(null);
        control.setBounds(new Rectangle(0, 180, Default_Width / 2, Default_Height-200));
        add(control);

        // Chuyển đổi ALL và TOP
        toggle = new JPanel(null);
        toggle.setBackground(Color.GRAY);
        toggle.setBounds(new Rectangle(Default_Width / 4 - 60, 20, 120, 30));
        control.add(toggle);

        btnOnOff = new JButton("ALL");
        btnOnOff.setBounds(new Rectangle(0, 0, 60, 30));
        btnOnOff.addActionListener(this);
        toggle.add(btnOnOff);

        JPanel controlAll = new JPanel(new GridLayout(2, 0));
        controlAll.setBackground(null);
        controlAll.setBounds(new Rectangle(20, 70, Default_Width / 2 - 20, 150));
//        controlAll.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        control.add(controlAll);

        // Chọn loại mã
        ButtonGroup id = new ButtonGroup();
        chooseMaSP = new JRadioButton("Sản phẩm");
        chooseMaSP.setFont(font0);
        chooseMaSP.setSelected(true);
        chooseMaSP.addItemListener(this);
        id.add(chooseMaSP);
        chooseMaNV = new JRadioButton("Nhân viên");
        chooseMaNV.setFont(font0);
        chooseMaNV.addItemListener(this);
        id.add(chooseMaNV);
        chooseMaKH = new JRadioButton("Khách hàng");
        chooseMaKH.setFont(font0);
        chooseMaKH.addItemListener(this);
        id.add(chooseMaKH);

        // Chọn kiểu thời gian
        ButtonGroup time = new ButtonGroup();
        chooseDate = new JRadioButton("DD/MM/YYY");
        chooseDate.setFont(font0);
        chooseDate.addItemListener(this);
        chooseDate.setSelected(true);
        time.add(chooseDate);
        chooseTrimester = new JRadioButton("Quý (3 tháng)");
        chooseTrimester.setFont(font0);
        chooseTrimester.addItemListener(this);
        time.add(chooseTrimester);
        choosePeriod = new JRadioButton("Kỳ (4 tháng)");
        choosePeriod.setFont(font0);
        choosePeriod.addItemListener(this);
        time.add(choosePeriod);

        JLabel lbId = new JLabel("   Chọn loại mã");
        lbId.setFont(font1);
        controlAll.add(lbId);
        controlAll.add(chooseMaSP);
        controlAll.add(chooseMaNV);
        controlAll.add(chooseMaKH);

        JLabel lbTime = new JLabel("   Chọn kiểu thời gian");
        lbTime.setFont(font1);
        controlAll.add(lbTime);
        controlAll.add(chooseDate);
        controlAll.add(chooseTrimester);
        controlAll.add(choosePeriod);

        /******************** PANEL THÔNG TIN THEO TỪNG LOẠI MÃ ********************/

        form = new JPanel(null);
        form.setBackground(null);
        form.setBounds(new Rectangle(20, 230, Default_Width / 2 - 20, 300));
        control.add(form);

        lbMa.setBounds(new Rectangle(20, 0, 100, 30));
        lbMa.setFont(font0);
        txtMa.setBounds(new Rectangle(120, 0, 300, 30));
        txtMa.setFont(font0);
        txtMa.addKeyListener(this);

        btnList = new JButton("...");
        btnList.setBounds(new Rectangle(420, 0, 30, 30));
        btnList.addActionListener(this);

        form.add(lbMa);
        form.add(txtMa);
        form.add(btnList);

        /******************** CHỌN THEO NGÀY ********************/

        panelTime = new JPanel(null);
        panelTime.setBackground(null);
        panelTime.setBounds(new Rectangle(20, 40, Default_Width / 2 - 20, 80));

        // FROM
        lbFromDate = new JLabel("Thời gian từ");
        lbFromDate.setFont(font0);
        lbFromDate.setBounds(new Rectangle(0, 0, 100, 30));

        cmbFromDate.setBounds(new Rectangle(100, 0, 100, 30));
        cmbFromDate.setFont(font0);
        listDate(cmbFromDate, true);
        JLabel sepTime0 = new JLabel("/");
        sepTime0.setFont(font0);
        sepTime0.setBounds(new Rectangle(205, 0, 10, 30));

        cmbFromMonth.addActionListener(this);
        cmbFromMonth.setBounds(new Rectangle(215, 0, 100, 30));
        cmbFromMonth.setFont(font0);
        listMonth(cmbFromMonth);
        JLabel sepTime1 = new JLabel("/");
        sepTime1.setFont(font0);
        sepTime1.setBounds(new Rectangle(320, 0, 10, 30));

        cmbFromYear.addActionListener(this);
        cmbFromYear.setBounds(new Rectangle(330, 0, 100, 30));
        cmbFromYear.setFont(font0);
        listYear(cmbFromYear);

        // TO
        lbToDate = new JLabel("Thời gian đến");
        lbToDate.setFont(font0);
        lbToDate.setBounds(new Rectangle(0, 40, 100, 30));

        cmbToDate.setBounds(new Rectangle(100, 40, 100, 30));
        cmbToDate.setFont(font0);
        listDate(cmbToDate, false);
        JLabel sepTime2 = new JLabel("/");
        sepTime2.setFont(font0);
        sepTime2.setBounds(new Rectangle(205, 40, 10, 30));

        cmbToMonth.addActionListener(this);
        cmbToMonth.setBounds(new Rectangle(215, 40, 100, 30));
        cmbToMonth.setFont(font0);
        listMonth(cmbToMonth);
        JLabel sepTime3 = new JLabel("/");
        sepTime3.setFont(font0);
        sepTime3.setBounds(new Rectangle(320, 40, 10, 30));

        cmbToYear.addActionListener(this);
        cmbToYear.setBounds(new Rectangle(330, 40, 100, 30));
        cmbToYear.setFont(font0);
        listYear(cmbToYear);

        panelTime.add(lbFromDate);
        panelTime.add(cmbFromDate);
        panelTime.add(sepTime0);
        panelTime.add(cmbFromMonth);
        panelTime.add(sepTime1);
        panelTime.add(cmbFromYear);

        panelTime.add(lbToDate);
        panelTime.add(cmbToDate);
        panelTime.add(sepTime2);
        panelTime.add(cmbToMonth);
        panelTime.add(sepTime3);
        panelTime.add(cmbToYear);

        form.add(panelTime);

        /******************** CHỌN THEO QUÝ ********************/

        panelTrimester = new JPanel(null);
        panelTrimester.setBackground(null);
        panelTrimester.setBounds(new Rectangle(20, 40, Default_Width / 2, 80));

        lbTrimester = new JLabel("Quý");
        lbTrimester.setFont(font0);
        lbTrimester.setBounds(new Rectangle(0, 0, 100, 30));

        cmbTrimester.setBounds(new Rectangle(120, 0, 160, 30));
        cmbTrimester.setFont(font0);
        for (int i = 1; i <= 12; i += 3) {
            cmbTrimester.addItem("Quý " + (i + 2) / 3 + " (tháng " + i + " - " + (i + 2) + ")");
        }

        lbYearTrimester = new JLabel("Năm", JLabel.CENTER);
        lbYearTrimester.setFont(font0);
        lbYearTrimester.setBounds(new Rectangle(270, 0, 40, 30));

        cmbYearTrimester.setBounds(new Rectangle(310, 0, 80, 30));
        cmbYearTrimester.setFont(font0);
        listYear(cmbYearTrimester);
        cmbYearTrimester.removeItemAt(0);

        panelTrimester.add(lbTrimester);
        panelTrimester.add(cmbTrimester);
        panelTrimester.add(lbYearTrimester);
        panelTrimester.add(cmbYearTrimester);

        panelTrimester.setVisible(false);

        form.add(panelTrimester);

        /******************** CHỌN THEO KỲ ********************/

        panelPeriod = new JPanel(null);
        panelPeriod.setBackground(null);
        panelPeriod.setBounds(new Rectangle(0, 40, Default_Width / 2, 80));

        lbPeriod = new JLabel("Kỳ");
        lbPeriod.setFont(font0);
        lbPeriod.setBounds(new Rectangle(20, 0, 100, 30));

        cmbPeriod.setBounds(new Rectangle(120, 0, 160, 30));
        cmbPeriod.setFont(font0);
        for (int i = 1; i <= 12; i += 4) {
            cmbPeriod.addItem("Kỳ " + (i + 3) / 4 + " (tháng " + i + " - " + (i + 3) + ")");
        }

        lbYearPeriod = new JLabel("Năm", JLabel.CENTER);
        lbYearPeriod.setFont(font0);
        lbYearPeriod.setBounds(new Rectangle(270, 0, 40, 30));

        cmbYearPeriod.setBounds(new Rectangle(310, 0, 80, 30));
        cmbYearPeriod.setFont(font0);
        listYear(cmbYearPeriod);
        cmbYearPeriod.removeItemAt(0);

        panelPeriod.add(lbPeriod);
        panelPeriod.add(cmbPeriod);
        panelPeriod.add(lbYearPeriod);
        panelPeriod.add(cmbYearPeriod);

        panelPeriod.setVisible(false);
        form.add(panelPeriod);

        /******************** BUTTON THỐNG KÊ ********************/

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setIcon(new ImageIcon("./img/statistics-icon.png"));
        btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnThongKe.setBounds(new Rectangle(20, 140, 520, 50));
        btnThongKe.addActionListener(this);

        form.add(btnThongKe);

        /******************** VIEW THỐNG KÊ DOANH THU & TOP ********************/

        // Thống kê doanh thu
        viewThongKe = new JTextArea();
        viewThongKe.setEditable(false);

        scrollViewALL = new JScrollPane(viewThongKe);
        scrollViewALL.setBounds(new Rectangle(Default_Width / 2 + 20, 190, Default_Width / 2 - 50, Default_Height - 210));
        scrollViewALL.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        add(scrollViewALL);

        // Thống kê top
        Vector header = new Vector();
        header.add("STT");
        header.add("Mã sản phẩm");
        header.add("Tên sản phẩm");
        header.add("Số lượng bán");

        model = new DefaultTableModel(header, 0);
        table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(140);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

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

        scrollViewTOP = new JScrollPane(table);
        scrollViewTOP.setBounds(new Rectangle(Default_Width / 2 + 20, 190, Default_Width / 2 - 50, Default_Height - 210));
        scrollViewTOP.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(140, 140, 255)));
        scrollViewTOP.setVisible(false);
        add(scrollViewTOP);

        /**************************************************************************************************************/

        JLabel lbBgSP = new JLabel(new ImageIcon("./img/thongkesp.png"));
        JLabel lbBgKH = new JLabel(new ImageIcon("./img/thongkekh.png"));
        JLabel lbBgNV = new JLabel(new ImageIcon("./img/thongkenv.png"));
        JLabel lbBgNCC = new JLabel(new ImageIcon("./img/thongkencc.png"));

        lbBgSP.setBounds(20, 20, 265, 144);
        lbBgKH.setBounds(40+265, 20, 265, 144);
        lbBgNV.setBounds(60+265*2, 20, 265, 144);
        lbBgNCC.setBounds(80+265*3, 20, 265, 144);

        add(lbBgSP);
        add(lbBgKH);
        add(lbBgNV);
        add(lbBgNCC);

        lblThongKeThucDon = new JLabel("", JLabel.CENTER);
        lblThongKeKhachHang = new JLabel("", JLabel.CENTER);
        lblThongKeNhanVien = new JLabel("", JLabel.CENTER);
        lblThongKeDoanhThu = new JLabel("", JLabel.CENTER);

        Font font = new Font("Tahoma", Font.BOLD, 50);
        lblThongKeThucDon.setFont(font);
        lblThongKeKhachHang.setFont(font);
        lblThongKeNhanVien.setFont(font);
        lblThongKeDoanhThu.setFont(font);

        lblThongKeThucDon.setForeground(Color.white);
        lblThongKeKhachHang.setForeground(Color.white);
        lblThongKeNhanVien.setForeground(Color.white);
        lblThongKeDoanhThu.setForeground(Color.white);

        lblThongKeThucDon.setBounds(30, 30, 50, 50);
        lblThongKeKhachHang.setBounds(30, 30, 50, 50);
        lblThongKeNhanVien.setBounds(30, 30, 50, 50);
        lblThongKeDoanhThu.setBounds(30, 30, 50, 50);

        lbBgSP.add(lblThongKeThucDon);
        lbBgKH.add(lblThongKeKhachHang);
        lbBgNV.add(lblThongKeNhanVien);
        lbBgNCC.add(lblThongKeDoanhThu);

        hienThiThongKe();
    }

    private void hienThiThongKe() {
        NumberFormat num = NumberFormat.getInstance();
        ThongKeDTO thongKe = tkBUS.ThongKe();
        lblThongKeThucDon.setText(num.format(thongKe.getSoLuongSP()));
        lblThongKeKhachHang.setText(num.format(thongKe.getSoLuongKH()));
        lblThongKeNhanVien.setText(num.format(thongKe.getSoLuongNV()));
        lblThongKeDoanhThu.setText(num.format(thongKe.getSoLuongNCC()));
    }

    public void listDate(JComboBox cmb, boolean flag) { // TRUE is FROM - FALSE is TO
        cmb.addItem("Ngày");
        int thisDate = 31, thisMonth = 12, thisYear = Calendar.getInstance().get(Calendar.YEAR);
        if (cmbFromYear.getSelectedIndex() > 0 || cmbToYear.getSelectedIndex() > 0) {
            thisYear = flag ? Integer.parseInt(cmbFromYear.getSelectedItem().toString()) : Integer.parseInt(cmbToYear.getSelectedItem().toString());
        }
        if (cmbFromMonth.getSelectedIndex() > 0 || cmbToMonth.getSelectedIndex() > 0) {
            thisMonth = flag ? cmbFromMonth.getSelectedIndex() : cmbToMonth.getSelectedIndex();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(thisYear, thisMonth - 1, 1);
        thisDate = calendar.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= thisDate; i++) {
            cmb.addItem(i);
        }
    }

    public void listMonth(JComboBox cmb) {
        cmb.addItem("Tháng");
        for (int i = 1; i <= 12; i++) {
            cmb.addItem(i);
        }
    }

    public void listYear(JComboBox cmb) {
        cmb.addItem("Năm");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear - 22; i--) {
            cmb.addItem(i);
        }
    }

    public void btnStaticticAction(ActionEvent e) {
        ThongKeBUS tk = new ThongKeBUS();
        String ma = txtMa.getText();
        Object obj = null;
        if (chooseMaSP.isSelected()) {
            if (OnOff) {
                obj = new SanPhamDTO();
                obj = spBUS.get(ma);
                if (obj == null) {
                    JOptionPane.showMessageDialog(null, "Không tồn tại sản phẩm !!");
                    return;
                }
            }
        } else if (chooseMaNV.isSelected()) {
            if (OnOff) {
                obj = new NhanVienDTO();
                obj = nvBUS.get(ma);
                if (obj == null) {
                    JOptionPane.showMessageDialog(null, "Không tồn tại nhân viên !!");
                    return;
                }
            }
        } else if (chooseMaKH.isSelected()) {
            if (OnOff) {
                obj = new KhachHangDTO();
                obj = khBUS.get(ma);
                if (obj == null) {
                    JOptionPane.showMessageDialog(null, "Không tồn tại khách hàng !!");
                    return;
                }
            }
        }

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        // Thống kê theo ngày
        if (chooseDate.isSelected()) {
            int fYear = cmbFromYear.getSelectedIndex() > 0 ? Integer.parseInt(cmbFromYear.getSelectedItem().toString()) : 2000;
            int fMonth = cmbFromMonth.getSelectedIndex() > 0 ? cmbFromMonth.getSelectedIndex() - 1 : 0;
            int fDate = cmbFromDate.getSelectedIndex() > 0 ? Integer.parseInt(cmbFromDate.getSelectedItem().toString()) : 1;
            from.set(fYear, fMonth, fDate, 0, 0, 0);

            int tYear = cmbToYear.getSelectedIndex() > 0 ? Integer.parseInt(cmbToYear.getSelectedItem().toString()) : Calendar.getInstance().get(Calendar.YEAR);
            int tMonth = cmbToMonth.getSelectedIndex() > 0 ? cmbToMonth.getSelectedIndex() - 1 : 11;
            int maxDate = cmbToDate.getItemCount();
            int tDate = cmbToDate.getSelectedIndex() > 0 ? Integer.parseInt(cmbToDate.getSelectedItem().toString()) : maxDate - 1;
            to.set(tYear, tMonth, tDate, 23, 0, 0);
        }
        // Thống kê theo quý
        else if (chooseTrimester.isSelected()) {
            int year = Integer.parseInt(cmbYearTrimester.getSelectedItem().toString());
            int fMonth = (cmbTrimester.getSelectedIndex() + 1) * 3 - 2;
            int tMonth = fMonth + 2;

            from.set(year, fMonth - 1, 1, 0, 0, 0);
            to.set(year, tMonth - 1, 1, 23, 0, 0);
            int dateOfMonth = to.getActualMaximum(Calendar.DAY_OF_MONTH);
            to.set(Calendar.DATE, dateOfMonth);
        }
        // Thống kê theo kỳ
        else if (choosePeriod.isSelected()) {
            int year = Integer.parseInt(cmbYearPeriod.getSelectedItem().toString());
            int fMonth = (cmbPeriod.getSelectedIndex() + 1) * 4 - 3;
            int tMonth = fMonth + 3;

            from.set(year, fMonth - 1, 1, 0, 0, 0);
            to.set(year, tMonth - 1, 1, 23, 0, 0);
            int dateOfMonth = to.getActualMaximum(Calendar.DAY_OF_MONTH);
            to.set(Calendar.DATE, dateOfMonth);
        }

        if (to.before(from)) {
            JOptionPane.showMessageDialog(null, "Lỗi");
            return;
        }

        if (OnOff) {
            String result = "";
            if (chooseMaSP.isSelected()) {
                result = tk.ThongKeSP(ma, from, to);
            } else if (chooseMaNV.isSelected()) {
                result = tk.ThongKeNV(ma, from, to);
            } else if (chooseMaKH.isSelected()) {
                result = tk.ThongKeKH(ma, from, to);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");

            viewThongKe.setText(outThongKe(obj, sdf.format(from.getTime()), sdf.format(to.getTime()), result));
        } else {
            if (chooseMaSP.isSelected()) {
                Vector header = new Vector();
                header.add("STT");
                header.add("Mã sản phẩm");
                header.add("Tên sản phẩm");
                header.add("Số lượng bán");
                model = new DefaultTableModel(header, 0);

                outThongKe(tk.ThongKeTopSP(from, to));
            } else if (chooseMaNV.isSelected()) {
                Vector header = new Vector();
                header.add("STT");
                header.add("Mã nhân viên");
                header.add("Họ và tên");
                header.add("Tổng tiền (VNĐ)");
                model = new DefaultTableModel(header, 0);

                outThongKe(tk.ThongKeTopNV(from, to));
            } else if (chooseMaKH.isSelected()) {
                Vector header = new Vector();
                header.add("STT");
                header.add("Mã khách hàng");
                header.add("Họ và tên");
                header.add("Tổng tiền (VNĐ)");
                model = new DefaultTableModel(header, 0);

                outThongKe(tk.ThongKeTopKH(from, to));
            }
        }
    }

    public String outThongKe(Object obj, String fromDate, String toDate, String result) {
        String s = "Từ ngày : " + fromDate + "\n";
        s += "Đến ngày : " + toDate + "\n";
        s += "-----------------------------------------------------------------\n";
        if (chooseMaSP.isSelected()) {
            SanPhamDTO sp = (SanPhamDTO) obj;
            s += "Mã sản phẩm : " + sp.getMaSP() + "\n";
            s += "Tên sản phẩm : " + sp.getTenSP() + "\n";
            s += result;
        } else if (chooseMaNV.isSelected()) {
            NhanVienDTO nv = (NhanVienDTO) obj;
            s += "Mã nhân viên : " + nv.getMaNV() + "\n";
            s += "Họ và tên : " + nv.getHoNV().concat(" " + nv.getTenNV()) + "\n";
            s += "Tuổi : " + (Calendar.getInstance().get(Calendar.YEAR) - nv.getNamSinh()) + "\n";
            s += "Giới tính : " + nv.getGioiTinh() + "\n";
            s += result;
        } else if (chooseMaKH.isSelected()) {
            KhachHangDTO kh = (KhachHangDTO) obj;
            s += "Mã khách hàng : " + kh.getMaKH() + "\n";
            s += "Họ và tên : " + kh.getHoKH().concat(" " + kh.getTenKH()) + "\n";
            s += "Điện thoại : " + kh.getDienThoai() + "\n";
            s += "Địa chỉ : " + kh.getDiaChi() + "\n";
            s += result;
        }
        return s;
    }

    public void outThongKe(ArrayList<String> sp) {
        model.setRowCount(0);
        for (int i = 0; i < sp.size(); i++) {
            String[] s = sp.get(i).split("_");
            String maSP = s[0].trim();
            String tenSP = s[1].trim();
            String sl = s[2].trim();

            Vector data = new Vector();
            data.add(i + 1);
            data.add(maSP);
            data.add(tenSP);
            data.add(sl);
            model.addRow(data);
        }
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btnOnOff)) {
            Color color = Color.green;
            int changeX = 60;
            String text = "TOP";
            if (!OnOff) {
                changeX = 0;
                text = "ALL";
                color = Color.GRAY;
            }
            btnOnOff.setText(text);
            btnOnOff.setBounds(new Rectangle(changeX, 0, 60, 30));
            toggle.setBackground(color);
            toggle.add(btnOnOff);

            lbMa.setVisible(!OnOff);
            txtMa.setVisible(!OnOff);
            btnList.setVisible(!OnOff);

            scrollViewALL.setVisible(!OnOff);
            scrollViewTOP.setVisible(OnOff);

            OnOff = !OnOff;
            repaint();
            revalidate();
        }

        if (obj.equals(cmbFromMonth) || obj.equals(cmbFromYear)) {
            cmbFromDate.removeAllItems();
            listDate(cmbFromDate, true);
        }

        if (obj.equals(cmbToMonth) || obj.equals(cmbToYear)) {
            cmbToDate.removeAllItems();
            listDate(cmbToDate, false);
        }

        if (obj.equals(btnList)) {
            if (chooseMaSP.isSelected()) {
                ListSanPham sp = new ListSanPham(1150, 750, txtMa.getText());
                String s = sp.getInfoSP();
                txtMa.setText(s.split("%")[0]);
            } else if (chooseMaNV.isSelected()) {
                ListNhanVien sp = new ListNhanVien(1150, 750);
                String s = sp.getInfoNV();
                txtMa.setText(s);
            } else if (chooseMaKH.isSelected()) {
                ListKhachHang sp = new ListKhachHang(1150, 750);
                String s = sp.getInfoKH();
                txtMa.setText(s);
            }
        }

        if (obj.equals(btnThongKe)) {
            btnStaticticAction(e);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Chọn mã
        if (chooseMaSP.isSelected() && OnOff) {
            lbMa.setVisible(true);
            txtMa.setVisible(true);
            lbMa.setText("Mã sản phẩm");
        } else if (chooseMaNV.isSelected() && OnOff) {
            lbMa.setVisible(true);
            txtMa.setVisible(true);
            lbMa.setText("Mã nhân viên");
        } else if (chooseMaKH.isSelected() && OnOff) {
            lbMa.setVisible(true);
            txtMa.setVisible(true);
            lbMa.setText("Mã khách hàng");
        } else {
            lbMa.setVisible(false);
            txtMa.setVisible(false);
        }
        // Chọn thời gian
        if (chooseDate.isSelected()) {
            panelTime.setVisible(true);
            panelTrimester.setVisible(false);
            panelPeriod.setVisible(false);
        } else if (chooseTrimester.isSelected()) {
            panelTime.setVisible(false);
            panelTrimester.setVisible(true);
            panelPeriod.setVisible(false);
        } else {
            panelTime.setVisible(false);
            panelTrimester.setVisible(false);
            panelPeriod.setVisible(true);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        txtMa.setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            btnThongKe.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}