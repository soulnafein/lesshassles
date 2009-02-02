package com.lesshassles.web;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.test.web.ModelAndViewAssert.*;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnit44Runner.class)
public class TaskListControllerTest {
	private TaskListController controller;
	@Mock private TaskListService taskListService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private static final Integer A_TASK_LIST_ID = 15;
	
	@Before
	public void setUp() {
		controller = new TaskListController();
		controller.setTaskListService(taskListService);
		
		request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI(String.format("/tasklists/%d.htm", A_TASK_LIST_ID));
		
		response = new MockHttpServletResponse();
		
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
		
		final String expectedView = String.format("redirect:/tasklists/%d.htm", A_TASK_LIST_ID); 
		assertEquals(expectedView, view);
	}
	
	@Test
	public void shouldShowTasksListViewPage() throws IOException {
		TaskList taskList = new TaskList("A task list");
		taskList.setId(A_TASK_LIST_ID);
		taskList.addTask("Task 1");
		taskList.addTask("Task 2");
		taskList.addTask("Task 3");
		
		when(taskListService.findById(A_TASK_LIST_ID)).thenReturn(taskList);
		
		ModelAndView mav = controller.show(request, response);
		
		assertNotNull(mav);
		assertViewName(mav, "taskListShow");
		assertModelAttributeAvailable(mav, "taskList");
		TaskList model = (TaskList)mav.getModel().get("taskList");
		assertEquals(taskList.getTasks().size(), model.getTasks().size());
		for(Task task : model.getTasks()) {
			assertEquals(taskList, task.getTaskList());
		}
	}
	
	@Test
	public void shouldShow404PageWhenTaskListNotFound() throws IOException {
		when(taskListService.findById(A_TASK_LIST_ID)).thenReturn(null);
		
		ModelAndView mav = controller.show(request, response);
		
		assertNull(mav);
		assertEquals(MockHttpServletResponse.SC_NOT_FOUND, response.getStatus());
	}
	
	@Test
	public void shouldShow404PageWhenIdNotParsable() throws IOException {
		request.setRequestURI("/tasklists/xxx.do");
		
		ModelAndView mav = controller.show(request, response);
		
		assertNull(mav);
		assertEquals(MockHttpServletResponse.SC_NOT_FOUND, response.getStatus());
	}
}
