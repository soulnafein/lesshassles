package com.lesshassles.persistence;

import com.lesshassles.model.Task;

public interface TaskDAO {

    Task findById(Integer id);

}
