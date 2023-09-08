package com.esewa.usermanagement;

import com.esewa.usermanagement.controller.LoginController;
import com.esewa.usermanagement.dto.JwtResponse;
import com.esewa.usermanagement.dto.LoginDto;
import com.esewa.usermanagement.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserManagementApplicationTests {
	@MockBean // This annotation will create a mock bean of HelloWorld & LoginService
	private HelloWorld helloWorld;
	@MockBean
	private LoginService loginService;
	//Autowired call to utilize the controller inside the test
	@Autowired
	private LoginController loginController;

	@Test
	void contextLoads() {
		System.out.println("Test");
	}

	//Sample Mockito Test to check functionality
	@Test
	public void testSayHello() {
		// Define the behavior of the mock object
		when(helloWorld.sayHello()).thenReturn("Mocked Hello, World!");

		// Perform some operations that would involve calling the sayHello method
		// In a real scenario, you would call this method on an actual instance
		String result = helloWorld.sayHello();

		// Verify that the method was called
		verify(helloWorld).sayHello();

		// Assert the result
		assertEquals("Mocked Hello, World!", result);
	}

	//TestLogin Controller against Moockito unit
	@Test
	public void testLogin() {
		// Create sample login credentials
		LoginDto credentials = new LoginDto();
		credentials.setUsername("username");
		credentials.setPassword("password");

		// Create a sample JWT response
		JwtResponse jwtResponse = new JwtResponse("sampleToken");

		// Mock the behavior of the loginService when loginUser is called with the provided credentials
		when(loginService.loginUser(credentials)).thenReturn(new ResponseEntity<>(jwtResponse, HttpStatus.OK));

		// Call the login method in the controller
		ResponseEntity<JwtResponse> responseEntity = loginController.login(credentials);

		// Verify that the loginService.loginUser method was called with the provided credentials
		verify(loginService).loginUser(credentials);

		// Verify that the response status code is HttpStatus.OK (200)
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Verify that the returned JWT token matches the sample token
		JwtResponse responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals("sampleToken", responseBody.getToken());
	}

}


class HelloWorld {
	public String sayHello() {
		return "Hello, World!";
	}
}

