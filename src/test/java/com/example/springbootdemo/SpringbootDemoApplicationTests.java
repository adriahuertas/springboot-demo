package com.example.springbootdemo;

import com.example.springbootdemo.student.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootDemoApplicationTests {

	@Autowired
	private StudentController studentController;

	@Test
	void contextLoads() {
		assertThat(studentController).isNotNull();
	}


}
