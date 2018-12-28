package com.task.dtos;

/**
 * Basic DTO with task info. 
 * 
 * @author Antonio
 */
public class Task {

	/* The id of the task */
	private String idTask;

	/* The id of the task in which it depends. If the task has no dependency, then it will be null */
	private String dependency;

	public Task() {

	}

	public Task(String idTask, String dependency) {
		this.idTask = idTask;
		this.dependency = dependency;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Task)) {
			return false;
		}

		Task other = (Task) obj;

		if (idTask == null) {
			if (other.getIdTask() != null)
				return false;
		}
		return this.idTask.equals(other.getIdTask());
	}

	@Override
	public String toString() {
		
		return idTask + " (" + this.dependency + ")";
	}

}
