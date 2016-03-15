package cn.com.agree.eteller.generic.utils;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Sort
{
  public static List getSortList(List list)
  {
    Collections.sort(list);
    return list;
  }
  
  public static List getSortStirng(List list)
  {
    Hanzi2Pinyin hanzi2Pinyin = new Hanzi2Pinyin();
    

    List elist = new ArrayList();
    
    List ulist = new ArrayList();
    
    List plist = new ArrayList();
    for (Iterator i = list.iterator(); i.hasNext();)
    {
      String str = (String)i.next();
      if (str.matches("^[0-9a-z].*")) {
        elist.add(str);
      } else if (str.matches("^[A-Z].*")) {
        ulist.add(str);
      } else {
        plist.add(str);
      }
    }
    getSortList(elist);
    getSortList(ulist);
    
    List pinyinList = hanzi2Pinyin.getPinyinList(plist);
    
    getSortList(pinyinList);
    


    elist.addAll(ulist);
    for (int i = 0; i < pinyinList.size(); i++)
    {
      String[] hanzi = ((String)pinyinList.get(i)).split("_");
      elist.add(hanzi[1]);
    }
    return elist;
  }
  
  public static List getSortStirng(List list, String methodname)
  {
    if ((methodname == null) || (methodname.equals(""))) {
      methodname = "toString";
    }
    if ((list == null) && (list.size() == 0)) {
      return list;
    }
    List templist = new ArrayList();
    Map map = new HashMap();
    try
    {
      Method m = list.get(0).getClass().getMethod(methodname, null);
      Object o = null;
      for (int i = 0; i < list.size(); i++)
      {
        o = m.invoke(list.get(i), null);
        o = o == null ? "" : o;
        templist.add((String)o);
        map.put((String)o + i, list.get(i));
      }
    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchMethodException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    List backlist = new ArrayList();
    templist = getSortStirng(templist);
    for (int i = 0; i < templist.size(); i++)
    {
      Object o = null;
      for (int j = 0; j < templist.size(); j++)
      {
        o = map.get((String)templist.get(i) + j);
        if (o != null)
        {
          backlist.add(o);
          break;
        }
      }
    }
    return backlist;
  }
  
  public static void main(String[] args)
  {
    List list = new ArrayList();
    





























    list.add("09年余量项目单");
    list.add("昆明非税系统");
    list.add("茂名非税");
    list.add("茂名国税");
    list.add("信用卡核心业务改造配合工作");
    list.add("汕头银电联");
    list.add("上海支付密码器");
    list.add("沈阳非税");
    list.add("沈阳分行财政专户资金监管系统");
    list.add("苏州支付");
    list.add("速汇金项目");
    list.add("手机银行及新一代网银项目");
    list.add("维护差错技术支持合同");
    list.add("网银跨行支付清算系统");
    list.add("在");
    list.add("湛江缴费通");
    list.add("湛江缴费通升级改造项目");
    list.add("银期众城钢铁中心接入");
    list.add("银期转账网银接入");
    list.add("银保通-信诚接入");
    list.add("银关通");
    list.add("银基通系统资金在线划拨");
    list.add("内部户改造");
    list.add("其他类型合同");
    list.add("宁波财税库");
    list.add("中山社区三期港华燃气");
    list.add("中山ETS");
    list.add("中山银联");
    list.add("中间业务平台2010年度上半年功能优化项目-SSU");
    list.add("北京代缴费“三通”工程便民支付平台渠道");
    list.add("财政国库集中支付业务系统");
    list.add("大连分行行行通代缴费");
    list.add("大庆地税");
    List list1 = getSortStirng(list);
    for (Iterator i = list1.iterator(); i.hasNext();) {
      System.out.println(i.next());
    }
  }
}
