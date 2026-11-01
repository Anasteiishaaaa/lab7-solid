package lsp;

/** Базовий контракт: те, що вміє КОЖЕН документ (читання та друк). */
public interface ReadableDocument {
    String getTitle();
    String getContent();
    String getFilePath();

    default void print() {
        System.out.println("Printing document '" + getTitle() + "': " + getContent());
    }
}
