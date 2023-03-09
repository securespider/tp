package seedu.address.model.student;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Assignment {
    private final String assignmentName;
    private LocalDate deadline;
    private final int weightage;
    private final int maxScore;

    public Assignment(String assignmentName, LocalDate deadline, int weightage, int maxScore) {
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.weightage = weightage;
        this.maxScore = maxScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return weightage == that.weightage && maxScore == that.maxScore && assignmentName.equals(that.assignmentName) && Objects.equals(deadline, that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentName, deadline, weightage, maxScore);
    }
}
