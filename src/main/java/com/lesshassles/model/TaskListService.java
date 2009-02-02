package com.lesshassles.model;


public interface TaskListService {

	Integer save(TaskList taskList);

	TaskList findById(Integer id);

}
