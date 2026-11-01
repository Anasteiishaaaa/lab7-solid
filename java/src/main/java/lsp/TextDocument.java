package lsp;

/** Редагований документ. Інваріант: вміст ніколи не null. */
public class TextDocument implements EditableDocument {
    private final DocumentInfo info;
    private String content;

    public TextDocument(String title, String content, String filePath) {
        this.info = new DocumentInfo(title, filePath);
        this.content = Guards.requireContent(content);
    }

    @Override public String getTitle() { return info.title(); }
    @Override public String getContent() { return content; }
    @Override public String getFilePath() { return info.filePath(); }

    @Override
    public void setContent(String newContent) {
        this.content = Guards.requireContent(newContent);
    }

    @Override
    public void save() {
        System.out.println("Saving document '" + info.title() + "' to path: " + info.filePath());
    }
}
