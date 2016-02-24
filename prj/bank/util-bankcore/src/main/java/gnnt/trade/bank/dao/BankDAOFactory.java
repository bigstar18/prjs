package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.*;

import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;

/**
 * <p>Title: BankDAO创建类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class BankDAOFactory {
	/**
	   * 创建DAO对象
	   * @return BankDAO
	   * @throws ClassNotFoundException
	   * @throws IllegalAccessException
	   * @throws InstantiationException
	   * @throws SQLException
	   * @throws NamingException
	   */
	  public static BankDAO getDAO() throws ClassNotFoundException,IllegalAccessException, InstantiationException
	  {
			BankDAO dao = null;
		    Properties params = new Configuration().getSection("BANK.Processor");
		    //System.out.println("params==null-------->"+(params==null));
		    String className = "gnnt.trade.bank.dao.BankDAO" + ((String)params.getProperty("DBType")).trim();
		    dao = (BankDAO)Class.forName(className).newInstance();
		    return dao;
	  }

}
