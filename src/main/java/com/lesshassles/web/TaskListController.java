package com.lesshassles.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lesshassles.model.Task;
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
    public ModelAndView show(HttpServletRequest request) throws IOException {

	Integer id = Integer.parseInt(request.getRequestURI().replaceAll(
		".*?(\\d*?)\\.htm", "$1"));

	TaskList taskList = taskListService.findById(id);

	ModelAndView mav = new ModelAndView("taskListShow", "taskList",
		taskList);
	mav.addObject("task", new Task(""));
	return mav;
    }

    public void setTaskListService(TaskListService taskListService) {
	this.taskListService = taskListService;
    }

    @RequestMapping(value = "browse.htm")
	public ModelAndView browse() {
    	List<TaskList> taskLists = taskListService.findAll();
		return new ModelAndView("taskListBrowse", "taskLists", taskLists);
	}
}
