<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	    <s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse" align="center">
			<tr>
			    <td width="10%">&nbsp;</td>
				<td width="20%" align="center" class="td_report">
					客户名称：<s:property value="#primaryMap.customerName" />
				</td>
				<td width="20%" align="center" class="td_report">
					起始日期：${oldParams["trunc(primary.clearDate)[>=][date]"]}
				</td>
				<td width="20%" align="center" class="td_report">
					交易账号：<s:property value="#primaryMap.customerNo" />&nbsp;
				</td>
				<td width="20%" align="center" class="td_report">
					终止日期：${oldParams["trunc(primary.clearDate)[<=][date]"]}
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>

