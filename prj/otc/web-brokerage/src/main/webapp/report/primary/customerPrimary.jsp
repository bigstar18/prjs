<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	    <s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse" align="center">
			<tr>
			    <td width="10%">
			      &nbsp;
			    </td>
				<td width="15%" align="right" class="td_report">
					����&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td width="15%" align="left"><s:property value="#primaryMap.customerName" /></td>
				</td>
				 <td  width="10%">
			     &nbsp;
			    </td>
			     <td  width="10%">
			     &nbsp;
			    </td>
				<td width="15%" align="right" class="td_report">
					��ʼ���ڣ�
				</td>
				<td width="15%" align="left">
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
				<td width="15%" align="right" class="td_report">
					�˻�&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td width="15%" align="left">
					<s:property value="#primaryMap.customerNo" />
				</td>
				<td  width="10%">
			     &nbsp;
			    </td>
			    <td  width="10%">
			     &nbsp;
			    </td>
				<td width="30%" colspan="2">
			      &nbsp;
			    </td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			   <td width="10%">
			      &nbsp;
			    </td>
				<td width="15%" align="right" class="td_report">
					����������
				</td>
				<td width="15%" align="left">
				   <s:property value="#primaryMap.organizationName" />
				</td>
				<td  width="10%">
			     &nbsp;
			    </td>
			    <td  width="10%">
			     &nbsp;
			    </td>
				<td width="15%" align="right" class="td_report">
					��ֹ���ڣ�
				</td>
				<td width="15%" align="left">
					${oldParams["primary.atClearDate[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			  <td width="10%">
			      &nbsp;
			    </td>
				<td width="15%" align="right" class="td_report">
					�������ͣ�
				</td>
				<td width="15%" align="left">
				    <s:property value="#primaryMap.brokerName" />
				</td>
				<td  width="10%">
			     &nbsp;
			    </td>
			    <td  width="10%">
			     &nbsp;
			    </td>
				<td width="30%" align="left" colspan="2">
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>

