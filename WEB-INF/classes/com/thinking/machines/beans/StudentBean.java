package com.thinking.machines.beans;
public class StudentBean implements java.io.Serializable
{
private int rollNumber;
private String name;
private String address;
private String dateOfBirth;
private String gender;
private boolean indian;
private int cityCode;
private String cityName;

public StudentBean()
{
this.rollNumber=0;
this.name="";
this.address="";
this.dateOfBirth="";
this.gender="";
this.indian=false;
this.cityCode=0;
this.cityName="";
}
public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setAddress(String address)
{
this.address=address;
}
public String getAddress()
{
return this.address;
}
public void setDateOfBirth(String dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public String getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(String gender)
{
this.gender=gender;
}
public String getGender()
{
return this.gender;
}
public void setIndian(boolean indian)
{
this.indian=indian;
}
public boolean getIndian()
{
return this.indian;
}
public void setCityCode(int cityCode)
{
this.cityCode=cityCode;
}
public int getCityCode()
{
return this.cityCode;
}
public void setCityName(String cityName)
{
this.cityName=cityName;
}
public String getCityName()
{
return this.cityName;
}

}
