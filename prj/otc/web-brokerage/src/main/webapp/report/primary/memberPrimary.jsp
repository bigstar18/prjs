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
					��Ա���ƣ�
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
					��ʼ���ڣ�
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
					�����˻���
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
					��ֹ���ڣ�
				</td>
				<td align="left" width="15%">
					${oldParams["primary.atClearDate[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

