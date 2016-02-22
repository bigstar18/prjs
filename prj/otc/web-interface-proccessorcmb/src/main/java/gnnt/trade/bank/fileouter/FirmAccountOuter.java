package gnnt.trade.bank.fileouter;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.page.PageQuery;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Vector;

public class FirmAccountOuter
  extends FileOutPut
{
  public FirmAccountOuter(OutputStream os)
  {
    super(os);
  }
  
  public FirmAccountOuter(OutputStream os, String operator)
  {
    super(os, operator);
  }
  
  public void output(String filter, int[] pageinfo)
  {
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      conn = this.DAO.getConnection();
      sql = "select fbf.CONTACT CONTACT ,case when fbf.ACCOUNTNAME is null then '-' else fbf.ACCOUNTNAME end ACCOUNTNAME ,case when fbf.ACCOUNT is null then '-' when fbf.bankid='005' and fbf.ACCOUNT='999999999999999999' then '-' else fbf.ACCOUNT end ACCOUNT ,case when fbf.bankid is null then '-' else fbb.bankname end bankName ,case when mf.firmType='C' then '-' else fbf.FIRMID end FIRMID ,case when mf.firmType='C' then '客户' when mf.firmType='M' then '会员' else '特别会员' end firmType ,case when fbf.cardtype='1' then '身份证' when fbf.cardtype='8' then '组织机构代码' when fbf.cardtype='9' then '营业执照' else '其他' end cardType ,case when fbf.isOpen=0 then '未签约' when fbf.isOpen=1 then '已签约' when fbf.isOpen=2 then '已解约' else '其他' end isOpen ,case when fbf.status=0 then '可用' when fbf.status=1 then '不可用' else '其他' end status ,case when fbf.bankid is null then '-' when fbf.bankid=ff.bankcode then '主账户' else '次账户' end mainBank ,case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember ,fbf.frozenfuns frozenfuns  from f_b_firmidandaccount fbf,f_b_banks fbb,m_firm mf,F_FirmFunds ff , m_customerinfo    mc where fbf.bankid=fbb.bankid(+) and fbf.firmid=mf.firmid(+) and fbf.firmid=ff.firmid(+) " + 
      











        filter;
      System.out.println(sql);
      rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
      if ("M".equalsIgnoreCase(this.operator)) {
        this.puter.outputMsg(getHeadM(), getTypesM(), rs, this.os);
      } else {
        this.puter.outputMsg(getHeadE(), getTypesE(), rs, this.os);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (this.DAO != null) {
        this.DAO.closeStatement(rs, state, conn);
      }
    }
  }
  
  private LinkedHashMap<String, Integer> getTypesE()
  {
    LinkedHashMap<String, Integer> result = new LinkedHashMap();
    result.put("CONTACT", Integer.valueOf(1));
    result.put("belevemember", Integer.valueOf(1));
    result.put("bankName", Integer.valueOf(1));
    result.put("mainBank", Integer.valueOf(1));
    result.put("ACCOUNT", Integer.valueOf(1));
    result.put("ACCOUNTNAME", Integer.valueOf(1));
    result.put("firmType", Integer.valueOf(1));
    result.put("cardType", Integer.valueOf(1));
    result.put("isOpen", Integer.valueOf(1));
    result.put("status", Integer.valueOf(1));
    result.put("frozenfuns", Integer.valueOf(8));
    return result;
  }
  
  private Vector<String> getHeadE()
  {
    Vector<String> result = new Vector();
    result.add("交易账号");
    result.add("所属会员");
    result.add("银行");
    result.add("账户属性");
    result.add("银行账号");
    result.add("账号名称");
    result.add("账号类型");
    result.add("证件类型");
    result.add("签约状态");
    result.add("是否可用");
    result.add("在途资金");
    return result;
  }
  
  private LinkedHashMap<String, Integer> getTypesM()
  {
    LinkedHashMap<String, Integer> result = new LinkedHashMap();
    result.put("CONTACT", Integer.valueOf(1));
    result.put("bankName", Integer.valueOf(1));
    result.put("mainBank", Integer.valueOf(1));
    result.put("ACCOUNT", Integer.valueOf(1));
    result.put("ACCOUNTNAME", Integer.valueOf(1));
    result.put("cardType", Integer.valueOf(1));
    result.put("isOpen", Integer.valueOf(1));
    result.put("status", Integer.valueOf(1));
    result.put("frozenfuns", Integer.valueOf(8));
    return result;
  }
  
  private Vector<String> getHeadM()
  {
    Vector<String> result = new Vector();
    result.add("交易账号");
    result.add("银行");
    result.add("账户属性");
    result.add("银行账号");
    result.add("账号名称");
    result.add("证件类型");
    result.add("签约状态");
    result.add("是否可用");
    result.add("在途资金");
    return result;
  }
}
