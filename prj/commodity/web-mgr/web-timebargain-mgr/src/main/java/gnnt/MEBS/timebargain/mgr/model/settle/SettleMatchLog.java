package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Timestamp;

public class SettleMatchLog extends StandardModel
{
  private static final long serialVersionUID = 1371618442269230589L;

  @ClassDiscription(name="操作编号", description="")
  private Integer id;

  @ClassDiscription(name="操作编号", description="")
  private String matchID;

  @ClassDiscription(name="操作员", description="")
  private String operator;

  @ClassDiscription(name="操作日志", description="")
  private String operateLog;

  @ClassDiscription(name="更新时间", description="")
  private Timestamp updateTime;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getMatchID()
  {
    return this.matchID;
  }

  public void setMatchID(String matchID)
  {
    this.matchID = matchID;
  }

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String operator)
  {
    this.operator = operator;
  }

  public String getOperateLog()
  {
    return this.operateLog;
  }

  public void setOperateLog(String operateLog)
  {
    this.operateLog = operateLog;
  }

  public Timestamp getUpdateTime()
  {
    return this.updateTime;
  }

  public void setUpdateTime(Timestamp updateTime)
  {
    this.updateTime = updateTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}