package com.esewa.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.esewa.usermanagement.controller.AdminController;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class UserManagementApplicationTests {

	@Mock
	private AdminService adminService;

	@InjectMocks
	private AdminController adminController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
	}

	@Test
	void registerUserTest() throws Exception {
		User user = new User();

		when(adminService.registerUser(any(User.class))).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/admin/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		verify(adminService, times(1)).registerUser(any(User.class));
	}


	@Test
	void getUsersTest() throws Exception {
		List<User> users = new ArrayList<>();

		when(adminService.getUsers()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/admin/user")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print());

		verify(adminService, times(1)).getUsers();
	}

	@Test
	void removeUserTest() throws Exception {
		Long userId = 1L;

		mockMvc.perform(MockMvcRequestBuilders
						.post("/admin/user/{id}/remove", userId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		verify(adminService, times(1)).removeUser(userId);
	}

}

