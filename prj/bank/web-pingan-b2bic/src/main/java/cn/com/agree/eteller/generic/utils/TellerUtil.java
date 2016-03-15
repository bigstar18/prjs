package cn.com.agree.eteller.generic.utils;

import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Department;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class TellerUtil
{
  public static final String TELLERNO = "tellerno";
  public static final String ZONENO = "zoneno";
  public static final String BRNO = "brno";
  public static final String ROLE_LEVEL = "roleLevel";
  public static final String HEAD_LEVEL = "0";
  public static final String ZONE_LEVEL = "1";
  public static final String BRANCH_LEVEL = "2";
  public static final String WANG_DIAN_LEVEL = "3";
  private Map<String, String> info = new HashMap();
  private LoginUser teller;
  
  public TellerUtil(LoginUser teller)
  {
    this.teller = teller;
    initInfo();
  }
  
  public static TellerUtil initTeller()
  {
    TellerUtil tu = new TellerUtil(getLoginTeller());
    return tu;
  }
  
  private void initInfo()
  {
    this.info.put("tellerno", this.teller.getUserId());
    this.info.put("roleLevel", this.teller.getDept().getDepnotype());
    if ("0".equals(this.teller.getDept().getDepnotype()))
    {
      this.info.put("zoneno", "100000");
      this.info.put("brno", "100000");
    }
    if ("1".equals(this.teller.getDept().getDepnotype()))
    {
      this.info.put("zoneno", this.teller.getDept().getId());
      this.info.put("brno", this.teller.getDept().getId());
    }
    else if (("2".equals(this.teller.getDept().getDepnotype())) || ("3".equals(this.teller.getDept().getDepnotype())))
    {
      this.info.put("zoneno", this.teller.getDept().getSuperiorDept().getId());
      this.info.put("brno", this.teller.getDept().getId());
    }
  }
  
  public String getTellerInfo(String infoName)
  {
    return (String)this.info.get(infoName);
  }
  
  public static LoginUser getLoginTeller()
  {
    return (LoginUser)ServletActionContext.getRequest().getSession().getAttribute("user");
  }
  
  public static boolean isHeadLevel()
  {
    return "0".equals(getLoginTeller().getDept().getDepnotype());
  }
  
  public static boolean isZoneLevel()
  {
    return "1".equals(getLoginTeller().getDept().getDepnotype());
  }
  
  public static boolean isBranchLevel()
  {
    return "2".equals(getLoginTeller().getDept().getDepnotype());
  }
  
  public static boolean isWandDianLevel()
  {
    return "3".equals(getLoginTeller().getDept().getDepnotype());
  }
}
