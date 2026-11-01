package srp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/** Єдина причина для зміни: спосіб збереження (файл medical_report_{id}.json). */
public class FileReportStorage implements ReportStorage {
    private final Path directory;

    public FileReportStorage(Path directory) {
        this.directory = directory;
    }

    @Override
    public void save(String patientId, String reportContent) {
        Path file = directory.resolve("medical_report_" + patientId + ".json");
        try (FileWriter writer = new FileWriter(file.toFile())) {
            writer.write(reportContent);
        } catch (IOException e) {
            // Поведінка збережена з legacy: помилка логується, обробка пацієнта не переривається.
            System.err.println("Помилка збереження JSON звіту: " + e.getMessage());
        }
    }
}
