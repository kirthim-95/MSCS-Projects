package document.element;

import document.DocumentVisitor;

/**
 * Representation of a hypertext link.
 */
public class HyperText extends BasicText {

    /**
     * The associated URL.
     */
    private String url;

    /**
     * Constructor for the HyperText.
     *
     * @param text the text
     * @param url  the url
     */
    public HyperText(String text, String url) {
        super(text);
        this.url = url;
    }

    /**
     * Returns the url for this hypertext.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

  @Override
  public <R> R accept(DocumentVisitor<R> visitor) {
    return visitor.visitHyperText(this);
  }
}
