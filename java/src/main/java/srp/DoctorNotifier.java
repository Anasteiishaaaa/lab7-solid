package srp;

/** Абстракція сповіщення лікаря про критичний стан пацієнта. */
public interface DoctorNotifier {
    void notifyCriticalCondition(DiagnosticResult result);
}
