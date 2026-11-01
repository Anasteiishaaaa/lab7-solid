package ocp;

import java.math.BigDecimal;

/** Звичайні клієнти: 10% для першого замовлення, або 5% при замовленні від $100. */
public class RegularDiscountStrategy implements DiscountStrategy {
    private static final BigDecimal THRESHOLD = new BigDecimal("100.00");

    @Override
    public CustomerCategory category() {
        return CustomerCategory.REGULAR;
    }

    @Override
    public BigDecimal calculate(Order order) {
        if (order.isFirstOrder()) {
            return Percent.of(order.getTotalAmount(), "0.10");
        }
        if (order.getTotalAmount().compareTo(THRESHOLD) >= 0) {
            return Percent.of(order.getTotalAmount(), "0.05");
        }
        return BigDecimal.ZERO;
    }
}
