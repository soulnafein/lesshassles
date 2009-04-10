package com.lesshassles.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.User;
import com.lesshassles.util.DatabaseTestSupport;

public class TaskServiceTest extends DatabaseTestSupport {
	@Autowired
	TaskService taskService;
	
	// Generic data for tests
	User loggedUser = new User("me@test.com");
	User anotherUser = new User("another@test.com");
	
	TaskList loggedUserTaskList = new TaskList("A list");
	TaskList anotherTaskList = new TaskList("Another list");
	
	@Before
	public void setUp() {
		loggedUser.setFullname("Logged user");
		anotherUser.setFullname("Another");
		populateDatabase(loggedUser, anotherUser);

		loggedUserTaskList.setOwner(loggedUser);
		anotherTaskList.setOwner(anotherUser);
		populateDatabase(loggedUserTaskList, anotherTaskList);
	}
	
	@Test
	public void shouldReturnListOfTasksAssignedToAUser() {
		
		List<Task> tasksInDatabase = new ArrayList<Task>();
		tasksInDatabase.add(new Task("A task").setAssignee(loggedUser).setTaskList(anotherTaskList));
		tasksInDatabase.add(new Task("Another task").setAssignee(loggedUser).setTaskList(anotherTaskList));
		tasksInDatabase.add(new Task("A task").setAssignee(anotherUser).setTaskList(loggedUserTaskList));
		populateDatabase(tasksInDatabase);
		
		List<Task> tasks = taskService.getTasksAssignedToUser(loggedUser);
		
		assertEquals(2, tasks.size());
		for(Task task : tasks) {
			assertEquals(loggedUser, task.getAssignee());
		}
		
	}
	
	@Test
	public void shouldReturnListOfTasksWithNullAssigneeButCreatedByLoggedUser() {
		List<Task> tasksInDatabase = new ArrayList<Task>();
		tasksInDatabase.add(new Task("Another task").setAssignee(null).setTaskList(anotherTaskList));
		tasksInDatabase.add(new Task("A task").setAssignee(null).setTaskList(loggedUserTaskList));
		populateDatabase(tasksInDatabase);
		
		List<Task> tasks = taskService.getTasksAssignedToUser(loggedUser);
		
		assertEquals(1, tasks.size());
		for(Task task : tasks) {
			assertNull(task.getAssignee());
			assertEquals(loggedUserTaskList, task.getTaskList());
		}		
	}
	
	@Test
	public void shouldChangeTheStatusOfAGivenTask() {
		Task aTaskInDatabase = new Task("A task").setTaskList(anotherTaskList).setStatus(TaskStatus.Open);
		populateDatabase(aTaskInDatabase);
		
		taskService.changeTaskStatus(aTaskInDatabase.getId(), TaskStatus.Completed);
		
		assertEquals(TaskStatus.Completed, aTaskInDatabase.getStatus());
	}
	
	@Test
	public void shouldRetrieveOneTask() {
		Task aTaskInDatabase = new Task("A task").setTaskList(anotherTaskList).setStatus(TaskStatus.Open);
		populateDatabase(aTaskInDatabase);
		
		Task retrievedTask = taskService.findById(aTaskInDatabase.getId());
		
		assertEquals(aTaskInDatabase, retrievedTask);
	}
	
	@Test
	public void shouldThrowAnExceptionWhenRetrievingAnInvalidId() {
		Integer anInvalidId = Integer.valueOf(666);
		
		try {
			Task task = taskService.findById(anInvalidId);
			// It's lazy so exception is thrown when trying to access object
			task.toString();
			fail();
		} catch(ObjectNotFoundException ex) {
		}
	}
	
	@Test
	public void shouldAssignTaskToAUser() {
		Task aTaskInDatabase = new Task("A task").setTaskList(anotherTaskList).setStatus(TaskStatus.Open);
		populateDatabase(aTaskInDatabase);
		
		taskService.assignTaskToUser(aTaskInDatabase.getId(), anotherUser);
		
		assertEquals(anotherUser, aTaskInDatabase.getAssignee());
	}
	
	@Test
	public void shouldDeassignTask() {
		Task aTaskInDatabase = new Task("A task")
					.setTaskList(anotherTaskList)
					.setStatus(TaskStatus.Open)
					.setAssignee(anotherUser);
		populateDatabase(aTaskInDatabase);
		
		taskService.deassignTask(aTaskInDatabase.getId());
		
		assertNull(aTaskInDatabase.getAssignee());
	}
}
