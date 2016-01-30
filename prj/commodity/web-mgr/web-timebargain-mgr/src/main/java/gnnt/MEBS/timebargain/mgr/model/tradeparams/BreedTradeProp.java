package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BreedTradeProp
  implements Comparable
{

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="商品Model", description="")
  private Breed breed;

  @ClassDiscription(name="交易时间", description="")
  private TradeTime tradeTime;

  public Breed getBreed()
  {
    return this.breed;
  }

  public void setBreed(Breed breed) {
    this.breed = breed;
  }

  public TradeTime getTradeTime() {
    return this.tradeTime;
  }

  public void setTradeTime(TradeTime tradeTime) {
    this.tradeTime = tradeTime;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public int compareTo(Object o) {
    if (!(o instanceof BreedTradeProp)) {
      return -1;
    }
    BreedTradeProp target = (BreedTradeProp)o;
    return getTradeTime().getSectionID().compareTo(target.getTradeTime().getSectionID());
  }

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      (this.breed.getBreedID() == null) || (this.tradeTime.getSectionID() == null) ? 0 : this.breed.getBreedID().hashCode() + this.tradeTime.getSectionID().hashCode());
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BreedTradeProp other = (BreedTradeProp)obj;
    if ((this.breed.getBreedID() == null) || (this.tradeTime.getSectionID() == null)) {
      return false;
    }
    if ((other.getBreed().getBreedID() == null) || (other.getTradeTime().getSectionID() == null)) {
      return false;
    }
    if ((this.breed.getBreedID() == other.getBreed().getBreedID()) && (this.tradeTime.getSectionID() == other.getTradeTime().getSectionID())) {
      return true;
    }
    return false;
  }
}