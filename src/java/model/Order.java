package model;

import java.util.Map;

public class Order {
    private int id;
    private int customerId;
    private Map<Integer, Integer> itemQuantities; // key: itemId, value: quantity
    private String orderType;
    private Integer tableId; // null if not applicable
    private double total;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, Integer> getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(Map<Integer, Integer> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
