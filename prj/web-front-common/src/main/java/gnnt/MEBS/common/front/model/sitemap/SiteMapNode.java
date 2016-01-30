package gnnt.MEBS.common.front.model.sitemap;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class SiteMapNode
{
  @XmlAttribute(name="title")
  public String title;
  @XmlAttribute(name="href")
  public String href;
  @XmlAttribute(name="path")
  public String path;
  @XmlElement(name="node")
  public List<SiteMapNode> node;
}
