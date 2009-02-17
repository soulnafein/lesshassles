package com.lesshassles.model;

import java.util.List;

public interface TaskListService {

    Integer save(TaskList taskList);

    void update(TaskList taskList);

    TaskList findByIdAndOwner(Integer id, User owner);

	List<TaskList> findByOwner(User owner);

}
