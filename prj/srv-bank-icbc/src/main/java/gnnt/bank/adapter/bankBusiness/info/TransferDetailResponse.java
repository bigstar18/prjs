package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Detail;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransferDetailResponse
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public ReturnResult Rst = new ReturnResult();

  @XmlElement
  public List<Detail> Detail = new ArrayList();

  @XmlElement
  public int RdSum;

  @XmlElement
  public String NextFlag = "";

  @XmlElement
  public String Mac = "";

  public void add(Detail Detail)
  {
    this.Detail.add(Detail);
  }
}