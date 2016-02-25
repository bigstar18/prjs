package gnnt.MEBS.vendue.server.beans.busbeans;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestClone
{
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    HashMap localHashMap1 = new HashMap();
    for (int i = 0; i < 10; i++)
    {
      localObject1 = new PartitionQuotation();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(i + "");
      ((PartitionQuotation)localObject1).setQuotationDetailList(localArrayList);
      ((PartitionQuotation)localObject1).setPartitionId(new Long(i));
      localHashMap1.put(i + "", localObject1);
    }
    HashMap localHashMap2 = (HashMap)((HashMap)localHashMap1).clone();
    Object localObject1 = localHashMap2.entrySet().iterator();
    Object localObject2;
    for (int j = 0; j < localHashMap2.size(); j++)
    {
      localObject2 = (Map.Entry)((Iterator)localObject1).next();
      PartitionQuotation localPartitionQuotation = (PartitionQuotation)((Map.Entry)localObject2).getValue();
      ((Map.Entry)localObject2).setValue((PartitionQuotation)localPartitionQuotation.clone());
    }
    for (j = 0; j < 10; j++)
    {
      localObject2 = (PartitionQuotation)localHashMap2.get(j + "");
      ((PartitionQuotation)localObject2).setPartitionId(new Long(((PartitionQuotation)localObject2).getPartitionId().longValue() + 10L));
      localHashMap2.put(j + "", localObject2);
      ((PartitionQuotation)localObject2).getQuotationDetailList().add(j + "");
    }
    System.out.println(localHashMap1);
    for (j = 0; j < 10; j++)
    {
      localObject2 = (PartitionQuotation)localHashMap1.get(j + "");
      System.out.print(((PartitionQuotation)localObject2).getQuotationDetailList() + "  ");
    }
    System.out.println("");
    System.out.println("=================");
    for (j = 0; j < 10; j++)
    {
      localObject2 = (PartitionQuotation)localHashMap2.get(j + "");
      System.out.print(((PartitionQuotation)localObject2).getQuotationDetailList() + "  ");
    }
  }
}
