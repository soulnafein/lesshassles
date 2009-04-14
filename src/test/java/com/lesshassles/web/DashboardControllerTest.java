package com.lesshassles.web;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
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
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.User;
import com.lesshassles.model.UserService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnit44Runner.class)
public class DashboardControllerTest {
	
	@Mock
	TaskService taskService;
	
	@Mock
	AuthenticationService authenticationService;
	
	@Mock
	TaskListService taskListService;
	
	@Mock
	UserService userService; 
	
	DashboardController controller;
	
	@Before
	public void setUp() {
		controller = new DashboardController(taskService, authenticationService, taskListService, userService);
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
		
		ModelAndView mav = controller.show();
		
		assertEquals(tasksAssignedToUser, mav.getModel().get("tasksAssignedToUser"));
		
	}
	
	@Test
	public void shouldDisplayListOfTasksAssignedToOtherUsers() {
		
		User loggedUser = new User("test@test.com");
		when(authenticationService.getAuthenticatedUser()).thenReturn(loggedUser);
		
		List<Task> tasksAssignedToOtherUsers = new ArrayList<Task>();
		tasksAssignedToOtherUsers.add(new Task("A task"));
		when(taskService.getTasksAssignedToOtherUsers(loggedUser)).thenReturn(tasksAssignedToOtherUsers);
		
		ModelAndView mav = controller.show();
		
		assertEquals(tasksAssignedToOtherUsers, mav.getModel().get("tasksAssignedToOtherUsers"));
		
	}
	
	@Test
	public void shouldShowTaskLists() {
		final Integer A_TASK_LIST_ID = 15;
		TaskList taskList = new TaskList("A task list").setId(A_TASK_LIST_ID)
				.addTask(new Task("Task 1"))
				.addTask(new Task("Task 2"))
				.addTask(new Task("Task 3"));

		List<TaskList> taskLists = new ArrayList<TaskList>();
		taskLists.add(taskList);

		User authenticatedUser = new User("test@test.tst");
		when(authenticationService.getAuthenticatedUser()).thenReturn(
				authenticatedUser);
		when(taskListService.findByOwner(authenticatedUser)).thenReturn(
				taskLists);
		
		ModelAndView mav = controller.show();
		
		assertModelAttributeAvailable(mav, "taskLists");
	}
	
}
