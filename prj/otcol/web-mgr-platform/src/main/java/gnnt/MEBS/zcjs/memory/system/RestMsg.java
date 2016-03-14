package gnnt.MEBS.zcjs.memory.system;

import java.util.List;

public class RestMsg
{
  private List<Integer> restWeeks;
  private List<String> restDays;
  
  public List<Integer> getRestWeeks()
  {
    return this.restWeeks;
  }
  
  public void setRestWeeks(List<Integer> restWeeks)
  {
    this.restWeeks = restWeeks;
  }
  
  public List<String> getRestDays()
  {
    return this.restDays;
  }
  
  public void setRestDays(List<String> restDays)
  {
    this.restDays = restDays;
  }
}
