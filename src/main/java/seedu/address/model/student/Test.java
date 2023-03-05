package seedu.address.model.student;

import java.time.LocalDate;

public class Test extends Assignment {
    private int score;

    public Test(String assignmentName, LocalDate deadline, int weightage, int maxScore, int score) {
        super(assignmentName, deadline, weightage, maxScore);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
