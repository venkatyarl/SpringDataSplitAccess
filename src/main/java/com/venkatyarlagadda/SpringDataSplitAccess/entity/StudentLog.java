package com.venkatyarlagadda.SpringDataSplitAccess.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class StudentLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long studentId;
	private String logMessage;
	private LocalDateTime createdDate;
	
	public static StudentLog build(final Long studentId, final String logMessage) {
		//@formatter:off
		return StudentLog
				.builder()
				.studentId(studentId)
				.logMessage(logMessage)
				.createdDate(LocalDateTime.now())
				.build();
		//@formatter:on
	}
}
