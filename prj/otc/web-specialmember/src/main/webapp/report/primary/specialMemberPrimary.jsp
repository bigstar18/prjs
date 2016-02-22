<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
		<s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse">
			<tr>
			    <td width="6%">
			      &nbsp;
			    </td>
				<td align="center" width="22%" class="td_report">
					特别会员编号：<s:property value="#primaryMap.s_memberno" />
				</td>
				<td align="center" width="22%" class="td_report">
					起始日期：${oldParams["trunc(primary.ClearDate)[>=][date]"]}
				</td>
				<td align="center" width="22%" class="td_report">
					特别会员名称：<s:property value="#primaryMap.name" />
				</td>
				<td align="center" width="22%" class="td_report">
					终止日期：${oldParams["trunc(primary.ClearDate)[<=][date]"]}
				</td>
				<td width="6%">
			      &nbsp;
			    </td>
			</tr>
		</table>

