package document;

import document.element.*;

import java.util.List;

public class BasicStringVisitor implements DocumentVisitor<String> {

    @Override
    public String visitBasicText(BasicText current) {
        return current.getText();
    }

    @Override
    public String visitBoldText(BoldText current) {
        return current.getText();
    }

    @Override
    public String visitHeading(Heading current) {
        return current.getText();
    }

    @Override
    public String visitHyperText(HyperText current) {
        return current.getText();
    }

    @Override
    public String visitItalicText(ItalicText current) {
        return current.getText();
    }

    @Override
    public String visitParagraph(Paragraph current) {
        return current.getText();
    }

    @Override
    public String toString(List<TextElement> content) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < content.size(); i++) {
            TextElement e = content.get(i);
            sb.append(e.accept(this));

            if(i != content.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
