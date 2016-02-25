package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.server.beans.busbeans.CountTime;
import gnnt.MEBS.vendue.server.beans.busbeans.PartitionHighBuffer;
import gnnt.MEBS.vendue.server.beans.busbeans.PartitionQuotation;
import gnnt.MEBS.vendue.server.beans.busbeans.ShareObjectOfQuotation;
import gnnt.MEBS.vendue.server.beans.dtobeans.QuotationValue;
import gnnt.MEBS.vendue.server.dao.QuotationValueDao;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;

public class QuotationCacheService
{
  private static final String CacheXmlConstant_Q = "<Q>";
  private static final String CacheXmlConstant_Q_End = "</Q>";
  private static final String CacheXmlConstant_C = "<C>";
  private static final String CacheXmlConstant_C_End = "</C>";
  private static final String CacheXmlConstant_P = "<P>";
  private static final String CacheXmlConstant_P_End = "</P>";
  private static final String CacheXmlConstant_A = "<A>";
  private static final String CacheXmlConstant_A_End = "</A>";
  private static final String CacheXmlConstant_CD = "<CD>";
  private static final String CacheXmlConstant_CD_End = "</CD>";
  private static final String CacheXmlConstant_ID = "<ID>";
  private static final String CacheXmlConstant_ID_End = "</ID>";
  private static final String CacheXmlConstant_N = "<N>";
  private static final String CacheXmlConstant_N_End = "</N>";
  private static final String CacheXmlConstant_S = "<S>";
  private static final String CacheXmlConstant_S_End = "</S>";
  private static final String CacheXmlConstant_U = "<U>";
  private static final String CacheXmlConstant_U_End = "</U>";
  private static final String CacheXmlConstant_T1 = "<T1>";
  private static final String CacheXmlConstant_T1_End = "</T1>";
  private static final String CacheXmlConstant_T2 = "<T2>";
  private static final String CacheXmlConstant_T2_End = "</T2>";
  private static final String CacheXmlConstant_R = "<R>";
  private static final String CacheXmlConstant_R_End = "</R>";
  private static final String CacheXmlConstant_XmlFileHead = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
  private static final String CacheXmlNullString = "";
  private static final DateFormat dfOfPartionStarttimeEndtime = DateFormat.getTimeInstance(2, Locale.CHINA);
  private static Logger logger = Logger.getLogger("QuotationCachelog");
  private StringBuffer sbForLogger = new StringBuffer();
  private Long lastSeqId;
  private static final Long partitionHighBufferNumber = new Long(0L);
  
  public String getLastXML(long paramLong1, long paramLong2)
  {
    Long localLong1 = new Long(paramLong1);
    Map localMap1 = ShareObjectOfQuotation.getSingleInstance().getSharedData();
    if (!localMap1.containsKey(localLong1)) {
      return ShareObjectOfQuotation.getSingleInstance().getXmlForNullPartition();
    }
    PartitionQuotation localPartitionQuotation = (PartitionQuotation)localMap1.get(localLong1);
    Long localLong2 = new Long(paramLong2);
    Map localMap2 = localPartitionQuotation.getPartitionHighBuffer().getHighBufferQuotationMap();
    List localList = localPartitionQuotation.getPartitionHighBuffer().getHighBufferQuotationIdList();
    if ((localList != null) && (localList.size() > 0))
    {
      if (localMap2.containsKey(localLong2)) {
        return (String)localMap2.get(localLong2);
      }
      if (localLong2.compareTo((Long)localList.get(0)) < 0) {
        return buildReturnXml(localPartitionQuotation, paramLong2);
      }
      for (int i = localList.size() - 1; i >= 0; i--) {
        if (((Long)localList.get(i)).compareTo(localLong2) < 0) {
          return (String)localMap2.get((Long)localList.get(i));
        }
      }
    }
    return buildReturnXml(localPartitionQuotation, paramLong2);
  }
  
