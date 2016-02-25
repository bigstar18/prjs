<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@page import="gnnt.MEBS.base.query.OrderField"%>
<%@page import="gnnt.MEBS.base.query.PageInfo"%>
<%@ page import='java.util.*'%>
<%@ page import="java.text.*"%>
<%@ page import="gnnt.MEBS.common.security.*"%>
<%@ page import="gnnt.MEBS.vendue.util.*"%>
<%@ page isELIgnored="false"%>
<%@ include file="../../common/public/session.jsp" %>

<%
    String USERNAME = AclCtrl.getLogonID(request);		
	String contextPath= request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/entity/";

	String publicPath = basePath+"public/";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	String nowDate = sdf.format(new Date());
	pageContext.setAttribute("nowDate",nowDate);
	PageInfo pageInfo=(PageInfo)request.getAttribute("pageInfo");
    String orderFiled="";
    String orderType="";
    if(pageInfo!=null)
    {
    List listf=pageInfo.getOrderFields();
    if(listf!=null&&listf.size()>0)
    {
        OrderField order=(OrderField)listf.get(0);
        orderFiled=order.getOrderField();
        orderType=order.isOrderDesc()+"";
    }
    }
    pageContext.setAttribute("orderFiled", orderFiled);
    pageContext.setAttribute("orderType", orderType);
%>
	<base href="<%=basePath%>/">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	
	<!--<link rel="stylesheet" href="<%=publicPath%>/css/style.css" type="text/css"/>-->
	<script language="javascript" src="<%=publicPath%>/jstools/formInit.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/common.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/frameCtrl.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/tools.js"></script>
	<script language="javascript" src="<%=publicPath%>/jstools/jquery.js"></script>
	<script language="javascript">
		var sign=true;
		var basePath = "<%=basePath%>";
		<c:if test='${not empty resultMsg}'>
			alert('<c:out value="${resultMsg}"/>');
		</c:if>
	</script>
	