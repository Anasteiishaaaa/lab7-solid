package ocp;

import java.math.BigDecimal;

/**
 * ДЕМОНСТРАЦІЯ OCP: новий тип клієнта — студенти, 12% на все.
 * Для його підтримки НЕ змінено жодного рядка в DiscountCalculator.
 */
public class StudentDiscountStrategy implements DiscountStrategy {
    @Override
    public CustomerCategory category() {
        return CustomerCategory.STUDENT;
    }

    @Override
    public BigDecimal calculate(Order order) {
        return Percent.of(order.getTotalAmount(), "0.12");
    }
}