  public void buildSharedData()
  {
    this.sbForLogger = new StringBuffer();
    this.sbForLogger.append("\n*************本次内存行情维护过程 开始时间:" + new Date() + "*************");
    this.sbForLogger.append("\n访问数据库获取数据, 上次最大流水号: " + (this.lastSeqId == null ? "NULL" : this.lastSeqId.toString()));
    long l1 = System.currentTimeMillis();
    try
    {
      QuotationValueDao localQuotationValueDao = new QuotationValueDao();
      ShareObjectOfQuotation localShareObjectOfQuotation = ShareObjectOfQuotation.getSingleInstance();
      localQuotationValueDao.fetchQuotationValue(this.lastSeqId);
      Long localLong = localQuotationValueDao.getNewestSequenceId();
      if (localLong == null)
      {
        this.sbForLogger.append("\n本次访问数据库没有获得最新的行情!");
      }
      else
      {
        this.lastSeqId = localLong;
        this.sbForLogger.append("\n本次访问数据库获得了最新的行情! 得到最大的流水号:" + localLong);
      }
      HashMap localHashMap = null;
      localHashMap = (HashMap)((HashMap)localShareObjectOfQuotation.getSharedData()).clone();
      Iterator localIterator = localHashMap.entrySet().iterator();
      for (int i = 0; i < localHashMap.size(); i++)
      {
        localObject1 = (Map.Entry)localIterator.next();
        PartitionQuotation localPartitionQuotation = (PartitionQuotation)((Map.Entry)localObject1).getValue();
        ((Map.Entry)localObject1).setValue((PartitionQuotation)localPartitionQuotation.clone());
      }
      List localList = localQuotationValueDao.getPartitionValueList();
      processModifiedQuotationValue(localList, localHashMap);
      Object localObject1 = buildXmlForNullPartition(null);
      localShareObjectOfQuotation.setSharedData(localHashMap, (String)localObject1);
    }
    catch (Exception localException)
    {
      localException = localException;
      localException.printStackTrace();
      debugPrintln(localException.toString());
    }
    finally {}
    long l2 = System.currentTimeMillis();
    this.sbForLogger.append("\n本次行情内存维护过程结束！共花费时间:" + (l2 - l1) + "毫秒！");
    this.sbForLogger.append("\n**************************************\n");
    logger.debug(this.sbForLogger.toString());
  }
  
  private void processModifiedQuotationValue(List paramList, Map paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    ArrayList localArrayList = new ArrayList();
    PartitionQuotation localPartitionQuotation2;
    for (int i = 0; i < paramMap.size(); i++)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Long localLong1 = (Long)localEntry.getKey();
      int m = 0;
      for (int n = 0; n < paramList.size(); n++)
      {
        localPartitionQuotation2 = (PartitionQuotation)paramList.get(n);
        if (localPartitionQuotation2.getPartitionId().equals(localLong1))
        {
          m = 1;
          break;
        }
      }
      if (m == 0) {
        localArrayList.add(localLong1);
      }
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int j = 0;
    for (int k = 0; k < localArrayList.size(); k++)
    {
      paramMap.remove(localArrayList.get(k));
      j = 1;
      localStringBuffer.append(localArrayList.get(k) + "   ");
    }
    if (j != 0)
    {
      this.sbForLogger.append("\n以下板块在curSysStatus表里消失, 需要从内存中删除:");
      this.sbForLogger.append(localStringBuffer);
    }
    for (k = 0; k < paramList.size(); k++)
    {
      PartitionQuotation localPartitionQuotation1 = (PartitionQuotation)paramList.get(k);
      makeXmlStringBufferOfQuotationInPartition(localPartitionQuotation1);
      Long localLong2 = localPartitionQuotation1.getPartitionId();
      this.sbForLogger.append("\n====维护板块ID:" + localLong2 + "=====");
      localPartitionQuotation2 = (PartitionQuotation)paramMap.get(localLong2);
      PartitionQuotation localPartitionQuotation3 = null;
      if (localPartitionQuotation2 == null)
      {
        this.sbForLogger.append("\n这个板块在旧内存中没有发现, 直接把最新获得的该板块行情添加到内存!");
        localPartitionQuotation3 = localPartitionQuotation1;
        clearPartitionQuotation(localPartitionQuotation3);
      }
      else
      {
        this.sbForLogger.append("\n这个板块的行情数据需要和内存中的行情合并!");
        localPartitionQuotation3 = mergePartitionQuotationModifiedAndOldMemory(localPartitionQuotation2, localPartitionQuotation1);
      }
      countCurrentCommodityNumberInCurrentSection(localPartitionQuotation3);
      localPartitionQuotation3.setPartOfPartitionQuotationXML(buildPartitionPartXmlString(localPartitionQuotation3));
      makeSequanceOfPartitionQuotation(localPartitionQuotation3);
      makeHighBufferOfPartitionQuotation(localPartitionQuotation3);
      paramMap.put(localLong2, localPartitionQuotation3);
      this.sbForLogger.append("\n板块维护总计:");
      this.sbForLogger.append("\n交易节:" + localPartitionQuotation3.getSection());
      this.sbForLogger.append("\n交易节开始时间:" + localPartitionQuotation3.getStartTime());
      this.sbForLogger.append("\n交易节结束时间:" + localPartitionQuotation3.getEndTime());
      this.sbForLogger.append("\n状态:" + localPartitionQuotation3.getStatus());
      this.sbForLogger.append("\n行情数量:" + localPartitionQuotation3.getCurNumOfCommodity());
      this.sbForLogger.append("\n==============\n");
    }
  }
  
