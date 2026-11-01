package ocp;

import java.math.BigDecimal;

public class Order {
    private final String orderId;
    private final BigDecimal totalAmount;
    private final int itemCount;
    private final boolean isFirstOrder;

    public Order(String orderId, BigDecimal totalAmount, int itemCount, boolean isFirstOrder) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.itemCount = itemCount;
        this.isFirstOrder = isFirstOrder;
    }

    public String getOrderId() { return orderId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public int getItemCount() { return itemCount; }
    public boolean isFirstOrder() { return isFirstOrder; }
}
