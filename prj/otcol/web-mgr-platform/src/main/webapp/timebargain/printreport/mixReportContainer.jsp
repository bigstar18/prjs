<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
	<%
	String reportName = null;
	String sign = request.getParameter("sign");
	if(sign != null && !"".equals(sign)){
		reportName = sign;
	}else{
	%>
	<script type="text/javascript">
		alert("没有指定报表的查询内容！");
	</script>	
	<%
	reportName = "[***]";//给定特殊str避免异常
	}
	%>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
</head>
<body>
<table align="right" width="10%" border="0">
<tr>
<td align="right">
<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
     <input type="submit" onclick="javascript:window.print();" class="button" value="打印">
</div>
</td>
</tr>
</table>
	<%@ include file="marketName.jsp" %>
	<%
	if("bargainResult".equals(reportName.trim())){
	%>
	<%@ include file="bargainResult.jsp" %>
	<%
	}else if("zijinjiesuan".equals(reportName.trim())){
	%>
	<%@ include file="zijinjiesuan.jsp" %>
	<%
	}else if("transferProfitAndLoss".equals(reportName.trim())){
	%>
	<%@ include file="transferProfitAndLoss.jsp" %>
	<%
	}else if("indentCollect".equals(reportName.trim())){
	%>
	<%@ include file="indentCollect.jsp" %>
	<%
	}else{
	%>
	<center>没有可查询数据...</center>
	<%
	}
	%>

<table align="right" width="10%" border="0">
<tr>
<td align="right">
<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
     <input type="submit" onclick="javascript:window.print();" class="button" value="打印">
</div>
</td>
</tr>
</table>
</body>
</html>