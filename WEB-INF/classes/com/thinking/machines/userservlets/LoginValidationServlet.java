package com.thinking.machines.userservlets;
import com.thinking.machines.beans.*;
import com.thinking.machines.model.*;
import com.thinking.machines.dao.*;
import com.thinking.machines.algorithms.*;
import com.thinking.machines.annotations.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.sql.*;
@Path("/Login")
public class LoginValidationServlet
{
@ResponseType("JSON")
@Path("/loginValidation")
public AJAXResponse loginValidation(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("\n\n\n\nLoginValidationServlet is processed\n\n\n\n");
UserBean ub=null;
AJAXResponse ajaxResponse=null;
try
{
HttpSession hs=request.getSession(false);
if(hs!=null)
{
ub=(UserBean)hs.getAttribute("loggedUser");
if(ub!=null)
{
ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(ub);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
return ajaxResponse;
}
else
{
ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Please go to login page");
return ajaxResponse;
}//ub if ends
}
else
{
ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Session not found");
return ajaxResponse;
}//hs id ends

}catch(Exception exception)
{
ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException(exception.getMessage());
return ajaxResponse;
}
//return ajaxResponse;
}

@ResponseType("JSON")
@Path("/login")
public AJAXResponse login(HttpServletRequest request,HttpServletResponse response,ServletContext servletContext,UserBean userBean)
{
AJAXResponse ajaxResponse=null;
System.out.println("\n\n\n\nLoginServlet is processed\n\n\n\n");
try
{
String userId=userBean.getUsername();
String password=userBean.getPassword();
System.out.println("User ID : "+userId+", Password : "+password);
//ServletContext sc=getServletContext();
ServletContext sc=servletContext;
StudentModel em=(StudentModel)sc.getAttribute("model");
Connection c=em.getDAOConnection().getConnection();

if(c!=null)
{
ResultSet resultSet;
PreparedStatement ps=c.prepareStatement("select * from administrator where username=?;");
ps.setString(1,userId);
resultSet=ps.executeQuery();
if(resultSet.next())
{
String user=resultSet.getString("username");
String encryptPassword=resultSet.getString("password");
String passwordKey=resultSet.getString("password_key");
String decryptPassword=AES.decrypt(encryptPassword,passwordKey);
System.out.println("User : "+user+",Encrypt Password : "+encryptPassword+", Password Key : "+passwordKey+",Decrypt Password : "+decryptPassword);
if(userId.equals(user) && !(password.equals(decryptPassword)))
{
ajaxResponse=new AJAXResponse();	
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Invalid Password");
ps.close();
resultSet.close();
return ajaxResponse;
}
if(userId.equals(user) && password.equals(decryptPassword))
{
UserBean ub=new UserBean();
ub.setUsername(user);
ub.setPassword(decryptPassword);
HttpSession hs=request.getSession();
hs.setAttribute("loggedUser",ub);
hs.setMaxInactiveInterval(60*10);

ajaxResponse=new AJAXResponse();	
ajaxResponse.setResponse(ub);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
ps.close();
resultSet.close();
return ajaxResponse;
}
}
else
{
System.out.println("User id invalid\n\n\n");
ajaxResponse=new AJAXResponse();	
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Invalid UserId..");
ps.close();
resultSet.close();
return ajaxResponse;
}
}
else
{
ajaxResponse=new AJAXResponse();	
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Connection Failed..");
return ajaxResponse;
}
}catch(Exception exception)
{
try{
ajaxResponse=new AJAXResponse();	
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException(exception.getMessage());
return ajaxResponse;
}catch(Exception e){}
System.out.println("LOGIN : "+exception);
}
return ajaxResponse;
}








}
