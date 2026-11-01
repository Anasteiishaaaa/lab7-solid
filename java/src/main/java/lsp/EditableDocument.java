package lsp;

/** Розширений контракт: лише документи, які можна змінювати та зберігати. */
public interface EditableDocument extends ReadableDocument {
    void setContent(String newContent);
    void save();
}
