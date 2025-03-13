package document.element;

import document.DocumentVisitor;

/**
 * The default element of any document is text.
 */
public class BasicText implements TextElement {

    /**
     * The text.
     */
    private final String text;

    /**
     * Constructor for the document element.
     *
     * @param text the content of the document element.
     */
    public BasicText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitBasicText(this);
    }
}
