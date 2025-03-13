package document.element;

import document.DocumentVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a paragraph in our document.
 */
public class Paragraph implements TextElement {

    /**
     * The content of the paragraph.
     */
    private List<BasicText> content;

    /**
     * Default constructor.
     */
    public Paragraph() {
        content = new ArrayList<>();
    }

    /**
     * Add a text element to the paragraph.
     *
     * @param part the element to add.
     */
    public void add(BasicText part) {
        content.add(part);
    }

    /**
     * We return the content of a paragraph in order to make visitors possible.
     *
     * @return the contents of the paragraph.
     */
    public List<BasicText> getContent() {
        return content;
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder("");
        for (BasicText element : content) {
            result.append(element.getText() + " ");
        }
        return result.toString().trim();
    }

    @Override
    public <R> R accept(DocumentVisitor<R> visitor) {
        return visitor.visitParagraph(this);
    }
}
