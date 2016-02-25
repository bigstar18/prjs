package gnnt.MEBS.timebargain.manage.model;

public class Settleprivilege
{
  private long id;
  private int type;
  private String typeId;
  private int kind;
  private String kindId;
  private int privilegecode_b;
  private int privilegecode_s;
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(String paramString)
  {
    this.typeId = paramString;
  }
  
  public int getKind()
  {
    return this.kind;
  }
  
  public void setKind(int paramInt)
  {
    this.kind = paramInt;
  }
  
  public String getKindId()
  {
    return this.kindId;
  }
  
  public void setKindId(String paramString)
  {
    this.kindId = paramString;
  }
  
  public int getPrivilegecode_b()
  {
    return this.privilegecode_b;
  }
  
  public void setPrivilegecode_b(int paramInt)
  {
    this.privilegecode_b = paramInt;
  }
  
  public int getPrivilegecode_s()
  {
    return this.privilegecode_s;
  }
  
  public void setPrivilegecode_s(int paramInt)
  {
    this.privilegecode_s = paramInt;
  }
}
