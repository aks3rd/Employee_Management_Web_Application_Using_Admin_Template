package com.thinking.machines.servlets;
import com.thinking.machines.beans.*;
import com.thinking.machines.dao.*;
import com.thinking.machines.model.*;
import com.thinking.machines.algorithms.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
//import org.json.simple.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.sql.*;
//import java.text.*;
public class DatabaseInstallationServlet extends HttpServlet
{
private Connection c=null;
private ServletContext sc=null;
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Get type request not allowed");

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception exception)
{
System.out.println("JSON : "+exception);
}
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
//one time read only
BufferedReader reader=request.getReader();
StringBuilder sb=new StringBuilder();
String jsonString="";
String line;
while(true)
{
line=reader.readLine();
if(line==null)break;
sb.append(line);
}
reader.close();
jsonString=sb.toString();
System.out.println("JSON String : "+jsonString);

/*
JSONParser parser = new JSONParser();
JSONObject json = (JSONObject) parser.parse(jsonString);
System.out.println("Json Object : "+json);
*/

Gson gson=new Gson();
DatabaseInstallationBean dbib;
dbib=(DatabaseInstallationBean)gson.fromJson(jsonString,DatabaseInstallationBean.class);
String databaseName=dbib.getDatabaseName();
String serverName=dbib.getServerName();
int portNumber=dbib.getPortNumber();
String dbUserId=dbib.getDbUserId();
String dbPassword=dbib.getDbPassword();
String adUserId=dbib.getAdUserId();
String adPassword=dbib.getAdPassword();


System.out.println("Database Name : "+databaseName);
System.out.println("Server Name : "+serverName);
System.out.println("Port Number : "+portNumber);
System.out.println("DbUserId : "+dbUserId);
System.out.println("DbPassword : "+dbPassword);
System.out.println("AdUserId : "+adUserId);
System.out.println("AdPassword : "+adPassword);

Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://"+serverName.trim()+":"+portNumber+"/"+databaseName.trim(),dbUserId.trim(),dbPassword.trim());

//Class.forName("com.mysql.jdbc.Driver");
//c=DriverManager.getConnection("jdbc:mysql://localhost:3306/ajaxonedb","akuser","AK_user2020");

if(c!=null)
{
sc=getServletContext();
String realPath=sc.getRealPath("");
System.out.println("Real path : "+realPath);
String fileName="";
File file=null;

String queryString="";
Statement s=null;
FileReader fileReader=null;
BufferedReader br=null;
s=c.createStatement();


fileName=realPath+"WEB-INF"+File.separator+"script"+File.separator+"createTable.sql";
file=new File(fileName);
if(!file.exists())
{
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Missing createTable.sql file....");

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
//Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
return;
}

fileReader=new FileReader(file);
br=new BufferedReader(fileReader);
int m=0;
while(true)
{
if(m==-1)break;
queryString="";
while(m!=-1)
{
m=br.read();
if(m==-1)break;
//System.out.print((char)m);
if((char)m==';')
{
queryString+=(char)m;
break;
}
queryString+=(char)m;
}//end while
if(queryString.trim().length()>0)
{
System.out.println(queryString);//now removing after testing
s.executeUpdate(queryString);
System.out.println("Table created.....");//now removing after testing
}//end if
}//end while
br.close();

String uniqueKey=UUID.randomUUID().toString();
System.out.println("Unique Key : "+uniqueKey);

String orgPassword=adPassword;
String encryptedPassword=AES.encrypt(orgPassword,uniqueKey);
String decryptedPassword=AES.decrypt(encryptedPassword,uniqueKey);

System.out.println("Original Password : "+orgPassword);
System.out.println("Encrypted Password : "+encryptedPassword);
System.out.println("Decrypted Password : "+decryptedPassword);


queryString="insert into administrator(username,password,password_key) values('"+adUserId+"','"+encryptedPassword+"','"+uniqueKey+"');";
s.executeUpdate(queryString);
System.out.println("Record Inserted....");//now removing after testing


fileName=realPath+"WEB-INF"+File.separator+"script"+File.separator+"databaseInfo.data";
System.out.println("File Name : "+fileName);
file=new File(fileName);
RandomAccessFile raf=null;
if(file.createNewFile())
{
raf=new RandomAccessFile(file,"rw");
raf.writeBytes(databaseName.trim()+"\n");
raf.writeBytes(serverName.trim()+"\n");
raf.writeBytes(portNumber+"\n");
raf.writeBytes(dbUserId.trim()+"\n");
raf.writeBytes(dbPassword.trim()+"\n");
raf.close();
}

String connectionString="jdbc:mysql://"+serverName.trim()+":"+portNumber+"/"+databaseName.trim();
String username=dbUserId.trim();
String password=dbPassword.trim();

DAOConnection daoConnection=new DAOConnection();
daoConnection.setConnectionString(connectionString);
daoConnection.setUsername(username);
daoConnection.setPassword(password);

StudentModel em=(StudentModel)sc.getAttribute("model");
em.setDAOConnection(daoConnection);

AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("Database Created");
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
//Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}
else
{
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("Connection Faild");

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
//Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}
}catch(Exception exception)
{
try{
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException(exception.getMessage());

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception e){}
System.out.println("JSON : "+exception);
}
}
}
