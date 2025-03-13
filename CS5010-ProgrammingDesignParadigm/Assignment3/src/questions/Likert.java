package questions;

import java.awt.*;

public class Likert implements Question{
    private final String text;

    public Likert(String text) {
        this.text = text;
    }
    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String answer(String input) {
        try {
            int answer = Integer.parseInt(input);
            if(answer >= 1 && answer <= 5) {
                return "Correct";
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        return "Incorrect";
    }

    //Likert comes after all the types of questions
    @Override
    public int compareTo(Question question) {
        if (question instanceof TrueFalse || question instanceof MultipleChoice || question instanceof MultipleSelect) {
            return 1;
        }
        if(question instanceof Likert) {
            return this.text.compareTo(question.getText());
        }
        return -1;
    }
}
