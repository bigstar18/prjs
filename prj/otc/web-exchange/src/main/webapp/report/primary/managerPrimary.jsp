<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="primaryMap" value="primaryMap()" />
<table width="100%" border="0" bordercolor="#000000"
	style="border-collapse: collapse">
	<tr>
		<td width="10%">
			&nbsp;
		</td>
		<td width="10%" align="right" class="td_report">
			客户代表编号：
		</td>
		<td width="10%" align="left">
			<s:property value="#primaryMap.managerno" />
		</td>
		<td align="right" width="10%" class="td_report">
			客户代表名称：
		</td>
		<td align="left" width="10%">
			<s:property value="#primaryMap.managerName" />
		</td>
		<td align="right" width="10%" class="td_report">
			所属机构：
		</td>
		<td align="left" width="10%">
			<s:property value="#primaryMap.organizationName" />
		</td>
		<td align="right" width="10%" align="center" class="td_report">
			起始日期：
		</td>
		<td align="left" width="10%">
			${oldParams["trunc(primary.clearDate)[>=][date]"]}
		</td>
		<td width="10%">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
		<td align="right" class="td_report">
			会员编号：
		</td>
		<td>
			<s:property value="#primaryMap.memberno" />
		</td>
		<td align="right" class="td_report">
			会员名称：
		</td>
		<td align="left">
			<s:property value="#primaryMap.memberName" />
		</td>
		<td align="left" colspan="2">
			&nbsp;
		</td>
		<td align="right" class="td_report">
			终止日期：
		</td>
		<td align="left">
			${oldParams["trunc(primary.clearDate)[<=][date]"]}
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
</table>

