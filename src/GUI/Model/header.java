package GUI.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class header extends JPanel {
    private final int width;
    private final int height;
    private JFrame f;

    public header(int width, int height, JFrame f) {
        this.width = width;
        this.height = height;
        this.f = f;
        init(f);
    }

    public void init(JFrame f) {
        setLayout(null);
        setSize(width, height);
        setBackground(null);
        Font font = new Font("Segoe UI", Font.BOLD, 15);

        JLabel logo = new JLabel(new ImageIcon("./img/iconTV.png"), JLabel.CENTER);
        logo.setBounds(new Rectangle(20, 0, 40, 40));
        add(logo);

        JLabel name = new JLabel("QUẢN LÝ CỬA HÀNG TIVI", JLabel.CENTER);
        name.setFont(font);
        name.setForeground(Color.white);
        name.setBounds(new Rectangle(60, 0, 200, 40));
        add(name);

        navItem close = new navItem("", new Rectangle(width - 50, -5, 50, 45), "exit_25px.png", "exit_25px.png", "exit_hover_25px.png", new Color(240, 71, 74));
        navItem minimize = new navItem("", new Rectangle(width - 100, -5, 50, 45), "minimize_25px.png", "minimize_25px.png", "minimize_hover_25px.png", new Color(80, 80, 80));

        add(close.isButton());
        add(minimize.isButton());

        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                f.setState(Frame.ICONIFIED);
            }
        });
    }
}