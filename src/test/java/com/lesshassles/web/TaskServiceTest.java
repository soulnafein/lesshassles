package com.lesshassles.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.User;
import com.lesshassles.model.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-hibernate.xml"})
@Transactional
public class TaskServiceTest {
	@Autowired
	TaskService taskService;
	
	@Autowired
	UserService userService;
	
	@Test
	public void shouldReturnListOfTasksAssignedToAUser() {
		User user = userService.findById(1);
		
		List<Task> tasks = taskService.getTasksAssignedToUser(user);
		
		assertEquals(4, tasks.size());
	}
}
