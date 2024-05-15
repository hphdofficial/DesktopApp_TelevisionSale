package GUI.Swing;

import GUI.Event.EventMenuSelected;
import GUI.Model.ModelMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ListMenu<E extends Object> extends JList<E> {

    private final DefaultListModel model;
    private int selectedIndex = -1;
    private int overIndex = -1;
    private EventMenuSelected event;

    public ListMenu() {
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {  // Nếu nhấp chuột trái
                    int index = locationToIndex(e.getPoint());  // Xác định ô nào được nhấp
                    Object o = model.getElementAt(index);  // Trả về vị trí index được chọn
                    if (o instanceof ModelMenu) {   // Kiểm tra o có phải là một thể hiện của class ModelMenu
                        ModelMenu menu = (ModelMenu) o;
                        if (menu.getType() == ModelMenu.MenuType.MENU) {
                            selectedIndex = index;
                            if (event != null) {
                                event.selected(index);
                            }
                        }
                    } else {
                        selectedIndex = index;
                    }
                    repaint();  // Cập nhật vị trí mới
                }
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                int index = locationToIndex(me.getPoint());
                if (index != overIndex) {
                    Object o = model.getElementAt(index);
                    if (o instanceof ModelMenu) {
                        ModelMenu menu = (ModelMenu) o;
                        if (menu.getType() == ModelMenu.MenuType.MENU) {
                            overIndex = index;
                        } else {
                            overIndex = -1;
                        }
                        repaint();
                    }
                }
            }
        });
    }

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
    }

    // interface LisCellRenderer có phương thức getCellRendererComponent để hiển trị lên JList
    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                ModelMenu listData;
                if (value instanceof ModelMenu) {
                    listData = (ModelMenu) value;
                } else {
                    listData = new ModelMenu("", value + "", ModelMenu.MenuType.EMPTY);
                }
                MenuItem item = new MenuItem(listData);
                item.setSelected(selectedIndex == index);
                item.setOver(overIndex == index);
                return item;
            }

        };
    }

    public void addItem(ModelMenu listData) {
        model.addElement(listData);
    }
}