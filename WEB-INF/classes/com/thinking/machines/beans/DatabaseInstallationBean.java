package com.thinking.machines.beans;
public class DatabaseInstallationBean implements java.io.Serializable
{
private String databaseName;
private String serverName;
private int portNumber;
private String dbUserId;
private String dbPassword;
private String adUserId;
private String adPassword;

public DatabaseInstallationBean()
{
this.databaseName="";
this.serverName="";
this.portNumber=0;
this.dbUserId="";
this.dbPassword="";
this.adUserId="";
this.adPassword="";
}
public void setDatabaseName(String databaseName)
{
this.databaseName=databaseName;
}
public String getDatabaseName()
{
return this.databaseName;
}
public void setServerName(String serverName)
{
this.serverName=serverName;
}
public String getServerName()
{
return this.serverName;
}
public void setPortNumber(int portNumber)
{
this.portNumber=portNumber;
}
public int getPortNumber()
{
return this.portNumber;
}
public void setDbUserId(String dbUserId)
{
this.dbUserId=dbUserId;
}
public String getDbUserId()
{
return this.dbUserId;
}
public void setDbPassword(String dbPassword)
{
this.dbPassword=dbPassword;
}
public String getDbPassword()
{
return this.dbPassword;
}

public void setAdUserId(String adUserId)
{
this.adUserId=adUserId;
}
public String getAdUserId()
{
return this.adUserId;
}
public void setAdPassword(String adPassword)
{
this.adPassword=adPassword;
}
public String getAdPassword()
{
return this.adPassword;
}
}
