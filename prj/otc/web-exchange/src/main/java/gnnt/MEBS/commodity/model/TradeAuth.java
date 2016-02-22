package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class TradeAuth
  extends SpecialSet
{
  private String firmName;
  private Integer display;
  private String commodityId;
  private String commodityName;
  private String firmId;
  private Integer cancel_L_Open;
  private Integer cancel_StopLoss;
  private Integer cancel_StopProfit;
  private Integer m_B_Open;
  private Integer m_B_Close;
  private Integer l_B_Open;
  private Integer l_B_CloseLose;
  private Integer l_B_CloseProfit;
  private Integer m_S_Open;
  private Integer m_S_Close;
  private Integer l_S_Open;
  private Integer l_S_CloseLose;
  private Integer l_S_CloseProfit;
  
  public TradeAuth() {}
  
  public TradeAuth(String commodityId, String commodityName, String firmId, Integer m_B_Open, Integer m_B_Close, Integer l_B_Open, Integer l_B_CloseLose, Integer l_B_CloseProfit, Integer m_S_Open, Integer m_S_Close, Integer l_S_Open, Integer l_S_CloseLose, Integer l_S_CloseProfit, Integer cancel_L_Open, Integer cancel_StopLoss, Integer cancel_StopProfit, Integer display, String firmName)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.m_B_Open = m_B_Open;
    this.m_B_Close = m_B_Close;
    this.l_B_Open = l_B_Open;
    this.l_B_CloseLose = l_B_CloseLose;
    this.l_B_CloseProfit = l_B_CloseProfit;
    this.m_S_Open = m_S_Open;
    this.m_S_Close = m_S_Close;
    this.l_S_Open = l_S_Open;
    this.l_S_CloseLose = l_S_CloseLose;
    this.l_S_CloseProfit = l_S_CloseProfit;
    this.cancel_L_Open = cancel_L_Open;
    this.cancel_StopProfit = cancel_StopProfit;
    this.cancel_StopLoss = cancel_StopLoss;
    this.display = display;
    this.firmName = firmName;
  }
  
  public TradeAuth(String commodityId, String commodityName, String firmId, Integer m_B_Open, Integer m_B_Close, Integer l_B_Open, Integer l_B_CloseLose, Integer l_B_CloseProfit, Integer m_S_Open, Integer m_S_Close, Integer l_S_Open, Integer l_S_CloseLose, Integer l_S_CloseProfit, Integer cancel_L_Open, Integer cancel_StopLoss, Integer cancel_StopProfit, Integer display)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.m_B_Open = m_B_Open;
    this.m_B_Close = m_B_Close;
    this.l_B_Open = l_B_Open;
    this.l_B_CloseLose = l_B_CloseLose;
    this.l_B_CloseProfit = l_B_CloseProfit;
    this.m_S_Open = m_S_Open;
    this.m_S_Close = m_S_Close;
    this.l_S_Open = l_S_Open;
    this.l_S_CloseLose = l_S_CloseLose;
    this.l_S_CloseProfit = l_S_CloseProfit;
    this.cancel_L_Open = cancel_L_Open;
    this.cancel_StopProfit = cancel_StopProfit;
    this.cancel_StopLoss = cancel_StopLoss;
    this.display = display;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getFirmName()
  {
    if (this.firmName != null) {
      return this.firmName;
    }
    return this.firmId;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityID)
  {
    this.commodityId = commodityID;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="买入市建:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getM_B_Open()
  {
    return this.m_B_Open;
  }
  
  public void setM_B_Open(Integer m_B_Open)
  {
    this.m_B_Open = m_B_Open;
  }
  
  @ClassDiscription(name="显示权限:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getDisplay()
  {
    return this.display;
  }
  
  public void setDisplay(Integer display)
  {
    this.display = display;
  }
  
  @ClassDiscription(name="买入市平:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getM_B_Close()
  {
    return this.m_B_Close;
  }
  
  @ClassDiscription(name="买入现建:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_B_Open()
  {
    return this.l_B_Open;
  }
  
  public void setL_B_Open(Integer l_B_Open)
  {
    this.l_B_Open = l_B_Open;
  }
  
  @ClassDiscription(name="买入止损:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_B_CloseLose()
  {
    return this.l_B_CloseLose;
  }
  
  public void setL_B_CloseLose(Integer l_B_CloseLose)
  {
    this.l_B_CloseLose = l_B_CloseLose;
  }
  
  @ClassDiscription(name="买入止盈:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_B_CloseProfit()
  {
    return this.l_B_CloseProfit;
  }
  
  public void setL_B_CloseProfit(Integer l_B_CloseProfit)
  {
    this.l_B_CloseProfit = l_B_CloseProfit;
  }
  
  @ClassDiscription(name="卖出市建:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getM_S_Open()
  {
    return this.m_S_Open;
  }
  
  public void setM_S_Open(Integer m_S_Open)
  {
    this.m_S_Open = m_S_Open;
  }
  
  @ClassDiscription(name="卖出市 平:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getM_S_Close()
  {
    return this.m_S_Close;
  }
  
  public void setM_S_Close(Integer m_M_Close)
  {
    this.m_S_Close = m_M_Close;
  }
  
  @ClassDiscription(name="卖出止建:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_S_Open()
  {
    return this.l_S_Open;
  }
  
  public void setL_S_Open(Integer l_M_Open)
  {
    this.l_S_Open = l_M_Open;
  }
  
  @ClassDiscription(name="卖出止损:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_S_CloseLose()
  {
    return this.l_S_CloseLose;
  }
  
  public void setL_S_CloseLose(Integer l_M_CloseLose)
  {
    this.l_S_CloseLose = l_M_CloseLose;
  }
  
  @ClassDiscription(name="卖出止盈:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getL_S_CloseProfit()
  {
    return this.l_S_CloseProfit;
  }
  
  public void setL_S_CloseProfit(Integer l_M_CloseProfit)
  {
    this.l_S_CloseProfit = l_M_CloseProfit;
  }
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public void setM_B_Close(Integer m_B_Close)
  {
    this.m_B_Close = m_B_Close;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  @ClassDiscription(name="撤销止建:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getCancel_L_Open()
  {
    return this.cancel_L_Open;
  }
  
  public void setCancel_L_Open(Integer cancel_L_Open)
  {
    this.cancel_L_Open = cancel_L_Open;
  }
  
  @ClassDiscription(name="撤销止损:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getCancel_StopLoss()
  {
    return this.cancel_StopLoss;
  }
  
  public void setCancel_StopLoss(Integer cancel_StopLoss)
  {
    this.cancel_StopLoss = cancel_StopLoss;
  }
  
  @ClassDiscription(name="撤销止盈:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="有")})
  public Integer getCancel_StopProfit()
  {
    return this.cancel_StopProfit;
  }
  
  public void setCancel_StopProfit(Integer cancel_StopProfit)
  {
    this.cancel_StopProfit = cancel_StopProfit;
  }
}
