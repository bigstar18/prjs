package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_userService")
  private UserService userService;
  
  public void testAdd()
  {
    User localUser1 = new User();
    localUser1.setUserId("001");
    localUser1.setName("001管理员");
    localUser1.setManage_id("0207仓库");
    localUser1.setPassword("111");
    this.userService.addUser(localUser1);
    User localUser2 = this.userService.getUser(localUser1.getUserId(), localUser1.getManage_id());
    String str = "select * from w_users where userid='" + localUser1.getUserId() + "' and manage_id='" + localUser1.getManage_id() + "'";
    Map localMap = this.jdbcTemplate.queryForMap(str);
    assertEquals(localUser1.getName(), localUser2.getName());
    assertEquals(localUser1.getManage_id(), localMap.get("MANAGE_ID"));
  }
}
