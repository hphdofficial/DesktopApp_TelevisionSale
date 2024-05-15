package GUI.Component;

import GUI.Event.EventMenuSelected;
import GUI.Model.ModelMenu;

import java.awt.*;

public class GradientMenu extends javax.swing.JPanel {

    private EventMenuSelected event;
    private javax.swing.JLabel jLabel1;
    private GUI.Swing.ListMenu<String> listMenu1;

    public GradientMenu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        init();
    }

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    private void init() {
        listMenu1.addItem(new ModelMenu("1", "Dashboard", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("2", "UI Elements", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("3", "Comonents", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("4", "Forms Stuff", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("5", "Date Table", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("", " ", ModelMenu.MenuType.EMPTY));

        listMenu1.addItem(new ModelMenu("", "My Data", ModelMenu.MenuType.TITLE));
        listMenu1.addItem(new ModelMenu("", " ", ModelMenu.MenuType.EMPTY));
        listMenu1.addItem(new ModelMenu("6", "Icons", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("7", "Sample Page", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("8", "Extra", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("9", "More", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("10", "Logout", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("", "", ModelMenu.MenuType.EMPTY));
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new GUI.Swing.ListMenu<>();

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HAWKSTAR");

        listMenu1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(46, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(42, 42, 42))
                        .addComponent(listMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#4b6cb7"), 0, getHeight(), Color.decode("#182848"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        g2.fillRect(0, -20, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }
}