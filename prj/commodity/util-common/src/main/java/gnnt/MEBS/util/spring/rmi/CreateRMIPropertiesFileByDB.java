package gnnt.MEBS.util.spring.rmi;

import gnnt.MEBS.util.spring.rmi.util.RWPropertiesFile;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.core.PriorityOrdered;

public class CreateRMIPropertiesFileByDB
  implements BeanFactoryPostProcessor, PriorityOrdered
{
  private int order = 1;

  private boolean isSetRMIPropertiesFromDB = false;
  private String rmiPropertiesName;
  private String dataSource;
  private int[] moduleArr;

  public void setOrder(int order)
  {
    this.order = order;
  }

  public void setSetRMIPropertiesFromDB(boolean isSetRMIPropertiesFromDB)
  {
    this.isSetRMIPropertiesFromDB = isSetRMIPropertiesFromDB;
  }

  public void setRmiPropertiesName(String rmiPropertiesName)
  {
    this.rmiPropertiesName = rmiPropertiesName;
  }

  public void setModuleArr(int[] moduleArr)
  {
    this.moduleArr = moduleArr;
  }

  public void setDataSource(String dataSource)
  {
    this.dataSource = dataSource;
  }

  public int getOrder() {
    return this.order;
  }

  public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
    throws BeansException
  {
    if (!this.isSetRMIPropertiesFromDB) {
      return;
    }
    if ((this.moduleArr == null) || (this.moduleArr.length == 0)) {
      return;
    }

    if ((this.dataSource == null) || (this.dataSource.length() == 0)) {
      throw new BeanInitializationException("dataSource is Null ");
    }

    if ((this.rmiPropertiesName == null) || (this.rmiPropertiesName.length() == 0)) {
      throw new BeanInitializationException("rmiPropertiesName is Null ");
    }

    BeanDefinition beanDefinition = configurableListableBeanFactory
      .getBeanDefinition(this.dataSource);
    MutablePropertyValues mutablePropertyValues = beanDefinition
      .getPropertyValues();

    String filePath = null;
    try {
      filePath = Thread.currentThread().getContextClassLoader()
        .getResource("").toURI().getPath() + 
        this.rmiPropertiesName;
    } catch (URISyntaxException e1) {
      throw new BeanInitializationException("Get filePath fail ", e1);
    }

    File file = new File(filePath);

    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        throw new BeanInitializationException("Create Rmi File fail ", 
          e);
      }
    }

    String jndiName = getMutablePropertyValueByName(mutablePropertyValues, 
      "jndiName");
    if (jndiName != null) {
      InitialContext ic = null;
      DataSource dataSource = null;

      Connection conn = null;
      try {
        ic = new InitialContext();
        dataSource = (DataSource)ic.lookup(jndiName);
        conn = dataSource.getConnection();
        writeRmiPropertiesByDB(filePath, conn);
      } catch (Exception e) {
        throw new BeanInitializationException("jndi exception ", e);
      } finally {
        try {
          if (conn != null)
            conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        try {
          ic.close();
        } catch (NamingException e) {
          e.printStackTrace();
        }
        ic = null;
        dataSource = null;
      }
    } else {
      String driverClassName = ((TypedStringValue)mutablePropertyValues
        .getPropertyValue("driverClassName").getValue()).getValue();
      String url = ((TypedStringValue)mutablePropertyValues
        .getPropertyValue("url").getValue()).getValue();
      String username = ((TypedStringValue)mutablePropertyValues
        .getPropertyValue("username").getValue()).getValue();
      String password = ((TypedStringValue)mutablePropertyValues
        .getPropertyValue("password").getValue()).getValue();

      BasicDataSource dateSource = null;
      try {
        Class clazz = Class.forName("org.apache.commons.dbcp.BasicDataSource");
        dateSource = (BasicDataSource)clazz.newInstance();
        dateSource.setPassword(password);
        password = dateSource.getPassword();
      }
      catch (Exception localException1)
      {
        try {
          if (dateSource != null)
            dateSource.close();
        }
        catch (Exception localException2)
        {
        }
      }
      catch (Error localError)
      {
        try
        {
          if (dateSource != null)
            dateSource.close();
        }
        catch (Exception localException3)
        {
        }
      }
      finally
      {
        try
        {
          if (dateSource != null) {
            dateSource.close();
          }
        }
        catch (Exception localException4)
        {
        }
      }
      Connection conn = null;
      try
      {
        Class.forName(driverClassName);

        conn = DriverManager.getConnection(url, username, password);

        writeRmiPropertiesByDB(filePath, conn);
      } catch (Exception e) {
        throw new BeanInitializationException("jdbc exception ", e);
      } finally {
        try {
          if (conn != null)
            conn.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  private String getMutablePropertyValueByName(MutablePropertyValues mutablePropertyValues, String key)
  {
    PropertyValue propertyValue = mutablePropertyValues
      .getPropertyValue(key);

    if (propertyValue != null) {
      TypedStringValue typedStringValue = (TypedStringValue)propertyValue
        .getValue();
      return typedStringValue.getValue();
    }
    return null;
  }

  private void writeRmiPropertiesByDB(String filePath, Connection conn)
  {
    Statement stmt = null;
    ResultSet rs = null;
    try {
      String moduleidStr = "";

      for (int moduleid : this.moduleArr) {
        moduleidStr = moduleidStr + moduleid + ",";
      }

      moduleidStr = moduleidStr.substring(0, moduleidStr.length() - 1);

      String sql = "select moduleid,enname,hostip,port,rmidataport from ((select moduleid,enname,hostip,port,rmidataport from c_trademodule) union (select moduleid,enname,hostip,port,rmidataport from C_Submodule ts)) t where t.moduleid in (" + 
        moduleidStr + ")";

      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        RWPropertiesFile.writeProperties(filePath, rs
          .getString("enname") + 
          ".rmi.ip", delNull(rs.getString("hostip")));
        RWPropertiesFile.writeProperties(filePath, rs
          .getString("enname") + 
          ".rmi.port", delNull(rs.getString("port")));

        RWPropertiesFile.writeProperties(filePath, rs.getString("enname") + 
          ".rmi.dataport", delNull(rs
          .getString("rmidataport")));
      }
    } catch (Exception e) {
      throw new BeanInitializationException("query exception ", e);
    } finally {
      try {
        if (rs != null)
          rs.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      try
      {
        if (stmt != null)
          stmt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  private String delNull(String str)
  {
    if (str == null) {
      return "";
    }
    return str;
  }
}