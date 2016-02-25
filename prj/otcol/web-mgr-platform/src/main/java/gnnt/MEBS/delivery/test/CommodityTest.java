package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.CommodityExpansion;
import gnnt.MEBS.delivery.services.CommodityService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommodityTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_commodityService")
  private CommodityService commodityService;
  
  public void testAdd()
  {
    Commodity localCommodity1 = new Commodity();
    localCommodity1.setAbility(999);
    localCommodity1.setCountType("sss");
    localCommodity1.setCreatetime(new Date());
    localCommodity1.setId("999");
    localCommodity1.setMinJS(2.2D);
    localCommodity1.setModifyDate(new Date());
    localCommodity1.setName("sds");
    localCommodity1.setOperationId("111");
    localCommodity1.setYlong(222.22D);
    localCommodity1.setYshort(22.0D);
    CommodityExpansion localCommodityExpansion = new CommodityExpansion();
    localCommodityExpansion.setCommodityId("111");
    localCommodityExpansion.setKind("22");
    localCommodityExpansion.setName("sdf");
    localCommodityExpansion.setNo(11);
    ArrayList localArrayList = new ArrayList();
    localCommodity1.addQualityList(localArrayList);
    localCommodity1.addGradeList(localArrayList);
    localCommodity1.addOriginList(localArrayList);
    localCommodity1.addSortList(localArrayList);
    Commodity localCommodity2 = this.commodityService.getCommodityById(localCommodity1.getId());
    String str = "select * from w_Commodity where id=" + localCommodity1.getId();
    Map localMap = this.jdbcTemplate.queryForMap(str);
    assertEquals(localCommodity1.getName(), localCommodity2.getName());
  }
}
