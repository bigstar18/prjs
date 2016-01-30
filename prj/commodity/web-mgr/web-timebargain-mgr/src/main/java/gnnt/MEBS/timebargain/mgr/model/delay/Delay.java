package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Delay extends StandardModel
{

  @ClassDiscription(name="商品代码", description="")
  private Long sectionID;

  @ClassDiscription(name="交易节名称", description="")
  private String name;

  @ClassDiscription(name="商品代码", description="")
  private String startTime;

  @ClassDiscription(name="商品代码", description="")
  private String endTime;

  @ClassDiscription(name="开始中间时间", description="")
  private String startMiddleTime;

  @ClassDiscription(name="结束中间时间", description="")
  private String endMiddleTime;

  @ClassDiscription(name="交易时间类型", description="")
  private Short type;

  @ClassDiscription(name="商品代码", description="")
  private Short status;

  @ClassDiscription(name=" 修改时间", description="")
  private Date modifyTime;

  public String getStartMiddleTime()
  {
    return this.startMiddleTime;
  }

  public void setStartMiddleTime(String startMiddleTime) {
    this.startMiddleTime = startMiddleTime;
  }

  public String getEndMiddleTime() {
    return this.endMiddleTime;
  }

  public void setEndMiddleTime(String endMiddleTime) {
    this.endMiddleTime = endMiddleTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getSectionID() {
    return this.sectionID;
  }

  public void setSectionID(Long sectionID) {
    this.sectionID = sectionID;
  }

  public String getStartTime() {
    return this.startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public Short getStatus() {
    return this.status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Short getType() {
    return this.type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public void setType(String type) {
    this.type = new Short(type);
  }

  public StandardModel.PrimaryInfo fetchPKey() {
    return new StandardModel.PrimaryInfo( "sectionID", this.sectionID);
  }
}