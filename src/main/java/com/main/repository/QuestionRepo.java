package com.main.repository;

import org.springframework.stereotype.Repository;
import com.main.model.Question;
import org.springframework.data.jpa.repository.*;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
