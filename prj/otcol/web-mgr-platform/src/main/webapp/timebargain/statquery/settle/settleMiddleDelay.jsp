<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<html>
<script type="text/javascript">
	function window_onload(url){
		window.location = url;
	}
</script>
<%
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	String sql = "select t.issettle issettle from M_TradeModule t where t.moduleid = '2'";
	List list = dao.queryBySQL(sql);
	String isSettle = "";
	if (list != null && list.size() > 0) {
		Map map = (Map)list.get(0);
		isSettle = map.get("isSettle").toString();
	}
	String url = "";
	if ("Y".equals(isSettle)) {
		url = "settleDelayPattern/settle.jsp";
	}else if ("N".equals(isSettle)) {
		url = "settleDelay/settle.jsp";
	}
%>
<body onload="window_onload('<%=url %>')">
	
</body>
</html>
