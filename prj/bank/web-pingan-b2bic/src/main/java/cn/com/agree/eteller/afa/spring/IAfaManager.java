package cn.com.agree.eteller.afa.spring;

import cn.com.agree.eteller.afa.dao.IAfaActnoadmDao;
import cn.com.agree.eteller.afa.dao.IAfaAgentadmDao;
import cn.com.agree.eteller.afa.dao.IAfaBrachDao;
import cn.com.agree.eteller.afa.dao.IAfaBranchcodeDao;
import cn.com.agree.eteller.afa.dao.IAfaChanneladmDao;
import cn.com.agree.eteller.afa.dao.IAfaCheckappDao;
import cn.com.agree.eteller.afa.dao.IAfaFeeinfoDao;
import cn.com.agree.eteller.afa.dao.IAfaHolidayadmDao;
import cn.com.agree.eteller.afa.dao.IAfaHostfuncmapDao;
import cn.com.agree.eteller.afa.dao.IAfaPininfoDao;
import cn.com.agree.eteller.afa.dao.IAfaPinzoneinfoDao;
import cn.com.agree.eteller.afa.dao.IAfaSubunitadmDao;
import cn.com.agree.eteller.afa.dao.IAfaSystemDao;
import cn.com.agree.eteller.afa.dao.IAfaTradeadmDao;
import cn.com.agree.eteller.afa.dao.IAfaUnitadmDao;
import cn.com.agree.eteller.afa.dao.IAfaUnitebrachDao;
import cn.com.agree.eteller.afa.dao.IAfaZhbrnoinfoDao;
import cn.com.agree.eteller.afa.dao.IAfaZhinfoDao;
import cn.com.agree.eteller.afa.dao.IAfaZoneinfoDao;
import cn.com.agree.eteller.afa.dao.IAfapMaindictDao;
import cn.com.agree.eteller.afa.dao.IAfapSubdictDao;
import cn.com.agree.eteller.afa.dao.ICisCommdataDao;
import cn.com.agree.eteller.afa.dao.ICredCardbinDao;
import cn.com.agree.eteller.afa.dao.ICredRecktradecodeDao;
import cn.com.agree.eteller.afa.dao.ICredSubbusisysinfoDao;
import cn.com.agree.eteller.afa.dao.IEtellerExcelPubDao;
import cn.com.agree.eteller.afa.dao.IGdbAcctDao;
import cn.com.agree.eteller.afa.dao.IGdbHostabsinfoDao;
import cn.com.agree.eteller.afa.dao.IGdbSysDao;
import cn.com.agree.eteller.generic.spring.IGenericManager;

public abstract interface IAfaManager
  extends IAfapMaindictDao, IAfapSubdictDao, IAfaSystemDao, IAfaUnitadmDao, IAfaSubunitadmDao, IAfaAgentadmDao, IAfaTradeadmDao, IAfaActnoadmDao, IAfaChanneladmDao, IAfaZoneinfoDao, IAfaZhinfoDao, IAfaZhbrnoinfoDao, IAfaBrachDao, IAfaUnitebrachDao, IGdbSysDao, IGdbHostabsinfoDao, IGdbAcctDao, IAfaBranchcodeDao, IAfaCheckappDao, IAfaHolidayadmDao, IAfaPinzoneinfoDao, ICisCommdataDao, IAfaPininfoDao, IEtellerExcelPubDao, IAfaFeeinfoDao, IAfaHostfuncmapDao, ICredCardbinDao, ICredRecktradecodeDao, ICredSubbusisysinfoDao, IGenericManager
{
  public abstract String[][] getSearchResultBySQL(String paramString);
}
