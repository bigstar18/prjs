<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<div class="report_w16_title">
			居间签约客户明细
		</div>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="70%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="left">签约开始时间：${oldParams["primary.opentime[>=][date]"]}</td>
	    <td align="left">签约结束时间：${oldParams["primary.opentime[<=][date]"]}</td>
	    <td align="left">操作人：${CURRENUSERID }</td>
	 	 </tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="0"></s:set>
			<tr class="report_w14">
				<td width="5%" >
					序号
				</td>
				<td width="8%" >
					居间编号
				</td>
				<td width="8%" >
					居间名称
				</td>
				<td width="8%" >
					交易账号
				</td>
				<td width="8%" >
					客户名称
				</td>
				<td width="10%" >
					客户状态
				</td>
				<td width="15%" >
					签约日期
				</td>
				<td width="15%" >
					签约银行
				</td>
				<td width="15%" >
					最后登录日期
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:property value="#dataMap.brokerageno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.brokeragename" />	
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />	
					</td>
					<td>
						<s:set var='status' value="#dataMap.status" />
						<s:property value="customerStatusMap[#status]" />
					</td>
					<td>
						<s:date name="#dataMap.opentime" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.bankname" />	
					</td>
					<td>
						<s:date name="#dataMap.logintime" format="yyyy-MM-dd"/>	
					</td>	
			</s:iterator>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
