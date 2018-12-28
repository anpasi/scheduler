package com.task.system.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.task.dtos.Task;
import com.task.services.TaskScheduler;

public class TaskSchedulerImplTest {
	
	private TaskScheduler service;
	
	@Before
	public void beforeEachTest() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("BeanLocations.xml");
		service = (TaskScheduler)appContext.getBean("taskScheduler");
	}
	
	@Test
	public void testExceptionWithCircleDependencies() {
		try {
			//When
			service.processTasks(generateCyclicTaskList());
			fail();
		} catch (Exception expected) {
			//Then
			assertEquals("Error - this is a cyclic dependency", expected.getMessage());
		}
	}
	
	@Test
	public void testEmptyResultWithNullTaskList() throws Exception{
		//When
		String result = service.processTasks(null);
		//Then
		assertEquals("", result);
	}
	
	@Test
	public void testEmptyResultWithEmptyTaskList() throws Exception{
		//Given 
		List<Task> tasks = Collections.emptyList();
		//When
		String result = service.processTasks(tasks );
		//Then
		assertEquals("", result);
	}
	
	@Test
	public void testEmptyResultWithTaskListWithNoDependencies() throws Exception{
		//When
		String result = service.processTasks(generateTaskListWithNoDependecies() );
		//Then
		assertEquals("[A, B, C, D]", result);
	}
	
	@Test
	public void testEmptyResultWithTaskListWithDependencies() throws Exception{
		//When
		String result = service.processTasks(generateTaskListWithDependencies() );
		//Then
		assertEquals("[C, B, A]", result);
	}
	
	/**
	 * Creates a task list that does not contain any dependecy. So the order of the tasks is 
	 * the same as the list
	 * @return list of tasks
	 */
	private List<Task> generateTaskListWithNoDependecies(){
		List<Task> list = new ArrayList<Task>();

		Task task = new Task("A", null);
		list.add(task);

		task = new Task("B", null);
		list.add(task);
		
		task = new Task("C", null);
		list.add(task); 

		task = new Task("D", null);
		list.add(task);
		
		return list;
	}
	
	/**
	 * Creates a task list that in which each task has a dependency, and this dependency is the following task, so the order
	 * of the execution is the reverse of their order in the list.
	 * @return list of tasks
	 */
	private List<Task> generateTaskListWithDependencies(){

		List<Task> list = new ArrayList<Task>();

		Task task = new Task("A", "B");
		list.add(task);

		task = new Task("B", "C");
		list.add(task);
		
		task = new Task("C", null);
		list.add(task);
		
		return list;
	}

	/**
	 * Creates a task list with a cyclic dependency
	 * @return list of tasks
	 */
	private List<Task> generateCyclicTaskList(){
		List<Task> list = new ArrayList<Task>();
		
		Task task = new Task("A", "B");
		list.add(task);
		
		task = new Task("B", "C");
		list.add(task);
		
		task = new Task("C", null);
		list.add(task);
		
		task = new Task("D", "A");
		list.add(task);
		
		return list;
	}

}
