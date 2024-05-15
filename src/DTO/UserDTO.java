package DTO;

public class UserDTO {
    private String userID, userName, passWord, email, roleID, enable;

    public UserDTO() {
    }

    public UserDTO(String userID, String userName, String passWord, String email, String roleID, String enable) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.roleID = roleID;
        this.enable = enable;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}