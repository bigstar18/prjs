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
					�Ӽ������ƣ�
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
					��ʼ���ڣ�
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
					�������ڣ�
				</td>
				<td align="left" width="15%">
					${oldParams["trunc(primary.atClearDate)[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

