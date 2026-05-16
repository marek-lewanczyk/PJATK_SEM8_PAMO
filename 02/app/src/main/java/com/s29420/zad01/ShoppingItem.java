package com.s29420.zad01;

public class ShoppingItem {
    private final String name;
    private final String quantity;
    private boolean purchased;

    public ShoppingItem(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
        this.purchased = false;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public boolean isPurchased() { return purchased; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
}
