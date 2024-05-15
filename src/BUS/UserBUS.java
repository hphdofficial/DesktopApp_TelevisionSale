package BUS;

import DAO.UserDAO;
import DTO.UserDTO;

import java.util.ArrayList;
import java.util.Arrays;

public class UserBUS {
    private ArrayList<UserDTO> dsUS;

    public UserBUS() {
        list();
    }

    public void list() {
        UserDAO usDAO = new UserDAO();
        dsUS = new ArrayList<>();
        dsUS = usDAO.list();
    }

    public void add(UserDTO user) {
        dsUS.add(user);
        UserDAO usDAO = new UserDAO();
        usDAO.add(user);
    }

    public void add(UserDTO user, int i) {
        dsUS.add(user);
        UserDAO usDAO = new UserDAO();
        usDAO.add(user);
    }

    public void update(UserDTO user) {
        for (int i = 0; i < dsUS.size(); i++) {
            if (dsUS.get(i).getUserID().equals(user.getUserID())) {
                dsUS.set(i, user);
                UserDAO usDAO = new UserDAO();
                usDAO.update(user);
                return;
            }
        }
    }

    public void delete(String userID) {
        for (UserDTO user : dsUS) {
            if (user.getUserID().equals(userID)) {
                dsUS.remove(user);
                UserDAO usDAO = new UserDAO();
                usDAO.delete(userID);
                return;
            }
        }
    }

    public void delete(String text, int i) {
        list();
        delete(text);
    }

    public UserDTO check(String userName, char[] pass) {
        for (UserDTO us : dsUS) {
            char[] correctPass = us.getPassWord().toCharArray();
            if (us.getUserName().equals(userName) && Arrays.equals(pass, correctPass) && us.getEnable().equals("1")) {
                return us;
            }
        }
        return null;
    }

    public ArrayList<UserDTO> search(String id, String name, String role, String enable) {
        ArrayList<UserDTO> search = new ArrayList<>();
        id = id.isEmpty() ? "" : id;
        name = name.isEmpty() ? "" : name;
        role = role.isEmpty() ? "" : role;
        for (UserDTO us : dsUS) {
            if (us.getUserID().contains(id) && us.getUserName().contains(name) && us.getRoleID().contains(role) && us.getEnable().contains(enable)) {
                search.add(us);
            }
        }
        return search;
    }

    public ArrayList<UserDTO> getList() {
        return dsUS;
    }
}