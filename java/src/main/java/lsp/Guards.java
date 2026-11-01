package lsp;

/** Єдине місце Guard Clauses для всіх документів (DRY / Single Source of Truth). */
final class Guards {
    private Guards() { }

    static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /** Вміст може бути порожнім рядком, але ніколи не null. */
    static String requireContent(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Вміст документа не може бути null.");
        }
        return value;
    }
}
