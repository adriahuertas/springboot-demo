package com.example.springbootdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            List<Student> students = new ArrayList<>();

            // Dummy data made with ChatGPT
            students.add(new Student("John Smith", LocalDate.of(1995, 3, 12), "john.smith@example.com"));
            students.add(new Student("Emily Johnson", LocalDate.of(1998, 8, 5), "emily.johnson@example.com"));
            students.add(new Student("Michael Davis", LocalDate.of(1997, 6, 18), "michael.davis@example.com"));
            students.add(new Student("Sophia Wilson", LocalDate.of(1999, 1, 25), "sophia.wilson@example.com"));
            students.add(new Student("William Brown", LocalDate.of(1996, 10, 8), "william.brown@example.com"));
            students.add(new Student("Olivia Miller", LocalDate.of(1997, 4, 30), "olivia.miller@example.com"));
            students.add(new Student("James Garcia", LocalDate.of(1994, 7, 22), "james.garcia@example.com"));
            students.add(new Student("Ava Martinez", LocalDate.of(1999, 11, 15), "ava.martinez@example.com"));
            students.add(new Student("Benjamin Johnson", LocalDate.of(1996, 2, 9), "benjamin.johnson@example.com"));
            students.add(new Student("Mia Taylor", LocalDate.of(1998, 9, 2), "mia.taylor@example.com"));
            students.add(new Student("Ethan Anderson", LocalDate.of(1995, 5, 20), "ethan.anderson@example.com"));
            students.add(new Student("Isabella Clark", LocalDate.of(1997, 12, 10), "isabella.clark@example.com"));
            students.add(new Student("Liam Rodriguez", LocalDate.of(1996, 4, 14), "liam.rodriguez@example.com"));
            students.add(new Student("Sophia White", LocalDate.of(1998, 3, 28), "sophia.white@example.com"));
            students.add(new Student("William Harris", LocalDate.of(1997, 8, 7), "william.harris@example.com"));
            students.add(new Student("Ava Lee", LocalDate.of(1995, 7, 17), "ava.lee@example.com"));
            students.add(new Student("James Walker", LocalDate.of(1996, 6, 23), "james.walker@example.com"));
            students.add(new Student("Olivia King", LocalDate.of(1999, 10, 1), "olivia.king@example.com"));
            students.add(new Student("Benjamin Lewis", LocalDate.of(1994, 12, 4), "benjamin.lewis@example.com"));
            students.add(new Student("Emily Hall", LocalDate.of(1996, 1, 28), "emily.hall@example.com"));


            repository.saveAll(students);
        };
    }
}
