package srp;

/** Категорія ризику. Єдине джерело правди для назв категорій та ознаки «критичний стан». */
public enum RiskCategory {
    HIGH("Високий ризик"),
    MODERATE("Помірний ризик"),
    LOW("Низький ризик");

    private final String label;

    RiskCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isCritical() {
        return this == HIGH;
    }
}
