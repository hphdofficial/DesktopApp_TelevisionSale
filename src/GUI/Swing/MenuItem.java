package GUI.Swing;

import GUI.Model.ModelMenu;

import javax.swing.*;
import java.awt.*;

public class MenuItem extends JPanel {

    private boolean over;

    private boolean selected;
    private JLabel lbIcon;
    private JLabel lbName;

    // ModelMenu listData -> Gọi đối tượng
    public MenuItem(ModelMenu listData) {
        initComponents();
        setOpaque(false);

        if (listData.getType() == ModelMenu.MenuType.MENU) {
            lbIcon.setIcon(listData.toIcon());
            lbName.setText(listData.getName());
        } else if (listData.getType() == ModelMenu.MenuType.TITLE) {
            lbIcon.setText(listData.getName());
            lbIcon.setFont(new Font("sansserif", 1, 12));
            lbName.setVisible(false);
        } else {
            lbName.setText(" ");
            lbName.setForeground(Color.WHITE);
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void setOver(boolean over) {
        this.over = over;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (selected || over) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (selected) {
                g2.setColor(new Color(255, 255, 255, 80));
            } else {
                g2.setColor(new Color(255, 255, 255, 20));
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        }
    }

    private void initComponents() {

        lbIcon = new JLabel();
        lbName = new JLabel();

        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setText("Menu Name");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lbIcon, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbName, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lbIcon, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbName, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );
    }
}