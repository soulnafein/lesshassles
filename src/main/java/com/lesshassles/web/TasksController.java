package com.lesshassles.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lesshassles.model.exceptions.DuplicateObjectException;

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

		task.setStatus(TaskStatus.Open);
		
		Integer taskListId = Integer.parseInt(request.getRequestURI()
				.replaceAll(".*?\\/tasklists\\/(\\d*?)\\/.*", "$1"));
		
		User authenticatedUser = authenticationService.getAuthenticatedUser();
		
		TaskList taskList = taskListService.findByIdAndOwner(taskListId, authenticatedUser);
		try {
			taskList.addTask(task);
		} catch(DuplicateObjectException ex) {
			return "taskErrorDuplicateEntry";
		}
		taskListService.update(taskList);

		return String.format("redirect:/tasklists/%d/tasks/%d.htm", taskList
				.getId(), task.getId());

	}

	@RequestMapping(value = "*.htm", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {

		Integer taskId = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasks\\/(\\d*?)\\.htm", "$1"));

		Task task = taskService.findById(taskId);

		return new ModelAndView("taskShowJson", "task", task);

	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(User.class, new UserCustomEditor(userService));
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
}
