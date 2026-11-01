package srp;

/** Єдина причина для зміни: правила валідації фізіологічних параметрів. */
public class PatientValidator {
    static final double MIN_WEIGHT_EXCLUSIVE = 2;
    static final double MAX_WEIGHT = 300;
    static final double MIN_HEIGHT_EXCLUSIVE = 40;
    static final double MAX_HEIGHT = 250;
    static final int MIN_SYSTOLIC = 50;
    static final int MIN_DIASTOLIC = 30;

    public void validate(PatientData patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Параметр patient не може бути null.");
        }
        if (patient.getWeightKg() <= MIN_WEIGHT_EXCLUSIVE || patient.getWeightKg() > MAX_WEIGHT) {
            throw new IllegalArgumentException("Некоректне значення ваги пацієнта.");
        }
        if (patient.getHeightCm() <= MIN_HEIGHT_EXCLUSIVE || patient.getHeightCm() > MAX_HEIGHT) {
            throw new IllegalArgumentException("Некоректне значення зросту пацієнта.");
        }
        if (patient.getSystolicBloodPressure() < MIN_SYSTOLIC
                || patient.getDiastolicBloodPressure() < MIN_DIASTOLIC) {
            throw new IllegalArgumentException("Показники тиску знаходяться за межами допустимого діапазону.");
        }
    }
}
