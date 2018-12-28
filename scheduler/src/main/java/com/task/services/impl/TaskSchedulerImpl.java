package com.task.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.task.dtos.Task;
import com.task.services.TaskScheduler;

@Service("taskScheduler")
public class TaskSchedulerImpl implements TaskScheduler {
	
	public String processTasks(List<Task> tasks) throws Exception {
		
		if (CollectionUtils.isEmpty(tasks)) {
			return "";
		}
		LinkedList<String> taskOrderList = new LinkedList<String>();
		LinkedList<String> dependencyList = new LinkedList<String>();
		
		for (Task task: tasks) {
			if (task.getDependency()!=null) {
				if (dependencyList.isEmpty()) {
					dependencyList.add(task.getIdTask());
				}
				dependencyList.addFirst(task.getDependency());
				addDependencyTaskToList(task.getIdTask(), task.getDependency(), taskOrderList);
			} else {
				if (!taskOrderList.contains(task.getIdTask())) {
					taskOrderList.addLast(task.getIdTask());
				}
			}
			
			if (dependencyList.size()>1 && dependencyList.getFirst().equals(dependencyList.getLast())){
				throw new Exception("Error - this is a cyclic dependency");
			}
		}
		
		return taskOrderList.toString();
	}
	
	/**
	 * Adds the task to the execution list in the right order according to the dependency
	 * @param idTask the task to add
	 * @param dependencyTask the id of the dependency task
	 * @param linkedlist the list with the task
	 */
	private void addDependencyTaskToList(String idTask, String dependencyTask, LinkedList<String> linkedlist) {
		
		if (linkedlist.isEmpty()) {
			linkedlist.addLast(dependencyTask);
			linkedlist.addLast(idTask);
			return;
		}
		
		int taskPosition = linkedlist.indexOf(idTask);
		
		if (taskPosition<0) {
			linkedlist.addLast(dependencyTask);
			linkedlist.addLast(idTask);
		} else {
			if (!linkedlist.contains(dependencyTask)) {
				linkedlist.add(taskPosition, dependencyTask);
			} 
		}
	}

}
