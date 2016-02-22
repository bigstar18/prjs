<%@ page pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

		<s:set var="primaryMap" value="primaryMap()" />
		<table width="100%" border="0" bordercolor="#000000"
			style="border-collapse: collapse">
			<tr>
			   <td width="5%">
			      &nbsp;
			    </td>
			    <td width="15%" align="right" class="td_report">
			    	  机构编号：
			    </td>
			    <td width="10%"  align="left">
			      <s:property value="#primaryMap.organizationno" />
			    </td>
				<td align="right" width="15%" class="td_report">
					机构名称：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.organizationName" />
				</td>
				<td align="right" width="15%" align="center" class="td_report">
					开始日期：
				</td>
				<td align="left" width="15%">
					${oldParams["trunc(primary.clearDate)[>=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
			<tr>
			    <td width="5%">
			      &nbsp;
			    </td>
				 <td width="15%" align="right" class="td_report">
			    	  所属会员编号：
			    </td>
			    <td width="10%">
			      <s:property value="#primaryMap.memberno" />
			    </td>
				<td align="right" width="15%" class="td_report">
					会员名称：
				</td>
				<td align="left" width="15%">
					<s:property value="#primaryMap.memberName" />
				</td>
				<td align="right" width="15%" class="td_report">
					结束日期：
				</td>
				<td align="left" width="15%">
					${oldParams["trunc(primary.clearDate)[<=][date]"]}
				</td>
				<td width="10%">
			      &nbsp;
			    </td>
			</tr>
		</table>
