package questions;

public class TrueFalse implements Question {
    private final String text;
    private final String answer;

    public TrueFalse(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String answer(String input) {
        return this.answer.equalsIgnoreCase(input) ? "Correct" : "Incorrect";
    }

    //TrueFalse comes above all the other type of questions
    @Override
    public int compareTo(Question question) {
        if (question instanceof TrueFalse) {
            return this.text.compareTo(question.getText());
        }
        return -1;
    }
}
