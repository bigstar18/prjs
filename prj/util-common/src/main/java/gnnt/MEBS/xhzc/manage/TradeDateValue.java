package gnnt.MEBS.xhzc.manage;

public class TradeDateValue
{
  public int iD;
  public String weekRest;
  public String yearRest;
  private int ID;

  public int getID()
  {
    return this.ID;
  }

  public String getWeekRest() {
    return this.weekRest;
  }

  public String getYearRest() {
    return this.yearRest;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public void setWeekRest(String weekRest) {
    this.weekRest = weekRest;
  }

  public void setYearRest(String yearRest) {
    this.yearRest = yearRest;
  }
}