package cn.com.agree.eteller.generic.vo;

import java.util.ArrayList;
import java.util.List;

public class CommonDataNode
{
  private CommonDataNode father;
  private List<CommonDataNode> children = new ArrayList();
  private String nodeId;
  private String nodeName;
  private String nodeAddress;
  private int evel = 0;
  
  public CommonDataNode() {}
  
  public CommonDataNode(String functionId, String functionName, String pageAddress)
  {
    this.nodeId = functionId;
    this.nodeName = functionName;
    this.nodeAddress = pageAddress;
  }
  
  public CommonDataNode(String functionId, String functionName, String pageAddress, int evel)
  {
    this.nodeId = functionId;
    this.nodeName = functionName;
    this.nodeAddress = pageAddress;
    this.evel = evel;
  }
  
  public CommonDataNode getFather()
  {
    return this.father;
  }
  
  public void setFather(CommonDataNode father)
  {
    this.father = father;
  }
  
  public String getNodeId()
  {
    return this.nodeId;
  }
  
  public void setNodeId(String nodeId)
  {
    this.nodeId = nodeId;
  }
  
  public String getNodeName()
  {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  public String getNodeAddress()
  {
    return this.nodeAddress;
  }
  
  public void setNodeAddress(String nodeAddress)
  {
    this.nodeAddress = nodeAddress;
  }
  
  public int getEvel()
  {
    return this.evel;
  }
  
  public void setEvel(int evel)
  {
    this.evel = evel;
  }
  
  public CommonDataNode addChild(CommonDataNode child)
  {
    this.children.add(child);
    child.setFather(this);
    return child;
  }
  
  public List<CommonDataNode> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(List<CommonDataNode> children)
  {
    this.children = children;
  }
}
