<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.math.*"%>
<%@ page import="gnnt.MEBS.common.security.util.Configuration"%>
<%@ page import="gnnt.MEBS.account.action.Condition"%>
<%@ page import="gnnt.MEBS.base.query.hibernate.*"%>
<%@ include file="/common/public/basecommon.jsp"%>

<%!
	//默认每页数据条数
	public static final int PAGESIZE = 10;
	
	//查询配置文件中的市场名称
	public String getMarketName()
	{
	  String marketName = "";
	  try 
	  {
		  Properties props = new Configuration().getSection("MEBS.marketInfo");
		  marketName = props.getProperty("marketName");
	  } 
	  catch (Exception e) 
	  {
		  e.printStackTrace();
	  }		

	  return marketName;
	}
%>
<%
	//取市场id,使用于key验证
	String marketId="";
	try
	{
		marketId=new Configuration().getSection("MEBS.key").getProperty("marketId");
	}catch(Exception e){}
%>
<script type="text/javascript" src="<%=basePath%>/public/validator.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/common/public/jslib/tools.js"></script>
<script language="javascript" src="<%=basePath%>/public/submitCount.js"></script>
<script type="text/javascript"
	src="<%=publicPath%>/formInit.js"></script>
<script language="javascript">
        var AUsessionId='${LOGINIDS}';
        var currenUserId='${sessionScope.CURRENUSERID}';
</script>
