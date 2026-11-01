package ocp;

import java.math.BigDecimal;

/** Співробітники компанії: фіксована знижка 30% на все. */
public class EmployeeDiscountStrategy implements DiscountStrategy {
    @Override
    public CustomerCategory category() {
        return CustomerCategory.EMPLOYEE;
    }

    @Override
    public BigDecimal calculate(Order order) {
        return Percent.of(order.getTotalAmount(), "0.30");
    }
}
