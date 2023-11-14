package com.aeydeee.customers.orderstatus;

public class Order {
    
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer totalPrice;
    private String dateOrdered;
    private Integer orderStatus;

    public Order(Integer id, String name, Integer quantity, Integer totalPrice, String dateOrdered, Integer orderStatus) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.dateOrdered = dateOrdered;
        this.orderStatus = orderStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
   

  
}