package com.thinking.machines.beans;
public class UserBean implements java.io.Serializable
{
private String username;
private String password;
public UserBean()
{
this.username=null;
this.password=null;
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
public String getPassword()
{
return this.password;
}
}
