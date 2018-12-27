package com.task.system.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.task.dtos.Task;
import com.task.services.TaskScheduler;

/**
 * Hello world!
 *
 */
public class Main 
{
	public static void main( String[] args )    {
		System.out.println( "Hello World!" );

		ApplicationContext appContext = 
				new ClassPathXmlApplicationContext("BeanLocations.xml");

		TaskScheduler taskScheduler = (TaskScheduler)appContext.getBean("taskScheduler");

		try {
			System.out.println("result = " + taskScheduler.processTasks(null));
			System.out.println("result = " + taskScheduler.processTasks(new ArrayList<Task>()));
			System.out.println("result = " + taskScheduler.processTasks(generateTaskList0()));
			System.out.println("result = " + taskScheduler.processTasks(generateTaskList()));
			System.out.println("result = " + taskScheduler.processTasks(generateTaskList2()));
			System.out.println("result = " + taskScheduler.processTasks(generateTaskListCircular()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Task> generateTaskList0(){

		List<Task> list = new ArrayList<Task>();

		Task task = new Task("A", "B");
		list.add(task);
		return list;
	}

	public static List<Task> generateTaskList(){

		List<Task> list = new ArrayList<Task>();

		Task task = new Task("A", "B");
		list.add(task);

		task = new Task("B", null);
		list.add(task);
		
		task = new Task("C", "D");
		list.add(task);

		task = new Task("D", null);
		list.add(task);
		
		return list;
	}
	
	public static List<Task> generateTaskList2(){

		List<Task> list = new ArrayList<Task>();

		Task task = new Task("A", "B");
		list.add(task);

		task = new Task("B", "C");
		list.add(task);
		
		task = new Task("C", null);
		list.add(task);
		
		return list;
	}
	
	public static List<Task> generateTaskListCircular(){

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
