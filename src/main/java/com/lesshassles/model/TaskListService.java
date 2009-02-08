package com.lesshassles.model;

import java.util.List;

public interface TaskListService {

    Integer save(TaskList taskList);

    void update(TaskList taskList);

    TaskList findById(Integer id);

	List<TaskList> findAll();

}
