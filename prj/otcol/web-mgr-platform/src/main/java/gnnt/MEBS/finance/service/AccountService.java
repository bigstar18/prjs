package gnnt.MEBS.finance.service;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.util.SysData;
import gnnt.MEBS.finance.dao.AccountDao;
import gnnt.MEBS.finance.unit.Account;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("f_accountService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AccountService
{
  private final transient Log logger = LogFactory.getLog(AccountService.class);
  @Autowired
  @Qualifier("f_accountDao")
  private AccountDao accountDao;
  
  public void createAccount(Account paramAccount)
  {
    this.accountDao.createAccount(paramAccount);
  }
  
  public void updateAccount(Account paramAccount)
  {
    this.accountDao.updateAccount(paramAccount);
  }
  
  public int getAccount(String paramString)
  {
    return this.accountDao.getAccount(paramString);
  }
  
  public int deleteAccount(String paramString1, String paramString2)
  {
    String str = "select * from F_VoucherEntry where AccountCode like '" + paramString1 + "%'";
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    List localList = localDaoHelper.queryBySQL(str, null);
    int i = -1;
    if ((localList != null) && (localList.size() > 0))
    {
      i = 0;
    }
    else
    {
      this.accountDao.deleteAccount(paramString1, paramString2);
      i = 1;
    }
    return i;
  }
  
  public Account getAccountByCode(String paramString)
  {
    return this.accountDao.getAccountByCode(paramString);
  }
  
  public Account getLeafAccountByCode(String paramString)
  {
    return this.accountDao.getLeafAccountByCode(paramString);
  }
  
  public List getAccounts(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.accountDao.getAccounts(paramQueryConditions, paramPageInfo);
  }
  
  public void generateFirmAccounts()
  {
    this.accountDao.executeStoredSubprogram("GenerateFirmAccounts()");
  }
}
