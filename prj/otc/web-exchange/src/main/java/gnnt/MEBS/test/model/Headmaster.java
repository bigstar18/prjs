package gnnt.MEBS.test.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class Headmaster
{
  private int id;
  private int age;
  private String name;
  private int Status;
  
  @ClassDiscription(name="状态")
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="-1", value="正常")})
  public int getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(int status)
  {
    this.Status = status;
  }
  
  @ClassDiscription(key=true, keyWord=true, name="主11111键")
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @ClassDiscription(name="获得年龄", discription="getAge()方法")
  public int getAge()
  {
    return this.age;
  }
  
  public void setAge(int age)
  {
    this.age = age;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
}
