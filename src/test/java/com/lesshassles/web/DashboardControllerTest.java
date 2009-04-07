package com.lesshassles.web;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.User;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnit44Runner.class)
public class DashboardControllerTest {
	
	@Mock
	TaskService taskService;
	
	@Mock
	AuthenticationService authenticationService;
	
	DashboardController controller;
	
	@Before
	public void setUp() {
		controller = new DashboardController(taskService, authenticationService);
	}
	
	@Test
	public void shouldReturnModelAndView() {
		
		ModelAndView mav = controller.show();
		
		assertNotNull(mav);
		
	}
	
	@Test
	public void shouldForwardToDashboardView() {
		
		ModelAndView mav = controller.show();
		
		assertViewName(mav, "dashboard");
		
	}
	
	@Test
	public void shouldDisplayListOfTasksAssignedToLoggedUser() {
	
		User loggedUser = new User("test@test.com");
		when(authenticationService.getAuthenticatedUser()).thenReturn(loggedUser);
		
		List<Task> tasksAssignedToUser = new ArrayList<Task>();
		tasksAssignedToUser.add(new Task("A task"));
		when(taskService.getTasksAssignedToUser(loggedUser)).thenReturn(tasksAssignedToUser);
		
		List<Task> model = controller.tasksAssignedToUser();
		
		assertNotNull(model);
		assertEquals(tasksAssignedToUser, model);

	}
	
}
