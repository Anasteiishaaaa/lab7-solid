package srp;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import static org.junit.jupiter.api.Assertions.*;

class MedicalReportFormatterTest {
    @Test void producesExpectedJson() {
        Clock fixed = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC);
        PatientData p = PatientValidatorTest.patient(70, 175, 120, 80);
        DiagnosticResult r = new DiagnosticResult(p, 22.857, RiskCategory.LOW);

        String json = new MedicalReportFormatter(fixed).toJson(r);

        String expected = "{\n"
                + "  \"PatientId\": \"P1\",\n"
                + "  \"Name\": \"Test Patient\",\n"
                + "  \"BMI\": 22.86,\n"
                + "  \"BloodPressure\": \"120/80\",\n"
                + "  \"Category\": \"Низький ризик\",\n"
                + "  \"GeneratedAt\": \"2026-01-01T10:00:00Z\"\n"
                + "}";
        assertEquals(expected, json);
    }
}
