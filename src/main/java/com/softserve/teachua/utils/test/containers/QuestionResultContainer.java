package com.softserve.teachua.utils.test.containers;

import com.softserve.teachua.dto.test.question.QuestionResult;
import lombok.Getter;

import java.util.Set;

import static com.softserve.teachua.utils.test.Messages.*;

@Getter
public class QuestionResultContainer {
    private int grade = 0;
    private int correctAmount = 0;
    private int correctSelectedAmount = 0;

    public void incrementCorrectSelectedAmount() {
        correctSelectedAmount++;
    }

    public void incrementCorrectAmount() {
        correctAmount++;
    }

    public void increaseGrade(int value) {
        grade += value;
    }

    public void decreaseGrade(int value) {
        grade -= value;
    }

    public int getGrade() {
        return Math.max(grade, 0);
    }

    public void setQuestionResultStatus(QuestionResult result, Set<Long> selectedAnswerIds) {
        if (correctSelectedAmount == correctAmount
                && correctAmount == selectedAnswerIds.size()) {
            result.setStatus(CORRECT_MESSAGE);
        } else if (correctSelectedAmount == 0) {
            result.setStatus(INCORRECT_MESSAGE);
        } else {
            result.setStatus(PARTIALLY_CORRECT_MESSAGE);
        }
    }
}
