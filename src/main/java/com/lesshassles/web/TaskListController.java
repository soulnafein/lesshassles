package com.lesshassles.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.User;
import com.lesshassles.model.UserService;

@Controller
@RequestMapping("/tasklists/*.htm")
public class TaskListController {

	private TaskListService taskListService;
	private AuthenticationService authenticationService;
	private UserService userService;

	@Autowired
	public TaskListController(TaskListService taskListService,
			AuthenticationService authenticationService,
			UserService userService) {
		this.taskListService = taskListService;
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.GET)
	public ModelAndView showForm() {
		return new ModelAndView("taskListForm").addObject(new TaskList(""));
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("taskList") TaskList taskList) {
		User authenticatedUser = authenticationService.getAuthenticatedUser();
		taskList.setOwner(authenticatedUser);
		Integer id = taskListService.save(taskList);
		String view = String.format("redirect:/tasklists/%d.htm", id);
		return view;
	}

	@RequestMapping(value = "*.htm")
	public ModelAndView show(HttpServletRequest request) {

		Integer id = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?(\\d*?)\\.htm", "$1"));

		User authenticatedUser = authenticationService.getAuthenticatedUser();
		TaskList taskList = taskListService.findByIdAndOwner(id,
				authenticatedUser);

		ModelAndView mav = new ModelAndView("taskListShow", "taskList",
				taskList);
		mav.addObject("task", new Task(""));
		return mav;
	}
	
	@ModelAttribute("users")
	public List<User> userList() {
		User authenticatedUser = authenticationService.getAuthenticatedUser();
		List<User> users = userService.findAllActiveUsers();
		users.remove(authenticatedUser);
		return users;
	}

	@RequestMapping(value = "*-edit.htm")
	public String updateTaskList(@RequestParam String taskListName, HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?(\\d*?)-edit\\.htm", "$1"));

		User authenticatedUser = authenticationService.getAuthenticatedUser();
		TaskList taskList = taskListService.findByIdAndOwner(id,
				authenticatedUser);
		taskList.setName(taskListName);
		
		taskListService.update(taskList);

		return "ajaxRequestResult";
	}

	@RequestMapping(value = "*-delete.htm")
	public String deleteTaskList(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?(\\d*?)-delete\\.htm", "$1"));

		User authenticatedUser = authenticationService.getAuthenticatedUser();
		TaskList taskList = taskListService.findByIdAndOwner(id,
				authenticatedUser);
		
		taskListService.delete(taskList);

		return "redirect:/dashboard/index.htm";
	}
}
