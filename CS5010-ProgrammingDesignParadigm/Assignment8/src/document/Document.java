package document;

import java.util.ArrayList;
import java.util.List;

import document.element.TextElement;

public class Document {
  
  private final List<TextElement> content;
  
  public Document() {
    this.content = new ArrayList<>();
  }

  public void add(TextElement e) {
    this.content.add(e);
  }

  public int countWords() {

    int result = 0;

    for (TextElement e : this.content) {
      result += e.getText().split(" ").length;
    }

    return result;
  }

  public String toText(DocumentVisitor<String> visitor) {
    return visitor.toString(this.content);
  }
}
