package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.dao.DirectFirmBreedHisDAO;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class DirectFirmBreedHisDAOJdbc
  extends BaseDAOJdbc
  implements DirectFirmBreedHisDAO
{
  public void directFirmBreedHisInsert(DirectFirmBreed paramDirectFirmBreed)
  {
    String str = "insert into t_h_directfirmbreed(deletedate, firmid, breedid) values (sysdate," + paramDirectFirmBreed.getFirmId() + "," + paramDirectFirmBreed.getBreedId() + ")";
    getJdbcTemplate().update(str);
  }
  
  public List directFirmBreedHisList(QueryConditions paramQueryConditions)
  {
    String str = "select e.breedId ,e.firmId ,b.breedname ,c.sortName ,e.deletedate from t_h_directfirmbreed e,t_a_breed b ,t_a_cmdtysort c where 1=1 and b.breedid=e.breedId and b.sortId = c.sortId";
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
  
  public void dirctFirmBreedHisDelete(DirectFirmBreed paramDirectFirmBreed, Date paramDate)
  {
    String str = "delete t_h_directfirmbreed where firmid = " + paramDirectFirmBreed.getFirmId() + " and breedid =" + paramDirectFirmBreed.getBreedId();
    getJdbcTemplate().update(str);
  }
}
