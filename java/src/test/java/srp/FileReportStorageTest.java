package srp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class FileReportStorageTest {
    @Test void writesFileNamedByPatientId(@TempDir Path dir) throws Exception {
        new FileReportStorage(dir).save("P77", "{json}");
        assertEquals("{json}", Files.readString(dir.resolve("medical_report_P77.json")));
    }

    @Test void ioFailureDoesNotThrow(@TempDir Path dir) {
        Path missing = dir.resolve("no-such-dir");
        assertDoesNotThrow(() -> new FileReportStorage(missing).save("P1", "x"));
    }
}
