package seedu.address.model.student;

import java.time.LocalDate;

public class Homework extends Assignment {
    private boolean isDone;

    public Homework(String assignmentName, LocalDate deadline, int weightage, int maxScore, boolean isDone) {
        super(assignmentName, deadline, weightage, maxScore);
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
