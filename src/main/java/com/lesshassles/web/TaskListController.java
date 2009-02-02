package com.lesshassles.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.TaskList;
import com.lesshassles.model.TaskListService;

@Controller
@RequestMapping("/tasklists/*.htm")
public class TaskListController {

	@Autowired
	TaskListService taskListService;

	@RequestMapping(value = "new.htm", method = RequestMethod.GET)
	public ModelAndView showForm() {
		return new ModelAndView("taskListForm").addObject(new TaskList());
	}

	@RequestMapping(value = "new.htm", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("taskList") TaskList taskList) {
		Integer id = taskListService.save(taskList);
		String view = String.format("redirect:/tasklists/%d.htm", id);
		return view;
	}

	@RequestMapping(value = "*.htm")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Integer id;
		try {
			id = Integer.parseInt(request.getRequestURI().replaceAll(".*?(\\d*?)\\.htm", "$1"));
		} catch(NumberFormatException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		TaskList taskList = taskListService.findById(id);
		if (taskList == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return new ModelAndView("taskListShow", "taskList", taskList);
	}
	
	public void setTaskListService(TaskListService taskListService) {
		this.taskListService = taskListService;
	}
}
