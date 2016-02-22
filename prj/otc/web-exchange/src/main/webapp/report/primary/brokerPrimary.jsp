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
					居间人名称：
				</td>
				<td width="15%" align="left">
				   <s:property value="#primaryMap.brokerName" />
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
				<td width="15%" align="left">
					${oldParams["trunc(primary.atClearDate)[>=][date]"]}
				</td>
			    <td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			  <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" colspan="2" width="30%" class="td_report">
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
					${oldParams["trunc(primary.atClearDate)[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

