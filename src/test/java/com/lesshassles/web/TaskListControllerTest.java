package com.lesshassles.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.User;

@RunWith(MockitoJUnit44Runner.class)
public class TaskListControllerTest {
	private TaskListController controller;
	@Mock private TaskListService taskListService;
	@Mock private AuthenticationService authenticationService;
	private MockHttpServletRequest request;
	private User authenticatedUser;

	private static final Integer A_TASK_LIST_ID = 15;

	@Before
	public void setUp() {
		controller = new TaskListController();
		controller.setTaskListService(taskListService);
		controller.setAuthenticationService(authenticationService);

		request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI(String
				.format("/tasklists/%d.htm", A_TASK_LIST_ID));
		
		authenticatedUser = new User();
		authenticatedUser.setEmail("test@test.tst");

	}

	@Test
	public void shouldShowCreateTaskListForm() {
		ModelAndView mav = controller.showForm();

		assertNotNull(mav);
		assertViewName(mav, "taskListForm");
		assertModelAttributeAvailable(mav, "taskList");
	}

	@Test
	public void shouldSaveTaskListAndShowTaskListViewPage() {
		final String name = "My task list";
		TaskList taskList = new TaskList(name);

		when(taskListService.save(taskList)).thenReturn(A_TASK_LIST_ID);

		String view = controller.submitForm(taskList);

		final String expectedView = String.format("redirect:/tasklists/%d.htm",
				A_TASK_LIST_ID);
		assertEquals(expectedView, view);
	}

	@Test
	public void shouldShowTasksListViewPage() throws IOException {
		TaskList taskList = new TaskList("A task list");
		taskList.setId(A_TASK_LIST_ID);
		taskList.addTask(new Task("Task 1"));
		taskList.addTask(new Task("Task 2"));
		taskList.addTask(new Task("Task 3"));

		when(authenticationService.getAuthenticatedUser()).thenReturn(authenticatedUser);
		when(taskListService.findByIdAndOwner(A_TASK_LIST_ID, authenticatedUser)).thenReturn(taskList);

		ModelAndView mav = controller.show(request);

		assertNotNull(mav);
		assertViewName(mav, "taskListShow");
		assertModelAttributeAvailable(mav, "taskList");
		assertModelAttributeAvailable(mav, "task");
		TaskList model = (TaskList) mav.getModel().get("taskList");
		assertEquals(taskList.getTasks().size(), model.getTasks().size());
		for (Task task : model.getTasks()) {
			assertEquals(taskList, task.getTaskList());
		}
	}
	
	@Test
	public void shouldShowTaskLists() {
		TaskList taskList = new TaskList("A task list");
		taskList.setId(A_TASK_LIST_ID);
		taskList.addTask(new Task("Task 1"));
		taskList.addTask(new Task("Task 2"));
		taskList.addTask(new Task("Task 3"));
		
		List<TaskList> taskLists = new ArrayList<TaskList>();
		taskLists.add(taskList);
		taskLists.add(taskList);
		
		when(authenticationService.getAuthenticatedUser()).thenReturn(authenticatedUser);
		when(taskListService.findByOwner(authenticatedUser)).thenReturn(taskLists);
		ModelAndView mav = controller.browse();
		assertNotNull(mav);
		assertViewName(mav, "taskListBrowse");
		assertModelAttributeAvailable(mav, "taskLists");
	}
}
