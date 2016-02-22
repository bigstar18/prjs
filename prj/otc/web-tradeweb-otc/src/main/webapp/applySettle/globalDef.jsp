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
//交易账号
public final String CONTACTTITLE = "交易账号";
//银行账号名称
public final String ACCOUNTNAMETITLE = "账号名称";
// 设置为空的字符串
public String replaceNull(String s){
  if(s==null||"".equals(s.trim())||"null".equals(s.trim())) return "-";
  else return s;
}

public final int SESSIONINTERVAL = 60000;
public final String marketpwd = "资金密码";
public String delNull(String s) {
	if(s == null) s = "";
	return s;
}


/**
 * 关闭数据库连接
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
public boolean isRegstatus(String bankID){//判断银行是否支持市场端签约
	if("005".equals(bankID)||"050".equals(bankID)){//农行
		return true;
	}
	return false;
}
public boolean delAccountBank(String bankID){//判断银行是否支持市场端解约
	if("005".equals(bankID)||"050".equals(bankID)){//农行
		return true;
	}
	return false;
}
/**
 * 是否校验密码
 * @param inoutmoney 交易类型 (0 入金;1 出金;2 查询余额)
 * @param bankid 银行编号
 * @param cardtype 证件类型
 * @return boolean (true 银行需要验证密码;false 银行不需要密码)
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

