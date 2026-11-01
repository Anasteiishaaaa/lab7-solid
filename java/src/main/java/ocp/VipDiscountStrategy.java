package ocp;

import java.math.BigDecimal;

/** VIP: базово 15%, плюс додатково 5% якщо в кошику більше 5 товарів. */
public class VipDiscountStrategy implements DiscountStrategy {
    private static final int BULK_ITEMS_THRESHOLD = 5;

    @Override
    public CustomerCategory category() {
        return CustomerCategory.VIP;
    }

    @Override
    public BigDecimal calculate(Order order) {
        BigDecimal discount = Percent.of(order.getTotalAmount(), "0.15");
        if (order.getItemCount() > BULK_ITEMS_THRESHOLD) {
            discount = discount.add(Percent.of(order.getTotalAmount(), "0.05"));
        }
        return discount;
    }
}
