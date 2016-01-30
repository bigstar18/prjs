package gnnt.MEBS.common.front.model.sitemap;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sitemap")
public class SiteMapModel
{
  @XmlElement(name="node")
  public List<SiteMapNode> siteMapNode;
}
