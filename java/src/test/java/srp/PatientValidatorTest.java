package srp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientValidatorTest {
    private final PatientValidator validator = new PatientValidator();

    static PatientData patient(double w, double h, int sys, int dia) {
        PatientData p = new PatientData();
        p.setPatientId("P1");
        p.setFullName("Test Patient");
        p.setWeightKg(w);
        p.setHeightCm(h);
        p.setSystolicBloodPressure(sys);
        p.setDiastolicBloodPressure(dia);
        p.setDoctorPhoneNumber("+380000000000");
        return p;
    }

    @Test void nullPatientRejected() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
    }

    @Test void validPatientAccepted() {
        assertDoesNotThrow(() -> validator.validate(patient(70, 175, 120, 80)));
    }

    @Test void weightBoundaries() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(2, 175, 120, 80)));
        assertDoesNotThrow(() -> validator.validate(patient(2.01, 175, 120, 80)));
        assertDoesNotThrow(() -> validator.validate(patient(300, 175, 120, 80)));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(300.1, 175, 120, 80)));
    }

    @Test void heightBoundaries() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(70, 40, 120, 80)));
        assertDoesNotThrow(() -> validator.validate(patient(70, 250, 120, 80)));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(70, 250.1, 120, 80)));
    }

    @Test void pressureBoundaries() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(70, 175, 49, 80)));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(patient(70, 175, 120, 29)));
        assertDoesNotThrow(() -> validator.validate(patient(70, 175, 50, 30)));
    }
}
