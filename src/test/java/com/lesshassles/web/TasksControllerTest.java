package com.lesshassles.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.TaskStatus;
import com.lesshassles.model.User;
import com.lesshassles.model.exceptions.*;

@RunWith(MockitoJUnit44Runner.class)
public final class TasksControllerTest {
	TasksController controller;
	@Mock
	TaskListService taskListService;
	@Mock
	TaskService taskService;
	@Mock
	AuthenticationService authenticationService;
	MockHttpServletRequest request;
	TaskList taskList;
	User authenticatedUser;
	static final Integer A_TASK_LIST_ID = 15;
	static final Integer A_TASK_ID = 2;

	@Before
	public void setUp() {
		controller = new TasksController(taskListService, taskService,
				authenticationService, null);

		request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI(String.format("/tasklists/%d/tasks/new.htm",
				A_TASK_LIST_ID));

		taskList = new TaskList("A task list").setId(A_TASK_LIST_ID).addTask(
				new Task("Task 1")).addTask(new Task("Task 2")).addTask(
				new Task("Task 3"));

		authenticatedUser = new User("test@test.tst");
	}

	@Test
	public void shouldSaveTaskAndRedirectToTaskView() {
		taskListLoadingExpectation();

		final Task task = new Task("A task");
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				task.setId(A_TASK_ID);
				return null;
			}
		}).when(taskListService).update(taskList);

		String view = controller.submitForm(task, request);

		assertTrue(taskList.getTasks().contains(task));
		assertNotNull(task.getId());
		assertEquals(String.format("redirect:/tasklists/%d/tasks/%d.htm",
				A_TASK_LIST_ID, A_TASK_ID), view);
	}

	@Test
	public void shouldThrowExceptionWhenTryingToAddTaskTwice() {
		taskListLoadingExpectation();

		Task task = new Task(taskList.getTasks().iterator().next()
				.getDescription());

		try {
			controller.submitForm(task, request);
			fail();
		} catch(DuplicateObjectException ex) {
			
		}
	}

	@Test
	public void shouldReturnTaskInHtmlFormat() {
		request.setRequestURI(String.format("/tasklists/%d/tasks/%d.htm",
				A_TASK_LIST_ID, A_TASK_ID));
		Task task = new Task("A task from DB");
		when(taskService.findById(A_TASK_ID)).thenReturn(task);

		ModelAndView mav = controller.show(request);

		assertNotNull(mav);
		assertViewName(mav, "taskShow");
		assertModelAttributeAvailable(mav, "task");
		Task taskInModel = (Task) mav.getModel().get("task");
		assertEquals(task, taskInModel);
	}

	@Test
	public void shouldChangeTaskAssignee() {
		request.setRequestURI(
				String.format("/tasklists/%d/tasks/%d-assign.htm", 
								A_TASK_LIST_ID,
								A_TASK_ID));
		final User assignee = new User("assigne@test.tst");
		
		String view = controller.assign(request, assignee);
		
		assertEquals("ajaxRequestResult", view);

	}
	
	@Test
	public void shouldDeassignTask() {
		request.setRequestURI(
				String.format("/tasklists/%d/tasks/%d-deassign.htm", 
								A_TASK_LIST_ID,
								A_TASK_ID));
		
		String view = controller.deassign(request);
		
		assertEquals("ajaxRequestResult", view);
	}
	
	@Test
	public void shouldChangeTaskStatus() {
		request.setRequestURI(
				String.format("/tasklists/%d/tasks/%d-changeStatus.htm",
								A_TASK_LIST_ID,
								A_TASK_ID));
		
		TaskStatus status = TaskStatus.Open;
		String view = controller.changeStatus(request, status);
		
		verify(taskService).changeTaskStatus(A_TASK_ID, status);
		assertEquals("ajaxRequestResult", view);
								
	}
	
	@Test
	public void shouldSetDeadline() {
		request.setRequestURI(
				String.format("/tasklists/%d/tasks/%d-setDeadline.htm",
								A_TASK_LIST_ID,
								A_TASK_ID));
		
		Date deadline = new Date(2009,2,2);
		String view = controller.changeStatus(request, deadline);
		
		verify(taskService).changeDeadline(A_TASK_ID, deadline);
		assertEquals("ajaxRequestResult", view);
								
	}

	void taskListLoadingExpectation() {
		when(authenticationService.getAuthenticatedUser()).thenReturn(
				authenticatedUser);
		when(
				taskListService.findByIdAndOwner(A_TASK_LIST_ID,
						authenticatedUser)).thenReturn(taskList);
	}
}
