package gnnt.MEBS.xhzc.manage;

import gnnt.MEBS.xhzc.manage.DAO.ManageDAO;
import gnnt.MEBS.xhzc.manage.DAO.ManageDAOFactory;

public class ReadCommodity extends Thread
{
  public void run()
  {
    CommodityCodeList ccl = new CommodityCodeList();

    if (!ccl.isNull())
    {
      ccl.clearValue();
    }

    try
    {
      ManageDAO manage = ManageDAOFactory.getDAO();

      String[] vals1 = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=7 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals1 = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals1[i] = cpavs[i].getID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      String[] vals2 = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=8 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals2 = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals2[i] = cpavs[i].getID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      String[] vals3 = null;
      String[] vals3_rel = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=9 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals3 = new String[cpavs.length];
        vals3_rel = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals3[i] = cpavs[i].getID();
          vals3_rel[i] = cpavs[i].getRelatedID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      String[] vals4 = null;
      String[] vals4_rel = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=10 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals4 = new String[cpavs.length];
        vals4_rel = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals4[i] = cpavs[i].getID();
          vals4_rel[i] = cpavs[i].getRelatedID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      String[] vals5 = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=11 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals5 = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals5[i] = cpavs[i].getID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      String[] vals6 = null;
      try
      {
        QueryValue qv = new QueryValue();
        qv.pageSize = 10000;
        qv.filter = " and classid=12 ";
        CommodityParameterValue[] cpavs = manage.getCommodityParameterList(qv);
        vals6 = new String[cpavs.length];
        for (int i = 0; i < cpavs.length; i++)
        {
          vals6[i] = cpavs[i].getID();
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }

      for (int i1 = 0; i1 < vals1.length; i1++)
      {
        String s1 = vals1[i1];
        for (int i2 = 0; i2 < vals2.length; i2++)
        {
          String s2 = vals2[i2];
          for (int i3 = 0; i3 < vals3.length; i3++)
          {
            String s3 = "";
            if (vals3_rel[i3].indexOf(s2) > -1)
            {
              s3 = vals3[i3];
            }
            for (int i4 = 0; i4 < vals4.length; i4++)
            {
              String s4 = "";
              if (vals4_rel[i4].indexOf(s2) > -1) {
                s4 = vals4[i4];
              }
              for (int i5 = 0; i5 < vals5.length; i5++)
              {
                String s5 = vals5[i5];
                for (int i6 = 0; i6 < vals6.length; i6++)
                {
                  String s6 = vals6[i6];
                  String code = s1 + s2 + s3 + s4 + s5 + s6;
                  if ((code != null) && (code.length() == 11))
                  {
                    ccl.setValue(code, code);
                  }
                }
              }
            }
          }

        }

      }

    }
    catch (Exception me)
    {
      me.printStackTrace();
    }

    ccl.setFlag(1);
  }
}