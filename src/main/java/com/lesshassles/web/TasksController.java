package com.lesshassles.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;
import com.lesshassles.model.TaskService;

@Controller
@RequestMapping("/tasklists/*/tasks/*.htm")
public class TasksController {

	@Autowired
	TaskListService taskListService;

	@Autowired
	TaskService taskService;

	public void setTaskListService(TaskListService taskListService) {
		this.taskListService = taskListService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.POST)
	public String submitForm(Task task, HttpServletRequest request) {

		Integer taskListId = Integer.parseInt(request.getRequestURI()
				.replaceAll(".*?\\/tasklists\\/(\\d*?)\\/.*", "$1"));
		TaskList taskList = taskListService.findById(taskListId);
		task.setTaskList(taskList);

		if (taskList.getTasks().contains(task)) {
			return "taskErrorDuplicateEntry";
		}

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

		return new ModelAndView("taskShowJson", "task", task);

	}

}
