package com.main.model;

import javax.persistence.Column;
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
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dashboardId;
    @Column(nullable = false)
    private int quizId;
    @Column(nullable = false)
    private int quesId;
}
