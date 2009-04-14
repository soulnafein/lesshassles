package com.lesshassles.model;

import java.util.Date;
import java.util.Comparator;

public class DeadlineComparator implements Comparator<Task> {

	public int compare(Task task1, Task task2) {
		if (task1 == null || task2 == null)
			throw new IllegalArgumentException("You can't compare with a null Task");
		
		if (task1.equals(task2))
			return 0;
			
		Date deadline1 = task1.getDeadline();
		Date deadline2 = task2.getDeadline();
		
		if (deadline1 == null && deadline2 == null) {
			return task1.getDescription().compareTo(task2.getDescription());
		}
		
		if (deadline1 == null)
			return 1;
		
		if (deadline2 == null)
			return -1;
		
		if (deadline1.getTime() > deadline2.getTime())
			return -1; 
		else
			return 1;
	}

}
