package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import com.google.gson.*;
import com.thinking.machines.service.*;
import com.thinking.machines.annotations.*;
//import com.thinking.machines.beans.*;
@MultipartConfig
public class TMService extends HttpServlet
{
 public void doPost(HttpServletRequest request, HttpServletResponse response)
 {
   PrintWriter pw=null;
   String responseString="";
   Object responseData=null;
   Gson gson=null;
   ServletContext servletContext=null;
  try
  {
   servletContext = getServletContext();
   Map<String, Service> map=(Map<String, Service>)servletContext.getAttribute("serviceMap");
   if(map==null)
   {     
     pw=response.getWriter();
     response.setContentType("application/json");
     pw.println("Error ! No Java(Path annonated) classes found.");
     return;
   }
   String requestURL=request.getRequestURI().toString();
   System.out.println("Request Url : "+requestURL);
   String searchURL=requestURL.substring(requestURL.indexOf("service") + "service".length());

   if(!map.containsKey(searchURL))
   {
     pw=response.getWriter();
     response.setContentType("application/json");
     pw.println("Response Service not found");
     return;
   }
   else
   {
     processRequest(searchURL,map,request,response);
   }
  }catch(Exception exception)
  {
    try{
     pw=response.getWriter();
     response.setContentType("application/json");
     pw.println(exception.getMessage());
    }catch(Exception e){}
  }
 }

