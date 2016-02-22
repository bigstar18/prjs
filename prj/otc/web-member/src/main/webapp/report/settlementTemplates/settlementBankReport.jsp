<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<div class="report_w16_titlemin">
		      贵金属交易所
		</div>
		<div class="report_w16_title">
			结算银行签约记录表
		</div>
		<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	 	 <tr>
	    <td align="center">操作人：${CURRENUSERID }</td>
	  <%   java.util.Date d = new java.util.Date(); 
			java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
			String datetime = dformat.format(d); 
	  %>
	   <td align="center">查询日期：<%=datetime%> </td>
	 	 </tr>
	 	 <tr><td height="10">&nbsp;</td></tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="begincapital" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="plsum" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="5%">
					序号
				</td>
				<td width="11%">
					编号
				</td>
				<td width="13%">
					虚拟账户
				</td>
				<td width="13%">
					名称
				</td>
				<td width="13%">
					签约银行
				</td>
				<td width="9%">
					签约日期
				</td>
				
				<td width="8%">
					签约时间
				</td>
				<td width="9%">
					解约日期
				</td>
				<td width="8%">
					解约时间
				</td>
				<td width="8%">
					账户类型
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.firmid"/>&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.contact" />
						
					</td>
					<td>
						<s:property value="#dataMap.name" />
						
					</td>
					<td>
						<s:property value="#dataMap.bankname" />
						
					</td>
					<td>
						<s:date name="#dataMap.signtime"  format="yyyy-MM-dd"></s:date>
					</td>
					
					<td>
						<s:date name="#dataMap.signtime"  format="HH:mm:ss"></s:date>
					</td>
					<td>
						<s:date name="#dataMap.cancletime"  format="yyyy-MM-dd"></s:date>
					</td>
					<td>
						<s:date name="#dataMap.cancletime"  format="HH:mm:ss"></s:date>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.firmtype"></s:set>
						<s:property value="bankMap[#mapKey]" />
					</td>
				</tr>
			</s:iterator>
		
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
