package com.thinking.machines.listener;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class ServletContextApplicationListener implements ServletContextListener
{
private static ServletContext servletContext;

@Override
public void contextDestroyed(ServletContextEvent contextEvent) 
{
servletContext = null;
System.out.println("context destroyed");
}

@Override
public void contextInitialized(ServletContextEvent contextEvent) 
{
servletContext = contextEvent.getServletContext();
System.out.println("context Initialized");
}
public static ServletContext getApplicationContext()
{
return servletContext; 
}
}
