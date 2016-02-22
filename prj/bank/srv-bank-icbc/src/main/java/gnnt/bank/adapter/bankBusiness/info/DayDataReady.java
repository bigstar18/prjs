package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.FileInfo;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DayDataReady
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String BkSeq = "";

  @XmlElement
  public String FtSeq = "";

  @XmlElement
  public List<FileInfo> FileInfo = new ArrayList();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";

  public void add(FileInfo FileInfo)
  {
    this.FileInfo.add(FileInfo);
  }
}