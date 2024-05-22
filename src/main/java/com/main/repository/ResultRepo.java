package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    Result findByUserId(String userId);

}