 public void processRequest(String searchURL,Map<String, Service> map,HttpServletRequest request, HttpServletResponse response)
 {
   PrintWriter pw=null;
   String responseString="";
   Object responseData=null;
   Gson gson=null;
   ServletContext servletContext=null;
  try
  {
     servletContext = getServletContext();
     Service service=map.get(searchURL);
     Object instance=service.getInstance();
     if(instance==null)
     {
       instance=service.getObjectClass().newInstance();
       service.setInstance(instance);
     }
     Method method=service.getObjectMethod();

     Secured secured=method.getAnnotation(Secured.class);
     boolean isSecured=false;
     if(secured==null)
     {
       secured=(Secured)service.getObjectClass().getAnnotation(Secured.class);
       if(secured!=null)isSecured=true;
     }
     else isSecured=true;
     if(isSecured)
     {
       Class<?> c=Class.forName(secured.value());
       Object o=c.newInstance();
       if(o==null)throw new Exception("Security error!");
       if((boolean)c.getMethod("hasAuthentication",HttpServletRequest.class,HttpServletResponse.class,ServletContext.class).invoke(o,request,response,servletContext)!=true)
       {
         pw=response.getWriter();
         response.setContentType("application/json");
         responseString=(String)c.getMethod("doProcess",HttpServletRequest.class, HttpServletResponse.class,ServletContext.class).invoke(o,request,response,servletContext).toString();
         System.out.println(responseString);
         pw.print(responseString);
         return;
       }
     }




     //System.out.println("Parameter Count : "+method.getParameterCount());
     Object [] arguments=new Object[method.getParameterCount()];
     if(method.getParameterCount()==0)
     {
	arguments=null;       
     }
     else
     {
       gson=new Gson();

       Annotation [][] parameterAnnotations=method.getParameterAnnotations();
       Class [] parameterTypes=method.getParameterTypes();
       int i=0;

       for(Annotation[] annotations:parameterAnnotations)
       {
          Class parameterType=parameterTypes[i];
          //System.out.println(parameterType);

          boolean requestDataFlag=false;
          for(Annotation annotation:annotations)
          {
             if(annotation instanceof RequestData)
             {
               RequestData rd=(RequestData)annotation;
               //System.out.println("Parameter : "+parameterType.getName());
               //System.out.println("Annoation"+rd+"Value: "+rd.value());
               String queryData=request.getParameter(rd.value());
               //System.out.println("Query Data : "+queryData);
               if(parameterType==String.class)
               {
                 //System.out.println("Paramter Type String.class");
                 arguments[i++]=new String(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Byte.class || parameterType==byte.class)
               {
                 //System.out.println("Paramter Type Byte.class || byte.class");
                 arguments[i++]=Byte.parseByte(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Short.class || parameterType==short.class)
               {
                 //System.out.println("Paramter Type Short.class || short.class");
                 arguments[i++]=Short.parseShort(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Integer.class || parameterType==int.class)
               {
                 //System.out.println("Paramter Type Integer.class || int.class");
                 arguments[i++]=Integer.parseInt(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Long.class || parameterType==long.class)
               {
                 //System.out.println("Paramter Type Long.class || long.class");
                 arguments[i++]=Long.parseLong(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Float.class || parameterType==float.class)
               {
                 //System.out.println("Paramter Type Float.class || float.class");
                 arguments[i++]=Float.parseFloat(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Double.class || parameterType==double.class)
               {
                 //System.out.println("Paramter Type Double.class || double.class");
                 arguments[i++]=Double.parseDouble(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Boolean.class || parameterType==boolean.class)
               {
                 //System.out.println("Paramter Type Boolean.class || boolean.class");
                 arguments[i++]=Boolean.parseBoolean(queryData);
                 requestDataFlag=true;
                 break;
               }
               else if(parameterType==Character.class || parameterType==char.class)
               {
                 //System.out.println("Paramter Type Character.class || char.class");
                 arguments[i++]=queryData.replace('\'',' ').trim().charAt(0);
                 requestDataFlag=true;
                 break;
               }
             }
          }
          if(requestDataFlag)continue;
          if(parameterType==HttpServletRequest.class)
          {
            arguments[i++]=request;
            continue;
          }
          if(parameterType==HttpServletResponse.class)
          {
            arguments[i++]=response;
            continue;
          }
          if(parameterType==ServletContext.class)
          {
            arguments[i++]=servletContext;
            continue;
          }
	  if(parameterType==File.class)
	  {
	    System.out.println("File.class");
            String uploadFilePath=servletContext.getRealPath("")+"WEB-INF"+File.separator+"uploads";
            System.out.println("UploadFilePath : "+uploadFilePath);
            File uploadFileDirectory=new File(uploadFilePath);
            if(!uploadFileDirectory.exists())uploadFileDirectory.mkdir();
          
            String fileName="";
            for(Part filePart:request.getParts())
            {
              System.out.println(filePart);
              fileName=getFileName(filePart);
              if(fileName==null)continue;
              File file=new File(uploadFilePath+File.separator+fileName);
              if(file.exists())file.delete();
              filePart.write(uploadFilePath+File.separator+fileName);
              arguments[i++]=file;
              break;
            }
            continue;
	  }
	  if(parameterType==File[].class)
	  {
	    System.out.println("File[].class");
            List<File> filesList=new ArrayList<>();
            String uploadFilePath=servletContext.getRealPath("")+"WEB-INF"+File.separator+"uploads";
            System.out.println("UploadFilePath : "+uploadFilePath);
            File uploadFileDirectory=new File(uploadFilePath);
            if(!uploadFileDirectory.exists())uploadFileDirectory.mkdir();
            OutputStream out = null;
            InputStream filecontent = null;
            String fileName="";
            for(Part filePart:request.getParts())
            {
              System.out.println(filePart);
              fileName=getFileName(filePart);
              if(fileName==null)continue;
              File file=new File(uploadFilePath+File.separator+fileName);
              if(file.exists())file.delete();
              filePart.write(uploadFilePath+File.separator+fileName);
              filesList.add(file);
            }
            arguments[i++]=filesList.toArray(new File[filesList.size()]);
            continue;
	  }
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
 
          arguments[i++]=gson.fromJson(jsonString,Class.forName(parameterType.getCanonicalName()));
        }
     }
     Class returnType=method.getReturnType();
     if(returnType==void.class)
     {
       System.out.println("void.class");
       method.invoke(instance,arguments);
       responseData="";
       responseString="";
     }
     if(returnType==String.class)
     {
       System.out.println("String.class");
       responseData=method.invoke(instance,arguments);
       if(responseData!=null)responseString=(String)responseData.toString();
     }
     else if(returnType!=void.class)
     {
       responseData=method.invoke(instance,arguments);
       if(responseData!=null)responseString=(String)responseData.toString();
     }

     Annotation[] annotations=method.getAnnotations();
     ForwardRequest rq=null;
     for(Annotation an:annotations)
     {
       if(an instanceof ResponseType)
       {
         ResponseType type=(ResponseType)an;
         System.out.println("ResponseType : "+type.value());
         if(type.value().equalsIgnoreCase("NOTHING"))responseString="";
         if(type.value().equalsIgnoreCase("HTML/Text"))responseString=responseData.toString();
         if(type.value().equalsIgnoreCase("JSON"))
         {
           System.out.println("ResponseType JSON Chala");
           responseString=gson.toJson(responseData);
         }
       }
       if(an instanceof ForwardRequest)
       {
         rq=(ForwardRequest)an;
       }
     }
     if(rq!=null)
     {
       System.out.println("RequestForward : "+rq.value());
       if(map.containsKey(rq.value()))
       {
         System.out.println(rq.value()+" This is a serviceRequest so its process first.");
         processRequest(rq.value(),map,request,response);
         System.out.println(rq.value()+" Process complete");
         return;
       }
       RequestDispatcher rd;
       rd=request.getRequestDispatcher(rq.value());
       rd.forward(request,response);
       return;
     }
    pw=response.getWriter();
    response.setContentType("application/json");
    System.out.println(responseString);
    pw.print(responseString);
    return;
  }catch(Exception exception)
  {
     exception.printStackTrace();//Remove after testing
  }
 }




private String getFileName(Part filePart)
{
  String cd=filePart.getHeader("content-disposition");
  System.out.println(cd);
  String pcs[]=cd.split(";");
  for(String pc:pcs)
  {
    if(pc.indexOf("filename")!=-1)
    {
      String fn=pc.substring(pc.indexOf("=")+2,pc.length()-1);
      System.out.println("Considering & Saving : "+fn);
      return fn;
     }
  }
  return null;
}


}


//comments
/*      
       for(Annotation[] annotations:parameterAnnotations)
       {
          Class parameterType=parameterTypes[i];
          for(Annotation annotation:annotations)
          {
             if(annotation instanceof RequestData)
             {
               RequestData rd=(RequestData)annotation;
               //System.out.println("Parameter : "+parameterType.getName());
               //System.out.println("Annoation"+rd+"Value: "+rd.value());
               String queryData=request.getParameter(rd.value());
               //System.out.println("Query Data : "+queryData);

               if(parameterType==Byte.class || parameterType==byte.class)
               {
                 //System.out.println("Paramter Type Byte.class || byte.class");
                 arguments[i]=Byte.parseByte(queryData);
               }
               else if(parameterType==Short.class || parameterType==short.class)
               {
                 //System.out.println("Paramter Type Short.class || short.class");
                 arguments[i]=Short.parseShort(queryData);
               }
               else if(parameterType==Integer.class || parameterType==int.class)
               {
                 //System.out.println("Paramter Type Integer.class || int.class");
                 arguments[i]=Integer.parseInt(queryData);
               }
               else if(parameterType==Long.class || parameterType==long.class)
               {
                 //System.out.println("Paramter Type Long.class || long.class");
                 arguments[i]=Long.parseLong(queryData);
               }
               else if(parameterType==Float.class || parameterType==float.class)
               {
                 //System.out.println("Paramter Type Float.class || float.class");
                 arguments[i]=Float.parseFloat(queryData);
               }
               else if(parameterType==Double.class || parameterType==double.class)
               {
                 //System.out.println("Paramter Type Double.class || double.class");
                 arguments[i]=Double.parseDouble(queryData);
               }
               else if(parameterType==Boolean.class || parameterType==boolean.class)
               {
                 //System.out.println("Paramter Type Boolean.class || boolean.class");
                 arguments[i]=Boolean.parseBoolean(queryData);
               }
               else if(parameterType==Character.class || parameterType==char.class)
               {
                 //System.out.println("Paramter Type Character.class || char.class");
                 arguments[i]=queryData.replace('\'',' ').trim().charAt(0);
               }

               else if(parameterType==String.class)
               {
                 //System.out.println("Paramter Type String.class");
                 arguments[i]=new String(queryData);
               }
             }
          }
          i++;
       }


















       for(Class parameterType:parameterTypes)
       {
         System.out.println(parameterType);
         if(parameterType==HttpServletRequest.class)
         {
           arguments[i++]=request;
           continue;
         }
         if(parameterType==HttpServletResponse.class)
         {
           arguments[i++]=response;
           continue;
         }
         if(parameterType==ServletContext.class)
         {
           arguments[i++]=servletContext;
           continue;
         }
	 if(parameterType==File.class)
	 {
	   System.out.println("File.class");
           String uploadFilePath=servletContext.getRealPath("")+"WEB-INF"+File.separator+"uploads";
           System.out.println("UploadFilePath : "+uploadFilePath);
           File uploadFileDirectory=new File(uploadFilePath);
           if(!uploadFileDirectory.exists())uploadFileDirectory.mkdir();
          
           String fileName="";
           for(Part filePart:request.getParts())
           {
             System.out.println(filePart);
             fileName=getFileName(filePart);
             if(fileName==null)continue;
             File file=new File(uploadFilePath+File.separator+fileName);
             if(file.exists())file.delete();
             filePart.write(uploadFilePath+File.separator+fileName);
             arguments[i++]=file;
             break;
           }
           continue;
	 }
	 if(parameterType==File[].class)
	 {
	   System.out.println("File[].class");
           List<File> filesList=new ArrayList<>();
           String uploadFilePath=servletContext.getRealPath("")+"WEB-INF"+File.separator+"uploads";
           System.out.println("UploadFilePath : "+uploadFilePath);
           File uploadFileDirectory=new File(uploadFilePath);
           if(!uploadFileDirectory.exists())uploadFileDirectory.mkdir();
           OutputStream out = null;
           InputStream filecontent = null;
           String fileName="";
           for(Part filePart:request.getParts())
           {
             System.out.println(filePart);
             fileName=getFileName(filePart);
             if(fileName==null)continue;
             File file=new File(uploadFilePath+File.separator+fileName);
             if(file.exists())file.delete();
             filePart.write(uploadFilePath+File.separator+fileName);
             filesList.add(file);
           }
           arguments[i++]=filesList.toArray(new File[filesList.size()]);
           continue;
	 }
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

         arguments[i]=gson.fromJson(jsonString,Class.forName(parameterType.getCanonicalName()));
         i++;
       }







*/

