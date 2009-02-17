package com.lesshassles.persistence;

import java.util.List;

import com.lesshassles.model.TaskList;
import com.lesshassles.model.User;

public interface TaskListDAO {
    Integer makePersistent(TaskList taskList);

    TaskList findByIdAndOwner(Integer id, User owner);

    void update(TaskList taskList);

	List<TaskList> findByOwner(User owner);
}
