package com.lesshassles.web;

import java.util.List;

import com.lesshassles.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard/*.htm")
public class DashboardController {

	TaskService taskService;
	
	AuthenticationService authenticationService;
	
	TaskListService taskListService;
	
	UserService userService;
	
	@Autowired
	public DashboardController(TaskService taskService, 
								AuthenticationService authenticationService, 
								TaskListService taskListService,
								UserService userService) {
		this.taskService = taskService;
		this.authenticationService = authenticationService;
		this.taskListService = taskListService;
		this.userService = userService;
	}

	@RequestMapping(value = "index.htm")
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("dashboard");
		User loggedUser = authenticationService.getAuthenticatedUser();
		
		List<Task> tasksAssignedToUser = taskService.getTasksAssignedToUser(loggedUser);
		mav.addObject("tasksAssignedToUser", tasksAssignedToUser);
		
		List<Task> tasksAssignedToOtherUsers = taskService.getTasksAssignedToOtherUsers(loggedUser);
		mav.addObject("tasksAssignedToOtherUsers", tasksAssignedToOtherUsers);
		
		List<TaskList> taskLists = taskListService.findByOwner(loggedUser);
		mav.addObject("taskLists", taskLists);
		
		return mav;
	}
	
	@ModelAttribute("users")
	public List<User> userList() {
		User authenticatedUser = authenticationService.getAuthenticatedUser();
		List<User> users = userService.findAllActiveUsers();
		users.remove(authenticatedUser);
		return users;
	}
}
