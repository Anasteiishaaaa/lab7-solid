import srp.*;
import ocp.*;
import lsp.*;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Clock;
import java.util.List;

/** Composition Root: збирає залежності та демонструє Завдання 1–3. */
public class Main {
    public static void main(String[] args) {
        // Завдання 1
        MedicalReportManager manager = new MedicalReportManager(
                new PatientValidator(), new HealthRiskCalculator(),
                new MedicalReportFormatter(Clock.systemUTC()),
                new FileReportStorage(Path.of(".")), new SmsDoctorNotifier());
        PatientData patient = new PatientData();
        patient.setPatientId("P-001");
        patient.setFullName("Іваненко Іван");
        patient.setWeightKg(110);
        patient.setHeightCm(175);
        patient.setSystolicBloodPressure(150);
        patient.setDiastolicBloodPressure(95);
        patient.setDoctorPhoneNumber("+380501112233");
        manager.processPatientDiagnostics(patient);

        // Завдання 2
        DiscountCalculator calculator = new DiscountCalculator(List.of(
                new RegularDiscountStrategy(), new VipDiscountStrategy(),
                new WholesaleDiscountStrategy(), new EmployeeDiscountStrategy(),
                new StudentDiscountStrategy()));
        calculator.calculateDiscount(new Order("A-1", new BigDecimal("200.00"), 6, false), CustomerCategory.VIP);
        calculator.calculateDiscount(new Order("A-2", new BigDecimal("80.00"), 1, true), CustomerCategory.STUDENT);

        // Завдання 3
        DocumentEditor editor = new DocumentEditor();
        editor.appendWatermarkAndSave(new TextDocument("Наказ", "Текст", "/docs/order.txt"), "CONFIDENTIAL");
        new ReadOnlyPdfDocument("Договір", "Текст PDF", "/docs/contract.pdf").print();
        // editor.appendWatermarkAndSave(new ReadOnlyPdfDocument(...), "X"); // НЕ КОМПІЛЮЄТЬСЯ
    }
}
