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
		alert("û��ָ������Ĳ�ѯ���ݣ�");
	</script>	
	<%
	reportName = "[***]";//��������str�����쳣
	}
	%>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
</head>
<body>
<table align="right" width="10%" border="0">
<tr>
<td align="right">
<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
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
	<center>û�пɲ�ѯ����...</center>
	<%
	}
	%>

<table align="right" width="10%" border="0">
<tr>
<td align="right">
<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
</div>
</td>
</tr>
</table>
</body>
</html>