package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String contactInfo;
    private String name;
    private String email;
    private String role; // 'user', 'staff', 'admin'
    private int roleID;
    private String fileName;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
    
    public int getRoleId() {
        return roleID;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRoleId(int roleID) {
        this.roleID = roleID;
    }
    
    
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setImagePath(String fileName) {
        this.fileName = fileName;
    }

    public String getImagePath() {
        return fileName;
    }
}
