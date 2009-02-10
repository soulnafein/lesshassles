package com.lesshassles.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesshassles.persistence.TaskListDAO;

@Service
@Transactional
public class TaskListServiceImpl implements TaskListService {
	@Autowired
	TaskListDAO taskListDAO;

	public Integer save(TaskList taskList) {
		if (taskList == null)
			throw new IllegalArgumentException(String.format(
					"%s save(TaskList taskList): taskList must be not null",
					this.getClass().getName()));

		taskList.setName(removeDuplicateWhitespacesAndTrim(taskList.getName()));

		return (Integer) taskListDAO.makePersistent(taskList);
	}

	public void update(TaskList taskList) {
		taskListDAO.update(taskList);
	}

	public TaskList findById(Integer id) {
		return taskListDAO.findById(id);
	}

	public void setTaskListDAO(TaskListDAO taskListDAO) {
		this.taskListDAO = taskListDAO;
	}

	private String removeDuplicateWhitespacesAndTrim(String name) {
		String patternStr = "\\s+";
		String replaceStr = " ";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(name);
		return matcher.replaceAll(replaceStr).trim();
	}

	public List<TaskList> findAll() {
		return taskListDAO.findAll();
	}
}
