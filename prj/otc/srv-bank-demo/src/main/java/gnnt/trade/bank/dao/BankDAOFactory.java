package gnnt.trade.bank.dao;

import java.sql.SQLException;

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
	  public static BankDAO getDAO() throws ClassNotFoundException,IllegalAccessException, InstantiationException {
			BankDAO dao = null;
//		    Properties params = new Configuration().getSection(ProcConstants.xmlName);
//		    String className = "gnnt.trade.bank.dao.BankDAO" + ((String)params.getProperty("DBType")).trim();
		    String className = "gnnt.trade.bank.dao.BankDAOOracle";
		    dao = (BankDAO)Class.forName(className).newInstance();
		    return dao;
	  }

}
