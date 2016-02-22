<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">结算银行签约记录表</div>
<table width="120%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="40%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="center">操作人：${CURRENUSERID }</td>
	  <%   java.util.Date d = new java.util.Date(); 
			java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
			String datetime = dformat.format(d); 
	  %>
	   <td align="center">查询日期：<%=datetime%> </td>
	 	 </tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="begincapital" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="plsum" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="12%">
					编号
				</td>
				<td width="15%">
					交易账号
				</td>
				<td width="12%">
					名称
				</td>
				<td width="12%">
					签约银行
				</td>
				<td width="17%">
					签约时间
				</td>
				<td width="17%">
					解约时间
				</td>
				<td width="12%">
					账户类型
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.firmid"/>&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.contact" />&nbsp;
						
					</td>
					<td>
						<s:property value="#dataMap.name" />
						
					</td>
					<td>
						<s:property value="#dataMap.bankname" />
						
					</td>
					
					<td>
						<s:date name="#dataMap.signtime"  format="yyyy-MM-dd HH:mm:ss"></s:date>
					</td>
					<td>
						<s:date name="#dataMap.cancletime"  format="yyyy-MM-dd HH:mm:ss"></s:date>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.firmtype"></s:set>
						<s:property value="bankMap[#mapKey]" />
					</td>
				</tr>
			</s:iterator>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
