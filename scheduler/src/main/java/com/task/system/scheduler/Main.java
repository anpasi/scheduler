package com.task.system.scheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.task.services.TaskScheduler;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

		ApplicationContext appContext = 
				new ClassPathXmlApplicationContext("../BeanLocations.xml");
		
		TaskScheduler taskScheduler = (TaskScheduler)appContext.getBean("taskScheduler");
		
		try {
			taskScheduler.processTasks(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//		List<City> cities = csvReader.getAllCities(classloader.getResourceAsStream("test_three.csv"));
		
        
    }
}
