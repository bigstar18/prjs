package gnnt.MEBS.timebargain.mgr.model.apply;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ApplyModel extends StandardModel
{
  private static final long serialVersionUID = 8955028967822823038L;

  @ClassDiscription(name="", description="")
  private Long id;

  @ClassDiscription(name="", description="")
  private Integer applyType;

  @ClassDiscription(name="", description="")
  private Integer status;

  @ClassDiscription(name="", description="")
  private String proposer;

  @ClassDiscription(name="", description="")
  private Date applyTime;

  @ClassDiscription(name="审核人", description="")
  private String approver;

  @ClassDiscription(name="审核时间", description="")
  private Date approvetime;

  @ClassDiscription(name="", description="")
  private String content;

  @ClassDiscription(name="", description="")
  private String note;
  private Map<String, String> dataMap = new HashMap();

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getApplyType() {
    return this.applyType;
  }

  public void setApplyType(Integer applyType) {
    this.applyType = applyType;
    setContent(this.content);
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getProposer() {
    return this.proposer;
  }

  public void setProposer(String proposer) {
    this.proposer = proposer;
  }

  public Date getApplyTime() {
    return this.applyTime;
  }

  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }

  public String getApprover() {
    return this.approver;
  }

  public void setApprover(String approver) {
    this.approver = approver;
  }

  public Date getApprovetime() {
    return this.approvetime;
  }

  public void setApprovetime(Date approvetime) {
    this.approvetime = approvetime;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;

    Document doc = null;
    if ((this.applyType != null) && (content != null)) {
      try {
        doc = DocumentHelper.parseText(content);
      } catch (DocumentException e) {
        e.printStackTrace();
      }
      Element root = doc.getRootElement();
      if (this.applyType.intValue() == 3) {
        Element commodityID = root.element("commodityID");
        this.dataMap.put("commodityID", commodityID.getText());
        Element feeAlgr = root.element("feeAlgr");
        this.dataMap.put("feeAlgr", feeAlgr.getText());
        Element settleFeeAlgr = root.element("settleFeeAlgr");
        this.dataMap.put("settleFeeAlgr", settleFeeAlgr.getText());
        Element lowestSettleFee = root.element("lowestSettleFee");
        this.dataMap.put("lowestSettleFee", lowestSettleFee.getText());
      } else if (this.applyType.intValue() == 4) {
        Element commodityID = root.element("commodityID");
        this.dataMap.put("commodityID", commodityID.getText());
        Element marginAlgr = root.element("marginAlgr");
        this.dataMap.put("marginAlgr", marginAlgr.getText());
        Element payoutAlgr = root.element("payoutAlgr");
        this.dataMap.put("payoutAlgr", payoutAlgr.getText());
        Element marginPriceType = root.element("marginPriceType");
        this.dataMap.put("marginPriceType", marginPriceType.getText());
      }
    }
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Map<String, String> getDataMap() {
    return this.dataMap;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}