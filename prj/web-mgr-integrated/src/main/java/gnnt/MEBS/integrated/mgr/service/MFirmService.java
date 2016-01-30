package gnnt.MEBS.integrated.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.ProcedureParameter;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmApply;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmModule;
import gnnt.MEBS.integrated.mgr.model.usermanage.Trader;
import gnnt.MEBS.integrated.mgr.model.usermanage.TraderModule;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("mfirmService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class MFirmService
  extends StandardService
{
  public int addFirm(MFirm firm, String userId, String password, String enableKey, String kcode)
    throws Exception
  {
    if (firm != null)
    {
      Set<MFirmModule> mFirmModuleSet = firm.getFirmModuleSet();
      firm.setFirmModuleSet(null);
      add(firm);
      for (MFirmModule mf : mFirmModuleSet)
      {
        this.logger.debug(mf.getMfirm().getFirmId() + "  " + mf.getModuleId() + "   " + mf.getEnabled());
        mf.setMfirm(firm);
        add(mf);
      }
      getDao().flush();
      int result = addMfrim(firm, userId, password);
      if (result > 0)
      {
        Trader trader = new Trader();
        trader.setTraderId(firm.getFirmId());
        trader = (Trader)get(trader);
        trader.setEnableKey(enableKey);
        trader.setKeyCode(kcode);
        update(trader);
      }
      return 1;
    }
    return 0;
  }
  
  public int addMfrim(MFirm firm, String userId, String password)
    throws Exception
  {
    int result = 0;
    String str = "";
    
    result = ((BigDecimal)getDao().executeProcedure("{?=call FN_M_FirmAdd(?,?,?,?) }", 
      new Object[] { firm.getFirmId(), userId, password, str })).intValue();
    
    return result;
  }
  
  public int passFirm(MFirm firm, String userId, String password, String firmApplyId, String note, String enableKey, String keyCode)
    throws Exception
  {
    if (firm != null)
    {
      Set<MFirmModule> mFirmModuleSet = firm.getFirmModuleSet();
      firm.setFirmModuleSet(null);
      add(firm);
      for (MFirmModule mf : mFirmModuleSet)
      {
        mf.setMfirm(firm);
        add(mf);
      }
      firm.setApplyID(Long.valueOf(Tools.strToLong(firmApplyId)));
      getDao().flush();
      int result = addPassMfrim(firm, userId, password, firmApplyId, note);
      if (result > 0)
      {
        Trader trader = new Trader();
        trader.setTraderId(firm.getFirmId());
        trader = (Trader)get(trader);
        trader.setEnableKey(enableKey);
        trader.setKeyCode(keyCode);
      }
      return 1;
    }
    return 0;
  }
  
  public int addPassMfrim(MFirm firm, String userId, String password, String firmApplyId, String note)
    throws Exception
  {
    int result = 0;
    String str = "";
    
    result = ((BigDecimal)getDao().executeProcedure("{?=call FN_M_FirmAdd(?,?,?,?) }", 
      new Object[] { firm.getFirmId(), userId, password, str })).intValue();
    if (result > 0)
    {
      MFirmApply apply = new MFirmApply();
      apply.setApplyID(Long.valueOf(Long.parseLong(firmApplyId)));
      apply = (MFirmApply)getDao().get(apply);
      apply.setModifyTime(firm.getModifyTime());
      apply.setAuditNote(note);
      apply.setStatus(Integer.valueOf(1));
      apply.setUserID(userId);
      getDao().update(apply);
      String brokerId = apply.getBrokerId();
      if ((brokerId != null) && (brokerId.length() > 0))
      {
        Connection conn = getDao().getSessionFactory().getCurrentSession().connection();
        PreparedStatement pstm = null;
        String sql = "";
        sql = "select * from BR_FIRMANDBROKER where firmid=?  ";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, firm.getFirmId());
        ResultSet rs = pstm.executeQuery();
        if (!rs.next())
        {
          String insertsql = "insert into BR_FIRMANDBROKER values(?,?,to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'))";
          pstm = conn.prepareStatement(insertsql);
          pstm.setString(2, brokerId);
          pstm.setString(1, firm.getFirmId());
          pstm.execute();
        }
        rs.close();
        pstm.close();
      }
    }
    else
    {
      result = -1;
    }
    return result;
  }
  
  public int updateMfrimStatus(Object[] ids, String status)
    throws Exception
  {
    this.logger.debug("修改交易商状态 调用存储");
    
    int result = 0;
    String str = "";
    Object[] arrayOfObject;
    int j = (arrayOfObject = ids).length;
    for (int i = 0; i < j; i++)
    {
      Object id = arrayOfObject[i];
      Object object = getDao().executeProcedure(
        "{?=call FN_M_FirmToStatus(?,?,?) }", 
        new Object[] { id, status, str });
      
      result = ((BigDecimal)object).intValue();
    }
    return result;
  }
  
  public Map<String, Object> delMfirmStatus(Object[] ids, String status)
    throws Exception
  {
    this.logger.debug("注销交易商 调用的存储");
    
    Map<String, Object> map = new HashMap();
    Object[] arrayOfObject;
    int j = (arrayOfObject = ids).length;
    for (int i = 0; i < j; i++)
    {
      Object id = arrayOfObject[i];
      List<ProcedureParameter> parameterList = new ArrayList();
      ProcedureParameter parameter = new ProcedureParameter();
      parameter.setName("result");
      parameter.setParameterType(1);
      parameter.setSqlType(2);
      parameterList.add(parameter);
      parameter = new ProcedureParameter();
      parameter.setName("firmId");
      parameter.setParameterType(2);
      parameter.setSqlType(12);
      parameter.setValue(id);
      parameterList.add(parameter);
      parameter = new ProcedureParameter();
      parameter.setName("errorInfo");
      parameter.setParameterType(1);
      parameter.setSqlType(12);
      parameterList.add(parameter);
      Object object = getDao().executeProcedure("{?=call FN_M_FirmDel(?,?) }", parameterList);
      map = (Map)object;
    }
    return map;
  }
  
  public void updateFirm(MFirm mFirm)
  {
    if (mFirm != null)
    {
      Set<MFirmModule> firmModuleSet = mFirm.getFirmModuleSet();
      mFirm.setFirmModuleSet(null);
      update(mFirm);
      if (firmModuleSet != null)
      {
        QueryConditions qc = new QueryConditions();
        qc.addCondition("mfirm.firmId", "=", mFirm.getFirmId());
        PageRequest<QueryConditions> pageRequest = new PageRequest(1, 100, qc, "");
        Page<StandardModel> page = getPage(pageRequest, new MFirmModule());
        deleteBYBulk(page.getResult());
        
        Map<Integer, Boolean> moduleN = new HashMap();
        for (MFirmModule mf : firmModuleSet)
        {
          mf.setMfirm(mFirm);
          add(mf);
          
          getDao().flush();
          if (!"Y".equalsIgnoreCase(mf.getEnabled())) {
            moduleN.put(mf.getModuleId(), Boolean.valueOf(true));
          }
        }
        if (moduleN.size() > 0)
        {
          QueryConditions qcTrader = new QueryConditions();
          qcTrader.addCondition("mfirm.firmId", "=", mFirm.getFirmId());
          Object pageRequestTrader = new PageRequest(1, 100, qcTrader, "");
          Page<StandardModel> pageTrader = getPage((PageRequest)pageRequestTrader, new Trader());
          for (int i = 0; i < pageTrader.getResult().size(); i++)
          {
            Trader trader = (Trader)pageTrader.getResult().get(i);
            for (TraderModule traderModule : trader.getTraderModuleSet()) {
              if (moduleN.get(traderModule.getModuleId()) != null) {
                traderModule.setEnabled("N");
              }
            }
            update(trader);
          }
        }
      }
    }
  }
}
