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
	
	@Autowired
	public DashboardController(TaskService taskService, AuthenticationService authenticationService) {
		this.taskService = taskService;
		this.authenticationService = authenticationService;
	}

	@RequestMapping(value = "index.htm")
	public ModelAndView show() {
		return new ModelAndView("dashboard");
	}

	@ModelAttribute("tasksAssignedToUser")
	public List<Task> tasksAssignedToUser() {
		User loggedUser = authenticationService.getAuthenticatedUser();
		return taskService.getTasksAssignedToUser(loggedUser);
	}
}
