package document.element;

import document.DocumentVisitor;

/**
 * Class that represents bold text.
 */
public class BoldText extends BasicText {

    /**
     * Constructor.
     *
     * @param text the text
     */
    public BoldText(String text) {
        super(text);
    }


    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitBoldText(this);
    }
}
