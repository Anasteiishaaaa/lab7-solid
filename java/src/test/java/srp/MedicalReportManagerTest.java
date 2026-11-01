package srp;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MedicalReportManagerTest {
    static class SpyStorage implements ReportStorage {
        final List<String> ids = new ArrayList<>();
        final List<String> contents = new ArrayList<>();
        public void save(String id, String content) { ids.add(id); contents.add(content); }
    }

    static class SpyNotifier implements DoctorNotifier {
        final List<DiagnosticResult> calls = new ArrayList<>();
        public void notifyCriticalCondition(DiagnosticResult r) { calls.add(r); }
    }

    private final SpyStorage storage = new SpyStorage();
    private final SpyNotifier notifier = new SpyNotifier();
    private final MedicalReportManager manager = new MedicalReportManager(
            new PatientValidator(), new HealthRiskCalculator(),
            new MedicalReportFormatter(Clock.systemUTC()), storage, notifier);

    @Test void healthyPatientIsSavedWithoutNotification() {
        manager.processPatientDiagnostics(PatientValidatorTest.patient(70, 175, 120, 80));
        assertEquals(List.of("P1"), storage.ids);
        assertTrue(storage.contents.get(0).contains("Низький ризик"));
        assertTrue(notifier.calls.isEmpty());
    }

    @Test void criticalPatientTriggersExactlyOneNotification() {
        manager.processPatientDiagnostics(PatientValidatorTest.patient(110, 175, 150, 95));
        assertEquals(1, storage.ids.size());
        assertEquals(1, notifier.calls.size());
        assertEquals(RiskCategory.HIGH, notifier.calls.get(0).risk());
    }

    @Test void invalidPatientStopsPipelineBeforeAnySideEffect() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.processPatientDiagnostics(PatientValidatorTest.patient(1, 175, 120, 80)));
        assertTrue(storage.ids.isEmpty());
        assertTrue(notifier.calls.isEmpty());
    }
}
