package srp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthRiskCalculatorTest {
    private final HealthRiskCalculator calc = new HealthRiskCalculator();

    @Test void bmiFormula() {
        assertEquals(22.857, calc.calculateBmi(PatientValidatorTest.patient(70, 175, 120, 80)), 0.001);
    }

    @Test void highRiskByBmiIsStrictlyGreaterThan30() {
        assertEquals(RiskCategory.MODERATE, calc.assessRisk(30.0, 120));
        assertEquals(RiskCategory.HIGH, calc.assessRisk(30.01, 120));
    }

    @Test void highRiskBySystolic140() {
        assertEquals(RiskCategory.HIGH, calc.assessRisk(20, 140));
        assertEquals(RiskCategory.MODERATE, calc.assessRisk(20, 139));
    }

    @Test void moderateBoundaries() {
        assertEquals(RiskCategory.MODERATE, calc.assessRisk(25, 120));
        assertEquals(RiskCategory.MODERATE, calc.assessRisk(20, 130));
        assertEquals(RiskCategory.LOW, calc.assessRisk(24.99, 129));
    }

    @Test void onlyHighIsCritical() {
        assertTrue(RiskCategory.HIGH.isCritical());
        assertFalse(RiskCategory.MODERATE.isCritical());
        assertFalse(RiskCategory.LOW.isCritical());
    }
}
