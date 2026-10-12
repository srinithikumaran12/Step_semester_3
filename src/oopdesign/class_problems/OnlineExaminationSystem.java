package oopdesign.class_problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Question 3: Online Examination System
 *
 * Classes and relationships:
 *   Question (abstract) <|-- MultipleChoiceQuestion, TrueFalseQuestion, ShortAnswerQuestion
 *   Examination 1 ---- 1..* Question                (composition: questions belong to the exam)
 *   Student 1 ---- 0..* Attempt ---- 1 Examination
 *   Attempt 1 ---- 0..* recorded answers (question number -> answer text)
 *
 * Each Question type evaluates itself. Attempt.submit() only calls
 * Question.evaluate(), so a new question type needs no change to the
 * submission and scoring logic. A student can have only one SUBMITTED
 * attempt per examination, and a submitted attempt is read-only.
 */
public class OnlineExaminationSystem {

    // ---------- Student ----------

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    // ---------- Question hierarchy ----------

    static abstract class Question {
        private final int number;
        private final String text;
        private final int points;

        Question(int number, String text, int points) {
            this.number = number;
            this.text = text;
            this.points = points;
        }

        abstract boolean isCorrect(String answer);

        // Common evaluation - identical for every question type
        int evaluate(String answer) {
            return (answer != null && isCorrect(answer)) ? points : 0;
        }

        int getNumber() {
            return number;
        }

        String getText() {
            return text;
        }

        int getPoints() {
            return points;
        }
    }

    static class MultipleChoiceQuestion extends Question {
        private final String correctOption;

        MultipleChoiceQuestion(int number, String text, int points, String correctOption) {
            super(number, text, points);
            this.correctOption = correctOption;
        }

        @Override
        boolean isCorrect(String answer) {
            return correctOption.equalsIgnoreCase(answer.trim());
        }
    }

    static class TrueFalseQuestion extends Question {
        private final boolean correctValue;

        TrueFalseQuestion(int number, String text, int points, boolean correctValue) {
            super(number, text, points);
            this.correctValue = correctValue;
        }

        @Override
        boolean isCorrect(String answer) {
            return String.valueOf(correctValue).equalsIgnoreCase(answer.trim());
        }
    }

    static class ShortAnswerQuestion extends Question {
        private final String expectedAnswer;

        ShortAnswerQuestion(int number, String text, int points, String expectedAnswer) {
            super(number, text, points);
            this.expectedAnswer = expectedAnswer;
        }

        @Override
        boolean isCorrect(String answer) {
            return expectedAnswer.equalsIgnoreCase(answer.trim());
        }
    }

    // ---------- Examination ----------

    static class Examination {
        private final String name;
        private final List<Question> questions = new ArrayList<>();
        private final Set<Student> studentsWhoSubmitted = new HashSet<>();

        Examination(String name) {
            this.name = name;
        }

        void addQuestion(Question question) {
            questions.add(question);
        }

        String getName() {
            return name;
        }

        List<Question> getQuestions() {
            return new ArrayList<>(questions); // defensive copy
        }

        int getTotalPoints() {
            int total = 0;
            for (Question q : questions) {
                total += q.getPoints();
            }
            return total;
        }

        Question findQuestion(int number) {
            for (Question q : questions) {
                if (q.getNumber() == number) {
                    return q;
                }
            }
            return null;
        }

        boolean hasSubmitted(Student student) {
            return studentsWhoSubmitted.contains(student);
        }

        void recordSubmission(Student student) {
            studentsWhoSubmitted.add(student);
        }

        Attempt start(Student student) {
            if (hasSubmitted(student)) {
                System.out.println(student.getName() + " has already submitted " + name + ".");
                return null;
            }
            System.out.println(name + " started by " + student.getName() + ".");
            return new Attempt(student, this);
        }
    }

    // ---------- Attempt ----------

    static class Attempt {
        enum State { IN_PROGRESS, SUBMITTED }

        private final Student student;
        private final Examination exam;
        private final Map<Integer, String> answers = new LinkedHashMap<>();
        private State state = State.IN_PROGRESS;

        Attempt(Student student, Examination exam) {
            this.student = student;
            this.exam = exam;
        }

        void answer(int questionNumber, String answer) {
            if (state == State.SUBMITTED) {
                System.out.println("Cannot change answers for a submitted examination.");
                return;
            }
            if (exam.findQuestion(questionNumber) == null) {
                System.out.println("Question " + questionNumber + " does not exist in " + exam.getName() + ".");
                return;
            }
            answers.put(questionNumber, answer);
            System.out.println("Answer recorded for Question " + questionNumber + ".");
        }

        void submit() {
            if (state == State.SUBMITTED) {
                System.out.println("This attempt has already been submitted.");
                return;
            }
            state = State.SUBMITTED;
            exam.recordSubmission(student);
            System.out.println(exam.getName() + " submitted by " + student.getName() + ".");
            printResult();
        }

        private void printResult() {
            StringBuilder result = new StringBuilder("Result: ");
            int score = 0;
            List<Question> questions = exam.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                int earned = q.evaluate(answers.get(q.getNumber())); // polymorphic evaluation
                score += earned;

                result.append("Question ").append(q.getNumber()).append(": ")
                        .append(earned > 0 ? "Correct" : "Incorrect")
                        .append(" (").append(earned).append(" points)");
                if (i < questions.size() - 1) {
                    result.append(", ");
                }
            }

            result.append(". Total score: ").append(score).append("/").append(exam.getTotalPoints()).append(".");
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        Examination examA = new Examination("Exam A");
        examA.addQuestion(new MultipleChoiceQuestion(1, "Which keyword creates an object?", 5, "C"));
        examA.addQuestion(new TrueFalseQuestion(2, "A Java interface can have instance fields.", 5, false));

        Student student1 = new Student("Student 1");

        Attempt attempt = examA.start(student1);
        attempt.answer(1, "C");
        attempt.answer(2, "True");
        attempt.submit();
        attempt.answer(1, "A"); // rejected - already submitted
    }
}