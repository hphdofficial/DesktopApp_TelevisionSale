package GUI.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class navItem extends JPanel implements MouseListener {
    private final JLabel lb;
    private final JLabel icon;
    private Color hover = new Color(100, 100, 255);
    private Color normal = new Color(0, 0, 100);
    private boolean active;
    private String name, img, imgActive, imgHover;
    private Rectangle rec = new Rectangle();

    public navItem(String s, Rectangle r, String img, String imgActive) {
        this.name = s;
        this.lb = new JLabel(name);
        this.rec = r;
        this.img = img;
        this.imgActive = imgActive;
        this.imgHover = this.img;
        this.icon = new JLabel();
        init();
    }

    public navItem(String s, Rectangle r, String img, String imgActive, String imgHover, Color hover) {
        this.name = s;
        this.lb = new JLabel(name);
        this.img = img;
        this.imgActive = imgActive;
        this.imgHover = imgHover;
        this.icon = new JLabel();
        rec = r;
        this.hover = hover;
        init();
    }

    public navItem(String s, Rectangle r) {
        lb = new JLabel(s);
        icon = new JLabel();
        rec = r;
        init();
    }

    public void setColorNormal(Color e) {
        this.normal = e;
        setBackground(normal);
        repaint();
    }

    public JPanel isButton() {
        icon.setBounds(new Rectangle(rec.width / 4 + 2, rec.height / 4, 50, 30));
        normal = null;
        setBackground(normal);
//        repaint();
        return this;
    }

    public void init() {
        addMouseListener(this);
        Font font = new Font("Segoe UI", Font.BOLD, 13);
        setLayout(null);
        setBounds(rec);

        icon.setIcon(new ImageIcon("./img/" + img));
        icon.setBackground(Color.white);
        icon.setBounds(new Rectangle(rec.width / 7, rec.height / 4, 50, 30));

        lb.setFont(font);
        lb.setForeground(Color.white);
        lb.setBounds(new Rectangle(rec.width / 4 + 10, rec.height / 4, 150, 30));

        if (active) {
            setBackground(Color.WHITE);
        } else {
            setBackground(normal);
        }
        add(icon);
        add(lb);
    }

    public void setActive(boolean a) {
        active = a;
    }

    public String getName() {
        return name;
    }

    public void doActive() {
        active = true;
        icon.setIcon(new ImageIcon("./img/" + imgActive));
        lb.setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }

    public void noActive() {
        active = false;
        icon.setIcon(new ImageIcon("./img/" + img));
        lb.setForeground(Color.WHITE);
        setBackground(normal);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!active) {
            setBackground(hover);
            icon.setIcon(new ImageIcon("./img/" + imgHover));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!active) {
            setBackground(normal);
            icon.setIcon(new ImageIcon("./img/" + img));
        }
    }
}