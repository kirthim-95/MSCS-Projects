package document;

import document.element.*;

import java.util.List;

public class WordCountVisitor implements DocumentVisitor<String> {

    @Override
    public String visitBasicText(BasicText current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String visitBoldText(BoldText current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String visitHeading(Heading current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String visitHyperText(HyperText current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String visitItalicText(ItalicText current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String visitParagraph(Paragraph current) {
        return String.valueOf(current.getText().split(" ").length);
    }

    @Override
    public String toString(List<TextElement> content) {
        int count = 0;
        for(TextElement element : content) {
            count += element.getText().split(" ").length;
        }
        return String.valueOf(count);
    }
}
