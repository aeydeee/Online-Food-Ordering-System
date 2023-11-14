package com.aeydeee.admins;

public class UserAdmin {

    private Integer id;
    private String fName;
    private String lName;
    private String category;
    private String username;
    private String email;

    private String bDay;
    private int houseNo;
    private String brgy;
    private String city;
    private int zip;
    private String imagePath;

    public UserAdmin(int id, String fName, String lName, String category, String username, String email, String bDay, String brgy, int houseNo, String city, int zip, String imagePath) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.category = category;
        this.username = username;
        this.email = email;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
