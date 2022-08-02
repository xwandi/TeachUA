package com.softserve.teachua.service.test;

import com.softserve.teachua.dto.test.result.CreateResult;
import com.softserve.teachua.model.test.Answer;
import com.softserve.teachua.model.test.Question;
import com.softserve.teachua.model.test.Result;

import java.util.List;

public interface ResultService {
    Result findById(Long id);
    int countGrade(CreateResult resultDto, List<Question> questions);
    List<Answer> getSelectedAnswers(CreateResult resultDto);
    void createQuestionHistory(Result result, List<Answer> selectedAnswers);
}
