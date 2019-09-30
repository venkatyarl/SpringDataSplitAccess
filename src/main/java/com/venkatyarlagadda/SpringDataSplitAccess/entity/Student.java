package com.venkatyarlagadda.SpringDataSplitAccess.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */

@Data
@Entity
@Table
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private LocalDateTime dateOfBirth;
	private LocalDateTime createdDate;
}
