package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.MarginDao;
import gnnt.MEBS.commodity.model.Margin;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("marginService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MarginService
  extends BaseService<Margin>
{
  private final transient Log logger = LogFactory.getLog(MarginService.class);
  @Autowired
  @Qualifier("marginDao")
  private MarginDao marginDao;
  
  public BaseDao getDao()
  {
    return this.marginDao;
  }
  
  public Margin get(Margin margin)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", margin.getFirmId());
    qc.addCondition("primary.commodityId", "=", margin.getCommodityId());
    List<Margin> list = this.marginDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      margin = new Margin();
      margin = (Margin)list.get(0);
    }
    else
    {
      margin = null;
    }
    return margin;
  }
  
  public int update(Margin margin)
  {
    int i = delete(margin);
    add(margin);
    return 3;
  }
}
