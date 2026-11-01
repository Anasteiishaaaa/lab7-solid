package ocp;

import java.math.BigDecimal;

/**
 * Extension Point системи: щоб додати нову категорію клієнтів, достатньо
 * реалізувати цей інтерфейс. Округлення виконує DiscountCalculator (єдине місце).
 */
public interface DiscountStrategy {
    /** Категорія клієнта, яку обслуговує ця стратегія. */
    CustomerCategory category();

    /** Знижка до округлення. */
    BigDecimal calculate(Order order);
}
