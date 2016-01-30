package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class MFirmModel extends StandardModel
{
  private static final long serialVersionUID = 4392470602333053274L;
  private String firmId;
  private String name;
  private String fullName;
  private Long type;
  private String status;
  private String address;
  private String contactMan;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private String note;
  private String zoneCode;
  private String industryCode;
  private String extendData;
  private Date createTime;
  private Date modifyTime;
  private HoldPositionModel holdPositionModel;
  private HistoryHoldPositionModel historyHoldPositionModel;
  private CustomerHoldSumModel customerHoldSumModel;
  private HistoryCustomerHoldSumModel historyCustomerHoldSumModel;
  private FirmHoldSumModel firmHoldSumModel;
  private HistoryFirmHoldSumModel historyFirmHoldSumModel;
  private TradeModel tradeModel;
  private OrdersModel ordersModel;
  private HistoryOrdersModel historyOrdersModel;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFullName()
  {
    return this.fullName;
  }

  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }

  public Long getType()
  {
    return this.type;
  }

  public void setType(Long type)
  {
    this.type = type;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getContactMan()
  {
    return this.contactMan;
  }

  public void setContactMan(String contactMan)
  {
    this.contactMan = contactMan;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getZoneCode()
  {
    return this.zoneCode;
  }

  public void setZoneCode(String zoneCode)
  {
    this.zoneCode = zoneCode;
  }

  public String getIndustryCode()
  {
    return this.industryCode;
  }

  public void setIndustryCode(String industryCode)
  {
    this.industryCode = industryCode;
  }

  public String getExtendData()
  {
    return this.extendData;
  }

  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public HoldPositionModel getHoldPositionModel()
  {
    return this.holdPositionModel;
  }

  public void setHoldPositionModel(HoldPositionModel holdPositionModel)
  {
    this.holdPositionModel = holdPositionModel;
  }

  public HistoryHoldPositionModel getHistoryHoldPositionModel()
  {
    return this.historyHoldPositionModel;
  }

  public void setHistoryHoldPositionModel(HistoryHoldPositionModel historyHoldPositionModel)
  {
    this.historyHoldPositionModel = historyHoldPositionModel;
  }

  public CustomerHoldSumModel getCustomerHoldSumModel()
  {
    return this.customerHoldSumModel;
  }

  public void setCustomerHoldSumModel(CustomerHoldSumModel customerHoldSumModel)
  {
    this.customerHoldSumModel = customerHoldSumModel;
  }

  public HistoryCustomerHoldSumModel getHistoryCustomerHoldSumModel()
  {
    return this.historyCustomerHoldSumModel;
  }

  public void setHistoryCustomerHoldSumModel(HistoryCustomerHoldSumModel historyCustomerHoldSumModel)
  {
    this.historyCustomerHoldSumModel = historyCustomerHoldSumModel;
  }

  public FirmHoldSumModel getFirmHoldSumModel()
  {
    return this.firmHoldSumModel;
  }

  public void setFirmHoldSumModel(FirmHoldSumModel firmHoldSumModel)
  {
    this.firmHoldSumModel = firmHoldSumModel;
  }

  public HistoryFirmHoldSumModel getHistoryFirmHoldSumModel()
  {
    return this.historyFirmHoldSumModel;
  }

  public void setHistoryFirmHoldSumModel(HistoryFirmHoldSumModel historyFirmHoldSumModel)
  {
    this.historyFirmHoldSumModel = historyFirmHoldSumModel;
  }

  public TradeModel getTradeModel()
  {
    return this.tradeModel;
  }

  public void setTradeModel(TradeModel tradeModel)
  {
    this.tradeModel = tradeModel;
  }

  public OrdersModel getOrdersModel()
  {
    return this.ordersModel;
  }

  public void setOrdersModel(OrdersModel ordersModel)
  {
    this.ordersModel = ordersModel;
  }

  public HistoryOrdersModel getHistoryOrdersModel()
  {
    return this.historyOrdersModel;
  }

  public void setHistoryOrdersModel(HistoryOrdersModel historyOrdersModel)
  {
    this.historyOrdersModel = historyOrdersModel;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}