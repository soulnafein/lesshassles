package com.lesshassles.model;

import java.util.regex.Pattern;

public class TaskListSpecification {
	private final static Pattern validNamePattern = Pattern.compile("([a-zA-Z0-9.\\(\\)\\-']+\\s)*[a-zA-Z0-9.\\(\\)\\-']+");
	
	public Boolean isSatisfiedBy(TaskList taskList) {
		if (taskList == null) { 
			return false;
		} else {		
			return validNamePattern.matcher(taskList.getName()).matches();
		}
	}
}
