package cn.com.agree.eteller.afa.spring.implement;

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
import cn.com.agree.eteller.afa.persistence.AfaActnoadm;
import cn.com.agree.eteller.afa.persistence.AfaAgentadm;
import cn.com.agree.eteller.afa.persistence.AfaBranch;
import cn.com.agree.eteller.afa.persistence.AfaBranchcode;
import cn.com.agree.eteller.afa.persistence.AfaChanneladm;
import cn.com.agree.eteller.afa.persistence.AfaFeeinfo;
import cn.com.agree.eteller.afa.persistence.AfaHolidayadm;
import cn.com.agree.eteller.afa.persistence.AfaHostfuncmap;
import cn.com.agree.eteller.afa.persistence.AfaPininfo;
import cn.com.agree.eteller.afa.persistence.AfaPinzoneinfo;
import cn.com.agree.eteller.afa.persistence.AfaSubunitadm;
import cn.com.agree.eteller.afa.persistence.AfaSystem;
import cn.com.agree.eteller.afa.persistence.AfaTradeadm;
import cn.com.agree.eteller.afa.persistence.AfaUnitadm;
import cn.com.agree.eteller.afa.persistence.AfaUnitebrach;
import cn.com.agree.eteller.afa.persistence.AfaZhbrnoinfo;
import cn.com.agree.eteller.afa.persistence.AfaZhinfo;
import cn.com.agree.eteller.afa.persistence.AfaZoneinfo;
import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.afa.persistence.CisCommdata;
import cn.com.agree.eteller.afa.persistence.CredCardbin;
import cn.com.agree.eteller.afa.persistence.CredRecktradecode;
import cn.com.agree.eteller.afa.persistence.CredSubbusisysinfo;
import cn.com.agree.eteller.afa.persistence.EtellerCheckapp;
import cn.com.agree.eteller.afa.persistence.EtellerExcelPub;
import cn.com.agree.eteller.afa.persistence.GdbAcct;
import cn.com.agree.eteller.afa.persistence.GdbHostabsinfo;
import cn.com.agree.eteller.afa.persistence.GdbSys;
import cn.com.agree.eteller.afa.spring.IAfaManager;
import cn.com.agree.eteller.generic.dao.IGenericDao;
import cn.com.agree.eteller.generic.spring.implement.GenericManager;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

