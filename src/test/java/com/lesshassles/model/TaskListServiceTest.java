package com.lesshassles.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lesshassles.util.DatabaseTestSupport;

public class TaskListServiceTest extends DatabaseTestSupport {
	
	@Autowired
	TaskListService taskListService;
	
	@Before
	public void setUp() {
	}

	@Test
	public void shouldTrimAndRemoveDuplicatedWhitespacesFromNameWhenSavingValidTaskList() {
		String validName = "  abcAbc 1234().   santo's    ";
		User aUser = new User("anemail@test.tst").setFullname("A user");
		populateDatabase(aUser);
		TaskList taskList = new TaskList(validName).setOwner(aUser);

		taskListService.save(taskList);
	}
}
