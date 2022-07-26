package com.softserve.teachua.service.test.impl;

import com.softserve.teachua.repository.test.QuestionCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class QuestionCategoryServiceImpl {
    private final QuestionCategoryRepository questionCategoryRepository;
}
