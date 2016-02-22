<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*" %>
<%@ page import="gnnt.trade.bank.util.*" %>
<%@ page import="gnnt.MEBS.member.ActiveUser.*" %>
<%@ page import="gnnt.trade.bank.processorrmi.*" %>
<%@ page import="java.rmi.Naming" %>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
<%!
public final int SESSIONINTERVAL = 60000;
public final String marketpwd = "�ʽ�����";
public String delNull(String s) {
	if(s == null) s = "";
	return s;
}
public void alert(String s,javax.servlet.jsp.JspWriter out){	
	try{
		out.println("<script>alert('"+ s +"');</script>");
	}catch(Exception e){
		System.out.println("function alert() exception : " + e.toString());
	}
}

public void sendRedirect(String url,javax.servlet.jsp.JspWriter out){	
	try{
	    if(url != null && !url.trim().equals("")){
			out.println("<script>window.location='"+ url +"'</script>");
		}
	}catch(Exception e){
		System.out.println(e.toString());
	}
}
/**
 * �����Ƿ�У������
 * @param inoutmoney �������� (0 ���;1 ����;2 ��ѯ���)
 * @param bankid ���б��
 * @param cardtype ֤������
 * @return boolean (true ������Ҫ��֤����;false ���в���Ҫ����)
 */
public boolean showPasswordBank(String inoutmoney,String bankid,String cardtype){
	if(("0".equals(inoutmoney) || "2".equals(inoutmoney)) && "005".equals(bankid)){
		return true;
	}
	return false;
}
%>
<%
	/*String RmiIpAddress = Tool.getConfig("RmiIpAddress");
	String RmiPortNumber = Tool.getConfig("RmiPortNumber");
	String RmiServiceName = Tool.getConfig("RmiServiceName");*/
	ErrorCode errorcode = new ErrorCode();
	errorcode.load();
%>
<%!
StaticMsg staticMsg = new  StaticMsg();
public CapitalProcessorRMI getBankUrl(String bankID){
	CapitalProcessorRMI result = null;
	String procUrl = "";
	if(bankID != null && bankID.trim().length()>0){
		procUrl = staticMsg.getBank(bankID).beleiveProcessor;
	}
	if(procUrl==null || procUrl.trim().length()<=0){
		procUrl = staticMsg.getBankMap().values().iterator().next().beleiveProcessor;
	}
	try{
		result = (CapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
%>
<script>
	/**
	 * �����Ƿ�У������
	 * @param inoutmoney �������� (0 ���;1 ����;2 ��ѯ���)
	 * @param bankid ���б��
	 * @param cardtype ֤������
	 * @return boolean (true ������Ҫ��֤����;false ���в���Ҫ����)
	 */
	function showPasswordBank(inoutmoney,bankid,cardtype){
		if((inoutmoney == 0 || inoutmoney == 2) && bankid == "005"){
			return true;
		}
		return false;
	}
</script>