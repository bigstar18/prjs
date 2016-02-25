package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.dao.DirectFirmBreedDAO;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class DirectFirmBreedDAOJdbc
  extends BaseDAOJdbc
  implements DirectFirmBreedDAO
{
  public List directFirmBreedList()
  {
    return getJdbcTemplate().queryForList("select d.breedId,d.firmId,a.breedname,c.sortName from T_E_DirectFirmBreed d,t_a_breed a,t_a_cmdtysort c where d.breedId=a.breedId and a.sortId = c.sortId");
  }
  
  public DirectFirmBreed directFirmView(DirectFirmBreed paramDirectFirmBreed)
  {
    String str = "select * from t_e_directFirmBreed where firmid=? and breedid=?";
    Object[] arrayOfObject = { paramDirectFirmBreed.getFirmId(), Integer.valueOf(paramDirectFirmBreed.getBreedId()) };
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    DirectFirmBreed localDirectFirmBreed = null;
    if (localList != null) {
      localDirectFirmBreed = (DirectFirmBreed)localList.get(0);
    }
    return localDirectFirmBreed;
  }
  
  public List getFirmList(QueryConditions paramQueryConditions)
  {
    String str = "select * from m_firm where 1 = 1  ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public List directFirmBreedGet(QueryConditions paramQueryConditions)
  {
    String str = "select e.breedId,e.firmId,b.breedname,c.sortName from t_e_directFirmBreed e,t_a_breed b ,t_a_cmdtysort c where 1=1 and b.breedId=e.breedId and b.sortId = c.sortId";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.log.debug("sql: " + str);
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public void directFirmBreedSave(DirectFirmBreed paramDirectFirmBreed)
  {
    String str = "insert into t_e_directfirmbreed (firmid, breedid) values ('" + paramDirectFirmBreed.getFirmId() + "'," + paramDirectFirmBreed.getBreedId() + ")";
    getJdbcTemplate().update(str);
  }
  
  public void directFirmBreedMod(DirectFirmBreed paramDirectFirmBreed1, DirectFirmBreed paramDirectFirmBreed2)
  {
    String str = "update t_e_directfirmbreed set firmid ='" + paramDirectFirmBreed1.getFirmId() + "', breedid =" + paramDirectFirmBreed1.getBreedId() + " where firmid =" + paramDirectFirmBreed2.getFirmId() + " and breedid = " + paramDirectFirmBreed2.getBreedId();
    getJdbcTemplate().update(str);
  }
  
  public void directFirmBreedDelete(DirectFirmBreed paramDirectFirmBreed)
  {
    String str = "delete t_e_directfirmbreed where firmid = '" + paramDirectFirmBreed.getFirmId() + "' and breedid =" + paramDirectFirmBreed.getBreedId();
    getJdbcTemplate().update(str);
  }
}
