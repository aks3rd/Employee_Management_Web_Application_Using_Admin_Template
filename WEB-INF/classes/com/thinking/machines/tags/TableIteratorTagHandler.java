package com.thinking.machines.tags;
import com.thinking.machines.beans.*;
import com.thinking.machines.dao.*;
import com.thinking.machines.model.*;
import com.thinking.machines.servlets.*;
import com.thinking.machines.initializer.*;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class TableIteratorTagHandler extends BodyTagSupport
{
private String table;
private String name;
private String orderBy;
private int index;
private String queryString;
private java.sql.Date dateOfBirth;
private String dateOfBirthString;

private Connection connection;
private Statement statement;
private ResultSet resultSet;
public TableIteratorTagHandler()
{
reset();
}
private void reset()
{
this.name="";
this.table="";
this.orderBy="";
this.index=0;
this.connection=null;
this.statement=null;
this.resultSet=null;
this.queryString="";
}
public void setTable(String table)
{
this.table=table;
}
public String getTable()
{
return this.table;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setOrderBy(String orderBy)
{
this.orderBy=orderBy;
}
public String getOrderBy()
{
return this.orderBy;
}

public int doStartTag()
{
try
{
ServletContext sc=StudentDatabaseInitializer.getApplicationServletContext();
String realPath=sc.getRealPath("");
System.out.println("Real Path :-> "+realPath);
StudentModel em=(StudentModel)sc.getAttribute("model");
System.out.println("DAOConnection : "+em.getDAOConnection().getConnection());


connection=em.getDAOConnection().getConnection();
queryString="select * from "+table;
if(orderBy!=null) queryString=queryString+" order by "+orderBy;
statement=connection.createStatement();
resultSet=statement.executeQuery(queryString);
index=0;
if(!resultSet.next()) return SKIP_BODY;
setupData();

}catch(SQLException sqlException)
{
System.out.println(sqlException);//remove after testing
}
catch(DAOException daoException)
{
System.out.println(daoException);
}
return EVAL_BODY_INCLUDE;
//either write return EVAL_BODY_INCLUDE; or return SKIP_BODY;
}

public int doAfterBody()
{
try
{
if(!resultSet.next()) return SKIP_BODY;
setupData();

}catch(SQLException sqlException)
{
System.out.println(sqlException);
}
return EVAL_BODY_AGAIN;
//either write return EVAL_BODY_INCLUDE; or return SKIP_BODY;
}
public int doEndTag()
{
try
{

resultSet.close();
statement.close();
connection.close();
reset();
}catch(SQLException sqlException)
{
System.out.println(sqlException);//remove after testing
}
return EVAL_PAGE;
//either write return EVAL_PAGE; or return SKIP_PAGE; // the rest of the jsp will be processesd
}
private void setupData()
{
try
{
if(table.equalsIgnoreCase("city"))
{
CityBean c=new CityBean();
c.setCode(resultSet.getInt("code"));
c.setName(resultSet.getString("name").trim());
pageContext.setAttribute(name,c,PageContext.SESSION_SCOPE);
pageContext.setAttribute("index",index,PageContext.SESSION_SCOPE);
}
if(table.equalsIgnoreCase("student_view"))
{
System.out.println("Student Wala Chala");
StudentBean studentBean=new StudentBean();
studentBean.setRollNumber(resultSet.getInt("Roll_Number"));
studentBean.setName(resultSet.getString("Name").trim());
studentBean.setAddress(resultSet.getString("Address").trim());
studentBean.setGender(resultSet.getString("Gender"));
dateOfBirth=resultSet.getDate("Date_Of_Birth");
dateOfBirthString=dateOfBirth.getDate()+"-"+(dateOfBirth.getMonth()+1)+"-"+(dateOfBirth.getYear()+1900);
System.out.println(dateOfBirth);
System.out.println(dateOfBirthString);
studentBean.setDateOfBirth(dateOfBirthString);
studentBean.setIndian(resultSet.getBoolean("Indian"));
studentBean.setCityCode(resultSet.getInt("City_Code"));
studentBean.setCityName(resultSet.getString("City_Name").trim());
pageContext.setAttribute(name,studentBean,PageContext.SESSION_SCOPE);
pageContext.setAttribute("index",index,PageContext.SESSION_SCOPE);
}
index++;
}catch(SQLException sqlException)
{
System.out.println(sqlException);//remove after testing
}

}
}
