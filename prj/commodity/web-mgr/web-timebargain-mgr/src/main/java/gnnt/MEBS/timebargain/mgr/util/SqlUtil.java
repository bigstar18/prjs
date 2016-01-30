package gnnt.MEBS.timebargain.mgr.util;

public class SqlUtil
{
  public static String makeFilterCondition(String id, String condition, boolean coditionIsNumber)
  {
    if ((condition == null) || (condition.trim().length() == 0)) {
      return "";
    }

    if (id.indexOf(' ') >= 0) {
      throw new RuntimeException("id error:[" + id + "]");
    }

    for (int i = 0; i < condition.length(); i++) {
      char ch = condition.charAt(i);
      if ((!Character.isLetterOrDigit(ch)) && (ch != '-') && (ch != ','))
      {
        throw new RuntimeException("condition error:[" + condition + "]");
      }
    }
    String[] ids = condition.split(",");
    String idTemp = null; String idStart = null; String idEnd = null;
    String[] idScope = null;
    StringBuffer sbIdScope = new StringBuffer();
    StringBuffer sbIds = new StringBuffer();

    for (int i = 0; i < ids.length; i++) {
      idTemp = ids[i].trim();
      if (idTemp.length() != 0)
      {
        idScope = ids[i].split("-");

        if ((idScope.length == 1) && (idScope[0].trim().length() != 0)) {
          if (sbIds.length() != 0) {
            sbIds.append(",");
          }
          sbIds.append("'").append(idScope[0].trim()).append("'");
        } else if (idScope.length == 2) {
          if (sbIdScope.length() != 0) {
            sbIdScope.append(" or ");
          }
          sbIdScope.append("(").append(id).append(">='").append(idScope[0].trim()).append("' and ").append(id).append("<='").append(idScope[1].trim()).append("')");
        }
      }
    }
    String ret = null;
    if (sbIdScope.length() > 0) {
      ret = sbIdScope.toString();
      if (sbIds.length() > 0) {
        ret = ret + " or " + id + " in (" + sbIds.toString() + ")";
      }
    }
    else if (sbIds.length() > 0) {
      ret = id + " in (" + sbIds.toString() + ")";
    }

    if (ret == null) {
      return "";
    }
    if (coditionIsNumber) {
      ret = ret.replaceAll("'", "");
    }

    return " (" + ret + ") ";
  }
}