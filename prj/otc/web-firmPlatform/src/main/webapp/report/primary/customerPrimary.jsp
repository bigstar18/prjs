<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	    <s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse" align="center">
			<tr>
			    <td width="10%">&nbsp;</td>
				<td width="20%" align="right" class="td_report">
					�����˺ţ�
					<s:property value="#primaryMap.customerNo" />
				</td>
				<td width="20%" align="right" class="td_report">
					�ͻ����ƣ�
					<s:property value="#primaryMap.customerName" />
				</td>
				<td width="20%" align="right" class="td_report">
					��ʼ���ڣ�
					${oldParams["primary.clearDate[>=][date]"]}
				</td>
				<td width="20%" align="right" class="td_report">
			               �������ڣ�
					${oldParams["primary.clearDate[<=][date]"]}
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>

