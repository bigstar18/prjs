<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@ include file="jar.jsp" %>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
  response.setHeader("Pragma","No-cache");
  response.setHeader("Cache-Control","no-cache");
  response.setDateHeader("Expires", 0);
  
	DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
    nf.applyPattern("###0.00");
%>
<%!
	public String handleNullValue(String str){
		String strResult = "";
		if( str == null || "".equals(str.trim())){
			strResult = "";
		}else{
			strResult = str;
		}
		return strResult;
	}
	public boolean chcekNull(String str){
	
		if(str == null){
			return false;
		}else{
			return true;
		}
	}
	public String turnToStr(Object str){
	
		if(str == null){
			return "&nbsp;";
		}else{
			return (String)str;
		}
	}
	public BigDecimal turnToNum(Object str){
	
		if(str == null){
			return new BigDecimal("0.00");
		}else{
			return (BigDecimal)str;
		}
	}

	public List getList(String sql)
	{
	  List list=null;
	  DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
      list=dao.queryBySQL(sql);
	  return list;
	}
%>
<style>
	table.reportTemp
{
	border-top: 1px solid #000000;
	border-left: 1px solid #000000;
	border-right: 1px solid #000000;
		font-family:宋体
}
td.td_reportMd
{
	text-align: center;
	border-bottom: 1px solid #000000;
    border-right: 1px solid #000000;
    font-size:12px;
    	font-family:宋体
	height:24px;
}
td.td_reportMdHead
{
	text-align: center;
	border-bottom: 1px solid #000000;
    border-right: 1px solid #000000;
    font-size:12px;
	height:24px;
	font-weight: bold;
	font-family:宋体
}
td.td_reportMdHead_Right
{
	border-bottom: 1px solid #000000;
    border-right: 1px solid #000000;
    font-size:14px;
	height:24px;
	font-weight: bold;
	font-family:宋体
}
td.td_reportMd1
{
	text-align: right;
	border-bottom: 1px solid #000000;
    border-right: 1px solid #000000;
    font-size:12px;
    	font-family:宋体
	height:24px;
}
td.td_reportRd
{
	text-align: center;
	border-bottom: 1px solid #000000;
    font-size:12px;
    	font-family:宋体
	height:24px;
}
td.td_reportRdHead
{
	text-align: center;
	border-bottom: 1px solid #000000;
    font-size:12px;
	height:24px;
	font-weight: bold;
	font-family:宋体
}
td.td_reportRd1
{
	text-align: right;
	border-bottom: 1px solid #000000;
    font-size:12px;
	font-family:宋体
	height:24px;
}
td.td_reportBd
{
	text-align: center;
	border-right: 1px solid #000000;
    font-size:14px;
	font-family:宋体
	height:24px;
}

.reportHead
{
font-size:22px;
text-align: center;
font-weight: bold;
	font-family:宋体
}
.reportLeft
{
font-size:12px;
text-align: left;
font-family:宋体
}
.reportRight
{
font-size:12px;
text-align: right;
font-family:宋体
}
.tableHead
{
font-size:18px;
text-align: center;
font-weight: bold;
font-family:宋体
}
.sumStr
{
font-size:16px;
text-align: center;
font-weight: bold;
	font-family:宋体
}
.reportHeadForMarketName
{
font-size:20px;
text-align: center;
font-weight: bold;
font-family:宋体
}
</style>