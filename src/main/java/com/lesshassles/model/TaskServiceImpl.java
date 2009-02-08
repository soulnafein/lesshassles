package com.lesshassles.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesshassles.persistence.TaskDAO;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired 
    TaskDAO taskDAO;
    
    public Task findById(Integer id) {
	return taskDAO.findById(id);
    }

}
