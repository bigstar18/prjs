package gnnt.MEBS.transformhq.server.model;

public class Glitch
{
  private String beginTime;
  private String endTime;
  private int frequency;
  private double range;
  private double glitchRange;
  private Boolean isHoldData;
  
  public Glitch() {}
  
  public Glitch(String beginTime, String endTime, int frequency, int range, int glitchRange, Boolean isHoldData)
  {
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.frequency = frequency;
    this.range = range;
    this.glitchRange = glitchRange;
    this.isHoldData = isHoldData;
  }
  
  public double getRange()
  {
    return this.range;
  }
  
  public void setRange(double range)
  {
    this.range = range;
  }
  
  public double getGlitchRange()
  {
    return this.glitchRange;
  }
  
  public void setGlitchRange(double glitchRange)
  {
    this.glitchRange = glitchRange;
  }
  
  public Boolean getIsHoldData()
  {
    return this.isHoldData;
  }
  
  public void setIsHoldData(Boolean isHoldData)
  {
    this.isHoldData = isHoldData;
  }
  
  public int getFrequency()
  {
    return this.frequency;
  }
  
  public void setFrequency(int frequency)
  {
    this.frequency = frequency;
  }
  
  public void setBeginTime(String beginTime)
  {
    this.beginTime = beginTime;
  }
  
  public String getBeginTime()
  {
    return this.beginTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
}
