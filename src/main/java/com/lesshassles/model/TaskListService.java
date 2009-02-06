package com.lesshassles.model;


public interface TaskListService {

	Integer save(TaskList taskList);
	
	void update(TaskList taskList);

	TaskList findById(Integer id);

	

}
