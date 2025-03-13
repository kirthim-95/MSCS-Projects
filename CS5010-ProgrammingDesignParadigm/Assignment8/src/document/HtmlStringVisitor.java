package document;

import document.element.*;

import java.util.List;

public class HtmlStringVisitor implements DocumentVisitor<String> {

    @Override
    public String visitBasicText(BasicText current) {
        return current.getText();
    }

    @Override
    public String visitBoldText(BoldText current) {
        return "<b>" + current.getText() + "</b>";
    }

    @Override
    public String visitHeading(Heading current) {
        return "<h" + current.getLevel() + ">" + current.getText() + "</h" + current.getLevel() + ">";
    }

    @Override
    public String visitHyperText(HyperText current) {
        return "<a href=\"" + current.getUrl() + "\">" + current.getText() + "</a>";
    }

    @Override
    public String visitItalicText(ItalicText current) {
        return "<i>" + current.getText() + "</i>";
    }

    @Override
    public String visitParagraph(Paragraph current) {
        List<BasicText> list = current.getContent();
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        for(BasicText basicText : list) {
            sb.append(basicText.accept(this)).append("\n");
        }
        sb.append("</p>");
        return sb.toString();
    }

    @Override
    public String toString(List<TextElement> content) {
       StringBuilder sb = new StringBuilder();
       for(int i = 0; i < content.size(); i++) {
           sb.append(content.get(i).accept(this));
           if(i != content.size() - 1) {
               sb.append("\n");
           }
       }
       return sb.toString();
    }
}
