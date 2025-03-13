package document.element;

import document.DocumentVisitor;

public class Heading extends BasicText {

    /**
     * The level of this heading.
     */
    private int level;

    /**
     * A heading in a document.
     *
     * @param text  the text
     * @param level the level of the heading
     */
    public Heading(String text, int level) {
        super(text);
        this.level = level;
    }

    /**
     * Accessor for the level of the heading.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitHeading(this);
    }
}
