package com.lesshassles.model;

import java.util.ArrayList;
import java.util.Date;
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
	public void shouldReturnListOfTasksAssignedToOtherUsers() {
		List<Task> tasksInDatabase = new ArrayList<Task>();
		tasksInDatabase.add(new Task("A task").setAssignee(loggedUser).setTaskList(anotherTaskList));
		tasksInDatabase.add(new Task("Another task").setAssignee(null).setTaskList(loggedUserTaskList));
		tasksInDatabase.add(new Task("A task").setAssignee(anotherUser).setTaskList(loggedUserTaskList));
		populateDatabase(tasksInDatabase);
		
		List<Task> tasks = taskService.getTasksAssignedToOtherUsers(loggedUser);
		
		assertEquals(1, tasks.size());
		for(Task task : tasks) {
			assertNotNull(task.getAssignee());
			assertFalse(loggedUser.equals(task.getAssignee()));
			assertEquals(loggedUser, task.getTaskList().getOwner());
		}	
	}
	
	@Test
	public void shouldReturnListOfTasksAssignedToOtherUsersSortedByDeadline() {
		Task taskWithDeadline = new Task("A task with deadline").setAssignee(anotherUser).setTaskList(loggedUserTaskList).setDeadline(new Date(2009,2,1));
		Task taskWithNoDeadline = new Task("A task without deadline").setAssignee(anotherUser).setTaskList(loggedUserTaskList);
		Task taskDueUrgently = new Task("A task due soon").setAssignee(anotherUser).setTaskList(loggedUserTaskList).setDeadline(new Date(2009,2,15));
		populateDatabase(taskWithDeadline, taskWithNoDeadline, taskDueUrgently);
		
		List<Task> tasks = taskService.getTasksAssignedToOtherUsers(loggedUser);

		assertEquals(3, tasks.size());
		assertEquals(tasks.get(0), taskDueUrgently);
		assertEquals(tasks.get(1), taskWithDeadline);
		assertEquals(tasks.get(2), taskWithNoDeadline);
			
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
	public void shouldReturnListOfTasksAssignedToAUserSortedByDeadline() {
		Task taskWithDeadline = new Task("A task with deadline").setAssignee(loggedUser).setTaskList(anotherTaskList).setDeadline(new Date(2009,2,1));
		Task taskWithNoDeadline = new Task("A task without deadline").setAssignee(loggedUser).setTaskList(anotherTaskList);
		Task taskDueUrgently = new Task("A task due soon").setAssignee(loggedUser).setTaskList(anotherTaskList).setDeadline(new Date(2009,2,15));
		populateDatabase(taskWithDeadline, taskWithNoDeadline, taskDueUrgently);
		
		List<Task> tasks = taskService.getTasksAssignedToUser(loggedUser);

		assertEquals(3, tasks.size());
		assertEquals(tasks.get(0), taskDueUrgently);
		assertEquals(tasks.get(1), taskWithDeadline);
		assertEquals(tasks.get(2), taskWithNoDeadline);
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
	
	@Test
	public void shouldChangeDeadline() {
		Task aTaskInDatabase = new Task("A task").setTaskList(anotherTaskList).setDeadline(new Date(2008,01,01));
		populateDatabase(aTaskInDatabase);
		
		Date aNewDeadline = new Date(2009,02,02);
		taskService.changeDeadline(aTaskInDatabase.getId(), aNewDeadline);
		
		assertEquals(aNewDeadline, aTaskInDatabase.getDeadline());
	}
	
}
