package com.venkatyarlagadda.SpringDataSplitAccess.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.venkatyarlagadda.SpringDataSplitAccess.entity.Student;
import com.venkatyarlagadda.SpringDataSplitAccess.repository.StudentRepository;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */

@Component
public class StudentController {
	
	private StudentLogController studentLogController;
	private StudentRepository studentRepository;
	
	private StudentController(final StudentLogController studentLogController, final  StudentRepository studentRepository) {
		this.studentLogController = studentLogController;
		this.studentRepository = studentRepository;
	}

	public ResponseEntity<List<Student>> getAllStudents() {
		return ResponseEntity.ok(studentRepository.findAll());
	}

	public ResponseEntity<Student> getStudent(final Long studentId) {
		studentLogController.createStudentLog(studentId, "Get Student");
		return ResponseEntity.ok(studentRepository.findById(studentId).get());
	}

	public ResponseEntity<Student> addStudent(final Student student) {
		student.setCreatedDate(LocalDateTime.now());
		final Student savedStudent = studentRepository.save(student);
		System.out.println("Student Saved at: " +LocalDateTime.now());
		studentLogController.createStudentLog(savedStudent.getId(), "Add Student");
		return ResponseEntity.ok(savedStudent);
	}

	public ResponseEntity<Boolean> deleteStudent(final Long studentId) {
		studentRepository.findById(studentId);
		studentLogController.createStudentLog(studentId, "Delete Student");
		return ResponseEntity.ok(Boolean.TRUE);
	}
}