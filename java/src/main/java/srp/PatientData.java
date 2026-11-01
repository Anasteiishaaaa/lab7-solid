package srp;

/** Сутність доменної моделі: діагностичні дані пацієнта (без змін відносно legacy). */
public class PatientData {
    private String patientId;
    private String fullName;
    private double weightKg;
    private double heightCm;
    private int systolicBloodPressure;
    private int diastolicBloodPressure;
    private String doctorPhoneNumber;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }

    public int getSystolicBloodPressure() { return systolicBloodPressure; }
    public void setSystolicBloodPressure(int value) { this.systolicBloodPressure = value; }

    public int getDiastolicBloodPressure() { return diastolicBloodPressure; }
    public void setDiastolicBloodPressure(int value) { this.diastolicBloodPressure = value; }

    public String getDoctorPhoneNumber() { return doctorPhoneNumber; }
    public void setDoctorPhoneNumber(String doctorPhoneNumber) { this.doctorPhoneNumber = doctorPhoneNumber; }
}
