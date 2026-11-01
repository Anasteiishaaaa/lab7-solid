package srp;

/** Єдина причина для зміни: медична формула ІМТ та пороги оцінки ризику. */
public class HealthRiskCalculator {
    static final double HIGH_BMI = 30;
    static final double MODERATE_BMI = 25;
    static final int HIGH_SYSTOLIC = 140;
    static final int MODERATE_SYSTOLIC = 130;

    public double calculateBmi(PatientData patient) {
        double heightMeters = patient.getHeightCm() / 100.0;
        return patient.getWeightKg() / (heightMeters * heightMeters);
    }

    public RiskCategory assessRisk(double bmi, int systolicPressure) {
        if (bmi > HIGH_BMI || systolicPressure >= HIGH_SYSTOLIC) {
            return RiskCategory.HIGH;
        }
        if (bmi >= MODERATE_BMI || systolicPressure >= MODERATE_SYSTOLIC) {
            return RiskCategory.MODERATE;
        }
        return RiskCategory.LOW;
    }

    public DiagnosticResult evaluate(PatientData patient) {
        double bmi = calculateBmi(patient);
        return new DiagnosticResult(patient, bmi, assessRisk(bmi, patient.getSystolicBloodPressure()));
    }
}
