package com.lesshassles.persistence;

import com.lesshassles.model.TaskList;

public interface TaskListDAO {
	Integer makePersistent(TaskList taskList);
	TaskList findById(Integer id);
	void update(TaskList taskList);
}
