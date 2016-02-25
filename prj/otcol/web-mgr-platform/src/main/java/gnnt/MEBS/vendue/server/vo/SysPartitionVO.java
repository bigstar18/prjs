package gnnt.MEBS.vendue.server.vo;

public class SysPartitionVO
{
  public int partitionID;
  public String engineClass;
  public String quotationClass;
  public String submitActionClass;
  public int validFlag;
  public String description;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("partitionID:" + this.partitionID + str);
    localStringBuffer.append("engineClass:" + this.engineClass + str);
    localStringBuffer.append("quotationClass:" + this.quotationClass + str);
    localStringBuffer.append("submitActionClass:" + this.submitActionClass + str);
    localStringBuffer.append("validFlag:" + this.validFlag + str);
    localStringBuffer.append("description:" + this.description + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
