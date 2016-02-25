<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.ecside.org" prefix="ec" %>

<%@ include file="../../common/public/choSkin.jsp"%>
<link rel="stylesheet" href="<%=skinPathForTimebargain%>/common.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=escideskinPathForTimebargain%>/ecside_style.css"/>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/common";
pageContext.setAttribute("basePath", basePath);
%>
<script language="javascript" src="<%=basePath%>/timebargain/scripts/global.js"></script>
<script type="text/javascript">
  function checkMoneyForSettle(obj,msg)
	{
		//var money = obj.value;
		var money = document.getElementById(obj).value;
		if(money!='' &&(money.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(money) == parseFloat(0)))
		{
			alert("请输入一个非0数字！");
	        document.getElementById(obj).value = "";
	        document.getElementById(obj).focus();
		}
		else
		{
			var thismoney = frm.thisPayMent.value;
			var percentMoney = (frm.totalMoney.value)*0.01*(frm.percent.value);
			
			if(percentMoney!='')
			{
			  thismoney = percentMoney;
			  frm.thisPayMent.value = percentMoney.toFixed(2);
			}
		}
	}
</script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html:xhtml />
