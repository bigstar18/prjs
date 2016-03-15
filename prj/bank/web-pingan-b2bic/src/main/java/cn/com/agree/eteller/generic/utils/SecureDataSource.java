package cn.com.agree.eteller.generic.utils;

import java.io.File;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

public class SecureDataSource
  extends BasicDataSource
  implements FactoryBean<BasicDataSource>
{
  private static Logger logger = Logger.getLogger(SecureDataSource.class);
  
  public BasicDataSource getObject()
    throws Exception
  {
    setPassword(Base64Decoder.decode(getPassword()));
    if (!this.url.startsWith("jdbc:h2:"))
    {
      String h2Mode = "tcp";
      try
      {
        ConfigUtil config = new ConfigUtil("jdbc.properties");
        h2Mode = config.getString("h2.mode");
      }
      catch (Exception e)
      {
        logger.error(ExceptionUtils.getStackTrace(e));
      }
      logger.info("当前文件绝对路径：" + new File("").getAbsolutePath());
      setUrl(("jdbc:h2:" + h2Mode + ":" + new File("").getAbsolutePath() + "/" + getUrl()).replaceAll("\\\\", "/"));
      logger.info("db url:" + getUrl());
    }
    return this;
  }
  
  public Class<BasicDataSource> getObjectType()
  {
    return BasicDataSource.class;
  }
  
  public boolean isSingleton()
  {
    return true;
  }
}
