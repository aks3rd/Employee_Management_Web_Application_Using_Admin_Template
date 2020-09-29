import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.util.*;
import com.thinking.machines.beans.*;
import com.thinking.machines.service.*;
import com.thinking.machines.annotations.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

public class GenerateService
{
private static String fileLocation="";
private static Document serviceDocument;
private static Document errorDocument;
private static PdfWriter serviceWriter;
private static PdfWriter errorWriter;
private static int serviceNumber=0;
private static int errorNumber=0;
private static Font contentHeaderFont=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
private static Font contentFont=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL);

public static void main(String arg[])
{
GenerateService generateService=new GenerateService();
if(arg[0].endsWith("/"))
{
generateService.fileLocation=arg[0];
}
else
{
generateService.fileLocation=arg[0]+"/";
}
System.out.println("File location : "+generateService.fileLocation);
try
{
File fileLocationDirectory=new File(fileLocation);
if(!fileLocationDirectory.exists())fileLocationDirectory.mkdir();
File serviceFile=new File(fileLocation+"service.pdf");
if(serviceFile.exists())serviceFile.delete();
serviceDocument=new Document();
serviceWriter=PdfWriter.getInstance(serviceDocument,new FileOutputStream(fileLocation+"servie.pdf"));

Rectangle rectangle=new Rectangle(30,30,550,800);
serviceWriter.setBoxSize("rectangle",rectangle);

HeaderFooterPageEvent event = new HeaderFooterPageEvent();
serviceWriter.setPageEvent(event);
serviceDocument.open();

File errorFile=new File(fileLocation+"error.pdf");
if(errorFile.exists())errorFile.delete();
errorDocument=new Document();
errorWriter=PdfWriter.getInstance(errorDocument,new FileOutputStream(fileLocation+"error.pdf"));
errorWriter.setBoxSize("rectangle",rectangle);

event = new HeaderFooterPageEvent();
errorWriter.setPageEvent(event);
errorDocument.open();

    ArrayList<Class> classes=new ArrayList<Class>();

    File urlFile=new File("../WEB-INF/paths/packagePaths.path");
    FileInputStream fis = new FileInputStream(urlFile);
    byte[] data = new byte[(int) urlFile.length()];
    fis.read(data);
    fis.close();
    String str = new String(data, "UTF-8");	
    Gson g=new Gson();
    PackagePaths packagePaths=g.fromJson(str,PackagePaths.class);
    java.util.List<String> listOfPaths=packagePaths.getList();
    for(String realPath: listOfPaths)
    {
      System.out.println(realPath);
      File file=new File(realPath);
      File[] files=file.listFiles();
      String packageName="";
      for(File f: files)
      {
        System.out.println("FileName : "+f.getName());
        if(f.isDirectory())
        {
          if(packageName.length()==0)classes.addAll(getClasses(f,f.getName()));
          else classes.addAll(getClasses(f,packageName+"."+f.getName()));
        }
      }    
    }
    generatePdfs(classes);



serviceDocument.close();
errorDocument.close();
}catch(Exception exception)
{
exception.printStackTrace();
}
}

