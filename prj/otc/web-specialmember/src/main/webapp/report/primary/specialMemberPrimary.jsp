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
					�ر��Ա��ţ�<s:property value="#primaryMap.s_memberno" />
				</td>
				<td align="center" width="22%" class="td_report">
					��ʼ���ڣ�${oldParams["trunc(primary.ClearDate)[>=][date]"]}
				</td>
				<td align="center" width="22%" class="td_report">
					�ر��Ա���ƣ�<s:property value="#primaryMap.name" />
				</td>
				<td align="center" width="22%" class="td_report">
					��ֹ���ڣ�${oldParams["trunc(primary.ClearDate)[<=][date]"]}
				</td>
				<td width="6%">
			      &nbsp;
			    </td>
			</tr>
		</table>

