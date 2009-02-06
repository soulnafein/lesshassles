package com.lesshassles.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnit44Runner.class)
public class TasksControllerTest {
	private TasksController controller;
	@Mock
	private TaskListService taskListService;
	private MockHttpServletRequest request;
	private static final Integer A_TASK_LIST_ID = 15;

	@Before
	public void setUp() {
		controller = new TasksController();
		controller.setTaskListService(taskListService);

		request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI(String
				.format("/tasklists/%d.htm", A_TASK_LIST_ID));

		new MockHttpServletResponse();
	}

	@Test
	public void shouldSaveTaskAndRedirectToTaskJson() {
		request.setRequestURI(String.format("/tasklists/%d/tasks/new.htm",
				A_TASK_LIST_ID));

		TaskList taskList = new TaskList("A task list");
		taskList.setId(A_TASK_LIST_ID);
		taskList.addTask(new Task("Task 1"));
		taskList.addTask(new Task("Task 2"));
		taskList.addTask(new Task("Task 3"));
		when(taskListService.findById(A_TASK_LIST_ID)).thenReturn(taskList);

		final Task task = new Task("A task");
		final Integer A_TASK_ID = 5;
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
}