private static void generatePdfs(ArrayList<Class> classes)
{
try
{
    Map<String,Service> map=new HashMap<>();
    boolean classFlag=false;
    boolean methodFlag=false;
    for(Class c:classes)
    {
      System.out.println(c);
      if(c.isAnnotationPresent(Path.class)==false)
      {
        continue;	
      }
      String classPath="";
      Path classPathAnnotation=(Path)c.getAnnotation(Path.class);
      if(classPathAnnotation!=null)
      {
        System.out.println("ClassPathAnnotation value : "+classPathAnnotation.value());
        classPath=classPathAnnotation.value();
        Service service;
        String methodPath="";
        Method[] methods=c.getDeclaredMethods();
        for(Method method:methods)
        {
          if(method.isAnnotationPresent(Path.class)==false)
          {
            continue;	
          }
          methodPath="";
          Path methodPathAnnotation=method.getAnnotation(Path.class);
          if(methodPathAnnotation!=null)
          {
            methodPath=(classPath+methodPathAnnotation.value());
	    System.out.println("MethodPathAnnotation value : "+methodPathAnnotation.value()+",MethodPath : "+methodPath);
            if(map.get(methodPath)!=null)
            {
               Service error=map.get(methodPath);
               errorNumber++;
               errorDocument.add(new Paragraph(errorNumber+". ServicePath Exists Error :",contentHeaderFont));
               errorDocument.add(new Paragraph("Service path : "+methodPath+" ,Is already in use with following service",contentFont));
               errorDocument.add(new Paragraph("Service Class : "+error.getObjectClass().getName(),contentFont));
               String methodData="Service Method : "+error.getObjectMethod().getReturnType().getName()+" "+error.getObjectMethod().getName()+"(";
               for(Parameter param:error.getObjectMethod().getParameters())
               {
                 methodData+=param.getParameterizedType()+" ,";
               }
               if(error.getObjectMethod().getParameters().length!=0)
               {
                 methodData=methodData.substring(0,methodData.length()-1)+")";
               }
               else
               {
                 methodData+=")";
               }
               errorDocument.add(new Paragraph(methodData,contentFont));
               errorDocument.add(new Paragraph("So,It cannot be used with following service : ",contentFont));

               errorDocument.add(new Paragraph("Service Class : "+c.getName(),contentFont));
               methodData="Service Method : "+method.getReturnType().getName()+" "+method.getName()+"(";
               for(Parameter param:method.getParameters())
               {
                 methodData+=param.getParameterizedType()+" ,";
               }
               if(method.getParameters().length!=0)
               {
                 methodData=methodData.substring(0,methodData.length()-1)+")";
               }
               else
               {
                 methodData+=")";
               }
               errorDocument.add(new Paragraph(methodData,contentFont));               
               continue;
            }
            service=new Service();
            service.setPath(methodPath);
            service.setObjectClass(c);
            service.setObjectMethod(method);
            map.put(service.getPath(),service); 
            if(method.isAnnotationPresent(Secured.class))
            {
               try{Class.forName(method.getAnnotation(Secured.class).value());}
               catch(Exception e)
               {
                 errorNumber++;
                 errorDocument.add(new Paragraph(errorNumber+". Secured Annotation Error : ",contentHeaderFont));
                 errorDocument.add(new Paragraph("Service path : "+methodPath,contentFont));
                 errorDocument.add(new Paragraph("Service Class : "+c.getName(),contentFont));
                 String methodData="Service Method : "+method.getReturnType().getName()+" "+method.getName()+"(";
                 for(Parameter param:method.getParameters())
                 {
                   methodData+=param.getParameterizedType()+" ,";
                 }
                 if(method.getParameters().length!=0)
                 {
                   methodData=methodData.substring(0,methodData.length()-1)+")";
                 }
                 else
                 {
                   methodData+=")";
                 }
                 errorDocument.add(new Paragraph(methodData,contentFont));
                 errorDocument.add(new Paragraph("( "+method.getAnnotation(Secured.class).value()+" ) Class defination cannot be found.",contentFont)); 
               }
            }
            if(c.isAnnotationPresent(Secured.class))
            {
               Secured s=(Secured)c.getAnnotation(Secured.class);
               try
               {
                 Class.forName(s.value());
               }
               catch(Exception e)
               {
                 errorNumber++;
                 errorDocument.add(new Paragraph(errorNumber+". Secured Annotation Error(In Class Level) : ",contentHeaderFont));
                 errorDocument.add(new Paragraph("Service path : "+methodPath,contentFont));
                 errorDocument.add(new Paragraph("Service Class : "+c.getName(),contentFont));
                 errorDocument.add(new Paragraph(s.value()+" Secured Annotation Class defination cannot be found.",contentFont)); 
               }
            }
            if(method.isAnnotationPresent(ResponseType.class))
            {
               String responseType=method.getAnnotation(ResponseType.class).value();
               if(responseType.equalsIgnoreCase("NOTHING")==false && responseType.equalsIgnoreCase("JSON")==false  &&  responseType.equalsIgnoreCase("TEXT/HTML")==false)
               {
                 errorNumber++;
                 errorDocument.add(new Paragraph(errorNumber+". ResponseType Annotation Error : ",contentHeaderFont));
                 errorDocument.add(new Paragraph("Service path : "+methodPath,contentFont));
                 errorDocument.add(new Paragraph("Service Class : "+c.getName(),contentFont));
                 String methodData="Service Method : "+method.getReturnType().getName()+" "+method.getName()+"(";
                 for(Parameter param:method.getParameters())
                 {
                   methodData+=param.getParameterizedType()+" ,";
                 }
                 if(method.getParameters().length!=0)
                 {
                   methodData=methodData.substring(0,methodData.length()-1)+")";
                 }
                 else
                 {
                   methodData+=")";
                 }
                 errorDocument.add(new Paragraph(methodData,contentFont));
                 errorDocument.add(new Paragraph("Annotation value is incorrect :  "+method.getAnnotation(ResponseType.class).value()+"\n"+"only one of the following values are allowed :-'JSON','NOTHING','TEXT/HTML'",contentFont)); 
               }
            }
            serviceNumber++;
            serviceDocument.add(new Paragraph(serviceNumber+". Service :- "+methodPath,contentHeaderFont));
            serviceDocument.add(new Paragraph("Service Class : "+c.getName(),contentFont));
            String methodData="Service Method : "+method.getReturnType().getName()+" "+method.getName()+"(";
            for(Parameter param:method.getParameters())
            {
               methodData+=param.getParameterizedType()+" ,";
            }
            if(method.getParameters().length!=0)
            {
               methodData=methodData.substring(0,methodData.length()-1)+")";
            }
            else
            {
               methodData+=")";
            }
            serviceDocument.add(new Paragraph(methodData,contentFont));
            serviceDocument.add(new Paragraph("Annotation on Service Class : ",contentFont));
            PdfPTable table1=new PdfPTable(4);
            table1.setWidthPercentage(100); //Width 100%
            table1.setSpacingBefore(20f); //Space before table
            table1.setSpacingAfter(10f); //Space after table
            //for set Alignment of all rows content
            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            //for setRowHeight 
            //table1.getDefaultCell().setFixedHeight(30);

            //Set Column widths
            //float[] columnWidths = {1f, 1f, 1f};
            float[] columnWidths = {1f,2f,2f,5f};
            table1.setWidths(columnWidths);

            table1.addCell(new PdfPCell(new Paragraph("S.No.",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Annotation",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Availability",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Value",contentHeaderFont)));

            table1.addCell(new PdfPCell(new Paragraph("1",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Secured",contentFont)));
            if(c.isAnnotationPresent(Secured.class))
            {
               table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
               try
               {
                 Secured s=(Secured)c.getAnnotation(Secured.class);                 
                 Class.forName(s.value());
                 table1.addCell(new PdfPCell(new Paragraph(s.value(),contentFont)));
               }
               catch(Exception e)
               {
                 table1.addCell(new PdfPCell(new Paragraph("Class defination not found.",contentFont)));
               }
            }
            else
            {
              table1.addCell(new PdfPCell(new Paragraph("Not Present",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph(".........",contentFont)));
            }
            table1.addCell(new PdfPCell(new Paragraph("2",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Path",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph(classPath,contentFont)));
            serviceDocument.add(table1);
            



            serviceDocument.add(new Paragraph("Annotation on Service Method : ",contentFont));
            table1=new PdfPTable(4);
            table1.setWidthPercentage(100); //Width 100%
            table1.setSpacingBefore(20f); //Space before table
            table1.setSpacingAfter(10f); //Space after table
            //for set Alignment of all rows content
            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            //for setRowHeight 
            //table1.getDefaultCell().setFixedHeight(30);

            //Set Column widths
            //float[] columnWidths = {1f, 1f, 1f};
            float[] columnWidths2 = {1f,2f,2f,5f};
            table1.setWidths(columnWidths2);

            table1.addCell(new PdfPCell(new Paragraph("S.No.",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Annotation",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Availability",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Value",contentHeaderFont)));

            table1.addCell(new PdfPCell(new Paragraph("1",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Secured",contentFont)));
            if(method.isAnnotationPresent(Secured.class))
            {
               table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
               try
               {
                 Secured s=(Secured)method.getAnnotation(Secured.class);                 
                 Class.forName(s.value());
                 table1.addCell(new PdfPCell(new Paragraph(s.value(),contentFont)));
               }
               catch(Exception e)
               {
                 table1.addCell(new PdfPCell(new Paragraph("Class defination not found.",contentFont)));
               }
            }
            else
            {
              table1.addCell(new PdfPCell(new Paragraph("Not Present",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph(".........",contentFont)));
            }
            table1.addCell(new PdfPCell(new Paragraph("2",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Path",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph(methodPathAnnotation.value(),contentFont)));

            table1.addCell(new PdfPCell(new Paragraph("3",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("ResponseType",contentFont)));
            if(method.isAnnotationPresent(ResponseType.class))
            {
               table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
               String responseType=method.getAnnotation(ResponseType.class).value();
               if(responseType.equalsIgnoreCase("NOTHING")==false && responseType.equalsIgnoreCase("JSON")==false  &&  responseType.equalsIgnoreCase("TEXT/HTML")==false)
               {
                 table1.addCell(new PdfPCell(new Paragraph("Incorrect Type : "+method.getAnnotation(ResponseType.class).value(),contentFont)));
               }
               else
               {
               table1.addCell(new PdfPCell(new Paragraph(method.getAnnotation(ResponseType.class).value(),contentFont)));
               }
            }
            else
            {
              table1.addCell(new PdfPCell(new Paragraph("Not Present",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph(".........",contentFont)));
            }

            table1.addCell(new PdfPCell(new Paragraph("4",contentFont)));
            table1.addCell(new PdfPCell(new Paragraph("ForwardRequest",contentFont)));
            if(method.isAnnotationPresent(ForwardRequest.class))
            {
               table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
               table1.addCell(new PdfPCell(new Paragraph(method.getAnnotation(ForwardRequest.class).value(),contentFont)));
            }
            else
            {
              table1.addCell(new PdfPCell(new Paragraph("Not Present",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph(".........",contentFont)));
            }
            serviceDocument.add(table1);
           
            serviceDocument.add(new Paragraph("RequestData Annotation on Service Method Parameter : ",contentFont));
            table1=new PdfPTable(4);
            table1.setWidthPercentage(100); //Width 100%
            table1.setSpacingBefore(20f); //Space before table
            table1.setSpacingAfter(10f); //Space after table
            //for set Alignment of all rows content
            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            //for setRowHeight 
            //table1.getDefaultCell().setFixedHeight(30);

            //Set Column widths
            //float[] columnWidths = {1f, 1f, 1f};
            float[] columnWidths3 = {2f,4f,2f,2f};
            table1.setWidths(columnWidths3);

            table1.addCell(new PdfPCell(new Paragraph("Parameter.No.",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Type",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Availability",contentHeaderFont)));
            table1.addCell(new PdfPCell(new Paragraph("Value",contentHeaderFont)));
 
            java.lang.annotation.Annotation [][] parameterAnnotations=method.getParameterAnnotations();
            Class [] parameterTypes=method.getParameterTypes();
            int i=0;
            for(java.lang.annotation.Annotation[] annotations:parameterAnnotations)
            {
              Class parameterType=parameterTypes[i];
              //System.out.println(parameterType);
              boolean reqData=false;
              for(java.lang.annotation.Annotation annotation:annotations)
              {
                if(annotation instanceof RequestData)
                {
                  RequestData rd=(RequestData)annotation;
                  table1.addCell(new PdfPCell(new Paragraph(" "+(i+1),contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph(parameterType.getName(),contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph("Present",contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph(rd.value(),contentFont)));
                  reqData=true;
                }
              }
              if(!reqData)
              {
                  table1.addCell(new PdfPCell(new Paragraph(" "+(i+1),contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph(parameterType.getName(),contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph("Not Present",contentFont)));
                  table1.addCell(new PdfPCell(new Paragraph("........",contentFont)));                  
              }
              i++;
            }
            if(method.getParameterCount()==0)
            {
              table1.addCell(new PdfPCell(new Paragraph("....",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph("....",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph("....",contentFont)));
              table1.addCell(new PdfPCell(new Paragraph("....",contentFont)));                  
            }
            serviceDocument.add(table1);
           }
         }
      }

    }
}catch(Exception exception)
{
exception.printStackTrace();
}
}

private static java.util.List<Class> getClasses(File directory,String packageName) throws ClassNotFoundException
{
  java.util.List<Class> classes=new java.util.ArrayList<Class>();
  if(!directory.exists())
  {
    return classes;
  }
  File[] files=directory.listFiles();
  for(File file:files)
  {
    System.out.println("FileName : "+file.getName());
    if(file.isDirectory())
    {
       classes.addAll(getClasses(file,packageName+"."+file.getName()));
    }
    else if(file.getName().endsWith(".class"))
    {
      classes.add(Class.forName(packageName+"."+file.getName().substring(0,file.getName().length()-6)));
    }
  }
  return classes;
}



}
