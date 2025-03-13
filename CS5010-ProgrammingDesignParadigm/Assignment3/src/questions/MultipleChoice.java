package questions;

import java.util.List;

public class MultipleChoice implements Question {
    private final String text;
    private final String correctAnswer;
    private final List<String> options;
    // Constructor accepting 3 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3});
    }

    // Constructor accepting 4 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3, String option4) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4});
    }

    // Constructor accepting 5 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5});
    }

    // Constructor accepting 6 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6});
    }

    // Constructor accepting 7 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6, String option7) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6, option7});
    }

    // Constructor accepting 8 options
    public MultipleChoice(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6, String option7, String option8) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6, option7, option8});
    }
    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String answer(String input) {
        return input.equals(this.correctAnswer) ? "Correct" : "Incorrect";
    }

    public List<String> getOptions() {
        return this.options;
    }

    //MultipleChoice < TrueFalse
    @Override
    public int compareTo(Question question) {
        if (question instanceof TrueFalse) {
            return 1;
        }
        if (question instanceof MultipleChoice) {
            return this.text.compareTo(question.getText());
        }
        return -1;
    }
}
