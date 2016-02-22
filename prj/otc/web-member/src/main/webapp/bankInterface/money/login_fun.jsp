<%@ page contentType="text/html;charset=GBK" import="java.util.regex.*,java.io.*,gnnt.MEBS.member.ActiveUser.*,org.dom4j.*" %><%!
//模块编号
public final String MODELID = "5";
//session超时时间，单位：毫秒
public final int SESSIONINTERVAL = 60000;

public Document parserStrtoDocument(String str) throws DocumentException{
	  Document document = null;
	  try
      {
		  document=DocumentHelper.parseText(str);
	  }
	  catch(Exception e)
	  {
	  }	  
	  return document;
 }  
 
 public String getXmlType(String type,Document document)
 {
	 String result = null;
	 try
	 {
		 Element root = document.getRootElement();
		 Element node = root.element("REQ");
		 result = node.attributeValue(type);
	 }
	 catch(Exception e)
	 {
	 }	 
	 return result;
 }

//得到xml中指定名称结点的值
public String getXmlParament(String xml,String param) throws Exception
{
 xml.replaceAll("\n","");
 if(xml==null)
         return "";
 String regBuffer="<"+param+">(.*)"+"</"+param+">";
    Pattern p = Pattern.compile(regBuffer); 
 //用Pattern类的matcher()方法生成一个Matcher对象 
 Matcher m = p.matcher(xml); 
 boolean result = m.find();
 String ret="";
 if(result) { 
  ret=m.group();
  ret=ret.replaceAll("<"+param+">","");
  ret=ret.replaceAll("</"+param+">","");
  System.out.println(m.group()); 
 }
    return ret;
}

public long toLong(String s)
{
	long r = -1;
	try
	{
		r = Long.parseLong(s);
	}
	catch(Exception e)
	{
	}
	return r;
}

%>