package com.softserve.teachua.repository.test;

import com.softserve.teachua.model.test.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {
}
