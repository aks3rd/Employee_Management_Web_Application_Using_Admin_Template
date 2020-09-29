package com.thinking.machines.beans;
public class MessageBean implements java.io.Serializable
{
private String message="";
public MessageBean()
{
this.message="";
}
public MessageBean(String message)
{
this.message=message;
}
public void setMessage(String message)
{
this.message=message;
}
public String getMessage()
{
return this.message;
}
}
