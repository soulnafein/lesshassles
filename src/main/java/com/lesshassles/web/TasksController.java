package com.lesshassles.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lesshassles.model.Task;
import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;

@Controller
@RequestMapping("/tasklists/*/tasks/*.htm")
public class TasksController {

	@Autowired
	TaskListService taskListService;

	public void setTaskListService(TaskListService taskListService) {
		this.taskListService = taskListService;
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.POST)
	public String submitForm(Task task, HttpServletRequest request) {

		Integer id = Integer.parseInt(request.getRequestURI().replaceAll(
				".*?\\/tasklists\\/(\\d*?)\\/.*", "$1"));
		TaskList taskList = taskListService.findById(id);
		taskList.addTask(task);

		taskListService.update(taskList);

		return String.format("redirect:/tasklists/%d/tasks/%d.htm", taskList
				.getId(), task.getId());
	}

}
