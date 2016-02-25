package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.LookupDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class LookupDAOJdbc
  extends BaseDAOJdbc
  implements LookupDAO
{
  public List getRoles()
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all roles...");
    }
    String str = "select * from app_role";
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getSections(Breed paramBreed)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all section with current user...");
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select t.sectionID sectionID,t.Name name from T_A_TradeTime t");
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getSectionsC(Breed paramBreed)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all section with current user...");
    }
    Long localLong = null;
    String str = "";
    if ((paramBreed != null) && (paramBreed.getBreedID() != null))
    {
      localLong = paramBreed.getBreedID();
      str = "select t.sectionID sectionID,t.Name name from T_A_BreedTradeProp btp,T_A_Breed b,T_A_TradeTime t where b.breedID = btp.breedID and t.SectionID = btp.SectionID and b.breedID=" + localLong;
    }
    else
    {
      str = "select t.sectionID sectionID,t.Name name from T_A_TradeTime t";
    }
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBmk(String paramString)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk ...");
    }
    String str = "select bm,mc from " + paramString + " order by bm";
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getModuleFunc()
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving table module_func ...");
    }
    String str = "select * from module_func";
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBmkTj(String paramString1, String paramString2)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk with tj ...");
    }
    String str = "select * from " + paramString1 + (paramString2 == null ? "" : paramString2);
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBmkTjDistinctCommodityID(String paramString1, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk with tj ...");
    }
    String str = "select distinct commodityID from " + paramString1 + (paramString2 == null ? "" : paramString2);
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving mc with tj ...");
    }
    String str = "select " + paramString2 + " from " + paramString1 + (paramString3 == null ? "" : paramString3);
    this.log.debug("sqlStr: " + str);
    List localList = getJdbcTemplate().queryForList(str);
    if (localList.size() <= 0) {
      return "";
    }
    Map localMap = (Map)localList.get(0);
    return (String)localMap.get(paramString2);
  }
}
