package lsp;

/**
 * Клієнт залежить від EditableDocument, тому передати йому ReadOnlyPdfDocument
 * неможливо ще на етапі компіляції. Контракт ніколи не порушується.
 */
public class DocumentEditor {
    public void appendWatermarkAndSave(EditableDocument doc, String watermark) {
        if (doc == null) {
            throw new IllegalArgumentException("Документ не може бути null.");
        }
        if (watermark == null || watermark.isBlank()) {
            throw new IllegalArgumentException("Водяний знак не може бути порожнім.");
        }
        doc.setContent(doc.getContent() + " \n[Watermark: " + watermark + "]");
        doc.save();
    }
}
