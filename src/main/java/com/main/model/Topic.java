package com.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int topicId;
    private String topicName;
    private String topicDescription;
}
