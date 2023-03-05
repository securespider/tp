package seedu.address.model.student;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * Student ID is the unique identifier for a student.
 * Optional: Parent Name, Parent Phone, Parent Email
 * Student Particulars
 * Compulsory:
 * Test    test/<TEST_NAME>
 * String value of test name
 * Attendance    att/<ATTENDANCE>
 * Homework    hw/<HOMEWORK>
 * Grade    gde/<GRADE>
 * Parents / Next-of-kin    nok/<NOK>
 * Optional:
 * Image [img]
 * Age [age]
 * CCA [cca]
 * Comments [cmt]
 */
public class Student extends Person {
    private final int studentId;
    private final String className;

    // String value of gender
    private final boolean isMale;

    // Mark as present / not present
    private final boolean attendance;

    // String value of homework name
    private final Homework homework;

    // String value of grade results
    private final Test test;

    // Replace with parent class when done
    private final Person nok;

    private final Optional<String> image;

    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, int studentId, String className, boolean isMale, boolean attendance, Homework homework, Test test, Person nok, String image) {
        super(name, phone, email, address, tags);
        this.studentId = studentId;
        this.className = className;
        this.isMale = isMale;
        this.attendance = attendance;
        this.homework = homework;
        this.test = test;
        this.nok = nok;
        this.image = Optional.of(image);
    }

    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, int studentId, String className, boolean isMale, boolean attendance, Homework homework, Test test, Person nok) {
        super(name, phone, email, address, tags);
        this.studentId = studentId;
        this.className = className;
        this.isMale = isMale;
        this.attendance = attendance;
        this.homework = homework;
        this.test = test;
        this.nok = nok;
        this.image = Optional.empty();
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", className='" + className + '\'' +
                ", isMale=" + isMale +
                ", attendance=" + attendance +
                ", homework=" + homework +
                ", test=" + test +
                ", nok=" + nok +
                ", image=" + image +
                '}';
    }
}
