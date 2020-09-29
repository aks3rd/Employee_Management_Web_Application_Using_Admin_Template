package com.thinking.machines.dao;
import java.sql.*;
public class DAOConnection
{
private String connectionString;
private String username;
private String password;
public void setConnectionString(String connectionString)
{
this.connectionString=connectionString;
}
public String getConnectionString()
{
return this.connectionString;
}
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public Connection getConnection() throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection(this.connectionString,this.username,this.password);
return c;
}catch(Exception e)
{
System.out.println(e);//now remove after testing
throw new DAOException(e.getMessage());
}
}
}
