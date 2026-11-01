package srp;

/** Абстракція збереження звіту (архівний журнал). */
public interface ReportStorage {
    void save(String patientId, String reportContent);
}
