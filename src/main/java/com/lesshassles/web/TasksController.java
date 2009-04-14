package com.lesshassles.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.TaskService;
import com.lesshassles.model.TaskStatus;
import com.lesshassles.model.User;
import com.lesshassles.model.UserService;

@Controller
@RequestMapping("/tasklists/*/tasks/*.htm")
public class TasksController {

	private TaskListService taskListService;
	private TaskService taskService;
	private AuthenticationService authenticationService;
	private UserService userService;
	
	@Autowired
	public TasksController(	TaskListService taskListService, 
							TaskService taskService, 
							AuthenticationService authenticationService,
							UserService userService) {
		this.taskListService = taskListService;
		this.taskService = taskService;
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.POST)
	public String submitForm(Task task, HttpServletRequest request) {

		Integer taskListId = Integer.parseInt(request.getRequestURI()
				.replaceAll(".*?\\/tasklists\\/(\\d*?)\\/.*", "$1"));
		
		User authenticatedUser = authenticationService.getAuthenticatedUser();
		
		TaskList taskList = taskListService.findByIdAndOwner(taskListId, authenticatedUser);
		
		taskList.addTask(task);

		taskListService.update(taskList);

		return String.format("redirect:/tasklists/%d/tasks/%d.htm", taskList
				.getId(), task.getId());

	}

	@RequestMapping(value = "*.htm", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {

		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)\\.htm", "$1"));

		Task task = taskService.findById(taskId);

		return new ModelAndView("taskShow", "task", task);

	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(User.class, new UserCustomEditor(userService));
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
	
	@RequestMapping(value = "*-assign.htm", method = RequestMethod.GET)
	public String assign(HttpServletRequest request, @RequestParam User assignee) {
		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)-assign\\.htm", "$1"));

		taskService.assignTaskToUser(taskId, assignee);
				
		return "ajaxRequestResult";
	}
	
	@RequestMapping(value = "*-deassign.htm", method = RequestMethod.GET)
	public String deassign(HttpServletRequest request) {
		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)-deassign\\.htm", "$1"));

		taskService.deassignTask(taskId);
				
		return "ajaxRequestResult";
	}

	@RequestMapping(value = "*-changeStatus.htm", method = RequestMethod.GET)
	public String changeStatus(HttpServletRequest request, @RequestParam TaskStatus status) {
		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)-changeStatus\\.htm", "$1"));

		taskService.changeTaskStatus(taskId, status);
				
		return "ajaxRequestResult";
	}

	@RequestMapping(value = "*-setDeadline.htm", method = RequestMethod.GET)
	public String changeStatus(HttpServletRequest request, @RequestParam Date deadline) {
		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)-setDeadline\\.htm", "$1"));

		taskService.changeDeadline(taskId, deadline);
				
		return "ajaxRequestResult";
	}
}
