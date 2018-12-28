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
		LinkedList<String> linkedlist = new LinkedList<String>();
		LinkedList<String> dependecies = new LinkedList<String>();
		
		for (Task task: tasks) {
			if (task.getDependency()!=null) {
				if (dependecies.isEmpty()) {
					dependecies.add(task.getIdTask());
				}
				dependecies.addFirst(task.getDependency());
				addDependencyTaskToList(task.getIdTask(), task.getDependency(), linkedlist);
			} else {
				if (!linkedlist.contains(task.getIdTask())) {
					linkedlist.addLast(task.getIdTask());
				}
			}
			
			if (dependecies.size()>1 && dependecies.getFirst().equals(dependecies.getLast())){
				throw new Exception("Error - this is a cyclic dependency");
			}
		}
		
		
		return linkedlist.toString();
	}
	
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
