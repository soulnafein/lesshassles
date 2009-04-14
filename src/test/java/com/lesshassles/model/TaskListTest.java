package com.lesshassles.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TaskListTest {
	
	@Test
	public void shouldReturnListOfTasksSortedByDueDate() {
		TaskList tasklist = new TaskList("A tasklist");
		Task taskWithDeadline = new Task("A task with deadline").setDeadline(new Date(2009,2,1));
		Task taskWithNoDeadline = new Task("A task without deadline");
		Task taskDueUrgently = new Task("A task due soon").setDeadline(new Date(2009,2,15));
		tasklist.addTask(taskWithDeadline);
		tasklist.addTask(taskWithNoDeadline);
		tasklist.addTask(taskDueUrgently);
		
		List<Task> tasks = new ArrayList<Task>(tasklist.getTasks());
		
		
		assertEquals(3,tasks.size());
		assertEquals(tasks.get(0), taskDueUrgently);
		assertEquals(tasks.get(1), taskWithDeadline);
		assertEquals(tasks.get(2), taskWithNoDeadline);

	}
	
}
