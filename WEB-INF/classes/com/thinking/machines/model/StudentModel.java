package com.thinking.machines.model;
import com.thinking.machines.dao.*;
public class StudentModel
{
private DAOConnection daoConnection;
public StudentModel()
{
this.daoConnection=null;
}
public void setDAOConnection(DAOConnection daoConnection)
{
this.daoConnection=daoConnection;
}
public DAOConnection getDAOConnection()
{
return this.daoConnection;
}
}
