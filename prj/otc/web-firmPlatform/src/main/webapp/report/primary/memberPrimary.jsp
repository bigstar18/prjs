<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse">
			<tr>
			    <td width="10%">&nbsp;</td>
				<td align="left" width="20%" class="td_report">
					会员名称：
					<s:property value="#primaryMap.memberName" />
				</td>
				<td align="left" width="20%" class="td_report">
					开始日期：
					${oldParams["primary.clearDate[>=][date]"]}
				</td>
				<td align="left" width="20%" class="td_report"> 
					会员编号：
					<s:property value="#primaryMap.memberNo" />
				</td>
				<td align="left" width="20%" class="td_report">
					结束日期：
					${oldParams["primary.clearDate[<=][date]"]}
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>

