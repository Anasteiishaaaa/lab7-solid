package ocp;

import java.math.BigDecimal;

/** Єдине місце розрахунку «відсоток від суми» (DRY). */
final class Percent {
    private Percent() { }

    static BigDecimal of(BigDecimal amount, String rate) {
        return amount.multiply(new BigDecimal(rate));
    }
}