public class AfaManager
  extends GenericManager
  implements IAfaManager
{
  private IAfapMaindictDao maindictDao;
  private IAfapSubdictDao subdictDao;
  private IAfaSystemDao systemDao;
  private IAfaUnitadmDao unitadmDao;
  private IAfaSubunitadmDao subunitadmDao;
  private IAfaAgentadmDao agentadmDao;
  private IAfaTradeadmDao tradeadmDao;
  private IAfaActnoadmDao afaActnoadmDao;
  private IAfaChanneladmDao afaChanneladmDao;
  private IAfaZoneinfoDao zoneinfoDao;
  private IAfaZhinfoDao zhinfoDao;
  private IAfaZhbrnoinfoDao zhbrnoinfoDao;
  private IAfaBrachDao brachDao;
  private IAfaUnitebrachDao unitebrachDao;
  private IGdbSysDao gdbsysDao;
  private IGdbHostabsinfoDao hostinfoDao;
  private IGdbAcctDao gdbacctDao;
  private IAfaBranchcodeDao branchcodeDao;
  private IAfaCheckappDao afaCheckappDao;
  private IAfaHolidayadmDao afaHolidayadmDao;
  private IAfaPinzoneinfoDao afapinzoneinfoDao;
  private ICisCommdataDao cisCommdataDao;
  private IAfaPininfoDao afaPininfoDao;
  private IEtellerExcelPubDao etellerExcelPubDao;
  private IAfaFeeinfoDao afaFeeinfoDao;
  private IAfaHostfuncmapDao afaHostfuncmapDao;
  private ICredCardbinDao credCardbinDao;
  private ICredRecktradecodeDao credRecktradecodeDao;
  private ICredSubbusisysinfoDao credSubbusisysinfoDao;
  @Resource
  private IGenericDao genericDao;
  
  public IAfaPininfoDao getAfaPininfoDao()
  {
    return this.afaPininfoDao;
  }
  
  public void setAfaPininfoDao(IAfaPininfoDao afaPininfoDao)
  {
    this.afaPininfoDao = afaPininfoDao;
  }
  
  public IAfapMaindictDao getMaindictDao()
  {
    return this.maindictDao;
  }
  
  public IAfapSubdictDao getSubdictDao()
  {
    return this.subdictDao;
  }
  
  public IAfaSystemDao getSystemDao()
  {
    return this.systemDao;
  }
  
  public IAfaUnitadmDao getUnitadmDao()
  {
    return this.unitadmDao;
  }
  
  public IAfaSubunitadmDao getSubunitadmDao()
  {
    return this.subunitadmDao;
  }
  
  public IAfaAgentadmDao getAgentadmDao()
  {
    return this.agentadmDao;
  }
  
  public IAfaTradeadmDao getTradeadmDao()
  {
    return this.tradeadmDao;
  }
  
  public IAfaZoneinfoDao getZoneinfoDao()
  {
    return this.zoneinfoDao;
  }
  
  public IAfaZhinfoDao getZhinfoDao()
  {
    return this.zhinfoDao;
  }
  
  public IAfaZhbrnoinfoDao getZhbrnoinfoDao()
  {
    return this.zhbrnoinfoDao;
  }
  
  public IGdbSysDao getGdbsysDao()
  {
    return this.gdbsysDao;
  }
  
  public IGdbHostabsinfoDao getHostinfoDao()
  {
    return this.hostinfoDao;
  }
  
  public IGdbAcctDao getGdbacctDao()
  {
    return this.gdbacctDao;
  }
  
  public IAfaBranchcodeDao getBranchcodeDao()
  {
    return this.branchcodeDao;
  }
  
  public IAfaBrachDao getBrachDao()
  {
    return this.brachDao;
  }
  
  public IAfaUnitebrachDao getUnitebrachDao()
  {
    return this.unitebrachDao;
  }
  
  public IAfaPinzoneinfoDao getAfapinzoneinfoDao()
  {
    return this.afapinzoneinfoDao;
  }
  
  public ICredCardbinDao getCredCardbinDao()
  {
    return this.credCardbinDao;
  }
  
  public ICredRecktradecodeDao getCredRecktradecodeDao()
  {
    return this.credRecktradecodeDao;
  }
  
  public ICredSubbusisysinfoDao getCredSubbusisysinfoDao()
  {
    return this.credSubbusisysinfoDao;
  }
  
  public void setCredCardbinDao(ICredCardbinDao credCardbinDao)
  {
    this.credCardbinDao = credCardbinDao;
  }
  
  public void setCredRecktradecodeDao(ICredRecktradecodeDao credRecktradecodeDao)
  {
    this.credRecktradecodeDao = credRecktradecodeDao;
  }
  
  public void setCredSubbusisysinfoDao(ICredSubbusisysinfoDao credSubbusisysinfoDao)
  {
    this.credSubbusisysinfoDao = credSubbusisysinfoDao;
  }
  
  public void setAgentadmDao(IAfaAgentadmDao agentadmDao)
  {
    this.agentadmDao = agentadmDao;
  }
  
  public void setTradeadmDao(IAfaTradeadmDao tradeadmDao)
  {
    this.tradeadmDao = tradeadmDao;
  }
  
  public void setMaindictDao(IAfapMaindictDao maindictDao)
  {
    this.maindictDao = maindictDao;
  }
  
  public void setSubdictDao(IAfapSubdictDao subdictDao)
  {
    this.subdictDao = subdictDao;
  }
  
  public void setSystemDao(IAfaSystemDao systemDao)
  {
    this.systemDao = systemDao;
  }
  
  public void setUnitadmDao(IAfaUnitadmDao unitadmDao)
  {
    this.unitadmDao = unitadmDao;
  }
  
  public void setSubunitadmDao(IAfaSubunitadmDao subunitadmDao)
  {
    this.subunitadmDao = subunitadmDao;
  }
  
  public void setZoneinfoDao(IAfaZoneinfoDao zoneinfoDao)
  {
    this.zoneinfoDao = zoneinfoDao;
  }
  
  public void setZhinfoDao(IAfaZhinfoDao zhinfoDao)
  {
    this.zhinfoDao = zhinfoDao;
  }
  
  public void setZhbrnoinfoDao(IAfaZhbrnoinfoDao zhbrnoinfoDao)
  {
    this.zhbrnoinfoDao = zhbrnoinfoDao;
  }
  
  public void setGdbsysDao(IGdbSysDao gdbsysDao)
  {
    this.gdbsysDao = gdbsysDao;
  }
  
  public void setHostinfoDao(IGdbHostabsinfoDao hostinfoDao)
  {
    this.hostinfoDao = hostinfoDao;
  }
  
  public void setGdbacctDao(IGdbAcctDao gdbacctDao)
  {
    this.gdbacctDao = gdbacctDao;
  }
  
  public void setBranchcodeDao(IAfaBranchcodeDao branchcodeDao)
  {
    this.branchcodeDao = branchcodeDao;
  }
  
  public void setBrachDao(IAfaBrachDao brachDao)
  {
    this.brachDao = brachDao;
  }
  
  public void setUnitebrachDao(IAfaUnitebrachDao unitebrachDao)
  {
    this.unitebrachDao = unitebrachDao;
  }
  
  public void setAfapinzoneinfoDao(IAfaPinzoneinfoDao pinzoneinfoDao)
  {
    this.afapinzoneinfoDao = pinzoneinfoDao;
  }
  
  public boolean addAfapMaindict(AfapMaindict ca)
  {
    return this.maindictDao.addAfapMaindict(ca);
  }
  
  public boolean deleteAfapMaindict(AfapMaindict ca)
  {
    return this.maindictDao.deleteAfapMaindict(ca);
  }
  
  public AfapMaindict[] getAfapMaindictByMap(Map map)
  {
    return this.maindictDao.getAfapMaindictByMap(map);
  }
  
  public String getNextItem()
  {
    return this.maindictDao.getNextItem();
  }
  
  public boolean updateAfapMaindict(AfapMaindict ca)
  {
    return this.maindictDao.updateAfapMaindict(ca);
  }
  
  public AfapMaindict[] getAfapMaindictByItem(String item)
  {
    return this.maindictDao.getAfapMaindictByItem(item);
  }
  
  public AfapMaindict[] getAllAfapMaindict()
  {
    return this.maindictDao.getAllAfapMaindict();
  }
  
  public AfapMaindict getAfapMaindictBysql(String sql)
  {
    return this.maindictDao.getAfapMaindictBysql(sql);
  }
  
  public boolean addAfapSubdict(AfapSubdict ca)
  {
    return this.subdictDao.addAfapSubdict(ca);
  }
  
  public boolean deleteAfapSubdict(AfapSubdict ca)
  {
    return this.subdictDao.deleteAfapSubdict(ca);
  }
  
  public AfapSubdict[] getAfapSubdictBymap(Map map)
  {
    return this.subdictDao.getAfapSubdictBymap(map);
  }
  
  public AfapSubdict[] getAfapSubdictBysql2(String sql)
  {
    return this.subdictDao.getAfapSubdictBysql2(sql);
  }
  
  public AfapSubdict[] getAllAfapSubdict()
  {
    return this.subdictDao.getAllAfapSubdict();
  }
  
  public boolean updateAfapSubdict(AfapSubdict ca)
  {
    return this.subdictDao.updateAfapSubdict(ca);
  }
  
  public AfapSubdict[] getLable(String itemename, int flag)
  {
    return this.subdictDao.getLable(itemename, flag);
  }
  
  public AfapSubdict getAfaBranchType(String itemename, int type)
  {
    return this.subdictDao.getAfaBranchType(itemename, type);
  }
  
  public AfapSubdict getAfaSubdictCodename(String itemename, String code)
  {
    return this.subdictDao.getAfaSubdictCodename(itemename, code);
  }
  
  public AfapSubdict getAfapSubdictBysql(String sql)
  {
    return this.subdictDao.getAfapSubdictBysql(sql);
  }
  
  public boolean addAfaSystem(AfaSystem ca)
  {
    return this.systemDao.addAfaSystem(ca);
  }
  
  public boolean deleteAfaSystem(AfaSystem ca)
  {
    return this.systemDao.deleteAfaSystem(ca);
  }
  
  public AfaSystem[] getAfaSystemBymap(Map map)
  {
    return this.systemDao.getAfaSystemBymap(map);
  }
  
  public boolean updateAfaSystem(AfaSystem ca)
  {
    return this.systemDao.updateAfaSystem(ca);
  }
  
  public AfaSystem[] getAllafaSystem()
  {
    return this.systemDao.getAllafaSystem();
  }
  
  public AfaSystem getAfaSystemBysql(String sql)
  {
    return this.systemDao.getAfaSystemBysql(sql);
  }
  
  public boolean addAfaUnitadm(AfaUnitadm ca)
  {
    return this.unitadmDao.addAfaUnitadm(ca);
  }
  
  public boolean deleteAfaUnitadm(AfaUnitadm ca)
  {
    return this.unitadmDao.deleteAfaUnitadm(ca);
  }
  
  public AfaUnitadm[] getAfaUnitadmBymap(Map map)
  {
    return this.unitadmDao.getAfaUnitadmBymap(map);
  }
  
  public boolean updateAfaUnitadm(AfaUnitadm ca)
  {
    return this.unitadmDao.updateAfaUnitadm(ca);
  }
  
  public AfaUnitadm[] getAllAfaUnitadm()
  {
    return this.unitadmDao.getAllAfaUnitadm();
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid(String sysid)
  {
    return this.unitadmDao.getAfaUnitadmBysysid(sysid);
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid3(String sysid)
  {
    return this.unitadmDao.getAfaUnitadmBysysid3(sysid);
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid2(String sysid)
  {
    return this.unitadmDao.getAfaUnitadmBysysid2(sysid);
  }
  
  public AfaUnitadm getAfaUnitadmBysql(String sql)
  {
    return this.unitadmDao.getAfaUnitadmBysql(sql);
  }
  
  public boolean addSubunitadm(AfaSubunitadm ca)
  {
    return this.subunitadmDao.addSubunitadm(ca);
  }
  
  public boolean deleteSubunitadm(AfaSubunitadm ca)
  {
    return this.subunitadmDao.deleteSubunitadm(ca);
  }
  
  public AfaSubunitadm[] getAllsubunitadm()
  {
    return this.subunitadmDao.getAllsubunitadm();
  }
  
  public AfaSubunitadm[] getSubunitadmBymap(Map map)
  {
    return this.subunitadmDao.getSubunitadmBymap(map);
  }
  
  public boolean updateSubunitadm(AfaSubunitadm ca)
  {
    return this.subunitadmDao.updateSubunitadm(ca);
  }
  
  public AfaSubunitadm[] getSubunitadmByunitno(String sysid, String unitno)
  {
    return this.subunitadmDao.getSubunitadmByunitno(sysid, unitno);
  }
  
  public AfaSubunitadm getAfaSubunitadmBysql(String sql)
  {
    return this.subunitadmDao.getAfaSubunitadmBysql(sql);
  }
  
  public boolean addAfaAgentadm(AfaAgentadm ca)
  {
    return this.agentadmDao.addAfaAgentadm(ca);
  }
  
  public boolean deleteAfaAgentadm(AfaAgentadm ca)
  {
    return this.agentadmDao.deleteAfaAgentadm(ca);
  }
  
  public AfaAgentadm[] getAfaAgentadmBymap(Map map)
  {
    return this.agentadmDao.getAfaAgentadmBymap(map);
  }
  
  public AfaAgentadm[] getAllAfaAgentadm()
  {
    return this.agentadmDao.getAllAfaAgentadm();
  }
  
  public boolean updateAfaAgentadm(AfaAgentadm ca)
  {
    return this.agentadmDao.updateAfaAgentadm(ca);
  }
  
  public AfaAgentadm getAfaAgentadmBysql(String sql)
  {
    return this.agentadmDao.getAfaAgentadmBysql(sql);
  }
  
  public boolean addAfaTradeadm(AfaTradeadm ca)
  {
    return this.tradeadmDao.addAfaTradeadm(ca);
  }
  
  public boolean deleteAfaTradeadm(AfaTradeadm ca)
  {
    return this.tradeadmDao.deleteAfaTradeadm(ca);
  }
  
  public AfaTradeadm[] getAfaTradeadmBymap(Map map)
  {
    return this.tradeadmDao.getAfaTradeadmBymap(map);
  }
  
  public AfaTradeadm[] getAllAfaTradeadm()
  {
    return this.tradeadmDao.getAllAfaTradeadm();
  }
  
  public boolean updateAfaTradeadm(AfaTradeadm ca)
  {
    return this.tradeadmDao.updateAfaTradeadm(ca);
  }
  
  public AfaTradeadm getAfaTradeadmBysql(String sql)
  {
    return this.tradeadmDao.getAfaTradeadmBysql(sql);
  }
  
  public boolean addAfaZoneinfo(AfaZoneinfo ca)
  {
    return this.zoneinfoDao.addAfaZoneinfo(ca);
  }
  
  public boolean deleteAfaZoneinfo(AfaZoneinfo ca)
  {
    return this.zoneinfoDao.deleteAfaZoneinfo(ca);
  }
  
  public AfaZoneinfo[] getAfaZoneinfoBymap(Map map)
  {
    return this.zoneinfoDao.getAfaZoneinfoBymap(map);
  }
  
  public AfaZoneinfo[] getAllAfaZoneinfo()
  {
    return this.zoneinfoDao.getAllAfaZoneinfo();
  }
  
  public boolean updateAfaZoneinfo(AfaZoneinfo ca)
  {
    return this.zoneinfoDao.updateAfaZoneinfo(ca);
  }
  
  public boolean addAfaZhinfo(AfaZhinfo ca)
  {
    return this.zhinfoDao.addAfaZhinfo(ca);
  }
  
  public boolean deleteAfaZhinfo(AfaZhinfo ca)
  {
    return this.zhinfoDao.deleteAfaZhinfo(ca);
  }
  
  public AfaZhinfo[] getAfaZhinfoBymap(Map map)
  {
    return this.zhinfoDao.getAfaZhinfoBymap(map);
  }
  
  public AfaZhinfo[] getAllAfaZhinfo()
  {
    return this.zhinfoDao.getAllAfaZhinfo();
  }
  
  public boolean updateAfaZhinfo(AfaZhinfo ca)
  {
    return this.zhinfoDao.updateAfaZhinfo(ca);
  }
  
  public boolean addAfaZhbrnoinfo(AfaZhbrnoinfo ca)
  {
    return this.zhbrnoinfoDao.addAfaZhbrnoinfo(ca);
  }
  
  public boolean deleteAfaZhbrnoinfo(AfaZhbrnoinfo ca)
  {
    return this.zhbrnoinfoDao.deleteAfaZhbrnoinfo(ca);
  }
  
  public AfaZhbrnoinfo[] getAfaZhbrnoinfoBymap(Map map)
  {
    return this.zhbrnoinfoDao.getAfaZhbrnoinfoBymap(map);
  }
  
  public AfaZhbrnoinfo[] getAllAfaZhbrnoinfo()
  {
    return this.zhbrnoinfoDao.getAllAfaZhbrnoinfo();
  }
  
  public boolean updateAfaZhbrnoinfo(AfaZhbrnoinfo ca)
  {
    return this.zhbrnoinfoDao.updateAfaZhbrnoinfo(ca);
  }
  
  public boolean addAfaBrach(AfaBranch ca)
  {
    return this.brachDao.addAfaBrach(ca);
  }
  
  public boolean deleteAfaBrach(AfaBranch ca)
  {
    return this.brachDao.deleteAfaBrach(ca);
  }
  
  public AfaBranch[] getAfaBrachByMap(Map map)
  {
    return this.brachDao.getAfaBrachByMap(map);
  }
  
  public AfaBranch[] getAllAfaBrach()
  {
    return this.brachDao.getAllAfaBrach();
  }
  
  public boolean updateAfaBrach(AfaBranch ca)
  {
    return this.brachDao.updateAfaBrach(ca);
  }
  
  public AfaBranch getAfaBrachBysql(String sql)
  {
    return this.brachDao.getAfaBrachBysql(sql);
  }
  
  public AfaBranch[] getAfaBrachBysql2(String sql)
  {
    return this.brachDao.getAfaBrachBysql2(sql);
  }
  
  public boolean addAfaUnitebrach(AfaUnitebrach ca)
  {
    return this.unitebrachDao.addAfaUnitebrach(ca);
  }
  
  public boolean deleteAfaUnitebrach(AfaUnitebrach ca)
  {
    return this.unitebrachDao.deleteAfaUnitebrach(ca);
  }
  
  public AfaUnitebrach[] getAfaUnitebrachByMap(Map map)
  {
    return this.unitebrachDao.getAfaUnitebrachByMap(map);
  }
  
  public AfaUnitebrach[] getAllAfaUnitebrach()
  {
    return this.unitebrachDao.getAllAfaUnitebrach();
  }
  
  public boolean updateAfaUnitebrach(AfaUnitebrach ca)
  {
    return this.unitebrachDao.updateAfaUnitebrach(ca);
  }
  
  public AfaUnitebrach getAfaUnitebrachBysql(String sql)
  {
    return this.unitebrachDao.getAfaUnitebrachBysql(sql);
  }
  
  public IAfaActnoadmDao getAfaActnoadmDao()
  {
    return this.afaActnoadmDao;
  }
  
  public void setAfaActnoadmDao(IAfaActnoadmDao afaActnoadmDao)
  {
    this.afaActnoadmDao = afaActnoadmDao;
  }
  
  public AfaActnoadm[] loadAfaActnoadm(Map map)
  {
    return this.afaActnoadmDao.loadAfaActnoadm(map);
  }
  
  public boolean addAfaActnoadm(AfaActnoadm ca)
  {
    return this.afaActnoadmDao.addAfaActnoadm(ca);
  }
  
  public boolean deleteAfaActnoadm(AfaActnoadm ca)
  {
    return this.afaActnoadmDao.deleteAfaActnoadm(ca);
  }
  
  public boolean updateAfaActnoadm(AfaActnoadm ca)
  {
    return this.afaActnoadmDao.updateAfaActnoadm(ca);
  }
  
  public AfaActnoadm getAfaActnoadmBysql(String sql)
  {
    return this.afaActnoadmDao.getAfaActnoadmBysql(sql);
  }
  
  public IAfaChanneladmDao getAfaChanneladmDao()
  {
    return this.afaChanneladmDao;
  }
  
  public void setAfaChanneladmDao(IAfaChanneladmDao afaChanneladmDao)
  {
    this.afaChanneladmDao = afaChanneladmDao;
  }
  
  public AfaChanneladm[] getAfaChanneladmBymap(Map map)
  {
    return this.afaChanneladmDao.getAfaChanneladmBymap(map);
  }
  
  public AfaChanneladm[] getAfaChanneladmBymap2(Map map)
  {
    return this.afaChanneladmDao.getAfaChanneladmBymap2(map);
  }
  
  public boolean addAfaChanneladm(AfaChanneladm ca)
  {
    return this.afaChanneladmDao.addAfaChanneladm(ca);
  }
  
  public boolean deleteAfaChanneladm(AfaChanneladm ca)
  {
    return this.afaChanneladmDao.deleteAfaChanneladm(ca);
  }
  
  public boolean updateAfaChanneladm(AfaChanneladm ca)
  {
    return this.afaChanneladmDao.updateAfaChanneladm(ca);
  }
  
  public AfaChanneladm getAfaChanneladmBysql(String sql)
  {
    return this.afaChanneladmDao.getAfaChanneladmBysql(sql);
  }
  
  public boolean addGdbsys(GdbSys ca)
  {
    return this.gdbsysDao.addGdbsys(ca);
  }
  
  public boolean deleteGdbsys(GdbSys ca)
  {
    return this.gdbsysDao.deleteGdbsys(ca);
  }
  
  public boolean updateGdbsys(GdbSys ca_old, GdbSys ca)
  {
    return this.gdbsysDao.updateGdbsys(ca_old, ca);
  }
  
  public GdbSys[] getGdbSyBymap(Map map)
  {
    return this.gdbsysDao.getGdbSyBymap(map);
  }
  
  public GdbSys getGdbSysBysql(String sql)
  {
    return this.gdbsysDao.getGdbSysBysql(sql);
  }
  
  public boolean addGdbHostabsinfo(GdbHostabsinfo ca)
  {
    return this.hostinfoDao.addGdbHostabsinfo(ca);
  }
  
  public boolean deleteGdbHostabsinfo(GdbHostabsinfo ca)
  {
    return this.hostinfoDao.deleteGdbHostabsinfo(ca);
  }
  
  public boolean updateGdbHostabsinfo(GdbHostabsinfo ca_old, GdbHostabsinfo ca)
  {
    return this.hostinfoDao.updateGdbHostabsinfo(ca_old, ca);
  }
  
  public GdbHostabsinfo[] getGdbHostabsinfoBymap(Map map)
  {
    return this.hostinfoDao.getGdbHostabsinfoBymap(map);
  }
  
  public GdbHostabsinfo getGdbHostabsinfoBysql(String sql)
  {
    return this.hostinfoDao.getGdbHostabsinfoBysql(sql);
  }
  
  public boolean addGdbAcct(GdbAcct ca)
  {
    return this.gdbacctDao.addGdbAcct(ca);
  }
  
  public boolean deleteGdbAcct(GdbAcct ca)
  {
    return this.gdbacctDao.deleteGdbAcct(ca);
  }
  
  public boolean updateGdbAcct(GdbAcct ca_old, GdbAcct ca)
  {
    return this.gdbacctDao.updateGdbAcct(ca_old, ca);
  }
  
  public GdbAcct[] getGdbAcctBymap(Map map)
  {
    return this.gdbacctDao.getGdbAcctBymap(map);
  }
  
  public GdbAcct getGdbAcctBysql(String sql)
  {
    return this.gdbacctDao.getGdbAcctBysql(sql);
  }
  
  public boolean addAfabranchcode(AfaBranchcode ca)
  {
    return this.branchcodeDao.addAfabranchcode(ca);
  }
  
  public boolean deleteAfabranchcode(AfaBranchcode ca)
  {
    return this.branchcodeDao.deleteAfabranchcode(ca);
  }
  
  public boolean updateAfabranchcode(AfaBranchcode ca_old, AfaBranchcode ca)
  {
    return this.branchcodeDao.updateAfabranchcode(ca_old, ca);
  }
  
  public AfaBranchcode[] getAfabranchcodeBymap(Map map)
  {
    return this.branchcodeDao.getAfabranchcodeBymap(map);
  }
  
  public AfaBranchcode getAfabranchcodeBysql(String sql)
  {
    return this.branchcodeDao.getAfabranchcodeBysql(sql);
  }
  
  public String addAfaCheckapp(EtellerCheckapp ca)
  {
    return this.afaCheckappDao.addAfaCheckapp(ca);
  }
  
  public boolean deleteAfaCheckapp(EtellerCheckapp ca)
  {
    return this.afaCheckappDao.deleteAfaCheckapp(ca);
  }
  
  public boolean updateAfaCheckapp(EtellerCheckapp ca)
  {
    return this.afaCheckappDao.updateAfaCheckapp(ca);
  }
  
  public EtellerCheckapp[] loadAfaCheckapp(Map map)
  {
    return this.afaCheckappDao.loadAfaCheckapp(map);
  }
  
  public EtellerCheckapp getEtellerCheckappBysql(String sql)
  {
    return this.afaCheckappDao.getEtellerCheckappBysql(sql);
  }
  
  public IAfaCheckappDao getAfaCheckappDao()
  {
    return this.afaCheckappDao;
  }
  
  public void setAfaCheckappDao(IAfaCheckappDao afaCheckappDao)
  {
    this.afaCheckappDao = afaCheckappDao;
  }
  
  public IAfaHolidayadmDao getAfaHolidayadmDao()
  {
    return this.afaHolidayadmDao;
  }
  
  public void setAfaHolidayadmDao(IAfaHolidayadmDao afaHolidayadmDao)
  {
    this.afaHolidayadmDao = afaHolidayadmDao;
  }
  
  public boolean deleteAfaHolidayadm(AfaHolidayadm ca)
  {
    return this.afaHolidayadmDao.deleteAfaHolidayadm(ca);
  }
  
  public boolean updateAfaHolidayadm(String start_day, String end_day, String holidayname, String sysid, String unitno, String note1)
  {
    return this.afaHolidayadmDao.updateAfaHolidayadm(start_day, end_day, 
      holidayname, sysid, unitno, note1);
  }
  
  public AfaHolidayadm[] getAfaHolidayadmBymap(Map map)
  {
    return this.afaHolidayadmDao.getAfaHolidayadmBymap(map);
  }
  
  public AfaHolidayadm[] getAllAfaHolidayadm()
  {
    return this.afaHolidayadmDao.getAllAfaHolidayadm();
  }
  
  public AfaHolidayadm getAfaHolidayadmBysql(String sql)
  {
    return this.afaHolidayadmDao.getAfaHolidayadmBysql(sql);
  }
  
  public boolean addAfaPinzoneinfo(AfaPinzoneinfo ca)
  {
    return this.afapinzoneinfoDao.addAfaPinzoneinfo(ca);
  }
  
  public boolean deleteAfaPinzoneinfo(AfaPinzoneinfo ca)
  {
    return this.afapinzoneinfoDao.deleteAfaPinzoneinfo(ca);
  }
  
  public boolean updateAfaPinzoneinfo(AfaPinzoneinfo ca)
  {
    return this.afapinzoneinfoDao.updateAfaPinzoneinfo(ca);
  }
  
  public AfaPinzoneinfo[] loadAfaPinzoneinfo(Map map)
  {
    return this.afapinzoneinfoDao.loadAfaPinzoneinfo(map);
  }
  
  public AfaPinzoneinfo getAfaPinzoneinfoBysql(String sql)
  {
    return this.afapinzoneinfoDao.getAfaPinzoneinfoBysql(sql);
  }
  
  public ICisCommdataDao getCisCommdataDao()
  {
    return this.cisCommdataDao;
  }
  
  public void setCisCommdataDao(ICisCommdataDao cisCommdataDao)
  {
    this.cisCommdataDao = cisCommdataDao;
  }
  
  public boolean addCisCommdata(CisCommdata ca)
  {
    return this.cisCommdataDao.addCisCommdata(ca);
  }
  
  public boolean deleteCisCommdata(CisCommdata ca)
  {
    return this.cisCommdataDao.deleteCisCommdata(ca);
  }
  
  public boolean updateCisCommdata(CisCommdata ca)
  {
    return this.cisCommdataDao.updateCisCommdata(ca);
  }
  
  public CisCommdata[] getCisCommdataBymap(Map map)
  {
    return this.cisCommdataDao.getCisCommdataBymap(map);
  }
  
  public CisCommdata[] getAllCisCommdata()
  {
    return this.cisCommdataDao.getAllCisCommdata();
  }
  
  public CisCommdata getCisCommdataBysql(String sql)
  {
    return this.cisCommdataDao.getCisCommdataBysql(sql);
  }
  
  public boolean addAfaPininfo(AfaPininfo ca)
  {
    return this.afaPininfoDao.addAfaPininfo(ca);
  }
  
  public boolean deleteAfaPininfo(AfaPininfo ca)
  {
    return this.afaPininfoDao.deleteAfaPininfo(ca);
  }
  
  public AfaPininfo[] loadAfaPininfo(Map map)
  {
    return this.afaPininfoDao.loadAfaPininfo(map);
  }
  
  public boolean updateAfaPininfo(AfaPininfo ca, AfaPininfo ca_old)
  {
    return this.afaPininfoDao.updateAfaPininfo(ca, ca_old);
  }
  
  public AfaPininfo getAfaPininfoBysql(String sql)
  {
    return this.afaPininfoDao.getAfaPininfoBysql(sql);
  }
  
  public boolean addEtellerExcelPub(EtellerExcelPub ca)
  {
    return this.etellerExcelPubDao.addEtellerExcelPub(ca);
  }
  
  public boolean deleteEtellerExcelPubOne(EtellerExcelPub ca)
  {
    return this.etellerExcelPubDao.deleteEtellerExcelPubOne(ca);
  }
  
  public EtellerExcelPub[] getEtellerExcelPubByMap(Map map)
  {
    return this.etellerExcelPubDao.getEtellerExcelPubByMap(map);
  }
  
  public IEtellerExcelPubDao getEtellerExcelPubDao()
  {
    return this.etellerExcelPubDao;
  }
  
  public void setEtellerExcelPubDao(IEtellerExcelPubDao etellerExcelPubDao)
  {
    this.etellerExcelPubDao = etellerExcelPubDao;
  }
  
  public boolean addAfaHolidayadm(AfaHolidayadm newca)
  {
    return this.afaHolidayadmDao.addAfaHolidayadm(newca);
  }
  
  public boolean deleteAfaHolidayadm(Map map)
  {
    return this.afaHolidayadmDao.deleteAfaHolidayadm(map);
  }
  
  public IAfaFeeinfoDao getAfaFeeinfoDao()
  {
    return this.afaFeeinfoDao;
  }
  
  public void setAfaFeeinfoDao(IAfaFeeinfoDao afaFeeinfoDao)
  {
    this.afaFeeinfoDao = afaFeeinfoDao;
  }
  
  public boolean addAfaFeeinfo(AfaFeeinfo ca)
  {
    return this.afaFeeinfoDao.addAfaFeeinfo(ca);
  }
  
  public boolean deleteAfaFeeinfo(AfaFeeinfo ca)
  {
    return this.afaFeeinfoDao.deleteAfaFeeinfo(ca);
  }
  
  public AfaFeeinfo[] getAfaFeeinfoByMap(Map map)
  {
    return this.afaFeeinfoDao.getAfaFeeinfoByMap(map);
  }
  
  public boolean updateAfaFeeinfo(AfaFeeinfo ca)
  {
    return this.afaFeeinfoDao.updateAfaFeeinfo(ca);
  }
  
  public AfaFeeinfo[] getAllAfaFeeinfo()
  {
    return this.afaFeeinfoDao.getAllAfaFeeinfo();
  }
  
  public AfaFeeinfo getAfaFeeinfoBysql(String sql)
  {
    return this.afaFeeinfoDao.getAfaFeeinfoBysql(sql);
  }
  
  public boolean addAfaHostfuncmap(AfaHostfuncmap ca)
  {
    return this.afaHostfuncmapDao.addAfaHostfuncmap(ca);
  }
  
  public boolean deleteAfaHostfuncmap(AfaHostfuncmap ca)
  {
    return this.afaHostfuncmapDao.deleteAfaHostfuncmap(ca);
  }
  
  public boolean updateAfaHostfuncmap(AfaHostfuncmap ca, AfaHostfuncmap ca_old)
  {
    return this.afaHostfuncmapDao.updateAfaHostfuncmap(ca, ca_old);
  }
  
  public AfaHostfuncmap[] getAfaHostfuncmapBymap(Map map)
  {
    return this.afaHostfuncmapDao.getAfaHostfuncmapBymap(map);
  }
  
  public AfaHostfuncmap[] getAllafaHostfuncmap()
  {
    return this.afaHostfuncmapDao.getAllafaHostfuncmap();
  }
  
  public AfaHostfuncmap getAfaHostfuncmapBysql(String sql)
  {
    return this.afaHostfuncmapDao.getAfaHostfuncmapBysql(sql);
  }
  
  public IAfaHostfuncmapDao getAfaHostfuncmapDao()
  {
    return this.afaHostfuncmapDao;
  }
  
  public void setAfaHostfuncmapDao(IAfaHostfuncmapDao afaHostfuncmapDao)
  {
    this.afaHostfuncmapDao = afaHostfuncmapDao;
  }
  
  public boolean addCredCardbin(CredCardbin ca)
  {
    return this.credCardbinDao.addCredCardbin(ca);
  }
  
  public boolean deleteCredCardbin(CredCardbin ca)
  {
    return this.credCardbinDao.deleteCredCardbin(ca);
  }
  
  public CredCardbin[] getAllCredCardbin()
  {
    return this.credCardbinDao.getAllCredCardbin();
  }
  
  public CredCardbin[] getCredCardbinByMap(Map map)
  {
    return this.credCardbinDao.getCredCardbinByMap(map);
  }
  
  public CredCardbin getCredCardbinBysql(String sql)
  {
    return this.credCardbinDao.getCredCardbinBysql(sql);
  }
  
  public boolean updateCredCardbin(CredCardbin ca)
  {
    return this.credCardbinDao.updateCredCardbin(ca);
  }
  
  public boolean addCredRecktradecode(CredRecktradecode ca)
  {
    return this.credRecktradecodeDao.addCredRecktradecode(ca);
  }
  
  public boolean deleteCredRecktradecode(CredRecktradecode ca)
  {
    return this.credRecktradecodeDao.deleteCredRecktradecode(ca);
  }
  
  public CredRecktradecode[] getAllCredRecktradecode()
  {
    return this.credRecktradecodeDao.getAllCredRecktradecode();
  }
  
  public CredRecktradecode[] getCredRecktradecodeByMap(Map map)
  {
    return this.credRecktradecodeDao.getCredRecktradecodeByMap(map);
  }
  
  public CredRecktradecode getCredRecktradecodeBysql(String sql)
  {
    return this.credRecktradecodeDao.getCredRecktradecodeBysql(sql);
  }
  
  public boolean updateCredRecktradecode(CredRecktradecode ca)
  {
    return this.credRecktradecodeDao.updateCredRecktradecode(ca);
  }
  
  public boolean addCredSubbusisysinfo(CredSubbusisysinfo ca)
  {
    return this.credSubbusisysinfoDao.addCredSubbusisysinfo(ca);
  }
  
  public boolean deleteCredSubbusisysinfo(CredSubbusisysinfo ca)
  {
    return this.credSubbusisysinfoDao.deleteCredSubbusisysinfo(ca);
  }
  
  public CredSubbusisysinfo[] getAllCredSubbusisysinfo()
  {
    return this.credSubbusisysinfoDao.getAllCredSubbusisysinfo();
  }
  
  public CredSubbusisysinfo[] getCredSubbusisysinfoByMap(Map map)
  {
    return this.credSubbusisysinfoDao.getCredSubbusisysinfoByMap(map);
  }
  
  public CredSubbusisysinfo getCredSubbusisysinfoBysql(String sql)
  {
    return this.credSubbusisysinfoDao.getCredSubbusisysinfoBysql(sql);
  }
  
  public boolean updateCredSubbusisysinfo(CredSubbusisysinfo ca)
  {
    return this.credSubbusisysinfoDao.updateCredSubbusisysinfo(ca);
  }
  
  public String[][] getSearchResultBySQL(String sql)
  {
    return this.genericDao.getSearchResultBySQL(sql);
  }
  
  public List<AfapMaindict> getAfapMaindictByMap(Map<String, Object> map, Pagination page)
  {
    return this.maindictDao.getAfapMaindictByMap(map, page);
  }
  
  public List<EtellerCheckapp> getEtellerCheckappByMap(Map<String, String> map, Pagination page)
  {
    return this.afaCheckappDao.getEtellerCheckappByMap(map, page);
  }
  
  public List<AfaSystem> getAfaSystemByMap(Map<String, String> map, Pagination page)
  {
    return this.systemDao.getAfaSystemByMap(map, page);
  }
  
  public List<AfaPininfo> loadAfaPininfoByMap(Map<String, String> map, Pagination page)
  {
    return this.afaPininfoDao.loadAfaPininfoByMap(map, page);
  }
  
  public List<AfaUnitadm> getAfaUnitadmByMap(Map<String, String> map, Pagination page)
  {
    return this.unitadmDao.getAfaUnitadmByMap(map, page);
  }
  
  public List<AfaSubunitadm> getAfaSubunitadmByMap(Map<String, String> map, Pagination page)
  {
    return this.subunitadmDao.getAfaSubunitadmByMap(map, page);
  }
  
  public List<AfapSubdict> getAfapSubdictByMap(Map<String, String> map, Pagination page)
  {
    return this.subdictDao.getAfapSubdictByMap(map, page);
  }
  
  public List<AfaActnoadm> getAfaActnoadmByMap(Map<String, String> map, Pagination page)
  {
    return this.afaActnoadmDao.getAfaActnoadmByMap(map, page);
  }
  
  public AfaUnitadm getAfaUnitadmById(String sysid, String unitno)
  {
    return this.unitadmDao.getAfaUnitadmById(sysid, unitno);
  }
  
  public List<AfaAgentadm> getAfaAgentadmByMap(Map<String, String> map, Pagination page)
  {
    return this.agentadmDao.getAfaAgentadmByMap(map, page);
  }
  
  public List<AfaTradeadm> getAfaTradeadmByMap(Map<String, String> map, Pagination page)
  {
    return this.tradeadmDao.getAfaTradeadmByMap(map, page);
  }
  
  public List<AfaChanneladm> getAfaChanneladmByMap(Map<String, String> map, Pagination page)
  {
    return this.afaChanneladmDao.getAfaChanneladmByMap(map, page);
  }
  
  public List<AfaFeeinfo> getAfaFeeinfoByMap2(Map<String, String> map, Pagination page)
  {
    return this.afaFeeinfoDao.getAfaFeeinfoByMap2(map, page);
  }
  
  public boolean updateAfaFeeinfo2(AfaFeeinfo oldFeeinfo, AfaFeeinfo newFeeinfo)
    throws Exception
  {
    return this.afaFeeinfoDao.updateAfaFeeinfo2(oldFeeinfo, newFeeinfo);
  }
  
  public List<GdbSys> getGdbSysByMap(Map<String, String> map, Pagination page)
  {
    return this.gdbsysDao.getGdbSysByMap(map, page);
  }
  
  public List<AfaBranch> getAfaBranchByMap(Map<String, String> map, Pagination page)
  {
    return this.brachDao.getAfaBranchByMap(map, page);
  }
  
  public List<AfaUnitebrach> getAfaUnitebrachByMap(Map<String, String> map, Pagination page)
  {
    return this.unitebrachDao.getAfaUnitebrachByMap(map, page);
  }
  
  public String getBranchNameByNo(String branchno)
  {
    return this.brachDao.getBranchNameByNo(branchno);
  }
  
  public List<GdbHostabsinfo> getGdbHostabsinfoByMap(Map<String, String> map, Pagination page)
  {
    return this.hostinfoDao.getGdbHostabsinfoByMap(map, page);
  }
  
  public List<GdbAcct> getGdbAcctByMap(Map<String, String> map, Pagination page)
  {
    return this.gdbacctDao.getGdbAcctByMap(map, page);
  }
  
  public List<AfaBranchcode> getAfaBranchcodeByMap(Map<String, String> map, Pagination page)
  {
    return this.branchcodeDao.getAfaBranchcodeByMap(map, page);
  }
  
  public List<CredCardbin> getCredCardbinByMap(Map<String, String> map, Pagination page)
  {
    return this.credCardbinDao.getCredCardbinByMap(map, page);
  }
  
  public List<CredRecktradecode> getCredRecktradecodeByMap(Map<String, String> map, Pagination page)
  {
    return this.credRecktradecodeDao.getCredRecktradecodeByMap(map, page);
  }
  
  public List<CredSubbusisysinfo> getCredSubbusisysinfoByMap(Map<String, String> map, Pagination page)
  {
    return this.credSubbusisysinfoDao.getCredSubbusisysinfoByMap(map, page);
  }
}
