package ocp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Закритий для модифікації: не містить if/switch за категорією.
 * Відкритий для розширення: нові стратегії передаються через конструктор.
 */
public class DiscountCalculator {
    private static final int SCALE = 2;

    private final Map<CustomerCategory, DiscountStrategy> strategies = new EnumMap<>(CustomerCategory.class);

    public DiscountCalculator(List<DiscountStrategy> registeredStrategies) {
        Objects.requireNonNull(registeredStrategies, "registeredStrategies");
        for (DiscountStrategy strategy : registeredStrategies) {
            if (strategies.putIfAbsent(strategy.category(), strategy) != null) {
                throw new IllegalStateException("Дубльована стратегія для категорії: " + strategy.category());
            }
        }
    }

    public BigDecimal calculateDiscount(Order order, CustomerCategory category) {
        DiscountStrategy strategy = strategies.get(category);
        if (strategy == null) {
            throw new IllegalArgumentException("Невідома категорія клієнта: " + category);
        }

        BigDecimal discount = strategy.calculate(order).setScale(SCALE, RoundingMode.HALF_UP);
        System.out.println("[DISCOUNT] Замовлення " + order.getOrderId()
                + ": Категорія=" + category + ", Розрахована знижка=$" + discount);
        return discount;
    }
}
