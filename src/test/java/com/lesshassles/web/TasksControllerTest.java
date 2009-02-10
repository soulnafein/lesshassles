package com.lesshassles.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

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

@RunWith(MockitoJUnit44Runner.class)
public class TasksControllerTest {
	private TasksController controller;
	@Mock
	private TaskListService taskListService;
	@Mock
	private TaskService taskService;
	private MockHttpServletRequest request;
	private TaskList taskList;
	private static final Integer A_TASK_LIST_ID = 15;
	private static final Integer A_TASK_ID = 2;

	@Before
	public void setUp() {
		controller = new TasksController();
		controller.setTaskListService(taskListService);
		controller.setTaskService(taskService);

		request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI(String
				.format("/tasklists/%d.htm", A_TASK_LIST_ID));

		taskList = new TaskList("A task list");
		taskList.setId(A_TASK_LIST_ID);
		taskList.addTask(new Task("Task 1"));
		taskList.addTask(new Task("Task 2"));
		taskList.addTask(new Task("Task 3"));
	}

	@Test
	public void shouldSaveTaskAndRedirectToTaskView() {
		request.setRequestURI(String.format("/tasklists/%d/tasks/new.htm",
				A_TASK_LIST_ID));

		when(taskListService.findById(A_TASK_LIST_ID)).thenReturn(taskList);

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
		request.setRequestURI(String.format("/tasklists/%d/tasks/new.htm",
				A_TASK_LIST_ID));

		when(taskListService.findById(A_TASK_LIST_ID)).thenReturn(taskList);

		Task task = new Task(taskList.getTasks().iterator().next().getDescription());

		String view = controller.submitForm(task, request);
		assertEquals("taskErrorDuplicateEntry", view);
	}

	@Test
	public void shouldCreateJsonForSpecifiedTask() {
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
}
