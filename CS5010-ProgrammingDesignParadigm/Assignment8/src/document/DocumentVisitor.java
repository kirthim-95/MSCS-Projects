package document;

import document.element.*;

import java.util.List;

public interface DocumentVisitor<R> {
  
  R visitBasicText(BasicText current);
  
  R visitBoldText(BoldText current);

  R visitHeading(Heading current);

  R visitHyperText(HyperText current);

  R visitItalicText(ItalicText current);

  R visitParagraph(Paragraph current);

  String  toString(List<TextElement> content);
}
