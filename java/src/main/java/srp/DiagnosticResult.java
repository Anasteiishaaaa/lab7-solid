package srp;

/** Незмінний результат розрахунку: пацієнт + ІМТ + категорія ризику. */
public record DiagnosticResult(PatientData patient, double bmi, RiskCategory risk) {
}
