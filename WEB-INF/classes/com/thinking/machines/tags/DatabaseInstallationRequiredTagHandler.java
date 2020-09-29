package com.thinking.machines.tags;
import com.thinking.machines.listener.*;
import com.thinking.machines.dao.*;
import com.thinking.machines.model.*;
import com.thinking.machines.servlets.*;
import com.thinking.machines.initializer.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class DatabaseInstallationRequiredTagHandler extends BodyTagSupport
{
public DatabaseInstallationRequiredTagHandler()
{

}
public int doStartTag()
{
try
{
System.out.println("\n\n\n DatabaseInstallationRequiredTagHandler \n\n\n");
System.out.println(StudentDatabaseInitializer.getApplicationServletContext().getRealPath(""));
ServletContext sc=StudentDatabaseInitializer.getApplicationServletContext();
String realPath=sc.getRealPath("");
System.out.println("Real Path :-> "+realPath);
StudentModel em=(StudentModel)sc.getAttribute("model");
System.out.println("DAOConnection : "+em.getDAOConnection().getConnection());
if(em.getDAOConnection().getConnection()!=null)
{
System.out.println("Connection exists....\n\n");
return SKIP_BODY;
}
else
{
System.out.println("Connection doesn't exists....\n\n");
}
System.out.println("Tag Chala Request Aayi");
}catch(Exception exception)
{
System.out.println(exception);
}
return EVAL_BODY_INCLUDE;
}
public int doAfterBody()
{
System.out.println("Body Chali Request process");
return SKIP_BODY;
}
public int doEndTag()
{
System.out.println("Tag End Response gaya");
return EVAL_PAGE;
}
}
