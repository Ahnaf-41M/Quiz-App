package com.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int rId;
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String userName;
	@Column(nullable = false)
	private int testId;
	@Column(nullable = false)
	private String testName;
	private int totalCorrect = 0;
}
