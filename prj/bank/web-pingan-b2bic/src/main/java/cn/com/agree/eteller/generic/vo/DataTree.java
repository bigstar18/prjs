package cn.com.agree.eteller.generic.vo;

import java.util.HashSet;
import java.util.Set;

public class DataTree
{
  private CommonDataNode rootNode;
  private Set treeLeaf = new HashSet();
  
  public DataTree() {}
  
  public DataTree(CommonDataNode rootNode)
  {
    this.rootNode = rootNode;
  }
  
  public void setRootNode(CommonDataNode rootNode)
  {
    this.rootNode = rootNode;
  }
  
  public CommonDataNode getRootNode()
  {
    return this.rootNode;
  }
  
  public Set getTreeLeaf()
  {
    return this.treeLeaf;
  }
  
  public void setTreeLeaf(Set treeLeaf)
  {
    this.treeLeaf = treeLeaf;
  }
}
