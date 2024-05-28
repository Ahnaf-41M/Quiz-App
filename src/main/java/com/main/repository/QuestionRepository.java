package com.main.repository;

import org.springframework.stereotype.Repository;
import com.main.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.*;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTestId(int testId);

    Question findByQuesId(int quesId);
}
