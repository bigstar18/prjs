<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="gnnt.MEBS.member.ActiveUser.*" %>
<%@ page import="java.rmi.Naming" %>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
<%
String computerIP = request.getRemoteAddr();
%>
<%!
//�����˺�
public final String CONTACTTITLE = "�����˺�";
//�����˺�����
public final String ACCOUNTNAMETITLE = "�˺�����";
// ����Ϊ�յ��ַ���
public String replaceNull(String s){
  if(s==null||"".equals(s.trim())||"null".equals(s.trim())) return "-";
  else return s;
}

public final int SESSIONINTERVAL = 60000;
public final String marketpwd = "�ʽ�����";
public String delNull(String s) {
	if(s == null) s = "";
	return s;
}


/**
 * �ر����ݿ�����
 * @param rs ResultSet
 * @param state Statement
 * @param conn Connection
 */
public static void closeStatement(ResultSet rs, Statement state)
{
	try
	{
		if (rs != null)
			rs.close();
	} catch (SQLException ex)
	{
		ex.printStackTrace();
	}

	try
	{
		if (state != null)
			state.close();
	} catch (SQLException ex)
	{
		ex.printStackTrace();
	}
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
public boolean isRegstatus(String bankID){//�ж������Ƿ�֧���г���ǩԼ
	if("005".equals(bankID)||"050".equals(bankID)){//ũ��
		return true;
	}
	return false;
}
public boolean delAccountBank(String bankID){//�ж������Ƿ�֧���г��˽�Լ
	if("005".equals(bankID)||"050".equals(bankID)){//ũ��
		return true;
	}
	return false;
}
/**
 * �Ƿ�У������
 * @param inoutmoney �������� (0 ���;1 ����;2 ��ѯ���)
 * @param bankid ���б��
 * @param cardtype ֤������
 * @return boolean (true ������Ҫ��֤����;false ���в���Ҫ����)
 */
public boolean showPasswordBank(String inoutmoney,String bankid,String cardtype){
	if(("0".equals(inoutmoney) || "2".equals(inoutmoney)) || "1".equals(inoutmoney)){
		return true;
	}
	return false;
}
%>
<%
%>
<%!
%>

