<%@ page contentType="text/html;charset=GBK" import="java.util.regex.*,java.io.*,gnnt.MEBS.member.ActiveUser.*,org.dom4j.*" %><%!
//ģ����
public final String MODELID = "5";
//session��ʱʱ�䣬��λ������
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

//�õ�xml��ָ�����ƽ���ֵ
public String getXmlParament(String xml,String param) throws Exception
{
 xml.replaceAll("\n","");
 if(xml==null)
         return "";
 String regBuffer="<"+param+">(.*)"+"</"+param+">";
    Pattern p = Pattern.compile(regBuffer); 
 //��Pattern���matcher()��������һ��Matcher���� 
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