package com.lesshassles.model;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;

import com.lesshassles.persistence.TaskListDAO;

@RunWith(MockitoJUnit44Runner.class)
public class TaskListServiceImplTest {
    private TaskListServiceImpl taskListService;
    @Mock
    private TaskListDAO taskListDAO;

    @Before
    public void setUp() {
	taskListService = new TaskListServiceImpl();
	taskListService.setTaskListDAO(taskListDAO);
    }

    @Test
    public void shouldTrimAndRemoveDuplicatedWhitespacesFromNameWhenSavingValidTaskList() {
	String validName = "  abcAbc 1234().   santo's    ";
	TaskList taskList = new TaskList(validName);

	taskListService.save(taskList);
	verify(taskListDAO).makePersistent(taskList);
    }

    @Test
    public void shouldThrowExceptionWhenSavingInvalidTaskList() {
	final String invalidName = "   ;'ssa\t   ";
	TaskList invalidTaskList = new TaskList(invalidName);
	try {
	    taskListService.save(invalidTaskList);
	    fail();
	} catch (IllegalArgumentException ex) {

	}
    }
}
