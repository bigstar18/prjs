package gnnt.MEBS.common.front.model;

import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class Apply
  extends StandardModel
{
  private static final long serialVersionUID = -1375749907158173756L;
  @ClassDiscription(name="申请编号", description="")
  private Long id;
  @ClassDiscription(name="申请类型", description="")
  private String applyType;
  @ClassDiscription(name="当前状态", description="当前状态 0：待审核  1：审核通过  2：审核驳回  3：撤销申请  ")
  private Integer status;
  @ClassDiscription(name="内容 ", description="")
  private String content;
  @ClassDiscription(name="备注", description="")
  private String note;
  @ClassDiscription(name="描述", description="")
  private String discribe;
  @ClassDiscription(name="修改时间 ", description="")
  private Date modTime;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="申请人", description="")
  private String applyUser;
  @ClassDiscription(name="操作类型", description="")
  private String operateType;
  @ClassDiscription(name="业务对象类", description="")
  private String entityClass;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(String paramString)
  {
    this.applyType = paramString;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getDiscribe()
  {
    return this.discribe;
  }
  
  public void setDiscribe(String paramString)
  {
    this.discribe = paramString;
  }
  
  public Date getModTime()
  {
    return this.modTime;
  }
  
  public void setModTime(Date paramDate)
  {
    this.modTime = paramDate;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getApplyUser()
  {
    return this.applyUser;
  }
  
  public void setApplyUser(String paramString)
  {
    this.applyUser = paramString;
  }
  
  public String getOperateType()
  {
    return this.operateType;
  }
  
  public void setOperateType(String paramString)
  {
    this.operateType = paramString;
  }
  
  public String getEntityClass()
  {
    return this.entityClass;
  }
  
  public void setEntityClass(String paramString)
  {
    this.entityClass = paramString;
  }
  
  public PrimaryInfo fetchPKey()
  {
    return new PrimaryInfo("id", this.id);
  }
}
