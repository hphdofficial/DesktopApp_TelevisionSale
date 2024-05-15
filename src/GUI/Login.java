package GUI;

import BUS.NhanVienBUS;
import BUS.UserBUS;
import DTO.NhanVienDTO;
import DTO.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener, MouseListener, KeyListener {
    private final UserBUS userBUS = new UserBUS();
    private final NhanVienBUS staffBUS = new NhanVienBUS();

    private ManagerTV managerTV;
    private JLabel JExit;
    private JPasswordField JPassword;
    private JButton btnSignUp;
    private JTextField txtAccount;

    public Login() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerTV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        init();
    }

    public void init() {
        setLayout(null);
        setBackground(null);
        setTitle("Login");
        ImageIcon logo = new ImageIcon("./img/iconTV.png");
        setIconImage(logo.getImage());

        JLabel lbTitle = new JLabel();
        lbTitle.setFont(new Font("Tw Cen MT", 1, 24));
        lbTitle.setForeground(new Color(255, 255, 255));
        lbTitle.setText("TV Manager");

        JLabel jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Tw Cen MT", 1, 24));
        jLabel1.setForeground(new Color(102, 153, 255));
        jLabel1.setText("HAWKSTAR SOFTWARE");

        JLabel jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Tw Cen MT", 2, 18));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("We are pleased to serve our");

        JLabel jLabel3 = new JLabel();
        jLabel3.setFont(new Font("Tw Cen MT", 2, 18));
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("customers anytime, anywhere");

        JLabel lbIMG = new JLabel();
        lbIMG.setIcon(new ImageIcon("./img/grap.png"));
        lbIMG.setText("lbIMG");

        JLabel lbSignUp = new JLabel();
        lbSignUp.setFont(new Font("Tw Cen MT", 1, 24));
        lbSignUp.setForeground(new Color(255, 204, 51));
        lbSignUp.setText("SIGN UP");

        JLabel lbAccount = new JLabel();
        lbAccount.setFont(new Font("Montserrat", 1, 12));
        lbAccount.setForeground(new Color(255, 153, 51));
        lbAccount.setText("Account");

        JLabel lbPass = new JLabel();
        lbPass.setFont(new Font("Montserrat", 1, 12));
        lbPass.setForeground(new Color(255, 153, 51));
        lbPass.setText("Password");

        txtAccount = new JTextField();
        txtAccount.setFont(new Font("Quicksand Medium", 0, 14));
        txtAccount.setText("admin");
        txtAccount.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 153, 51)));
        txtAccount.addMouseListener(this);
        txtAccount.addKeyListener(this);

        JPassword = new JPasswordField();
        JPassword.setFont(new Font("Quicksand Medium", 0, 14));
        JPassword.setText("admin");
        JPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 153, 51)));
        JPassword.addMouseListener(this);
        JPassword.addKeyListener(this);

        btnSignUp = new JButton();
        btnSignUp.setFont(new Font("Tw Cen MT", 1, 18));
        btnSignUp.setBackground(new Color(255, 153, 51));
        btnSignUp.setForeground(new Color(255, 255, 255));
        btnSignUp.setText("SIGN UP");
        btnSignUp.setBorder(null);
        btnSignUp.setFocusPainted(false);
        btnSignUp.addActionListener(this);

        JExit = new JLabel();
        JExit.setIcon(new ImageIcon("./img/exit_25px.png"));
        JExit.addMouseListener(this);

        GUI.Component.GradientMenu gradientMenu = new GUI.Component.GradientMenu();

        GroupLayout gradientMenuLayout = new GroupLayout(gradientMenu);
        gradientMenu.setLayout(gradientMenuLayout);

        gradientMenuLayout.setHorizontalGroup(
                gradientMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                .addGroup(gradientMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                                .addGap(0, 0, 50)
                                                .addComponent(jLabel1))
                                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                                .addGap(0, 0, 50)
                                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                                .addGap(0, 0, 50)
                                                .addComponent(jLabel3))
                                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lbIMG, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(34, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, gradientMenuLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lbTitle)
                                .addGap(132, 132, 132))
        );

        gradientMenuLayout.setVerticalGroup(
                gradientMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gradientMenuLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(lbTitle, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbIMG, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        GUI.Swing.GradientsBorder gradientsBorder = new GUI.Swing.GradientsBorder();
        gradientsBorder.setBackground(Color.white);

        GroupLayout gradientsBorderLayout = new GroupLayout(gradientsBorder);
        gradientsBorder.setLayout(gradientsBorderLayout);

        gradientsBorderLayout.setHorizontalGroup(
                gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gradientsBorderLayout.createSequentialGroup()
                                .addComponent(gradientMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, gradientsBorderLayout.createSequentialGroup()
                                                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50))
                                        .addGroup(GroupLayout.Alignment.TRAILING, gradientsBorderLayout.createSequentialGroup()
                                                .addComponent(lbSignUp)
                                                .addGap(140, 140, 140))
                                        .addComponent(JExit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, gradientsBorderLayout.createSequentialGroup()
                                                .addGroup(gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(gradientsBorderLayout.createSequentialGroup()
                                                                .addComponent(lbPass, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(JPassword, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gradientsBorderLayout.createSequentialGroup()
                                                                .addComponent(lbAccount)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(txtAccount, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(51, 51, 51))))
        );
        gradientsBorderLayout.setVerticalGroup(
                gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(gradientMenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(gradientsBorderLayout.createSequentialGroup()
                                .addComponent(JExit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(lbSignUp)
                                .addGap(38, 38, 38)
                                .addGroup(gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbAccount)
                                        .addComponent(txtAccount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(gradientsBorderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(JPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbPass))
                                .addGap(50, 50, 50)
                                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(gradientsBorder, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(gradientsBorder, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (userBUS.getList() == null)
            userBUS.list();
        String username = txtAccount.getText();
        char[] password = JPassword.getPassword();
        UserDTO user = userBUS.check(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(null, "Incorrect username or password");
            return;
        }
        if (staffBUS.getList() == null) staffBUS.list();
        NhanVienDTO staff = new NhanVienDTO();
        staff = staffBUS.get(user.getUserID());

        managerTV = new ManagerTV(staff.getMaNV(), staff.getHoNV().concat(" " + staff.getTenNV()), user.getRoleID());
        dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == JExit) {
            System.exit(0);
        }

        if (e.getSource() == txtAccount) {
            txtAccount.setText("");
        }

        if (e.getSource() == JPassword) {
            JPassword.setText("");
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object a = e.getSource();
        if (a.equals(txtAccount) || a.equals(JPassword)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                btnSignUp.doClick();
            }
        }
        if (a.equals(txtAccount)) {
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                JPassword.setText("");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}