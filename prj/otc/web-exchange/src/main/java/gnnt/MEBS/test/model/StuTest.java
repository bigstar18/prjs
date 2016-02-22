package gnnt.MEBS.test.model;

import gnnt.MEBS.base.model.Clone;

public class StuTest
  extends Clone
{
  private Long id;
  private String name;
  private int age;
  private String grade;
  private String speciality;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getAge()
  {
    return this.age;
  }
  
  public void setAge(int age)
  {
    this.age = age;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String grade)
  {
    this.grade = grade;
  }
  
  public String getSpeciality()
  {
    return this.speciality;
  }
  
  public void setSpeciality(String speciality)
  {
    this.speciality = speciality;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
