package com.task.services;

import java.util.List;

import com.task.dtos.Task;

public interface TaskScheduler {
	
	/**
	 * Checks the list of tasks and return a list with the order in which they will be proccessed
	 * 
	 * @param tasks list of tasks to process
	 * @return the order of execution of the tasks
	 * @throws Exception 
	 */
	String processTasks(List<Task> tasks) throws Exception;

}
