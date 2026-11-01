package lsp;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DocumentLspTest {
    @Test void editorAppendsWatermarkToEditableDocument() {
        TextDocument doc = new TextDocument("Наказ", "Текст", "/d/o.txt");
        new DocumentEditor().appendWatermarkAndSave(doc, "SECRET");
        assertEquals("Текст \n[Watermark: SECRET]", doc.getContent());
    }

    /** Будь-який ReadableDocument (обидва підтипи) однаково підставляється там, де потрібне лише читання/друк. */
    @Test void everyReadableDocumentSupportsReadAndPrint() {
        List<ReadableDocument> docs = List.of(
                new TextDocument("A", "a", "/a"), new ReadOnlyPdfDocument("B", "b", "/b.pdf"));
        for (ReadableDocument d : docs) {
            assertFalse(d.getTitle().isBlank());
            assertDoesNotThrow(d::print);
        }
    }

    @Test void pdfCannotBeUsedAsEditableByConstruction() {
        Object pdf = new ReadOnlyPdfDocument("B", "b", "/b.pdf");
        assertFalse(pdf instanceof EditableDocument);
    }

    @Test void guardClausesFailFast() {
        assertThrows(IllegalArgumentException.class, () -> new TextDocument(" ", "c", "/p"));
        assertThrows(IllegalArgumentException.class, () -> new TextDocument("t", null, "/p"));
        assertThrows(IllegalArgumentException.class, () -> new TextDocument("t", "c", ""));
        assertThrows(IllegalArgumentException.class, () -> new ReadOnlyPdfDocument("t", null, "/p"));
        TextDocument doc = new TextDocument("t", "c", "/p");
        assertThrows(IllegalArgumentException.class, () -> doc.setContent(null));
        assertEquals("c", doc.getContent(), "інваріант збережено після відхиленого запису");
    }

    @Test void editorRejectsBadArguments() {
        DocumentEditor editor = new DocumentEditor();
        assertThrows(IllegalArgumentException.class, () -> editor.appendWatermarkAndSave(null, "w"));
        assertThrows(IllegalArgumentException.class,
                () -> editor.appendWatermarkAndSave(new TextDocument("t", "c", "/p"), " "));
    }
}
