package gnnt.MEBS.checkLogon.check.broker;

import gnnt.MEBS.checkLogon.check.BaseCheck;
import gnnt.MEBS.checkLogon.dao.broker.BrokerCheckDAO;
import gnnt.MEBS.checkLogon.po.broker.BrokerPO;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.checkLogon.vo.broker.BrokerLogonResultVO;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;

public class BrokerCheck extends BaseCheck
{
  private static volatile BrokerCheck instance;
  private int LOG_MANAGE_OPERATORTYPR = 1903;

  private int PWD_MANAGE_OPERATORTYPR = 1904;
  private BrokerCheckDAO brokerCheckDAO;

  private BrokerCheck(DataSource dataSource)
  {
    this.brokerCheckDAO = new BrokerCheckDAO();
    this.brokerCheckDAO.setDataSource(dataSource);
  }

  public static BrokerCheck createInstance(DataSource dataSource)
  {
    if (instance == null) {
      synchronized (BrokerCheck.class) {
        if (instance == null) {
          instance = new BrokerCheck(dataSource);
        }
      }
    }
    return instance;
  }

  public static BrokerCheck getInstance()
  {
    return instance;
  }

  public BrokerLogonResultVO logon(String brokerID, String password, String logonIP, int moduleID, String logonType)
  {
    this.logger.info("加盟商[" + brokerID + "]在[" + logonIP + "]登录");

    BrokerLogonResultVO result = new BrokerLogonResultVO();
    result.setResult(-1);

    BrokerPO brokerPO = this.brokerCheckDAO.getBrokerPOByID(brokerID);

    if (brokerPO == null) {
      result.setRecode("-1");
      result.setMessage("加盟商不存在");
      this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    String inPassword = MD5.getMD5(brokerID, password);
    if (!brokerPO.getPassword().equals(inPassword)) {
      result.setRecode("-12");
      result.setMessage("密码不正确");
      this.brokerCheckDAO.addGlobalLog(brokerID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "加盟商登录，返回信息：" + result.getMessage(), 0);
      this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    LogonVO logonVO = new LogonVO();
    logonVO.setLogonType(logonType);
    logonVO.setModuleID(moduleID);
    logonVO.setUserID(brokerID);

    LogonResultVO rv = LogonActualize.getInstance().logon(logonVO);

    if (rv.getResult() != 1) {
      result.setRecode(rv.getRecode());
      result.setMessage(rv.getMessage());
      this.brokerCheckDAO.addGlobalLog(brokerID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "加盟商登录，返回信息：" + result.getMessage(), 0);
      this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    this.brokerCheckDAO.addGlobalLog(brokerID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "加盟商登录成功", 1);
    this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]登录成功");

    result.setResult(1);
    result.setSessionID(rv.getUserManageVO().getSessionID());

    return result;
  }

  public int changePassowrd(String brokerID, String oldPassword, String newPassword, String logonIP)
  {
    this.logger.info("加盟商[" + brokerID + "]在[" + logonIP + "]修改密码");

    BrokerPO brokerPO = this.brokerCheckDAO.getBrokerPOByID(brokerID);

    if (brokerPO == null) {
      this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]修改密码，返回信息：加盟商不存在");
      return -2;
    }

    String inPassword = MD5.getMD5(brokerID, oldPassword);
    if (!brokerPO.getPassword().equals(inPassword)) {
      this.brokerCheckDAO.addGlobalLog(brokerID, logonIP, this.PWD_MANAGE_OPERATORTYPR, "加盟商修改密码，原密码不正确", 0);
      this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]修改密码，返回信息：原密码不正确");
      return -1;
    }

    this.brokerCheckDAO.changePassowrd(brokerID, MD5.getMD5(brokerID, newPassword));

    this.brokerCheckDAO.addGlobalLog(brokerID, logonIP, this.PWD_MANAGE_OPERATORTYPR, "加盟商修改密码成功", 1);
    this.logger.debug("加盟商[" + brokerID + "]在[" + logonIP + "]修改密码成功");

    return 1;
  }
}