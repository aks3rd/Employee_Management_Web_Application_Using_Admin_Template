package com.thinking.machines.beans;
public class ErrorBean implements java.io.Serializable
{
private String type="";
private boolean success=false;
private String message="";
private Object object=null;

public ErrorBean()
{
this.type="";
this.success=false;
this.message="";
this.object=null;
}
public ErrorBean(String type,boolean success,String message,Object object)
{
this.type=type;
this.success=success;
this.message=message;
this.object=object;
}
public void setType(String type)
{
this.type=type;
}
public String getType()
{
return this.type;
}
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setMessage(String message)
{
this.message=message;
}
public String getMessage()
{
return this.message;
}
public void setObject(Object object)
{
this.object=object;
}
public Object getObject()
{
return this.object;
}
}
