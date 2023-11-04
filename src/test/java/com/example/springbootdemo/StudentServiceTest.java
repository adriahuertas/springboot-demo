package com.example.springbootdemo;

import com.example.springbootdemo.student.Student;
import com.example.springbootdemo.student.StudentController;
import com.example.springbootdemo.student.StudentService;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentService studentService;

    @Test
    void getStudentsShouldReturn20Students(){
        List<Student> students = studentService.getStudents();
        assertEquals(students.size(), 20);
    }

    @Test
    void getStudentShouldReturnOneStudent() {
        Optional<Student> studentOptional = studentService.getStudent(1L);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            assertEquals(student.getName(), "John Smith");
            assertEquals(student.getEmail(), "john.smith@example.com");
        }
    }

    @Test
    void addNewStudentShouldAddOneStudent() {
        List<Student> students = studentService.getStudents();
        int prevSize = students.size();

        Student student = new Student("Pablo picaso", LocalDate.of(1995, 3, 12), "pablo.picaso@example.com");

        Student addedStudent = studentService.addNewStudent(student);

        List<Student> newStudents = studentService.getStudents();
        int currentSize = newStudents.size();

        assertEquals(prevSize + 1, currentSize);

        // Delete the added student to reverse to initial state
        studentService.deleteStudent(addedStudent.getId());
    }

    @Test
    void addNewStudentWithExistingEmailShouldThrowException() {
        // Estudiante cuyo email ya existe
        Student existingStudent = new Student("John Smith25", LocalDate.of(1990, 1, 15), "john.smith@example.com");

        // Verifica que se lance una excepción y captura la excepción lanzada
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            studentService.addNewStudent(existingStudent);
        });

        // Verifica que el mensaje de error contenga el texto esperado
        String expectedErrorMessage = "Email already registered";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    void deleteStudentShouldDecreaseListSize() {
        List<Student> students = studentService.getStudents();

        // Obtener tamaño
        int prevSize = students.size();

        // Añade estudiante a la lista
        Student student = new Student("Pablo", LocalDate.of(1995, 3, 12), "pablo@example.com");
        Student addedStudent = studentService.addNewStudent(student);

        // Eliminar estudiante
        studentService.deleteStudent(addedStudent.getId());

        // Obtener nuevo tamaño
        List<Student> newStudents = studentService.getStudents();
        int currentSize = newStudents.size();

        // Verificar que el tamaño se ha decrementado por 1
        assertEquals(prevSize, currentSize);

    }

    @Test
    void deleteStudentShouldThrowErrorWhenStudentDoesNotExist() {
        // Verificar que se lanza excepcion cuando se intenta eliminar un estudiante que no existe
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            studentService.deleteStudent(200L);
        });

        // Verifica que el mensaje de error contenga el texto esperado
        String expectedErrorMessage = "student with id 200 doesn't exist";
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    void updateStudentShouldUpdateNameAndEmail() {
        // Modificar name y email de un estudiante cualquiera
        studentService.updateStudent(5L, "Adrià Huertas","newemail@gmail.com");

        // Obtener estudiante modificado
        Optional<Student> studentOptional = studentService.getStudent(5L);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            // Verificar que los cambios se han registrado correctamente
            assertEquals(student.getName(), "Adrià Huertas");
            assertEquals(student.getEmail(), "newemail@gmail.com");
        }
    }

    @Test
    void updateStudentShouldThrowErrorIfEmailExist() {
        // Obtener estudiantee cualquiera
        Optional<Student> studentOptional = studentService.getStudent(12L);
        Optional<Student> studentOptional2 = studentService.getStudent(13L);

        if (studentOptional.isPresent() && studentOptional2.isPresent()) {
            Student student = studentOptional.get();
            Student student2 = studentOptional2.get();

            // Verificar que se lanza excepcion cuando se intenta asignar un email que ya existe
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                studentService.updateStudent(student.getId(), "Adrià Huertas", student2.getEmail());
            });

            // Verifica que el mensaje de error contenga el texto esperado
            String expectedErrorMessage = "Email already registered";
            String actualErrorMessage = exception.getMessage();
            assertEquals(expectedErrorMessage, actualErrorMessage);
        }
    }
}
