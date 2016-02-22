<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
		<s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse">
			<tr>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					特别会员编号：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.s_memberno" />
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					开始日期：
				</td>
				<td align="left" width="15%">
					${oldParams["trunc(primary.ClearDate)[>=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					特别会员名称：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.name" />
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					结束日期：
				</td>
				<td align="left" width="15%">
					${oldParams["trunc(primary.ClearDate)[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

