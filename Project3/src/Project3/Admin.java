package Project3;


import java.util.Date;

public class Admin {
    private String id;
    private String name;
    private String password;
    private Date birth_date;
    private int admin_id;

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }
    
    public Admin(int admin_id, String id, String name, String password) {
       
        this.admin_id = admin_id;
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
