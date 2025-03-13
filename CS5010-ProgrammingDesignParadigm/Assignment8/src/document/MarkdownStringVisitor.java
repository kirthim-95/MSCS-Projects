package document;

import document.element.*;

import java.util.List;

public class MarkdownStringVisitor implements DocumentVisitor<String> {

    @Override
    public String visitBasicText(BasicText current) {
        return current.getText();
    }

    @Override
    public String visitBoldText(BoldText current) {
        return "**" + current.getText() + "**";
    }

    @Override
    public String visitHeading(Heading current) {
        StringBuilder sb = new StringBuilder();
        int level = current.getLevel();
        while(level-- != 0) {
            sb.append("#");
        }
        sb.append(" ");
        return sb.append(current.getText()).toString();
    }

    @Override
    public String visitHyperText(HyperText current) {
        return "[" + current.getText() + "](" + current.getUrl() + ")";
    }

    @Override
    public String visitItalicText(ItalicText current) {
        return "*" + current.getText() + "*";
    }

    @Override
    public String visitParagraph(Paragraph current) {
        List<BasicText> basicTexts = current.getContent();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0; i < basicTexts.size(); i++) {
            sb.append(basicTexts.get(i).accept(this));
            if(i != basicTexts.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString(List<TextElement> content) {
        StringBuilder sb = new StringBuilder();
        for(TextElement element : content) {
            sb.append(element.accept(this));
            if (element != content.get(content.size() - 1)) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
