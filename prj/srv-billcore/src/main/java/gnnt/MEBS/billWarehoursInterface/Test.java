package gnnt.MEBS.billWarehoursInterface;

import gnnt.MEBS.billWarehoursInterface.VO.PropertyMap;
import gnnt.MEBS.billWarehoursInterface.VO.PropertyVO;
import gnnt.MEBS.billWarehoursInterface.VO.RegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.client.impl.SuccessClient;
import gnnt.MEBS.billWarehoursInterface.client.impl.WarehoursClient;
import gnnt.MEBS.billWarehoursInterface.util.GnntBeanFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.support.AbstractApplicationContext;

public class Test
{
  public static void main(String[] args) {}
  
  public static void testRegisterWareinfo()
  {
    RegisterRequestVO r = new RegisterRequestVO();
    r.setBreedName("ABS再生料");
    r.setCategoryName("再生塑料");
    r.setUnit("吨");
    r.setFirmID("555");
    r.setFirmName("555");
    PropertyVO property = new PropertyVO();
    List<PropertyMap> propertyList = new ArrayList();
    PropertyMap map = new PropertyMap();
    map.setName("无");
    map.setValue("有");
    propertyList.add(map);
    PropertyMap map2 = new PropertyMap();
    map2.setName("形态");
    map2.setValue("颗粒");
    propertyList.add(map2);
    PropertyMap map3 = new PropertyMap();
    map3.setName("级别");
    map3.setValue("二级");
    propertyList.add(map3);
    property.setPropertyList(propertyList);
    r.setProperty(property);
    r.setWareHousePassword("111111");
    r.setQuantity(100.0D);
    r.setStockID("987654321");
    r.setWareHouseID("999");
    WarehoursClient warehoursClient = (WarehoursClient)
      GnntBeanFactory.getBean("warehoursClient");
    warehoursClient.communicate(r);
    ((AbstractApplicationContext)GnntBeanFactory.getBeanFactory()).close();
  }
  
  public static void testUnRegisterWareinfo()
  {
    UnRegisterRequestVO r = new UnRegisterRequestVO();
    r.setStockID("nijw_123456");
    r.setQuantity(13.0D);
    r.setUserID("nijw_UserID");
    r.setPwd("nijw_pwd");
    r.setUserName("nijw_测试交易商1");
    SuccessClient successClient = (SuccessClient)GnntBeanFactory.getBean("successClient");
    successClient.communicate(r);
    ((AbstractApplicationContext)GnntBeanFactory.getBeanFactory()).close();
  }
}
