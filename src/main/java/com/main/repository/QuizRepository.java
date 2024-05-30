package com.main.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    boolean existsByQuizId(int quizId);

    boolean existsByQuesId(int quesId);

    boolean existsByQuizIdAndQuesId(int quizId, int quesId);

    List<Quiz> findByQuizId(int quizId);

    Quiz findByQuesId(int quesId);

    Quiz findByQuizIdAndQuesId(int quizId, int quesId);
}
