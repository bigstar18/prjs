<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">天津贵金属交易所</div>
		<div class="report_w16_title">主次银行转账记录表</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="center">操作人：${username }</td>
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
				<td width="10%">
					序号
				</td>
				<td width="18%">
					转账记录号
				</td>
				<td width="18%">
					交易商名称
				</td>
				<td width="18%">
					银行
				</td>
				<td width="18%">
					目标银行
				</td>
				<td width="18%">
					金额
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.transId"/>&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.firmName" />&nbsp;
						
					</td>
					<td>
						<s:property value="#dataMap.bankName" />
						
					</td>
					<td>
						<s:property value="#dataMap.bankTargetName" />
						
					</td>
					
				</tr>
			</s:iterator>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