  private void makeHighBufferOfPartitionQuotation(PartitionQuotation paramPartitionQuotation)
  {
    PartitionHighBuffer localPartitionHighBuffer = new PartitionHighBuffer();
    List localList = paramPartitionQuotation.getQuotationDetailList();
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int i = Math.min(partitionHighBufferNumber.intValue(), localList.size());
    for (int j = localList.size() - i; j < localList.size(); j++)
    {
      Long localLong = ((QuotationValue)localList.get(j)).getId();
      localArrayList.add(localLong);
      String str = buildReturnXml(paramPartitionQuotation, localLong.longValue());
      localHashMap.put(localLong, str);
    }
    localPartitionHighBuffer.setHighBufferQuotationIdList(localArrayList);
    localPartitionHighBuffer.setHighBufferQuotationMap(localHashMap);
    paramPartitionQuotation.setPartitionHighBuffer(localPartitionHighBuffer);
  }
  
  private void makeSequanceOfPartitionQuotation(PartitionQuotation paramPartitionQuotation)
  {
    List localList = paramPartitionQuotation.getQuotationDetailList();
    Collections.sort(localList);
  }
  
  private void clearPartitionQuotation(PartitionQuotation paramPartitionQuotation)
  {
    if (paramPartitionQuotation.getStatus().longValue() == 5L)
    {
      paramPartitionQuotation.getQuotationDetailList().clear();
      this.sbForLogger.append("\n该板块为闭市状态, 清掉得到的行情数据");
    }
    List localList = paramPartitionQuotation.getQuotationDetailList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = localList.size() - 1; j >= 0; j--)
    {
      QuotationValue localQuotationValue = (QuotationValue)localList.get(j);
      if ((!localQuotationValue.getSection().equals(paramPartitionQuotation.getSection())) || (localQuotationValue.getPrice().doubleValue() < 0.0D))
      {
        localList.remove(j);
        i = 1;
        localStringBuffer.append(localQuotationValue.getCode() + "(" + localQuotationValue.getId() + ")  ");
      }
    }
    if (i != 0)
    {
      this.sbForLogger.append("\n板块中这些行情的交易节和板块不匹配或者行情价格为负,需要被删除. 行情商品编码和流水:\n");
      this.sbForLogger.append(localStringBuffer);
    }
  }
  
  private PartitionQuotation mergePartitionQuotationModifiedAndOldMemory(PartitionQuotation paramPartitionQuotation1, PartitionQuotation paramPartitionQuotation2)
  {
    List localList1 = paramPartitionQuotation2.getQuotationDetailList();
    List localList2 = paramPartitionQuotation1.getQuotationDetailList();
    if (paramPartitionQuotation2.getStatus().longValue() == 5L)
    {
      localList1.clear();
      localList2.clear();
      this.sbForLogger.append("\n板块状态为闭市(5), 把行情清掉.");
    }
    if ((!paramPartitionQuotation2.getStatus().equals(paramPartitionQuotation1.getStatus())) || (!paramPartitionQuotation2.getSection().equals(paramPartitionQuotation1.getSection())))
    {
      localList2.clear();
      this.lastSeqId = null;
      this.sbForLogger.append("\n板块当前状态为" + paramPartitionQuotation2.getStatus() + ", 内存中该板块的状态为" + paramPartitionQuotation1.getStatus());
      this.sbForLogger.append("\n板块当前交易节为" + paramPartitionQuotation2.getSection() + ", 内存中该板块的交易节为" + paramPartitionQuotation1.getSection());
      this.sbForLogger.append("\n结论:板块状态或者交易节发生了改变, 清除掉内存中的行情数据, 准备插入最新的行情数据.");
      this.sbForLogger.append("\n为了防止数据出错，准备下次在读数据库时读出全部的值, 那么把最大流水号设为NULL");
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    QuotationValue localQuotationValue1;
    for (int j = localList2.size() - 1; j >= 0; j--)
    {
      localQuotationValue1 = (QuotationValue)localList2.get(j);
      if (!localQuotationValue1.getSection().equals(paramPartitionQuotation2.getSection()))
      {
        localList2.remove(j);
        i = 1;
        localStringBuffer.append(localQuotationValue1.getCode() + "(" + localQuotationValue1.getId() + ")  ");
      }
    }
    if (i != 0)
    {
      this.sbForLogger.append("\n旧内存中还有以下行情不是本交易节的数据,需要从内存中清除, 行情:");
      this.sbForLogger.append(localStringBuffer);
    }
    for (j = 0; j < localList1.size(); j++)
    {
      localQuotationValue1 = (QuotationValue)localList1.get(j);
      this.sbForLogger.append("\n行情有新的流水, 行情流水ID:" + localQuotationValue1.getCode() + "(" + localQuotationValue1.getId() + ")  ");
      for (int k = localList2.size() - 1; k >= 0; k--)
      {
        QuotationValue localQuotationValue2 = (QuotationValue)localList2.get(k);
        if ((localQuotationValue2.getCode().equals(localQuotationValue1.getCode())) && (localQuotationValue2.getSection().equals(localQuotationValue1.getSection())))
        {
          localList2.remove(k);
          this.sbForLogger.append("从内存中删除旧的   ");
          break;
        }
      }
      if ((localQuotationValue1.getPrice().doubleValue() < 0.0D) || (!localQuotationValue1.getSection().equals(paramPartitionQuotation2.getSection())))
      {
        this.sbForLogger.append("行情的交易节不匹配或者价格为负, 删除  ");
      }
      else
      {
        this.sbForLogger.append("行情添加到内存  ");
        localList2.add(localQuotationValue1);
      }
    }
    paramPartitionQuotation1.setEndTime(paramPartitionQuotation2.getEndTime());
    paramPartitionQuotation1.setPartitionId(paramPartitionQuotation2.getPartitionId());
    paramPartitionQuotation1.setSection(paramPartitionQuotation2.getSection());
    paramPartitionQuotation1.setStartTime(paramPartitionQuotation2.getStartTime());
    paramPartitionQuotation1.setStatus(paramPartitionQuotation2.getStatus());
    return paramPartitionQuotation1;
  }
  
  private void countCurrentCommodityNumberInCurrentSection(PartitionQuotation paramPartitionQuotation)
  {
    long l = 0L;
    Long localLong = paramPartitionQuotation.getSection();
    List localList = paramPartitionQuotation.getQuotationDetailList();
    for (int i = 0; i < localList.size(); i++)
    {
      QuotationValue localQuotationValue = (QuotationValue)localList.get(i);
      if (localQuotationValue.getSection().equals(localLong)) {
        l += 1L;
      }
    }
    paramPartitionQuotation.setCurNumOfCommodity(new Long(l));
  }
  
  private void makeXmlStringBufferOfQuotationInPartition(PartitionQuotation paramPartitionQuotation)
  {
    List localList = paramPartitionQuotation.getQuotationDetailList();
    for (int i = 0; i < localList.size(); i++)
    {
      QuotationValue localQuotationValue = (QuotationValue)localList.get(i);
      localQuotationValue.setXmlStringBuffer1(buildQuotationValueXmlString_1(localQuotationValue));
      localQuotationValue.setXmlStringBuffer2(buildQuotationValueXmlString_2(localQuotationValue));
    }
  }
  
  private StringBuffer buildQuotationValueXmlString_1(QuotationValue paramQuotationValue)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<Q>").append("<C>").append(paramQuotationValue.getCode()).append("</C>").append("<P>").append(paramQuotationValue.getPrice()).append("</P>").append("<A>").append(paramQuotationValue.getValidAmount()).append("</A>").append("<CD>");
    return localStringBuffer;
  }
  
  private StringBuffer buildQuotationValueXmlString_2(QuotationValue paramQuotationValue)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("</CD>").append("<ID>").append(paramQuotationValue.getId()).append("</ID>").append("</Q>");
    return localStringBuffer;
  }
  
  private StringBuffer buildQuotationValueXmlString(QuotationValue paramQuotationValue)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<Q>").append("<C>").append(paramQuotationValue.getCode()).append("</C>").append("<P>").append(paramQuotationValue.getPrice()).append("</P>").append("<A>").append(paramQuotationValue.getValidAmount()).append("</A>").append("<CD>");
    CountTime localCountTime = paramQuotationValue.getCountTime();
    if (localCountTime != null)
    {
      long l = localCountTime.getRestSecond();
      if (l < 0L) {
        l = 0L;
      }
      localStringBuffer.append(l);
    }
    else
    {
      localStringBuffer.append(-1);
    }
    localStringBuffer.append("</CD>").append("<ID>").append(paramQuotationValue.getId()).append("</ID>").append("</Q>");
    return localStringBuffer;
  }
  
  private StringBuffer buildQuotationValueXmlStringUseBuffer(StringBuffer paramStringBuffer, QuotationValue paramQuotationValue)
  {
    paramStringBuffer.append(paramQuotationValue.getXmlStringBuffer1());
    CountTime localCountTime = paramQuotationValue.getCountTime();
    if (localCountTime != null)
    {
      long l = localCountTime.getRestSecond();
      if (l < 0L) {
        l = 0L;
      }
      paramStringBuffer.append(l);
    }
    else
    {
      paramStringBuffer.append(-1);
    }
    paramStringBuffer.append(paramQuotationValue.getXmlStringBuffer2());
    return paramStringBuffer;
  }
  
  private StringBuffer buildPartitionPartXmlString(PartitionQuotation paramPartitionQuotation)
  {
    Timestamp localTimestamp1 = paramPartitionQuotation.getStartTime();
    Timestamp localTimestamp2 = paramPartitionQuotation.getEndTime();
    String str1 = null;
    String str2 = null;
    if (localTimestamp1 == null) {
      str1 = "";
    } else {
      str1 = dfOfPartionStarttimeEndtime.format(localTimestamp1);
    }
    if (localTimestamp2 == null) {
      str2 = "";
    } else {
      str2 = dfOfPartionStarttimeEndtime.format(localTimestamp2);
    }
    long l = 0L;
    l = paramPartitionQuotation.getCurNumOfCommodity().longValue();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<N>").append(l).append("</N>").append("<S>").append(paramPartitionQuotation.getStatus()).append("</S>").append("<U>").append(paramPartitionQuotation.getSection()).append("</U>").append("<T1>").append(str1).append("</T1>").append("<T2>").append(str2).append("</T2>");
    return localStringBuffer;
  }
  
  private String buildXmlForNullPartition(Long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = null;
    if (paramLong == null) {
      str = "";
    } else {
      str = paramLong.toString();
    }
    localStringBuffer.append("<R>").append("<N>").append("0").append("</N>").append("<S>").append("</S>").append("<U>").append(str).append("</U>").append("<T1>").append("</T1>").append("<T2>").append("</T2>").append("</R>");
    return localStringBuffer.toString();
  }
  
  private String buildReturnXml(PartitionQuotation paramPartitionQuotation, long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>").append("<R>");
    List localList = paramPartitionQuotation.getQuotationDetailList();
    for (int i = localList.size() - 1; i >= 0; i--)
    {
      QuotationValue localQuotationValue = (QuotationValue)localList.get(i);
      if (localQuotationValue.getSection().equals(paramPartitionQuotation.getSection()))
      {
        if (localQuotationValue.getId().longValue() <= paramLong) {
          break;
        }
        localStringBuffer = buildQuotationValueXmlStringUseBuffer(localStringBuffer, localQuotationValue);
      }
    }
    localStringBuffer.append(paramPartitionQuotation.getPartOfPartitionQuotationXML());
    localStringBuffer.append("</R>");
    return localStringBuffer.toString();
  }
  
  public void printAllMemoryXML()
  {
    Map localMap = ShareObjectOfQuotation.getSingleInstance().getSharedData();
    debugPrintln("==========开始打印内存======");
    Iterator localIterator = localMap.entrySet().iterator();
    Map.Entry localEntry;
    Long localLong;
    for (int i = 0; i < localMap.size(); i++)
    {
      localEntry = (Map.Entry)localIterator.next();
      localLong = (Long)localEntry.getKey();
      debugPrintln("板块：" + localLong + "===========");
      debugPrintln(getLastXML(localLong.longValue(), -111L));
    }
    debugPrintln("测试传入一个不存在的板块：2323===========");
    debugPrintln(getLastXML(-232L, -111L));
    debugPrintln("测试传入流水号：3===========");
    localIterator = localMap.entrySet().iterator();
    for (i = 0; i < localMap.size(); i++)
    {
      localEntry = (Map.Entry)localIterator.next();
      localLong = (Long)localEntry.getKey();
      debugPrintln("板块：" + localLong + "===========");
      debugPrintln(getLastXML(localLong.longValue(), 3L));
    }
  }
  
  public static void debugPrintln(Object paramObject) {}
}
