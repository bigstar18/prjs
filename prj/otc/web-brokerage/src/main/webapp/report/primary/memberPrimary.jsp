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
					会员名称：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.memberName" />
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					起始日期：
				</td>
				<td align="left" width="15%">
					${oldParams["primary.atClearDate[>=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr height="30">
				<td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report"> 
					交易账户：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.customerNo" />
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" class="td_report">
					终止日期：
				</td>
				<td align="left" width="15%">
					${oldParams["primary.atClearDate[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

