package com.example.springbootdemo;

import com.example.springbootdemo.student.Student;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTest {
    private static final String apiUrl = "/api/v1/students";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Students API");
    }

    @Test
    void greetingShouldContainAuthorName() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Adrià Huertas");
    }

    @Test
    void studentsRouteShouldContainInitialStudents() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + apiUrl, String.class);

        // Verifica que el código de respuesta sea 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verifica que el contenido de la respuesta contiene "John Smith" (según tu prueba original)
        assertThat(responseEntity.getBody()).contains("John Smith");
    }

    @Test
    void studentsIdRouteShouldReturnOneStudent() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://Localhost:" + port + apiUrl, String.class);

        // Verificar codigo de respuesta
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar contenido
        Student student = new Student("Michael Davis", LocalDate.of(1997, 6, 18), "michael.davis@example.com");
        assertThat(responseEntity.getBody()).contains(student.getName());
        assertThat(responseEntity.getBody()).contains(student.getEmail());
    }

    @Test
    void createStudentShouldReturnCreatedStatus() {
        Student student = new Student("Michael Davis2", LocalDate.of(1997, 6, 18), "michael.davis2@example.com");
        // Realiza una solicitud POST para crear un nuevo estudiante
        ResponseEntity<Void> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + apiUrl, student, Void.class);

        // Verifica que el código de respuesta sea 201 Created
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void updateStudentShouldReturnNoContentStatus() {
        long studentId = 15L;
        String apiUrl = "/api/v1/students/" + studentId;  // URL completa del estudiante que deseas actualizar

        // Datos de actualización del estudiante (cambia estos valores según tus necesidades)
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("name", "Nuevo Nombre");
        updateData.put("email", "nuevo.email@example.com");

        // Construye la solicitud con los datos de actualización
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(updateData);

        // Realiza una solicitud PUT para actualizar el estudiante
        ResponseEntity<Void> responseEntity = this.restTemplate.exchange(
                "http://localhost:" + port + apiUrl,
                HttpMethod.PUT,
                requestEntity,
                Void.class
        );

        // Verifica que el código de respuesta sea 204 No Content
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteStudentShouldReturnNoContentStatus() {
        long studentId = 16L;
        String apiUrl = "/api/v1/students/" + studentId;  // URL completa del estudiante que deseas eliminar

        // Realizar una solicitud DELETE para eliminar el estudiante
        ResponseEntity<Void> responseEntity = this.restTemplate.exchange(
                "http://localhost:" + port + apiUrl,
                HttpMethod.DELETE,
                null,  // No se requieren datos de eliminación en el cuerpo
                Void.class
        );

        // Verificar que el código de respuesta sea 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}