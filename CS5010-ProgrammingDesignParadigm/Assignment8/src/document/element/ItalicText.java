package document.element;

import document.DocumentVisitor;

/**
 * Representation for italic text.
 */
public class ItalicText extends BasicText {

    public ItalicText(String text) {
        super(text);
    }

    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitItalicText(this);
    }
}
