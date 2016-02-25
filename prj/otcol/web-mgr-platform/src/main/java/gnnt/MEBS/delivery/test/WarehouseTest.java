package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.services.WarehouseService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class WarehouseTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_warehouseService")
  private WarehouseService warehouseService;
  
  public void testAdd()
  {
    Warehouse localWarehouse1 = new Warehouse();
    localWarehouse1.setId("4312");
    localWarehouse1.setName("粮仓");
    localWarehouse1.setFullName("北京粮库");
    localWarehouse1.setLinkman("李维");
    localWarehouse1.setBail(100.0D);
    localWarehouse1.setMax_Capacity(10000.0D);
    localWarehouse1.setUsed_Capacity(900.0D);
    User localUser = new User();
    localUser.setUserId("admin_" + localWarehouse1.getId());
    localUser.setName(localWarehouse1.getName() + "超级管理员");
    localUser.setPassword("111111");
    localUser.setManage_id(localWarehouse1.getId());
    localUser.setRoleStatus(0);
    localUser.setPopedom("1");
    this.warehouseService.addWarehouse(localWarehouse1, localUser, null);
    Warehouse localWarehouse2 = this.warehouseService.getWarehouse(localWarehouse1.getId());
    String str = "select * from w_warehouse where id='" + localWarehouse1.getId() + "'";
    Map localMap = this.jdbcTemplate.queryForMap(str);
    assertEquals(localWarehouse1.getName(), localWarehouse2.getName());
  }
}
