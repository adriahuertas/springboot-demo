package com.example.springbootdemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        Sort sortById = Sort.by(Sort.Order.asc("id"));
        return studentRepository.findAll(sortById);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already registered");
        }
    studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student with id "+studentId+" doesn't exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id "+studentId+ " not found"
                ));

        if (name != null && name.length() < 3) {
            throw new IllegalStateException("Name must be atleast 3 letters long");
        }

        if (email != null && email.length() >= 3) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email already registered");
            }
            student.setEmail(email);
        }
        if (name != null) {
            student.setName(name);
        }
    }

    public Optional<Student> getStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }
}
