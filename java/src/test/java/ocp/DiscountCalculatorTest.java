package ocp;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {
    private static DiscountCalculator fullCalculator() {
        return new DiscountCalculator(List.of(
                new RegularDiscountStrategy(), new VipDiscountStrategy(),
                new WholesaleDiscountStrategy(), new EmployeeDiscountStrategy(),
                new StudentDiscountStrategy()));
    }

    private static Order order(String amount, int items, boolean first) {
        return new Order("T", new BigDecimal(amount), items, first);
    }

    private static BigDecimal bd(String s) { return new BigDecimal(s); }

    @Test void regular() {
        DiscountCalculator c = fullCalculator();
        assertEquals(bd("10.00"), c.calculateDiscount(order("100.00", 1, true), CustomerCategory.REGULAR));
        assertEquals(bd("5.00"), c.calculateDiscount(order("100.00", 1, false), CustomerCategory.REGULAR));
        assertEquals(bd("0.00"), c.calculateDiscount(order("99.99", 1, false), CustomerCategory.REGULAR));
    }

    @Test void vip() {
        DiscountCalculator c = fullCalculator();
        assertEquals(bd("30.00"), c.calculateDiscount(order("200.00", 5, false), CustomerCategory.VIP));
        assertEquals(bd("40.00"), c.calculateDiscount(order("200.00", 6, false), CustomerCategory.VIP));
    }

    @Test void wholesale() {
        DiscountCalculator c = fullCalculator();
        assertEquals(bd("200.00"), c.calculateDiscount(order("1000.00", 1, false), CustomerCategory.WHOLESALE));
        assertEquals(bd("99.90"), c.calculateDiscount(order("999.00", 1, false), CustomerCategory.WHOLESALE));
    }

    @Test void employee() {
        assertEquals(bd("30.00"),
                fullCalculator().calculateDiscount(order("100.00", 1, false), CustomerCategory.EMPLOYEE));
    }

    @Test void roundingIsHalfUp() {
        assertEquals(bd("0.01"),
                fullCalculator().calculateDiscount(order("0.05", 1, false), CustomerCategory.WHOLESALE));
    }

    /** OCP: нова категорія працює без змін у DiscountCalculator. */
    @Test void newStudentStrategyWorksWithoutTouchingCalculator() {
        assertEquals(bd("12.00"),
                fullCalculator().calculateDiscount(order("100.00", 1, false), CustomerCategory.STUDENT));
    }

    @Test void unregisteredCategoryIsRejected() {
        DiscountCalculator onlyRegular = new DiscountCalculator(List.of(new RegularDiscountStrategy()));
        assertThrows(IllegalArgumentException.class,
                () -> onlyRegular.calculateDiscount(order("1.00", 1, false), CustomerCategory.VIP));
        assertThrows(IllegalArgumentException.class,
                () -> onlyRegular.calculateDiscount(order("1.00", 1, false), null));
    }

    @Test void duplicateStrategyIsRejected() {
        assertThrows(IllegalStateException.class, () -> new DiscountCalculator(
                List.of(new VipDiscountStrategy(), new VipDiscountStrategy())));
    }

    /** Еталон: перенесена дослівно логіка legacy if-else; нова реалізація має давати однакові результати. */
    private static BigDecimal legacy(Order order, CustomerCategory category) {
        BigDecimal discount;
        BigDecimal amount = order.getTotalAmount();
        if (category == CustomerCategory.REGULAR) {
            discount = BigDecimal.ZERO;
            if (order.isFirstOrder()) discount = amount.multiply(new BigDecimal("0.10"));
            else if (amount.compareTo(new BigDecimal("100.00")) >= 0) discount = amount.multiply(new BigDecimal("0.05"));
        } else if (category == CustomerCategory.VIP) {
            discount = amount.multiply(new BigDecimal("0.15"));
            if (order.getItemCount() > 5) discount = discount.add(amount.multiply(new BigDecimal("0.05")));
        } else if (category == CustomerCategory.WHOLESALE) {
            discount = amount.compareTo(new BigDecimal("1000.00")) >= 0
                    ? amount.multiply(new BigDecimal("0.20")) : amount.multiply(new BigDecimal("0.10"));
        } else {
            discount = amount.multiply(new BigDecimal("0.30"));
        }
        return discount.setScale(2, RoundingMode.HALF_UP);
    }

    @Test void matchesLegacyOnWideGrid() {
        DiscountCalculator c = fullCalculator();
        CustomerCategory[] legacyCats = {CustomerCategory.REGULAR, CustomerCategory.VIP,
                CustomerCategory.WHOLESALE, CustomerCategory.EMPLOYEE};
        String[] amounts = {"0.01", "0.05", "50.00", "99.99", "100.00", "100.01", "999.99", "1000.00", "12345.67"};
        for (CustomerCategory cat : legacyCats)
            for (String a : amounts)
                for (int items : new int[]{1, 5, 6, 20})
                    for (boolean first : new boolean[]{true, false}) {
                        Order o = order(a, items, first);
                        assertEquals(legacy(o, cat), c.calculateDiscount(o, cat), cat + " " + a + " " + items + " " + first);
                    }
    }
}
