package com.main.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Question;
import org.springframework.data.jpa.repository.*;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

}
