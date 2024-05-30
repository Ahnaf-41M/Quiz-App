package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Test findByTestId(int testId);

    boolean existsByTestName(String testName);
}
