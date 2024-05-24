package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Topic findByTopicId(int topicId);
}
