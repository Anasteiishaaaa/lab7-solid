package lsp;

/** Незмінний Value Object з Guard Clauses: некоректний стан неможливо створити (fail-fast). */
public record DocumentInfo(String title, String filePath) {
    public DocumentInfo {
        Guards.requireNonBlank(title, "Назва документа не може бути порожньою.");
        Guards.requireNonBlank(filePath, "Шлях до файлу не може бути порожнім.");
    }
}
