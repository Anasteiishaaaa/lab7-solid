package srp;

import java.util.Locale;

/** Єдина причина для зміни: канал і текст термінового SMS-сповіщення. */
public class SmsDoctorNotifier implements DoctorNotifier {
    static final String GATEWAY_URL = "http://sms-gateway.local/send";

    @Override
    public void notifyCriticalCondition(DiagnosticResult result) {
        PatientData p = result.patient();
        try {
            String message = String.format(Locale.ROOT,
                    "УВАГА: Пацієнт %s має критичні показники! ІМТ: %.1f, Тиск: %d/%d",
                    p.getFullName(), result.bmi(),
                    p.getSystolicBloodPressure(), p.getDiastolicBloodPressure());
            System.out.println("Надсилання SMS на номер " + p.getDoctorPhoneNumber() + " через " + GATEWAY_URL);
            System.out.println("Повідомлення: " + message);
        } catch (Exception ex) {
            System.out.println("Не вдалося відправити SMS лікарю: " + ex.getMessage());
        }
    }
}
