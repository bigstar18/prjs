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
					���ƣ�
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.organizationName" />
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td align="right" width="15%" align="center" class="td_report">
					��ʼ���ڣ�
				</td>
				<td align="left" width="15%">
					${oldParams["primary.atClearDate[>=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td colspan="2" width="30%">
					&nbsp;
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
