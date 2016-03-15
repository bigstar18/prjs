package cn.com.agree.eteller.generic.utils;

import cn.com.agree.eteller.generic.spring.BaseManager;
import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;

public class DBUtil
{
  public static <T> List<T> queryEntitiesByProperties(BaseManager<T> mg, T entity, String[] propNames, Pagination page)
    throws Exception
  {
    if (entity == null) {
      return mg.getAll(page);
    }
    List<String> props = new ArrayList(Arrays.asList(propNames));
    List<Object> vals = new ArrayList();
    for (Iterator<String> iter = props.iterator(); iter.hasNext();)
    {
      String val = BeanUtils.getProperty(entity, (String)iter.next());
      if (!ComFunction.isEmpty(val)) {
        vals.add(val);
      } else {
        iter.remove();
      }
    }
    if (props.isEmpty()) {
      return mg.getAll(page);
    }
    return mg.getByProperties(page, (String[])props.toArray(new String[0]), 
      vals.toArray());
  }
  
  public static void generateHibernateDaoAndService(String servicePackage, String daoPackage, String entityPackage, String entityName)
  {
    new DaoGenerator(daoPackage, entityPackage, entityName).generate();
    new ServiceGenerator(servicePackage, daoPackage, entityPackage, 
      entityName).generate();
  }
  
  private static class DaoGenerator
  {
    private String daoPackage;
    private String entityPackage;
    private String entityName;
    private String daoContent;
    private String daoImplContent;
    
    public DaoGenerator(String daoPackage, String entityPackage, String entityName)
    {
      this.daoPackage = daoPackage;
      this.entityPackage = entityPackage;
      this.entityName = entityName;
      
      loadTemplate();
    }
    
    private void loadTemplate()
    {
      File dao = new File("src/resource/DAO.template");
      File daoImpl = new File("src/resource/DAOImpl.template");
      
      this.daoContent = IOUtils.readAll(dao);
      this.daoImplContent = IOUtils.readAll(daoImpl);
    }
    
    public void generate()
    {
      this.daoContent = this.daoContent.replaceAll("\\$\\{entityName\\}", 
        this.entityName);
      this.daoContent = this.daoContent.replaceAll("\\$\\{daoPackage\\}", 
        this.daoPackage);
      this.daoContent = this.daoContent.replaceAll("\\$\\{entityPackage\\}", 
        this.entityPackage);
      
      this.daoImplContent = this.daoImplContent.replaceAll("\\$\\{entityName\\}", 
        this.entityName);
      this.daoImplContent = this.daoImplContent.replaceAll("\\$\\{daoPackage\\}", 
        this.daoPackage);
      this.daoImplContent = this.daoImplContent.replaceAll(
        "\\$\\{entityPackage\\}", this.entityPackage);
      this.daoImplContent = this.daoImplContent.replaceAll(
        "\\$\\{defaultEntityName\\}", 
        new StringBuilder(this.entityName).replace(
        0, 
        1, 
        new String(String.valueOf(this.entityName.charAt(0)))
        .toLowerCase()).toString());
      
      String prjPath = new File("").getAbsolutePath();
      try
      {
        FileUtil.saveFile(new StringBufferInputStream(this.daoContent), 
          prjPath + "/src/" + this.daoPackage.replaceAll("\\.", "/") + 
          "/" + this.entityName + "DAO.java");
        FileUtil.saveFile(new StringBufferInputStream(this.daoImplContent), 
          prjPath + "/src/" + this.daoPackage.replaceAll("\\.", "/") + 
          "/impl" + "/" + this.entityName + "DAOImpl.java");
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private static class ServiceGenerator
  {
    private String servicePackage;
    private String daoPackage;
    private String entityPackage;
    private String entityName;
    private String serviceContent;
    private String serviceImplContent;
    
    public ServiceGenerator(String servicePackage, String daoPackage, String entityPackage, String entityName)
    {
      this.servicePackage = servicePackage;
      this.daoPackage = daoPackage;
      this.entityPackage = entityPackage;
      this.entityName = entityName;
      
      loadTemplate();
    }
    
    private void loadTemplate()
    {
      File service = new File("src/resource/Manager.template");
      File serviceImpl = new File("src/resource/ManagerImpl.template");
      
      this.serviceContent = IOUtils.readAll(service);
      this.serviceImplContent = IOUtils.readAll(serviceImpl);
    }
    
    public void generate()
    {
      this.serviceContent = this.serviceContent.replaceAll("\\$\\{entityName\\}", 
        this.entityName);
      this.serviceContent = this.serviceContent.replaceAll(
        "\\$\\{servicePackage\\}", this.servicePackage);
      this.serviceContent = this.serviceContent.replaceAll(
        "\\$\\{entityPackage\\}", this.entityPackage);
      
      this.serviceImplContent = this.serviceImplContent.replaceAll(
        "\\$\\{entityName\\}", this.entityName);
      this.serviceImplContent = this.serviceImplContent.replaceAll(
        "\\$\\{daoPackage\\}", this.daoPackage);
      this.serviceImplContent = this.serviceImplContent.replaceAll(
        "\\$\\{servicePackage\\}", this.servicePackage);
      this.serviceImplContent = this.serviceImplContent.replaceAll(
        "\\$\\{entityPackage\\}", this.entityPackage);
      this.serviceImplContent = this.serviceImplContent.replaceAll(
        "\\$\\{defaultEntityName\\}", 
        new StringBuilder(this.entityName).replace(
        0, 
        1, 
        new String(String.valueOf(this.entityName.charAt(0)))
        .toLowerCase()).toString());
      
      String prjPath = new File("").getAbsolutePath();
      try
      {
        FileUtil.saveFile(
          new StringBufferInputStream(this.serviceContent), 
          prjPath + "/src/" + 
          this.servicePackage.replaceAll("\\.", "/") + "/" + 
          this.entityName + "Manager.java");
        FileUtil.saveFile(
          new StringBufferInputStream(this.serviceImplContent), 
          prjPath + "/src/" + 
          this.servicePackage.replaceAll("\\.", "/") + 
          "/impl" + "/" + this.entityName + 
          "ManagerImpl.java");
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args)
  {
    generateHibernateDaoAndService("cn.com.agree.eteller.usermanager.spring", 
      "cn.com.agree.eteller.usermanager.dao", 
      "cn.com.agree.eteller.usermanager.persistence", "Notice");
  }
}
