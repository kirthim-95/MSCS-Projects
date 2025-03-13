package questions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultipleSelect implements Question {
    private final String text;
    private final String correctAnswers;
    private final List<String> options;

    // Constructor accepting 3 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3});
    }

    // Constructor accepting 4 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3, String option4) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4});
    }

    // Constructor accepting 5 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5});
    }

    // Constructor accepting 6 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6});
    }

    // Constructor accepting 7 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6, String option7) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6, option7});
    }

    // Constructor accepting 8 options
    public MultipleSelect(String text, String correctAnswer, String option1, String option2, String option3, String option4, String option5, String option6, String option7, String option8) {
        this.text = text;
        this.correctAnswers = correctAnswer;
        this.options = List.of(new String[]{option1, option2, option3, option4, option5, option6, option7, option8});
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String answer(String input) {
        List<String> inputChoices = List.of(input.split(" "));
        List<String> correctChoices = List.of(this.correctAnswers.split(" "));
        return new HashSet<>(inputChoices).containsAll(correctChoices) && new HashSet<>(correctChoices).containsAll(inputChoices) ? "Correct" : "Incorrect";
    }

    //MultipleSelect < MultipleChoice < MultipleSelect
    @Override
    public int compareTo(Question question) {
        if (question instanceof TrueFalse || question instanceof MultipleChoice) {
            return 1;
        }
        if (question instanceof MultipleSelect) {
            return this.text.compareTo(question.getText());
        }
        return -1;
    }
}
