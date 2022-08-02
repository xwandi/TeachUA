package com.softserve.teachua.service.test.impl;

import com.softserve.teachua.converter.DtoConverter;
import com.softserve.teachua.dto.test.result.CreateResult;
import com.softserve.teachua.dto.test.result.SuccessCreatedResult;
import com.softserve.teachua.model.User;
import com.softserve.teachua.model.test.*;
import com.softserve.teachua.repository.test.QuestionHistoryRepository;
import com.softserve.teachua.repository.test.ResultRepository;
import com.softserve.teachua.service.UserService;
import com.softserve.teachua.service.test.AnswerService;
import com.softserve.teachua.service.test.QuestionService;
import com.softserve.teachua.service.test.ResultService;
import com.softserve.teachua.service.test.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final QuestionService questionService;
    private final QuestionHistoryRepository questionHistoryRepository;
    private final AnswerService answerService;

    public List<Result> findResultsByTest(Test test) {
        return resultRepository.findResultsByTest(test);
    }

    public List<Result> findResultsByUser(User user) {
        return resultRepository.findResultsByUser(user);
    }

    @Override
    public Result findById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("There is no result with id '%s'", id)
                ));
    }

    @Override
    public List<Answer> getSelectedAnswers(CreateResult resultDto){
        List<Answer> selectedAnswers = new ArrayList<>();
        for(Long l: resultDto.getSelectedAnswersId()){
            selectedAnswers.add(answerService.findById(l).orElseThrow(() -> new NoSuchElementException(
                    String.format("There is no result with id '%s'", l)
            )));
        }
        return selectedAnswers;
    }

    public void createQuestionHistory(Result result, List<Answer> selectedAnswers) {
        for (Answer a : selectedAnswers) {
            QuestionHistory questionHistory = new QuestionHistory();
            questionHistory.setResult(result);
            questionHistory.setAnswer(a);
            questionHistoryRepository.save(questionHistory);
        }
        resultRepository.save(result);
    }

    public int countGrade(CreateResult resultDto, List<Question> questions) {
        int grade = 0;
        List<Answer> selectedAnswers = getSelectedAnswers(resultDto);
        for (Question q : questions) {
            grade += countGradeForQuestion(q, selectedAnswers);
        }
        return grade;
    }

    private int countGradeForQuestion(Question q, List<Answer> selectedAnswers) {
        int gradeForQuestion = 0;
        if (q.getQuestionType().getTitle().equals("radio")) {
            for (Answer a : q.getAnswers()) {
                if (a.isCorrect() && selectedAnswers.contains(a)) {
                    gradeForQuestion += a.getValue();
                }
            }
        } else if (q.getQuestionType().getTitle().equals("checkbox")) {
            for (Answer a : q.getAnswers()) {
                if (a.isCorrect() && selectedAnswers.contains(a)) {
                    gradeForQuestion += a.getValue();
                } else if (a.isCorrect() && !selectedAnswers.contains(a)) {
                    gradeForQuestion -= a.getValue();
                } else if (!a.isCorrect() && selectedAnswers.contains(a)) {
                    gradeForQuestion -= a.getValue();
                }
            }
        }
        if (gradeForQuestion < 0) gradeForQuestion = 0;
        return gradeForQuestion;
    }
}
