package srp;

import java.time.Clock;
import java.time.Instant;
import java.util.Locale;

/**
 * Єдина причина для зміни: формат медичного звіту (JSON).
 * Clock інжектується, щоб час можна було зафіксувати в тестах.
 * Locale.ROOT гарантує крапку як десятковий роздільник (інакше в uk-локалі JSON був би некоректним).
 */
public class MedicalReportFormatter {
    private final Clock clock;

    public MedicalReportFormatter(Clock clock) {
        this.clock = clock;
    }

    public String toJson(DiagnosticResult result) {
        PatientData p = result.patient();
        return String.format(Locale.ROOT, "{\n"
                        + "  \"PatientId\": \"%s\",\n"
                        + "  \"Name\": \"%s\",\n"
                        + "  \"BMI\": %.2f,\n"
                        + "  \"BloodPressure\": \"%d/%d\",\n"
                        + "  \"Category\": \"%s\",\n"
                        + "  \"GeneratedAt\": \"%s\"\n"
                        + "}",
                p.getPatientId(),
                p.getFullName(),
                result.bmi(),
                p.getSystolicBloodPressure(), p.getDiastolicBloodPressure(),
                result.risk().getLabel(),
                Instant.now(clock));
    }
}
