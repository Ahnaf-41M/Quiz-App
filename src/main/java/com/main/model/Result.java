package com.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Entity
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int rId;
	@Column(unique = true, nullable = false)
	private String userId;
	private String userName;
	private int totalCorrect = 0;
}
