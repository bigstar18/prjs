package gnnt.MEBS.checkLogon.check.broker;

import gnnt.MEBS.checkLogon.check.BaseCheck;
import gnnt.MEBS.checkLogon.dao.broker.BrokerAgeCheckDAO;
import gnnt.MEBS.checkLogon.po.broker.BrokerAgePO;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.checkLogon.vo.broker.BrokerAgeLogonResultVO;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;

public class BrokerAgeCheck extends BaseCheck
{
  private static volatile BrokerAgeCheck instance;
  private int LOG_MANAGE_OPERATORTYPR = 1903;

  private int PWD_MANAGE_OPERATORTYPR = 1904;
  private BrokerAgeCheckDAO brokerAgeCheckDAO;

  private BrokerAgeCheck(DataSource dataSource)
  {
    this.brokerAgeCheckDAO = new BrokerAgeCheckDAO();
    this.brokerAgeCheckDAO.setDataSource(dataSource);
  }

  public static BrokerAgeCheck createInstance(DataSource dataSource)
  {
    if (instance == null) {
      synchronized (BrokerAgeCheck.class) {
        if (instance == null) {
          instance = new BrokerAgeCheck(dataSource);
        }
      }
    }
    return instance;
  }

  public static BrokerAgeCheck getInstance()
  {
    return instance;
  }

  public BrokerAgeLogonResultVO logon(String brokerAgeID, String password, String logonIP, int moduleID, String logonType)
  {
    this.logger.info("居间商[" + brokerAgeID + "]在[" + logonIP + "]登录");

    BrokerAgeLogonResultVO result = new BrokerAgeLogonResultVO();
    result.setResult(-1);

    BrokerAgePO brokerAgePO = this.brokerAgeCheckDAO.getBrokerAgePOByID(brokerAgeID);

    if (brokerAgePO == null) {
      result.setRecode("-1");
      result.setMessage("居间商不存在");
      this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    String inPassword = MD5.getMD5(brokerAgeID, password);
    if (!brokerAgePO.getPassword().equals(inPassword)) {
      result.setRecode("-2");
      result.setMessage("密码不正确");
      this.brokerAgeCheckDAO.addGlobalLog(brokerAgeID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "居间商登录，返回信息：" + result.getMessage(), 0);
      this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    LogonVO logonVO = new LogonVO();
    logonVO.setLogonType(logonType);
    logonVO.setModuleID(moduleID);
    logonVO.setUserID(brokerAgeID);

    LogonResultVO rv = LogonActualize.getInstance().logon(logonVO);

    if (rv.getResult() != 1) {
      result.setRecode(rv.getRecode());
      result.setMessage(rv.getMessage());
      this.brokerAgeCheckDAO.addGlobalLog(brokerAgeID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "居间商登录，返回信息：" + result.getMessage(), 0);
      this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]登录，返回信息：" + result.getMessage());
      return result;
    }

    this.brokerAgeCheckDAO.addGlobalLog(brokerAgeID, logonIP, this.LOG_MANAGE_OPERATORTYPR, "居间商登录成功", 1);
    this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]登录成功");
    result.setResult(1);
    result.setSessionID(rv.getUserManageVO().getSessionID());

    return result;
  }

  public int changePassword(String brokerAgeID, String oldPassword, String newPassword, String logonIP)
  {
    this.logger.info("居间商[" + brokerAgeID + "]在[" + logonIP + "]修改密码");

    BrokerAgePO brokerAgePO = this.brokerAgeCheckDAO.getBrokerAgePOByID(brokerAgeID);

    if (brokerAgePO == null) {
      this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]修改密码，居间商不存在");
      return -2;
    }

    String inPassword = MD5.getMD5(brokerAgeID, oldPassword);
    if (!brokerAgePO.getPassword().equals(inPassword)) {
      this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]修改密码，原密码错误");
      this.brokerAgeCheckDAO.addGlobalLog(brokerAgeID, logonIP, this.PWD_MANAGE_OPERATORTYPR, "居间商修改密码，原密码错误", 0);
      return -1;
    }

    this.brokerAgeCheckDAO.changePassowrd(brokerAgeID, MD5.getMD5(brokerAgeID, newPassword));

    this.brokerAgeCheckDAO.addGlobalLog(brokerAgeID, logonIP, this.PWD_MANAGE_OPERATORTYPR, "居间商修改密码成功", 1);
    this.logger.debug("居间商[" + brokerAgeID + "]在[" + logonIP + "]修改密码成功");

    return 1;
  }
}