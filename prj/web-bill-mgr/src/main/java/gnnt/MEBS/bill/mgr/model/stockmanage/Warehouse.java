package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Warehouse
  extends StandardModel
{
  private static final long serialVersionUID = 4901122171733186296L;
  @ClassDiscription(name="编号", description="")
  private Long id;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseId;
  @ClassDiscription(name="仓库名称", description="")
  private String warehouseName;
  @ClassDiscription(name="仓库状态", description="仓库状态： 0 可用 1 不可用")
  private Integer status;
  @ClassDiscription(name="仓库归属单位", description="")
  private String ownershipUnits;
  @ClassDiscription(name="注册资本", description="")
  private double registeredCapital;
  @ClassDiscription(name="投资总额", description="")
  private double investmentAmount;
  @ClassDiscription(name="仓库地址", description="")
  private String address;
  @ClassDiscription(name="仓库坐标", description="")
  private String coordinate;
  @ClassDiscription(name="仓储环境", description="")
  private String environmental;
  @ClassDiscription(name="仓库等级", description="仓库等级：1 一星级 2 二星级 3 三星级 4 四星级 5 五星级")
  private int rank;
  @ClassDiscription(name="检测条件", description="")
  private String testConditions;
  @ClassDiscription(name="与交易中心签订协议日期", description="")
  private Date agreementDate;
  @ClassDiscription(name="省份", description="")
  private String province;
  @ClassDiscription(name="市", description="")
  private String city;
  @ClassDiscription(name="邮编", description="")
  private String postcode;
  @ClassDiscription(name="法人", description="")
  private String corporateRepresentative;
  @ClassDiscription(name="法人联系电话", description="")
  private String representativePhone;
  @ClassDiscription(name="业务联系人", description="")
  private String contactMan;
  @ClassDiscription(name="联系人电话", description="")
  private String phone;
  @ClassDiscription(name="联系人手机", description="")
  private String mobile;
  @ClassDiscription(name="传真", description="")
  private String fax;
  @ClassDiscription(name="是否有码头", description="是否有码头：0 有码头 1 没有码头")
  private int hasDock;
  @ClassDiscription(name="码头吨位", description="")
  private double dockTonnage;
  @ClassDiscription(name="泊位数量", description="泊位日吞吐量")
  private double dockDailyThroughput;
  @ClassDiscription(name="停靠船舶类型", description="停靠船舶类型：0 海伦 1 江轮 2 全部 3 不支持")
  private int shipType;
  @ClassDiscription(name="是否有铁路专用线", description="是否有铁路专用线：0 有铁路专线 1 没有铁路专线")
  private int hasRailway;
  @ClassDiscription(name="铁路日装御能力", description="")
  private double railwayDailyThroughput;
  @ClassDiscription(name="是否支持槽车装御", description="是否支持槽车装御：0 支持 1 不支持")
  private int hasTanker;
  @ClassDiscription(name="发车鹤位数量", description="槽车日装卸能力")
  private double tankerDailyThroughput;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
  
  public String getWarehouseName()
  {
    return this.warehouseName;
  }
  
  public void setWarehouseName(String paramString)
  {
    this.warehouseName = paramString;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public String getOwnershipUnits()
  {
    return this.ownershipUnits;
  }
  
  public void setOwnershipUnits(String paramString)
  {
    this.ownershipUnits = paramString;
  }
  
  public double getRegisteredCapital()
  {
    return this.registeredCapital;
  }
  
  public void setRegisteredCapital(double paramDouble)
  {
    this.registeredCapital = paramDouble;
  }
  
  public double getInvestmentAmount()
  {
    return this.investmentAmount;
  }
  
  public void setInvestmentAmount(double paramDouble)
  {
    this.investmentAmount = paramDouble;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString;
  }
  
  public String getCoordinate()
  {
    return this.coordinate;
  }
  
  public void setCoordinate(String paramString)
  {
    this.coordinate = paramString;
  }
  
  public String getEnvironmental()
  {
    return this.environmental;
  }
  
  public void setEnvironmental(String paramString)
  {
    this.environmental = paramString;
  }
  
  public int getRank()
  {
    return this.rank;
  }
  
  public void setRank(int paramInt)
  {
    this.rank = paramInt;
  }
  
  public String getTestConditions()
  {
    return this.testConditions;
  }
  
  public void setTestConditions(String paramString)
  {
    this.testConditions = paramString;
  }
  
  public Date getAgreementDate()
  {
    return this.agreementDate;
  }
  
  public void setAgreementDate(Date paramDate)
  {
    this.agreementDate = paramDate;
  }
  
  public String getProvince()
  {
    return this.province;
  }
  
  public void setProvince(String paramString)
  {
    this.province = paramString;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public void setCity(String paramString)
  {
    this.city = paramString;
  }
  
  public String getPostcode()
  {
    return this.postcode;
  }
  
  public void setPostcode(String paramString)
  {
    this.postcode = paramString;
  }
  
  public String getCorporateRepresentative()
  {
    return this.corporateRepresentative;
  }
  
  public void setCorporateRepresentative(String paramString)
  {
    this.corporateRepresentative = paramString;
  }
  
  public String getRepresentativePhone()
  {
    return this.representativePhone;
  }
  
  public void setRepresentativePhone(String paramString)
  {
    this.representativePhone = paramString;
  }
  
  public String getContactMan()
  {
    return this.contactMan;
  }
  
  public void setContactMan(String paramString)
  {
    this.contactMan = paramString;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String paramString)
  {
    this.fax = paramString;
  }
  
  public int getHasDock()
  {
    return this.hasDock;
  }
  
  public void setHasDock(int paramInt)
  {
    this.hasDock = paramInt;
  }
  
  public double getDockTonnage()
  {
    return this.dockTonnage;
  }
  
  public void setDockTonnage(double paramDouble)
  {
    this.dockTonnage = paramDouble;
  }
  
  public double getDockDailyThroughput()
  {
    return this.dockDailyThroughput;
  }
  
  public void setDockDailyThroughput(double paramDouble)
  {
    this.dockDailyThroughput = paramDouble;
  }
  
  public int getShipType()
  {
    return this.shipType;
  }
  
  public void setShipType(int paramInt)
  {
    this.shipType = paramInt;
  }
  
  public int getHasRailway()
  {
    return this.hasRailway;
  }
  
  public void setHasRailway(int paramInt)
  {
    this.hasRailway = paramInt;
  }
  
  public double getRailwayDailyThroughput()
  {
    return this.railwayDailyThroughput;
  }
  
  public void setRailwayDailyThroughput(double paramDouble)
  {
    this.railwayDailyThroughput = paramDouble;
  }
  
  public int getHasTanker()
  {
    return this.hasTanker;
  }
  
  public void setHasTanker(int paramInt)
  {
    this.hasTanker = paramInt;
  }
  
  public double getTankerDailyThroughput()
  {
    return this.tankerDailyThroughput;
  }
  
  public void setTankerDailyThroughput(double paramDouble)
  {
    this.tankerDailyThroughput = paramDouble;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
