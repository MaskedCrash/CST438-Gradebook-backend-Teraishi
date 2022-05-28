package com.cst438.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;

@RestController
public class EnrollmentController {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	/*
	 * endpoint used by registration service to add an enrollment to an existing
	 * course.
	 */
	@PostMapping("/enrollment")
	@Transactional
	public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
		Enrollment enrollment = new Enrollment();
		Course course = courseRepository.findById(enrollmentDTO.course_id).orElse(null);
		enrollment.setId(enrollmentDTO.id);
		enrollment.setCourse(course);
		enrollment.setStudentEmail(enrollmentDTO.studentEmail);
		enrollment.setStudentName(enrollmentDTO.studentName);
		
		Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
		EnrollmentDTO result = createEnrollmentDTO(savedEnrollment);
		return result;
	}
	
	private EnrollmentDTO createEnrollmentDTO(Enrollment enrollment) {
		EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
		Course course = enrollment.getCourse();
		enrollmentDTO.id = enrollment.getId();
		enrollmentDTO.course_id = course.getCourse_id();
		enrollmentDTO.studentEmail = enrollment.getStudentEmail();
		enrollmentDTO.studentName = enrollment.getStudentName();
		
		return enrollmentDTO;
	}

}
