package com.venkatyarlagadda.SpringDataSplitAccess.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.venkatyarlagadda.SpringDataSplitAccess.entity.StudentLog;
import com.venkatyarlagadda.SpringDataSplitAccess.repository.StudentLogRepository;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */

@Component
public class StudentLogController {
	
	@Autowired
	private StudentLogRepository studentLogRepository;
	
	@Async
	public void createStudentLog(final Long studentId, final String logMessage) {
		final StudentLog studentLog = StudentLog.build(studentId, logMessage);
		System.out.println("Student Log Saved at: " +LocalDateTime.now());
		studentLogRepository.save(studentLog);
	}
	
	public ResponseEntity<List<StudentLog>> getAllStudentLogs(){
		return ResponseEntity.ok(studentLogRepository.findAll());
	}
}