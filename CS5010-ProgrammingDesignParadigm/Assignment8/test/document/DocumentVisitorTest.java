package document;

import document.element.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentVisitorTest {

    Document document;

    @Before
    public void setup() {

        document = new Document();

        Paragraph paragraph1 = new Paragraph();
        paragraph1.add(new BoldText("Bold1"));
        paragraph1.add(new ItalicText("Italic1"));
        paragraph1.add(new Heading("Heading1", 1));
        paragraph1.add(new Heading("Heading2", 2));
        paragraph1.add(new HyperText("Google to find more", "www.google.com"));
        document.add(paragraph1);

        Paragraph paragraph3 = new Paragraph();
        paragraph3.add(new BoldText("Bold3"));
        paragraph3.add(new ItalicText("Italic3"));
        document.add(paragraph3);

    }

    @Test
    public void basicStringVisitor() {
        assertEquals("Bold1 Italic1 Heading1 Heading2 Google to find more Bold3 Italic3", document.toText(new BasicStringVisitor()));
    }

    @Test
    public void testHtmlStringVisitor() {
        assertEquals("<p><b>Bold1</b>\n" +
                "<i>Italic1</i>\n" +
                "<h1>Heading1</h1>\n" +
                "<h2>Heading2</h2>\n" +
                "<a href=\"www.google.com\">Google to find more</a>\n" +
                "</p>\n" +
                "<p><b>Bold3</b>\n" +
                "<i>Italic3</i>\n" +
                "</p>", document.toText(new HtmlStringVisitor()));
    }

    @Test
    public void testMarkdownStringVisitor() {
        assertEquals("\n**Bold1**\n" +
                "*Italic1*\n" +
                "# Heading1\n" +
                "## Heading2\n" +
                "[Google to find more](www.google.com)\n" +
                "\n" +
                "**Bold3**\n" +
                "*Italic3*", document.toText(new MarkdownStringVisitor()));
    }

    @Test
    public void testWordCountVisitor() {
        assertEquals("10", document.toText(new WordCountVisitor()));
    }
}