
package com.aeydeee.loginRegister;



public class User {
    
     private Integer id;
    private String fName;
    private String lName;
    private String username;
    private String email;
    private String password;
    private String bDay;
    private int houseNo;
    private String brgy;
    private String city;
    private int zip;
    private String imagePath;

    public User(Integer id, String fName, String lName, String username, String email, String password, String bDay, int houseNo, String brgy, String city, int zip, String imagePath) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bDay = bDay;
        this.houseNo = houseNo;
        this.brgy = brgy;
        this.city = city;
        this.zip = zip;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getbDay() {
        return bDay;
    }

    public void setbDay(String bDay) {
        this.bDay = bDay;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public String getBrgy() {
        return brgy;
    }

    public void setBrgy(String brgy) {
        this.brgy = brgy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    
    
    
    
}
