package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.member.broker.model.BrokerGroup;
import java.io.PrintStream;

public class BrokerGroupDao
  extends DaoHelperImpl
{
  public void insertBrokerGroup(BrokerGroup paramBrokerGroup)
  {
    System.out.println("brokerGroup.getGroupName():" + paramBrokerGroup.getGroupName());
    System.out.println("brokerGroup.getBrokerId():" + paramBrokerGroup.getBrokerId());
    String str = "insert into m_b_brokergroup  (groupid, groupname,brokerid) values(SEQ_M_BROKERGROUP.Nextval, ?,?)";
    Object[] arrayOfObject = { paramBrokerGroup.getGroupName(), paramBrokerGroup.getBrokerId() };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
