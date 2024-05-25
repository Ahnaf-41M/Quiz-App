package com.main.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByUserId(String userId);

    boolean existsByUserIdAndTestId(String userId, int testId);

}
