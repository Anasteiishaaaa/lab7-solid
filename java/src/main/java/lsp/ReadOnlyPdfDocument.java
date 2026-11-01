package lsp;

/**
 * PDF лише для читання. Не наслідує EditableDocument, тож не має методів,
 * які довелося б «забороняти» винятками. Повністю незмінний.
 */
public class ReadOnlyPdfDocument implements ReadableDocument {
    private final DocumentInfo info;
    private final String content;

    public ReadOnlyPdfDocument(String title, String content, String filePath) {
        this.info = new DocumentInfo(title, filePath);
        this.content = Guards.requireContent(content);
    }

    @Override public String getTitle() { return info.title(); }
    @Override public String getContent() { return content; }
    @Override public String getFilePath() { return info.filePath(); }
}
