package srp;

/**
 * Клас-оркестратор: лише координує кроки, сам не містить жодної бізнес-логіки.
 * Усі залежності передаються через конструктор.
 */
public class MedicalReportManager {
    private final PatientValidator validator;
    private final HealthRiskCalculator calculator;
    private final MedicalReportFormatter formatter;
    private final ReportStorage storage;
    private final DoctorNotifier notifier;

    public MedicalReportManager(PatientValidator validator,
                                HealthRiskCalculator calculator,
                                MedicalReportFormatter formatter,
                                ReportStorage storage,
                                DoctorNotifier notifier) {
        this.validator = validator;
        this.calculator = calculator;
        this.formatter = formatter;
        this.storage = storage;
        this.notifier = notifier;
    }

    public void processPatientDiagnostics(PatientData patient) {
        validator.validate(patient);

        DiagnosticResult result = calculator.evaluate(patient);
        storage.save(patient.getPatientId(), formatter.toJson(result));

        if (result.risk().isCritical()) {
            notifier.notifyCriticalCondition(result);
        }
    }
}
