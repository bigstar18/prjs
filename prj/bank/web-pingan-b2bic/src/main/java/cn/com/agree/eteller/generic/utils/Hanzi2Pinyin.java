package cn.com.agree.eteller.generic.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Hanzi2Pinyin
{
  public List getPinyinList(List list)
  {
    List pinyinList = new ArrayList();
    for (Iterator i = list.iterator(); i.hasNext();)
    {
      String str = (String)i.next();
      try
      {
        String pinyin = getPinYin(str);
        pinyinList.add(pinyin);
      }
      catch (BadHanyuPinyinOutputFormatCombination e)
      {
        e.printStackTrace();
      }
    }
    return pinyinList;
  }
  
  private String getPinYin(String zhongwen)
    throws BadHanyuPinyinOutputFormatCombination
  {
    String zhongWenPinYin = "";
    char[] chars = zhongwen.toCharArray();
    for (int i = 0; i < chars.length; i++)
    {
      String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], 
        getDefaultOutputFormat());
      if (pinYin != null) {
        zhongWenPinYin = zhongWenPinYin + pinYin[0];
      } else {
        zhongWenPinYin = zhongWenPinYin + chars[i];
      }
    }
    return zhongWenPinYin + "_" + zhongwen;
  }
  
  private HanyuPinyinOutputFormat getDefaultOutputFormat()
  {
    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
    
    return format;
  }
}
