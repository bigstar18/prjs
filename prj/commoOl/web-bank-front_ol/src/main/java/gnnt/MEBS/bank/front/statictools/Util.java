package gnnt.MEBS.bank.front.statictools;

public class Util
{
  public static boolean isContentsBank(String bankIDs, String bankID)
  {
    if ((bankIDs == null) || (bankID == null)) {
      return false;
    }
    if (bankIDs.trim().equals(bankID.trim())) {
      return true;
    }
    if (bankIDs.trim().startsWith(bankID.trim() + ",")) {
      return true;
    }
    if (bankIDs.trim().endsWith("," + bankID.trim())) {
      return true;
    }
    if (bankIDs.contains("," + bankID.trim() + ",")) {
      return true;
    }
    return false;
  }
}
