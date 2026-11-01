package ocp;

import java.math.BigDecimal;

/** Оптові покупці: 20% при замовленні від $1000, інакше 10%. */
public class WholesaleDiscountStrategy implements DiscountStrategy {
    private static final BigDecimal THRESHOLD = new BigDecimal("1000.00");

    @Override
    public CustomerCategory category() {
        return CustomerCategory.WHOLESALE;
    }

    @Override
    public BigDecimal calculate(Order order) {
        String rate = order.getTotalAmount().compareTo(THRESHOLD) >= 0 ? "0.20" : "0.10";
        return Percent.of(order.getTotalAmount(), rate);
    }
}
