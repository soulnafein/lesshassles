package com.lesshassles.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lesshassles.util.DatabaseTestSupport;

public class TaskListServiceTest extends DatabaseTestSupport {
	
	@Autowired
	TaskListService taskListService;
	
	User aUser;
	
	@Before
	public void setUp() {
		aUser = new User("anemail@test.tst").setFullname("A user");
		populateDatabase(aUser);
	}

	@Test
	public void shouldTrimAndRemoveDuplicatedWhitespacesFromNameWhenSavingValidTaskList() {
		String validName = "  abcAbc 1234().   santo's    ";
	
		TaskList taskList = new TaskList(validName).setOwner(aUser);

		taskListService.save(taskList);
	}
	
	@Test
	public void shouldThrowInvalidArgumentExceptionWhenTryingToSaveANullTaskList() {
		try {
			taskListService.save(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	public void shouldUpdateTaskListName() {
		TaskList taskList = new TaskList("A tasklist").setOwner(aUser);
		populateDatabase(taskList);
		
		final String taskListNewName = "A new name for the tasklist";
		taskList.setName(taskListNewName);
		
		taskListService.update(taskList);
		flush();

		TaskList updatedTaskList = (TaskList)retrieve(TaskList.class, taskList.getId());
		assertEquals(taskListNewName, updatedTaskList.getName());
	}
	
	@Test
	public void shouldRetrieveOneTaskListWhenProvidingValidIdAndOwner() {
		TaskList taskList = new TaskList("A tasklist").setOwner(aUser);
		populateDatabase(taskList);
		
		TaskList retrievedTaskList = taskListService.findByIdAndOwner(taskList.getId(), aUser);
		
		assertEquals(taskList, retrievedTaskList);		
	}
	
	@Test
	public void shouldReturnNullWhenAccessingSomeoneElseTaskList() {
		TaskList taskList = new TaskList("A tasklist").setOwner(aUser);
		populateDatabase(taskList);
		User anotherUser = new User("another@test.tst").setFullname("Another user");
		populateDatabase(anotherUser);
		
		TaskList retrievedTaskList = taskListService.findByIdAndOwner(taskList.getId(), anotherUser);
		
		assertNull(retrievedTaskList);
	}
	
	@Test
	public void shouldReturnAllListOwnedByAUser() {
		Collection<TaskList> taskListsStoredInDatabase = new ArrayList<TaskList>();
		taskListsStoredInDatabase.add(new TaskList("A tasklist").setOwner(aUser));
		taskListsStoredInDatabase.add(new TaskList("A tasklist").setOwner(aUser));
		populateDatabase(taskListsStoredInDatabase);
		User anotherUser = new User("another@test.tst").setFullname("Another user");
		populateDatabase(anotherUser);
		taskListsStoredInDatabase.add(new TaskList("A tasklist").setOwner(anotherUser));
		
		
		Collection<TaskList> taskLists = taskListService.findByOwner(aUser);
		
		assertEquals(2, taskLists.size());
		for(TaskList taskList : taskLists) {
			assertEquals(aUser, taskList.getOwner());
		}
	}
	
	@Test
	public void shouldReturnAnEmptyListWhenThereAreNoTaskListForAUser() {
		Collection<TaskList> taskLists = taskListService.findByOwner(aUser);
		
		assertEquals(0, taskLists.size());
	}
	
	@Test
	public void shouldDeleteTaskList() {
		TaskList taskList = new TaskList("A tasklist").setOwner(aUser);
		populateDatabase(taskList);		
		TaskList taskListLoadedFromDatabase = (TaskList)retrieve(TaskList.class, taskList.getId());
		
		taskListService.delete(taskListLoadedFromDatabase);
		flush();
		
		assertNull(retrieve(TaskList.class, taskList.getId()));
	}
}
