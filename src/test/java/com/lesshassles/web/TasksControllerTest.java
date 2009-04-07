package com.lesshassles.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	public void shouldReturnErrorMessageWhenTryingToAddTaskTwice() {
		taskListLoadingExpectation();

		Task task = new Task(taskList.getTasks().iterator().next()
				.getDescription());

		String view = controller.submitForm(task, request);
		assertEquals("taskErrorDuplicateEntry", view);
	}

	@Test
	public void shouldReturnTaskInJsonFormat() {
		request.setRequestURI(String.format("/tasklists/%d/tasks/%d.htm",
				A_TASK_LIST_ID, A_TASK_ID));
		Task task = new Task("A task from DB");
		when(taskService.findById(A_TASK_ID)).thenReturn(task);

		ModelAndView mav = controller.show(request);

		assertNotNull(mav);
		assertViewName(mav, "taskShowJson");
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

	void taskListLoadingExpectation() {
		when(authenticationService.getAuthenticatedUser()).thenReturn(
				authenticatedUser);
		when(
				taskListService.findByIdAndOwner(A_TASK_LIST_ID,
						authenticatedUser)).thenReturn(taskList);
	}
}
