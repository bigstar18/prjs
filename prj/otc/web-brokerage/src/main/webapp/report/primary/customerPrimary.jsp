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
					名称&nbsp;&nbsp;&nbsp;&nbsp;：
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
					起始日期：
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
					账户&nbsp;&nbsp;&nbsp;&nbsp;：
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
					所属机构：
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
					终止日期：
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
					所属经纪：
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

